package com.yandex.div.core.expression.local

import com.yandex.div.core.Div2Logger
import com.yandex.div.core.DivViewFacade
import com.yandex.div.core.expression.ExpressionResolverImpl
import com.yandex.div.core.expression.ExpressionsRuntime
import com.yandex.div.core.expression.triggers.TriggersController
import com.yandex.div.core.expression.variables.VariableControllerImpl
import com.yandex.div.core.view2.divs.DivActionBinder
import com.yandex.div.core.view2.errors.ErrorCollector
import com.yandex.div.data.Variable
import com.yandex.div.evaluable.EvaluationContext
import com.yandex.div.evaluable.Evaluator
import com.yandex.div.internal.Assert
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.DivBase
import com.yandex.div2.DivTrigger

private const val ERROR_UNKNOWN_RESOLVER =
    "ExpressionResolverImpl didn't call RuntimeStore#putRuntime on create."
internal const val ERROR_ROOT_RUNTIME_NOT_SPECIFIED = "Root runtime is not specified."
private const val WARNING_LOCAL_USING_LOCAL_VARIABLES =
    "You are using local variables. Please ensure that all elements that use local variables " +
    "and all of their parents recursively have an 'id' attribute."


internal class RuntimeStore(
    private val evaluator: Evaluator,
    private val errorCollector: ErrorCollector,
    private val div2Logger: Div2Logger,
    private val divActionBinder: DivActionBinder,
) {
    private var warningShown = false
    private val pathToRuntimes = mutableMapOf<String, ExpressionsRuntime?>()
    private val resolverToRuntime = mutableMapOf<ExpressionResolver, ExpressionsRuntime?>()
    private val allRuntimes = mutableSetOf<ExpressionsRuntime>()

    internal var rootRuntime: ExpressionsRuntime? = null
        set(value) {
            field = value
            field?.let { putRuntime(it) }
        }

    private val onCreateCallback by lazy {
        ExpressionResolverImpl.OnCreateCallback { resolver, variableController ->
            ExpressionsRuntime(resolver, variableController, null, this).also {
                /**
                 * we cannot provide path here, otherwise descendants of ExpressionResolver will
                 * receive the same callback and override runtime for provided path.
                 */
                putRuntime(runtime = it)
            }
        }
    }

    internal fun showWarningIfNeeded(child: DivBase) {
        if (!warningShown && child.variables != null) {
            warningShown = true
            errorCollector.logWarning(Throwable(WARNING_LOCAL_USING_LOCAL_VARIABLES))
        }
    }

    /**
     * Returns runtime if it have been store before, otherwise creates new runtime using
     * @param parentRuntime or @param parentResolver.
     *
     * NOTE: Always provide parentResolver or parentRuntime.
     * Otherwise, if runtime wasn't created it will be created using rootRuntime
     */
    internal fun getOrCreateRuntime(
        path: String,
        variables: List<Variable>? = null,
        triggers: List<DivTrigger>? = null,
        parentResolver: ExpressionResolver? = null,
        parentRuntime: ExpressionsRuntime? = null,
    ) = pathToRuntimes[path] ?: getRuntimeOrCreateChild(
        path, variables, triggers,null, parentResolver, parentRuntime
    )

    internal fun getRuntimeWithOrNull(resolver: ExpressionResolver) = resolverToRuntime[resolver]

    internal fun putRuntime(runtime: ExpressionsRuntime, path: String? = null) {
        resolverToRuntime[runtime.expressionResolver] = runtime
        allRuntimes.add(runtime)

        if (path != null) {
            pathToRuntimes[path] = runtime
            runtime.updateSubscriptions()
        }
    }

    internal fun resolveRuntimeWith(
        path: String,
        variables: List<Variable>?,
        triggers: List<DivTrigger>?,
        resolver: ExpressionResolver,
    ): ExpressionsRuntime? {
        val runtimeForPath = pathToRuntimes[path]
        if (resolver == runtimeForPath?.expressionResolver) return runtimeForPath

        val existingRuntime = getRuntimeWithOrNull(resolver) ?: run {
            reportError(ERROR_UNKNOWN_RESOLVER)
            return null
        }

        if (runtimeForPath != null) removeRuntimesFor(path)
        return getRuntimeOrCreateChild(path, variables, triggers, existingRuntime)
    }

    internal fun cleanup() {
        warningShown = false
        allRuntimes.forEach { it.cleanup() }
    }

    internal fun updateSubscriptions() =
        allRuntimes.forEach { it.updateSubscriptions() }

    internal fun onAttachedToWindow(view: DivViewFacade) =
        allRuntimes.forEach { it.onAttachedToWindow(view) }

    internal fun clearBindings() = allRuntimes.forEach { it.clearBinding() }

    internal fun getUniquePathsAndRuntimes(): List<Pair<String, ExpressionsRuntime>> {
        return pathToRuntimes.mapNotNull { (path, runtime) ->
            runtime?.let { path to it }
        }
    }

    private fun reportError(message: String) {
        Assert.fail(message)
        errorCollector.logError(AssertionError(message))
    }

    private fun createChildRuntime(
        parentRuntime: ExpressionsRuntime,
        path: String,
        variables: List<Variable>?,
        variablesTriggers: List<DivTrigger>?,
    ): ExpressionsRuntime {
        val localVariableController = VariableControllerImpl(parentRuntime.variableController)
        variables?.forEach { localVariableController.declare(it) }

        val evaluationContext = EvaluationContext(
            variableProvider = localVariableController,
            storedValueProvider = evaluator.evaluationContext.storedValueProvider,
            functionProvider = evaluator.evaluationContext.functionProvider,
            warningSender = evaluator.evaluationContext.warningSender
        )

        val evaluator = Evaluator(evaluationContext)
        val resolver = ExpressionResolverImpl(
            variableController = localVariableController,
            evaluator = evaluator,
            errorCollector = errorCollector,
            onCreateCallback = onCreateCallback,
        )

        val triggerController = if (variablesTriggers == null) null else TriggersController(
            localVariableController,
            resolver,
            evaluator,
            errorCollector,
            div2Logger,
            divActionBinder
        ).apply {
            ensureTriggersSynced(variablesTriggers)
        }

        return ExpressionsRuntime(
            resolver, localVariableController, triggerController, parentRuntime.runtimeStore
        ).also {
            putRuntime(it, path)
        }
    }

    private fun removeRuntimesFor(path: String) {
        pathToRuntimes[path]?.let {
            pathToRuntimes.entries.filter { (key, _) -> key.contains(path) }.forEach { (key, value) ->
                pathToRuntimes.remove(key)
                resolverToRuntime.remove(value?.expressionResolver)
            }
        }
    }

    private fun getRuntimeOrCreateChild(
        path: String,
        variables: List<Variable>?,
        variablesTriggers: List<DivTrigger>?,
        existingRuntime: ExpressionsRuntime? = null,
        parentResolver: ExpressionResolver? = null,
        parentRuntime: ExpressionsRuntime? = null,
    ): ExpressionsRuntime? {
        val runtime = existingRuntime
            ?: parentRuntime
            ?: parentResolver?.let { getRuntimeWithOrNull(it) }
            ?: rootRuntime
            ?: run {
                reportError(ERROR_ROOT_RUNTIME_NOT_SPECIFIED)
                return null
            }

        return if (variables.isNullOrEmpty() && variablesTriggers.isNullOrEmpty()) {
            runtime.also {
                pathToRuntimes[path] = it
                runtime.updateSubscriptions()
            }
        } else {
            createChildRuntime(runtime, path, variables, variablesTriggers)
        }
    }
}

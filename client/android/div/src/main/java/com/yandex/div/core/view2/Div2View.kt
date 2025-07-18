package com.yandex.div.core.view2

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Canvas
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.annotation.VisibleForTesting
import androidx.core.view.ViewCompat
import androidx.core.view.doOnAttach
import androidx.core.view.isVisible
import androidx.transition.Scene
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.yandex.div.DivDataTag
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivActionHandler.DivActionReason
import com.yandex.div.core.DivCreationTracker
import com.yandex.div.core.DivCustomContainerChildFactory
import com.yandex.div.core.DivKit
import com.yandex.div.core.DivViewConfig
import com.yandex.div.core.DivViewFacade
import com.yandex.div.core.ObserverList
import com.yandex.div.core.actions.logError
import com.yandex.div.core.annotations.Mockable
import com.yandex.div.core.dagger.Div2Component
import com.yandex.div.core.dagger.Div2ViewComponent
import com.yandex.div.core.downloader.DivDataChangedObserver
import com.yandex.div.core.downloader.DivPatchApply
import com.yandex.div.core.downloader.PersistentDivDataObserver
import com.yandex.div.core.expression.local.RuntimeStore
import com.yandex.div.core.expression.local.RuntimeStoreImpl
import com.yandex.div.core.expression.suppressExpressionErrors
import com.yandex.div.core.images.LoadReference
import com.yandex.div.core.player.DivVideoActionHandler
import com.yandex.div.core.state.DivStatePath
import com.yandex.div.core.state.DivViewState
import com.yandex.div.core.state.StateConflictException
import com.yandex.div.core.timer.DivTimerEventDispatcher
import com.yandex.div.core.tooltip.DivTooltipController
import com.yandex.div.core.util.SingleTimeOnAttachCallback
import com.yandex.div.core.util.walk
import com.yandex.div.core.view2.animations.DivComparator
import com.yandex.div.core.view2.animations.DivTransitionHandler
import com.yandex.div.core.view2.animations.SceneRootWatcher
import com.yandex.div.core.view2.animations.allowsTransitionsOnDataChange
import com.yandex.div.core.view2.animations.doOnEnd
import com.yandex.div.core.view2.divs.DivLayoutProviderVariablesHolder
import com.yandex.div.core.view2.divs.bindLayoutParams
import com.yandex.div.core.view2.divs.bindingContext
import com.yandex.div.core.view2.divs.clearFocusOnClick
import com.yandex.div.core.view2.divs.drawShadow
import com.yandex.div.core.view2.divs.widgets.DivAnimator
import com.yandex.div.core.view2.divs.widgets.MediaReleaseViewVisitor
import com.yandex.div.core.view2.divs.widgets.ReleaseUtils.releaseAndRemoveChildren
import com.yandex.div.core.view2.divs.widgets.ReleaseUtils.releaseChildren
import com.yandex.div.core.view2.divs.widgets.ReleaseUtils.releaseMedia
import com.yandex.div.core.view2.divs.widgets.ReleaseViewVisitor
import com.yandex.div.core.view2.logging.bind.BindingEventReporterProvider
import com.yandex.div.core.view2.logging.bind.ForceRebindReporter
import com.yandex.div.core.view2.logging.bind.SimpleRebindReporter
import com.yandex.div.core.view2.logging.patch.PatchEventReporter
import com.yandex.div.core.view2.logging.patch.PatchEventReporterProvider
import com.yandex.div.core.view2.reuse.ComplexRebindReporter
import com.yandex.div.core.view2.reuse.RebindTask
import com.yandex.div.core.view2.reuse.ReusableTokenList
import com.yandex.div.data.VariableMutationException
import com.yandex.div.histogram.Div2ViewHistogramReporter
import com.yandex.div.histogram.HistogramCallType
import com.yandex.div.internal.Assert
import com.yandex.div.internal.KAssert
import com.yandex.div.internal.KLog
import com.yandex.div.internal.core.DivItemBuilderResult
import com.yandex.div.internal.core.VariableMutationHandler
import com.yandex.div.internal.util.hasScrollableChildUnder
import com.yandex.div.internal.util.immutableCopy
import com.yandex.div.internal.widget.FrameContainerLayout
import com.yandex.div.internal.widget.menu.OverflowMenuSubscriber
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div.util.INVALID_STATE_ID
import com.yandex.div.util.getInitialStateId
import com.yandex.div2.Div
import com.yandex.div2.DivAction
import com.yandex.div2.DivData
import com.yandex.div2.DivPatch
import com.yandex.div2.DivTransitionSelector
import java.util.UUID
import java.util.WeakHashMap
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

/**
 * Main entry point for building Div2s
 */
@SuppressLint("ViewConstructor")
@Mockable
class Div2View private constructor(
    internal val context: Div2Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val constructorCallTime: Long,
) : FrameContainerLayout(context, attrs, defStyleAttr), DivViewFacade {

    internal val div2Component: Div2Component = context.div2Component
    internal val viewComponent: Div2ViewComponent = div2Component.viewComponent()
        .divView(this)
        .build()

    private val bindOnAttachEnabled = div2Component.isBindOnAttachEnabled
    private val complexRebindEnabled = div2Component.isComplexRebindEnabled

    private val bindingProvider: ViewBindingProvider = viewComponent.bindingProvider

    private val bindingReporterProvider = BindingEventReporterProvider(this)
    private val patchReporterProvider = PatchEventReporterProvider(this)

    private val divBuilder: Div2Builder = context.div2Component.div2Builder
    private val loadReferences = mutableListOf<LoadReference>()
    private val overflowMenuListeners = mutableListOf<OverflowMenuSubscriber.Listener>()
    private val divDataChangedObservers = mutableListOf<DivDataChangedObserver>()
    private val persistentDivDataObservers = ObserverList<PersistentDivDataObserver>()
    private val viewToDivBindings = WeakHashMap<View, Div>()
    private val bulkActionsHandler = BulkActionHandler()
    private val divVideoActionHandler: DivVideoActionHandler
        get() = div2Component.divVideoActionHandler
    private val tooltipController: DivTooltipController
        get() = div2Component.tooltipController
    internal val releaseViewVisitor: ReleaseViewVisitor
        get() = viewComponent.releaseViewVisitor
    internal val mediaReleaseViewVisitor: MediaReleaseViewVisitor
        get() = viewComponent.mediaReleaseViewVisitor
    private var oldRuntimeStore: RuntimeStore? = null
    internal val oldExpressionResolver: ExpressionResolver
        get() = oldRuntimeStore.resolver
    internal var runtimeStore: RuntimeStore = RuntimeStore.EMPTY
    internal var inMiddleOfBind = false

    internal var bindingContext: BindingContext = BindingContext(this, ExpressionResolver.EMPTY)

    internal var divTimerEventDispatcher: DivTimerEventDispatcher? = null

    @PublishedApi
    internal var forceCanvasClipping: Boolean = false

    private val monitor = Any()

    @VisibleForTesting
    internal var bindOnAttachRunnable: SingleTimeOnAttachCallback? = null
    private var reportBindingResumedRunnable: SingleTimeOnAttachCallback? = null
    private var reportBindingFinishedRunnable: SingleTimeOnAttachCallback? = null

    @VisibleForTesting
    internal var stateId = DivData.INVALID_STATE_ID
    private var config = DivViewConfig.DEFAULT

    private var rebindTask: RebindTask? = null
    internal val currentRebindReusableList: ReusableTokenList?
        get() {
            if (!complexRebindInProgress) return null

            return rebindTask?.reusableList
        }
    internal val complexRebindInProgress: Boolean
        get() = rebindTask?.rebindInProgress ?: false

    private val renderConfig = {
        DivKit.getInstance(context).component.histogramRecordConfiguration.renderConfiguration.get()
    }
    private val histogramReporter by lazy(LazyThreadSafetyMode.NONE) {
        Div2ViewHistogramReporter(
            { div2Component.histogramReporter },
            renderConfig
        )
    }

    private val gestureDetector = GestureDetector(
        this.context,
        object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                clearFocusOnClick(inputFocusTracker)
                return true
            }
        },
        Handler(Looper.getMainLooper())
)

    internal val inputFocusTracker = viewComponent.inputFocusTracker

    internal val layoutSizes = mutableMapOf<ExpressionResolver, MutableMap<String, Int>>()
    internal val variablesHolders = mutableMapOf<DivData, DivLayoutProviderVariablesHolder>()
    internal var clearVariablesListener: ViewTreeObserver.OnPreDrawListener? = null

    var dataTag: DivDataTag = DivDataTag.INVALID
        internal set(value) {
            prevDataTag = field
            field = value
            bindingProvider.update(field, divData)
        }

    var prevDataTag: DivDataTag = DivDataTag.INVALID
        internal set

    var divData: DivData? = null
        internal set(value) {
            field = value
            updateRuntimeStore()
            updateTimers()
            bindingProvider.update(dataTag, field)
        }

    private fun updateRuntimeStore(data: DivData? = divData, tag: DivDataTag = dataTag) {
        data ?: return
        oldRuntimeStore = runtimeStore
        runtimeStore = div2Component.runtimeStoreProvider.getOrCreate(tag, data, this)
        runtimeStore.updateSubscriptions()
        if (oldRuntimeStore != runtimeStore) {
            oldRuntimeStore?.clearBindings(this)
        }
        bindingContext = BindingContext(this, expressionResolver)
    }

    private fun tryAttachVariableTriggers(data: DivData?) {
        if (bindOnAttachEnabled && !view.isAttachedToWindow) {
            return
        }

        val state = data?.state() ?: return
        viewComponent.runtimeVisitor.createAndAttachRuntimes(
            state.div, DivStatePath.fromState(state), this
        )
    }

    private fun updateTimers() {
        val data = divData ?: return

        val newDivTimerEventDispatcher = div2Component.divTimersControllerProvider
            .getOrCreate(dataTag, data, expressionResolver)

        if (divTimerEventDispatcher != newDivTimerEventDispatcher) {
            divTimerEventDispatcher?.onDetach(this)
        }

        divTimerEventDispatcher = newDivTimerEventDispatcher

        newDivTimerEventDispatcher?.onAttach(this)
    }

    val logId: String
        get() = divData?.logId ?: ""

    var actionHandler: DivActionHandler? = null

    /**
     * Name of component that uses [Div2View].
     */
    var componentName: String?
        get() = histogramReporter.component
        set(value) {
            histogramReporter.component = value
        }

    private var timeCreated: Long = -1

    @HistogramCallType
    private val viewCreateCallType: String = div2Component.divCreationTracker.viewCreateCallType

    private var drawWasSkipped = true

    internal val divTransitionHandler = DivTransitionHandler(this)

    init {
        timeCreated = DivCreationTracker.currentUptimeMillis
        div2Component.releaseManager.observeDivLifecycle(this)
    }

    @JvmOverloads
    constructor(context: Div2Context,
                attrs: AttributeSet? = null,
                defStyleAttr: Int = 0
    ) : this(context, attrs, defStyleAttr, SystemClock.uptimeMillis())

    fun setData(
        data: DivData?,
        tag: DivDataTag
    ) = setData(data, divData, tag)

    fun setData(
        data: DivData?,
        oldDivData: DivData?,
        tag: DivDataTag
    ): Boolean = synchronized(monitor) {
        val reporter = bindingReporterProvider.get(oldDivData, data)

        if (data == null) {
            reporter.onBindingFatalNoData()
            return false
        } else if (divData === data) {
            reporter.onBindingFatalSameData()
            return false
        }

        notifyBindStarted()
        bindOnAttachRunnable?.cancel()

        histogramReporter.onRenderStarted()

        val oldData = divData ?: oldDivData
        updateRuntimeStore(data, tag)
        dataTag = tag

        data.states.forEach {
            div2Component.divViewDataPreloader.preload(it.div, bindingContext, DivStatePath.fromState(it))
        }

        val isDataReplaceable = DivComparator.isDivDataReplaceable(
            oldData,
            data,
            stateId,
            oldExpressionResolver,
            expressionResolver,
            reporter
        )

        val result = when {
            oldData == null || data.allowsTransitionsOnDataChange(expressionResolver) -> {
                updateNow(data, tag, reporter)
            }
            !isDataReplaceable
                && complexRebindEnabled
                && view.getChildAt(0) is ViewGroup
                && complexRebind(data, oldData, reporter)
            -> {
                false
            }
            isDataReplaceable -> {
                rebind(data, false, reporter)
                false
            }
            else -> updateNow(data, tag, reporter)
        }
        div2Component.divBinder.attachIndicators()

        sendCreationHistograms()
        notifyBindEnded()
        return result
    }

    fun setDataWithStates(
        data: DivData?,
        tag: DivDataTag,
        paths: List<DivStatePath>,
        temporary: Boolean
    ): Boolean = synchronized(monitor) {
        val reporter = bindingReporterProvider.get(divData, data)

        if (data == null) {
            reporter.onBindingFatalNoData()
            return false
        } else if (divData === data) {
            reporter.onBindingFatalSameData()
            return false
        }
        notifyBindStarted()
        bindOnAttachRunnable?.cancel()

        histogramReporter.onRenderStarted()

        val oldData = divData
        updateRuntimeStore(data, tag)
        val isDataReplaceable = DivComparator.isDivDataReplaceable(
            oldData,
            data,
            stateId,
            oldExpressionResolver,
            expressionResolver,
            reporter
        )
        dataTag = tag

        data.states.forEach {
            div2Component.divViewDataPreloader.preload(it.div, bindingContext, DivStatePath.fromState(it))
        }
        paths.forEach { path ->
            div2Component.stateManager.updateStates(divTag.id, path, temporary)
        }
        val result = when {
            oldData == null -> updateNow(data, tag, reporter)
            !isDataReplaceable
                && complexRebindEnabled
                && view.getChildAt(0) is ViewGroup
                && complexRebind(data, oldData, reporter)
            -> {
                true
            }
            isDataReplaceable -> {
                rebind(data, false, reporter)
                true
            }
            else -> updateNow(data, tag, reporter)
        }
        div2Component.divBinder.attachIndicators()
        sendCreationHistograms()
        notifyBindEnded()
        return result
    }

    private fun notifyBindStarted() {
        if (inMiddleOfBind) {
            logError(RuntimeException("New binding started when previous not ended!"))
        }
        inMiddleOfBind = true
        persistentDivDataObservers.forEach { it.onBeforeDivDataChanged() }
    }

    private fun notifyBindEnded() {
        inMiddleOfBind = false
        persistentDivDataObservers.forEach { it.onAfterDivDataChanged() }
    }

    fun applyPatch(patch: DivPatch): Boolean = synchronized(monitor) {
        val oldData: DivData = divData ?: return false
        val newDivData = div2Component.patchManager.createPatchedDivData(oldData, dataTag, patch, expressionResolver)
        val reporter = patchReporterProvider.get(patch)

        if (newDivData != null && tryApplyPatch(patch, oldData, newDivData, reporter)) {
            div2Component.patchManager.removePatch(dataTag)
            divDataChangedObservers.forEach { it.onDivPatchApplied(newDivData) }
            tryAttachVariableTriggers(newDivData)
            div2Component.divBinder.attachIndicators()
            reporter.onPatchSuccess()
            div2Component.actionBinder
                .handleActions(this, expressionResolver, patch.onAppliedActions, DivActionReason.PATCH)
            return true
        }

        div2Component.actionBinder
            .handleActions(this, expressionResolver, patch.onFailedActions, DivActionReason.PATCH)
        reporter.onPatchNoState()
        return false
    }

    private fun tryApplyPatch(
        patch: DivPatch,
        oldData: DivData,
        newDivData: DivData,
        reporter: PatchEventReporter,
    ): Boolean {
        val state = newDivData.stateToBind ?: return false

        bindOnAttachRunnable?.cancel()
        val oldRootDiv = oldData.state()?.div
        val rootChanges = patch.changes.find { it.id == oldRootDiv?.value()?.id } ?: run {
            rebind(oldData, false, reporter)
            divData = newDivData
            div2Component.divBinder.setDataWithoutBinding(bindingContext, getChildAt(0), state.div)
            return true
        }

        val items = rootChanges.items
        val newRootDiv = when {
            items.isNullOrEmpty() -> {
                KLog.e(DivPatchApply.TAG) { "Unable to patch root div because there is no div in patch." }
                return false
            }
            items.size > 1 -> {
                KLog.e(DivPatchApply.TAG) { "More than 1 div in patch for root div. The first was applied." }
                items[0]
            }
            else -> items[0]
        }

        val bindingReporter = bindingReporterProvider.get(newDivData, oldData)
        val isDataReplaceable = DivComparator.areDivsReplaceable(
            oldRootDiv,
            newRootDiv,
            expressionResolver,
            expressionResolver,
            bindingReporter
        )
        return when {
            !isDataReplaceable && complexRebindEnabled && view.getChildAt(0) is ViewGroup &&
                complexRebind(newDivData, oldData, bindingReporter) -> true
            isDataReplaceable -> {
                rebind(newDivData, false, reporter)
                true
            }
            else -> updateNow(newDivData, dataTag, reporter)
        }
    }

    private fun updateNow(data: DivData, tag: DivDataTag, reporter: ForceRebindReporter): Boolean {
        val oldData = divData
        if (oldData == null) {
            histogramReporter.onBindingStarted()
        } else {
            histogramReporter.onRebindingStarted()
        }

        cleanup(removeChildren = false)

        dataTag = tag
        divData = data

        val result = switchToDivData(oldData, data, reporter)

        tryAttachVariableTriggers(data)

        if (oldData != null) {
            histogramReporter.onRebindingFinished()
            return result
        }

        if (!bindOnAttachEnabled) {
            histogramReporter.onBindingFinished()
            return result
        }

        histogramReporter.onBindingPaused()
        reportBindingResumedRunnable = SingleTimeOnAttachCallback(this) {
            histogramReporter.onBindingResumed()
        }
        reportBindingFinishedRunnable = SingleTimeOnAttachCallback(this) {
            histogramReporter.onBindingFinished()
        }
        return result
    }

    fun tryLogVisibility() {
        val state = divData?.states?.firstOrNull { it.stateId == stateId }
        state?.let { trackStateVisibility(it) }
        trackChildrenVisibility()
    }

    /**
     * Canceling visibility tracking.
     * */
    fun discardVisibilityTracking() {
        val state = divData?.states?.firstOrNull { it.stateId == stateId }
        state?.let { discardStateVisibility(it) }
        discardChildrenVisibility()
    }

    private fun trackStateVisibility(state: DivData.State) {
        div2Component.visibilityActionTracker.trackVisibilityActionsOf(this, expressionResolver, view, state.div)
    }

    private fun discardStateVisibility(state: DivData.State) {
        div2Component.visibilityActionTracker.trackVisibilityActionsOf(
            scope = this,
            resolver = expressionResolver,
            view = null,
            div = state.div
        )
    }

    fun trackChildrenVisibility() {
        val visibilityActionTracker = div2Component.visibilityActionTracker
        viewToDivBindings.forEach { (view, div) ->
            view.bindingContext?.expressionResolver?.let {
                if (ViewCompat.isAttachedToWindow(view)) {
                    visibilityActionTracker.trackVisibilityActionsOf(this, it, view, div)
                } else {
                    visibilityActionTracker.trackVisibilityActionsOf(this, it, null, div)
                }
            }
        }
    }

    private fun discardChildrenVisibility() {
        val visibilityActionTracker = div2Component.visibilityActionTracker
        viewToDivBindings.forEach { (view, div) ->
            view.bindingContext?.expressionResolver?.let {
                visibilityActionTracker.trackVisibilityActionsOf(this, it, null, div)
            }
        }
    }

    internal fun getCustomContainerChildFactory(): DivCustomContainerChildFactory {
        return div2Component.divCustomContainerChildFactory
    }

    private fun sendCreationHistograms() {
        if (timeCreated < 0) {
            return
        }
        div2Component.divCreationTracker.sendHistograms(
            constructorCallTime, timeCreated, div2Component.histogramReporter, viewCreateCallType
        )
        timeCreated = -1
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (inputFocusTracker.isFocusedOnInput()) {
            gestureDetector.onTouchEvent(event)
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Capturing DOWN event to get UP event, but only if actively focused on input
                 return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        histogramReporter.onLayoutStarted()
        super.onLayout(changed, left, top, right, bottom)
        tryLogVisibility()
        histogramReporter.onLayoutFinished()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        histogramReporter.onMeasureStarted()
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        histogramReporter.onMeasureFinished()
    }

    override fun draw(canvas: Canvas) {
        drawWasSkipped = false
        histogramReporter.onDrawStarted()
        super.draw(canvas)
        histogramReporter.onDrawFinished()
        drawWasSkipped = true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        reportBindingResumedRunnable?.onAttach()
        tryAttachVariableTriggers(divData)
        bindOnAttachRunnable?.onAttach()
        reportBindingFinishedRunnable?.onAttach()
        divTimerEventDispatcher?.onAttach(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        discardVisibilityTracking()
        divTimerEventDispatcher?.onDetach(this)
        viewComponent.animatorController.onDetachedFromWindow()
        runtimeStore.onDetachedFromWindow(this)
    }

    override fun addLoadReference(loadReference: LoadReference, targetView: View) {
        synchronized(monitor) {
            loadReferences.add(loadReference)
        }
    }

    /** Returns true if div can be replaced with given DivData or false otherwise **/
    fun prepareForRecycleOrCleanup(
        newData: DivData,
        oldData: DivData? = null,
        newDataTag: DivDataTag? = null
    ): Boolean {
        val tag = newDataTag ?: DivDataTag(UUID.randomUUID().toString())
        val canBeReplaced = DivComparator.isDivDataReplaceable(
            divData ?: oldData,
            newData,
            stateId,
            expressionResolver,
            div2Component.runtimeStoreProvider.getOrCreate(tag, newData, this).rootRuntime.expressionResolver
        )
        if (canBeReplaced) {
            releaseChildren(this)
            stopLoadAndSubscriptions()
        } else {
            cleanup()
        }

        return canBeReplaced
    }

    fun releaseMedia() {
        cancelImageLoads()
        releaseMedia(this)
    }

    override fun cleanup() = synchronized(monitor) {
        cleanup(removeChildren = true)
    }

    private fun cleanup(removeChildren: Boolean) {
        rebindTask?.clear()?.let {
            rebindTask = null
        }
        discardVisibilityTracking()
        cancelImageLoads()
        releaseMedia(this)
        stopLoadAndSubscriptions() // Depends on children, should be called before removing them
        div2Component.bitmapEffectHelper.release()
        if (removeChildren) {
            releaseAndRemoveChildren(this) // Removes children
        }
        viewComponent.errorCollectors.getOrNull(dataTag, divData)?.cleanRuntimeWarningsAndErrors()
        divData = null
        dataTag = DivDataTag.INVALID
    }

    private fun stopLoadAndSubscriptions() {
        viewToDivBindings.clear()
        cancelTooltips() // Depends on children, should be called before removing them
        clearSubscriptions()
        divDataChangedObservers.clear()
    }

    private fun cancelImageLoads() {
        loadReferences.forEach { it.cancel() }
        loadReferences.clear()
    }

    override fun switchToState(stateId: Long, temporary: Boolean) = synchronized(monitor) {
        if (stateId != DivData.INVALID_STATE_ID) {
            bindOnAttachRunnable?.cancel()
            forceSwitchToState(stateId, temporary)
        }
    }

    override fun switchToInitialState() {
        val data = divData ?: return
        var stateId = data.getInitialStateId()
        val viewState = currentState
        if (viewState != null) {
            stateId = viewState.currentDivStateId
        }
        switchToState(stateId)
    }

    override fun switchToState(path: DivStatePath, temporary: Boolean) = synchronized(monitor) {
        val state = divData?.states?.firstOrNull { it.stateId == path.topLevelStateId }
        bulkActionsHandler.switchState(state, path, temporary)
    }

    /**
     * Switch view to states
     * @param pathList - states. See {@link com.yandex.div.core.state.DivStatePath}
     */
    fun switchToMultipleStates(pathList: List<DivStatePath>, temporary: Boolean, withAnimations: Boolean) {
        val firstPath = if (pathList.isNotEmpty()) {
            pathList[0]
        } else {
            Assert.fail("Empty path list!")
            return
        }
        pathList.find { it.topLevelStateId != firstPath.topLevelStateId }?.let {
            Assert.fail("Trying to switch different top level states in path list!")
        }
        if (stateId == firstPath.topLevelStateId) {
            val state = divData?.states?.firstOrNull { it.stateId == firstPath.topLevelStateId }
            bulkActionsHandler.switchMultipleStates(state, pathList, temporary)
        } else {
            pathList.forEach { path ->
                div2Component.stateManager.updateStates(divTag.id, path, temporary)
            }
            switchToState(firstPath.topLevelStateId)
        }
    }

    fun isInState(statePath: DivStatePath): Boolean {
        val stateCache = div2Component.temporaryDivStateCache
        return stateCache.getState(dataTag.id, statePath.pathToLastState.toString()) == statePath.lastStateId
    }

    /**
     * Observers called when DivData changed. Now it used when patch applied.
     * The list with these observers cleans when setting new DivData.
     */
    fun addDivDataChangeObserver(observer: DivDataChangedObserver) {
        synchronized(monitor) {
            divDataChangedObservers.add(observer)
        }
    }

    fun removeDivDataChangeObserver(observer: DivDataChangedObserver) {
        synchronized(monitor) {
            divDataChangedObservers.remove(observer)
        }
    }

    /**
     * Observers called before and after changing div data.
     * The list with these observers doesn't cleans when setting new DivData.
     */
    internal fun addPersistentDivDataObserver(observer: PersistentDivDataObserver) {
        synchronized(monitor) {
            persistentDivDataObservers.addObserver(observer)
        }
    }

    internal fun removePersistentDivDataObserver(observer: PersistentDivDataObserver) {
        synchronized(monitor) {
            persistentDivDataObservers.removeObserver(observer)
        }
    }

    override fun resetToInitialState() {
        val viewState = currentState
        viewState?.reset()
        div2Component.temporaryDivStateCache.resetCard(divTag.id)

        switchToInitialState()
    }

    private fun switchToDivData(oldData: DivData?, newData: DivData, reporter: ForceRebindReporter): Boolean {
        val oldState = oldData?.state()
        val newState = newData.state()

        this.stateId = newData.stateId()

        if (newState == null) {
            reporter.onForceRebindFatalNoState()
            return false
        }

        val isColdBind = oldData == null
        val newStateView = if (isColdBind) {
            buildViewAsyncAndUpdateState(newState, stateId)
        } else {
            buildViewAndUpdateState(newState, stateId)
        }

        oldState?.let { discardStateVisibility(it) }
        trackStateVisibility(newState)

        val allowsTransition = oldData?.allowsTransitionsOnDataChange(oldExpressionResolver) == true ||
            newData.allowsTransitionsOnDataChange(expressionResolver)
        addNewStateViewWithTransition(oldData, newData, oldState?.div, newState,
            newStateView, allowsTransition, bindBeforeViewAdded = false)

        if (oldData != null) {
            reporter.onForceRebindSuccess()
        } else {
            reporter.onFirstBindingCompleted()
        }

        return true
    }

    private fun DivData.stateId(): Long {
        return currentState?.currentDivStateId ?: getInitialStateId()
    }

    private fun DivData.state(): DivData.State? {
        val stateId = stateId()
        return states.firstOrNull { it.stateId == stateId }
    }

    internal fun rootDiv() = divData?.state()?.div

    private fun forceSwitchToState(stateId: Long, temporary: Boolean): Boolean {
        this.stateId = stateId

        val currentStateId = currentState?.currentDivStateId
        val data = divData ?: return false

        val currentState = data.states.firstOrNull { it.stateId == currentStateId }
        val newState = (data.states.firstOrNull { it.stateId == stateId }) ?: return false

        currentState?.let { discardStateVisibility(it) }
        trackStateVisibility(newState)
        val isReplaceable = DivComparator.areDivsReplaceable(
                currentState?.div,
                newState.div,
                expressionResolver,
                expressionResolver
        )
        val newStateView = if (isReplaceable) {
            updateState(stateId, temporary)
        } else {
            buildViewAndUpdateState(newState, stateId, temporary)
        }

        addNewStateViewWithTransition(data, data, currentState?.div, newState, newStateView,
            data.allowsTransitionsOnDataChange(expressionResolver), bindBeforeViewAdded = isReplaceable)

        return true
    }

    private fun addNewStateViewWithTransition(
        oldData: DivData?, newData: DivData, oldDiv: Div?, newState: DivData.State,
        newStateView: View, allowsTransition: Boolean, bindBeforeViewAdded: Boolean,
    ) {
        val transition = if (allowsTransition) {
            prepareTransition(oldData, newData, oldDiv, newState.div)
        } else {
            null
        }

        if (transition != null) {
            val currentScene = Scene.getCurrentScene(this)
            currentScene?.setExitAction {
                releaseAndRemoveChildren(this)
            }
        } else {
            releaseAndRemoveChildren(this)
        }

        if (bindBeforeViewAdded) {
            div2Component.divBinder.bind(
                bindingContext,
                newStateView,
                newState.div,
                DivStatePath.fromState(newState)
            )
        }

        if (transition != null) {
            TransitionManager.endTransitions(this)
            val newStateScene = Scene(this, newStateView)
            SceneRootWatcher.watchFor(newStateScene, transition)
            TransitionManager.go(newStateScene, transition)
        } else {
            addView(newStateView)
            viewComponent.errorMonitor.connect(this)
        }
    }

    private fun updateState(
        stateId: Long,
        temporary: Boolean,
    ): View {
        val rootView = view.getChildAt(0)
        div2Component.stateManager.updateState(dataTag, stateId, temporary)
        div2Component.divBinder.attachIndicators()
        return rootView
    }

    private fun buildViewAndUpdateState(
        newState: DivData.State,
        stateId: Long,
        isUpdateTemporary: Boolean = true
    ): View {
        div2Component.stateManager.updateState(dataTag, stateId, isUpdateTemporary)
        return divBuilder.buildView(newState.div, bindingContext, DivStatePath.fromState(newState)).also {
            div2Component.divBinder.attachIndicators()
        }
    }

    private fun buildViewAsyncAndUpdateState(
        newState: DivData.State,
        stateId: Long,
        isUpdateTemporary: Boolean = true
    ): View {
        div2Component.stateManager.updateState(dataTag, stateId, isUpdateTemporary)
        val path = DivStatePath.fromState(newState)
        val view = divBuilder.createView(newState.div, bindingContext, path)
        if (bindOnAttachEnabled) {
            bindOnAttachRunnable = SingleTimeOnAttachCallback(this) {
                suppressExpressionErrors {
                    div2Component.divBinder.bind(bindingContext, view, newState.div, path)
                }
                div2Component.divBinder.attachIndicators()
            }
        } else {
            div2Component.divBinder.bind(bindingContext, view, newState.div, path)
            doOnAttach {
                div2Component.divBinder.attachIndicators()
            }
        }
        return view
    }

    private fun prepareTransition(oldData: DivData?, newData: DivData, oldDiv: Div?, newDiv: Div?): Transition? {
        if (oldDiv === newDiv) {
            return null
        }

        val transition = viewComponent.transitionBuilder.buildTransitions(
            from = oldDiv?.let { itemSequenceForTransition(oldData, it, oldExpressionResolver) },
            to = newDiv?.let { itemSequenceForTransition(newData, it, expressionResolver) },
            fromResolver = oldExpressionResolver,
            toResolver = expressionResolver
        )

        if (transition.transitionCount == 0) {
            return null
        }

        val dataChangeListener = div2Component.divDataChangeListener
        dataChangeListener.beforeAnimatedDataChange(this, newData)
        transition.doOnEnd {
            dataChangeListener.afterAnimatedDataChange(this, newData)
        }

        return transition
    }

    private fun itemSequenceForTransition(
        divData: DivData?,
        div: Div,
        resolver: ExpressionResolver
    ): Sequence<DivItemBuilderResult> {
        val selectors = ArrayDeque<DivTransitionSelector>().apply {
            addLast(divData?.transitionAnimationSelector?.evaluate(resolver) ?: DivTransitionSelector.NONE)
        }

        return div.walk(resolver)
            .onEnter { div ->
                if (div is Div.State) selectors.addLast(div.value.transitionAnimationSelector.evaluate(resolver))
                true
            }
            .onLeave { div ->
                if (div is Div.State) selectors.removeLast()
            }
            .filter { item ->
                item.div.value().transitionTriggers?.allowsTransitionsOnDataChange()
                    ?: selectors.lastOrNull()?.allowsTransitionsOnDataChange()
                    ?: false
            }
    }

    fun startDivAnimation() {
        if (childCount > 0) (getChildAt(0) as? DivAnimator)?.startDivAnimation()
    }

    fun stopDivAnimation() {
        if (childCount > 0) (getChildAt(0) as? DivAnimator)?.stopDivAnimation()
    }

    @JvmOverloads
    fun handleAction(
        action: DivAction,
        reason: String = DivActionReason.EXTERNAL,
        resolver: ExpressionResolver = expressionResolver
    ) {
        handleActionWithResult(action, reason, resolver)
    }

    @JvmOverloads
    fun handleActionWithResult(
        action: DivAction,
        reason: String = DivActionReason.EXTERNAL,
        resolver: ExpressionResolver = expressionResolver
    ): Boolean {
        return div2Component.actionBinder.handleAction(
            divView = this,
            resolver,
            action = action,
            reason = reason,
            actionUid = null,
            viewActionHandler = actionHandler
        )
    }

    override fun handleUri(uri: Uri) {
        if (actionHandler?.handleActionUrl(uri, this) == true) {
            return
        }

        div2Component.actionHandler.handleActionUrl(uri, this)
    }

    override fun setConfig(viewConfig: DivViewConfig) {
        this.config = viewConfig
    }

    override fun getConfig(): DivViewConfig = config

    override fun getDivTag(): DivDataTag = dataTag

    override fun subscribe(listener: OverflowMenuSubscriber.Listener) {
        synchronized(monitor) {
            overflowMenuListeners.add(listener)
        }
    }

    override fun clearSubscriptions() = synchronized(monitor) {
        overflowMenuListeners.clear()
    }

    override fun onConfigurationChangedOutside(newConfig: Configuration) {
        dismissPendingOverflowMenus()
    }

    override fun dismissPendingOverflowMenus() = synchronized(monitor) {
        overflowMenuListeners.forEach { it.dismiss() }
    }

    override fun hasScrollableViewUnder(event: MotionEvent): Boolean = hasScrollableChildUnder(event)

    override fun getCurrentStateId() = stateId

    internal val currentRootPath: DivStatePath
        get() {
            return divData?.states?.firstOrNull { it.stateId == stateId }?.let {
                DivStatePath.fromState(it)
            } ?: DivStatePath.fromState(stateId)
        }

    override fun getCurrentState(): DivViewState? {
        val data = divData ?: return null
        val currentState = div2Component.stateManager.getState(dataTag)
        return if (data.states.any { it.stateId == currentState?.currentDivStateId }) {
            currentState
        } else {
            null
        }
    }

    override fun getView() = this

    override fun getExpressionResolver(): ExpressionResolver {
        return runtimeStore.resolver
    }

    private val RuntimeStore?.resolver get() =
        (this as? RuntimeStoreImpl)?.rootRuntime?.expressionResolver ?: ExpressionResolver.EMPTY

    override fun showTooltip(tooltipId: String) {
        tooltipController.showTooltip(tooltipId, bindingContext)
    }

    override fun showTooltip(tooltipId: String, multiple: Boolean) {
        tooltipController.showTooltip(tooltipId, bindingContext, multiple)
    }

    override fun hideTooltip(tooltipId: String) {
        tooltipController.hideTooltip(tooltipId, this)
    }

    override fun cancelTooltips() {
        tooltipController.cancelTooltips(this)
    }

    override fun dispatchDraw(canvas: Canvas) {
        // By default ViewGroup (and FrameLayout) doesn't draw, so draw() call may be skipped.
        // dispatchDraw() is called directly in this case.
        if (drawWasSkipped) {
            histogramReporter.onDrawStarted()
        }

        super.dispatchDraw(canvas)

        if (drawWasSkipped) {
            histogramReporter.onDrawFinished()
        }
    }

    override fun drawChild(canvas: Canvas, child: View?, drawingTime: Long): Boolean {
        if (child != null && child.isVisible) {
            child.drawShadow(canvas)
        }
        return super.drawChild(canvas, child, drawingTime)
    }

    internal fun bindViewToDiv(view: View, div: Div) {
        viewToDivBindings[view] = div
    }

    internal fun takeBindingDiv(view: View) = viewToDivBindings[view]

    /**
     * @return exception if setting variable failed, null otherwise.
     */
    fun setVariable(name: String, value: String): VariableMutationException? =
        VariableMutationHandler.setVariable(this, name, value, expressionResolver)

    fun applyTimerCommand(id: String, command: String) {
        divTimerEventDispatcher?.changeState(id, command)
    }

    @JvmOverloads
    fun applyVideoCommand(
        divId: String,
        command: String,
        expressionResolver: ExpressionResolver = getExpressionResolver()
    ): Boolean {
        return divVideoActionHandler.handleAction(this, divId, command, expressionResolver)
    }

    internal fun unbindViewFromDiv(view: View): Div? = viewToDivBindings.remove(view)

    private fun rebind(newData: DivData, isAutoanimations: Boolean, reporter: SimpleRebindReporter) {
        try {
            if (childCount == 0) {
                reporter.onSimpleRebindNoChild()
                updateNow(newData, dataTag, reporter)
                return
            }
            val state = newData.stateToBind ?: let {
                reporter.onSimpleRebindFatalNoState()
                return
            }

            histogramReporter.onRebindingStarted()
            viewComponent.errorCollectors.getOrNull(dataTag, divData)?.cleanRuntimeWarningsAndErrors()
            val rootDivView = getChildAt(0).apply {
                bindLayoutParams(state.div.value(), expressionResolver)
            }
            divData = newData
            div2Component.stateManager.updateState(dataTag, state.stateId, true)
            div2Component.divBinder.bind(bindingContext, rootDivView, state.div, DivStatePath.fromState(state))
            requestLayout()
            if (isAutoanimations) {
                div2Component.divStateChangeListener.onDivAnimatedStateChanged(this)
            }
            tryAttachVariableTriggers(newData)
            histogramReporter.onRebindingFinished()

            reporter.onSimpleRebindSuccess()
        } catch (error: Exception) {
            reporter.onSimpleRebindException(error)
            updateNow(newData, dataTag, reporter)
            KAssert.fail(error)
        }
    }

    private fun complexRebind(newData: DivData, oldData: DivData, reporter: ComplexRebindReporter): Boolean {
        val stateToBind = newData.stateToBind ?: let {
            reporter.onComplexRebindFatalNoState()
            return false
        }
        histogramReporter.onRebindingStarted()
        divData = newData

        val task = this.rebindTask ?: RebindTask(
            div2View = this,
            div2Component.divBinder,
            oldExpressionResolver,
            expressionResolver,
            reporter
        ).also {
            this.rebindTask = it
        }

        val viewToRebind = (view.getChildAt(0) as ViewGroup).apply {
            bindLayoutParams(stateToBind.div.value(), expressionResolver)
        }

        div2Component.stateManager.updateState(dataTag, stateToBind.stateId, false)
        val result = task.prepareAndRebind(
            oldData,
            newData,
            viewToRebind,
            DivStatePath.fromState(stateToBind)
        )
        if (!result) {
            return false
        }
        requestLayout()

        histogramReporter.onRebindingFinished()
        return true
    }

    private val DivData.stateToBind get() = states.find { it.stateId == stateId } ?: states.firstOrNull()

    fun stateToBind(divData: DivData): DivData.State? = divData.stateToBind

    var visualErrorsEnabled: Boolean
        set(value) {
            viewComponent.errorMonitor.enabled = value
        }
        get() {
            return viewComponent.errorMonitor.enabled
        }

    internal fun bulkActions(function: () -> Unit) {
        bulkActionsHandler.bulkActions(function)
    }

    private inner class BulkActionHandler {
        private var bulkModeDepth = 0
        private var pendingState: DivData.State? = null
        private var isPendingStateTemporary: Boolean = true
        private val pendingPaths = mutableListOf<DivStatePath>()

        fun bulkActions(function: () -> Unit = {}) {
            bulkModeDepth++
            function()
            bulkModeDepth--
            if (bulkModeDepth == 0) {
                runBulkActions()
            }
        }

        fun switchState(state: DivData.State?, path: DivStatePath, temporary: Boolean) {
            switchMultipleStates(state, listOf(path), temporary)
        }

        fun switchMultipleStates(
            state: DivData.State?,
            paths: List<DivStatePath>,
            temporary: Boolean,
        ) {
            if (pendingState != null && state != pendingState) {
                reset()
            }
            pendingState = state
            isPendingStateTemporary = isPendingStateTemporary && temporary
            pendingPaths += paths
            paths.forEach { path: DivStatePath ->
                div2Component.stateManager.updateStates(divTag.id, path, temporary)
            }

            if (bulkModeDepth == 0) {
                runBulkActions()
            }
        }

        fun runBulkActions() {
            val newState = pendingState ?: return
            if (newState.stateId != stateId) {
                switchToState(newState.stateId, isPendingStateTemporary)
            } else if (childCount > 0) {
                try {
                    viewComponent.stateSwitcher.switchStates(newState, pendingPaths.immutableCopy(), expressionResolver)
                } catch (e: StateConflictException) {
                    logError(e)
                    resetToInitialState()
                }
            }
            reset()
        }

        private fun reset() {
            pendingState = null
            isPendingStateTemporary = true
            pendingPaths.clear()
        }
    }
}

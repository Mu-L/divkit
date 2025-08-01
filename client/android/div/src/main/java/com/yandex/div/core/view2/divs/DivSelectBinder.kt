package com.yandex.div.core.view2.divs

import android.widget.TextView
import com.yandex.div.core.dagger.DivScope
import com.yandex.div.core.expression.variables.TwoWayStringVariableBinder
import com.yandex.div.core.state.DivStatePath
import com.yandex.div.core.view2.BindingContext
import com.yandex.div.core.view2.DivTypefaceResolver
import com.yandex.div.core.view2.DivViewBinder
import com.yandex.div.core.view2.animations.DEFAULT_CLICK_ANIMATION
import com.yandex.div.core.view2.divs.widgets.DivSelectView
import com.yandex.div.core.view2.errors.ErrorCollectors
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.Div
import com.yandex.div2.DivSelect
import javax.inject.Inject

@DivScope
internal class DivSelectBinder @Inject constructor(
    baseBinder: DivBaseBinder,
    private val typefaceResolver: DivTypefaceResolver,
    private val variableBinder: TwoWayStringVariableBinder,
    private val errorCollectors: ErrorCollectors
) : DivViewBinder<Div.Select, DivSelect, DivSelectView>(baseBinder) {

    override fun DivSelectView.bind(
        bindingContext: BindingContext,
        div: DivSelect,
        oldDiv: DivSelect?,
        path: DivStatePath
    ) {
        val divView = bindingContext.divView
        val expressionResolver = bindingContext.expressionResolver

        textAlignment = TextView.TEXT_ALIGNMENT_VIEW_START
        focusTracker = divView.inputFocusTracker

        applyOptions(div, bindingContext)
        observeVariable(div, bindingContext, path)

        observeBaseTextProperties(div, oldDiv, expressionResolver)

        observeHintText(div, expressionResolver)
        observeHintColor(div, expressionResolver)
    }

    private fun DivSelectView.applyOptions(div: DivSelect, bindingContext: BindingContext) {
        setAnimatedTouchListener(bindingContext, DEFAULT_CLICK_ANIMATION, null)

        val itemList = createObservedItemList(div, bindingContext.expressionResolver)

        setItems(itemList)

        onItemSelectedListener = { position ->
            text = itemList[position]
            valueUpdater?.invoke(div.options[position].value.evaluate(bindingContext.expressionResolver))
        }
    }

    private fun DivSelectView.createObservedItemList(div: DivSelect, resolver: ExpressionResolver): List<String> {
        val itemList = mutableListOf<String>()

        div.options.forEachIndexed { index, option ->
            val item = option.run { text ?: value }

            itemList.add(item.evaluate(resolver))

            item.observe(resolver) {
                itemList[index] = it
                setItems(itemList)
            }
        }

        return itemList
    }

    private fun DivSelectView.observeVariable(
        div: DivSelect,
        bindingContext: BindingContext,
        path: DivStatePath,
    ) {
        val resolver = bindingContext.expressionResolver
        val errorCollector = errorCollectors.getOrCreate(bindingContext.divView.dataTag, bindingContext.divView.divData)

        val subscription = variableBinder.bindVariable(
            bindingContext,
            div.valueVariable,
            callbacks = object : TwoWayStringVariableBinder.Callbacks {
                override fun onVariableChanged(value: String?) {
                    val matchingOptionsSequence = div.options
                        .asSequence()
                        .filter { it.value.evaluate(resolver) == value }
                        .iterator()

                    text = if (!matchingOptionsSequence.hasNext()) {
                        errorCollector.logWarning(Throwable("No option found with value = \"$value\""))

                        ""
                    } else {
                        val option = matchingOptionsSequence.next()

                        if (matchingOptionsSequence.hasNext()) {
                            errorCollector.logWarning(Throwable("Multiple options found with value = \"$value\", selecting first one"))
                        }

                        option.let { it.text ?: it.value }.evaluate(resolver)
                    }
                }

                override fun setViewStateChangeListener(valueUpdater: (String) -> Unit) {
                    this@observeVariable.valueUpdater = valueUpdater
                }
            },
            path = path)

        addSubscription(subscription)
    }

    private fun DivSelectView.observeBaseTextProperties(
        div: DivSelect,
        oldDiv: DivSelect?,
        resolver: ExpressionResolver
    ) {
        observeBaseTextProperties(
            div.fontSize,
            div.fontSizeUnit,
            div.letterSpacing,
            div.textColor,
            div.lineHeight,
            div.fontFamily,
            div.fontWeight,
            div.fontWeightValue,
            div.fontVariationSettings,
            oldDiv?.fontSize,
            oldDiv?.fontSizeUnit,
            oldDiv?.letterSpacing,
            oldDiv?.textColor,
            oldDiv?.lineHeight,
            oldDiv?.fontFamily,
            oldDiv?.fontWeight,
            oldDiv?.fontWeightValue,
            oldDiv?.fontVariationSettings,
            oldDiv,
            typefaceResolver,
            resolver,
        )
    }

    private fun DivSelectView.observeHintText(div: DivSelect, resolver: ExpressionResolver) {
        val hintTextExpr = div.hintText ?: return

        addSubscription(hintTextExpr.observeAndGet(resolver) { hint ->
            setHint(hint)
        })
    }

    private fun DivSelectView.observeHintColor(div: DivSelect, resolver: ExpressionResolver) {
        addSubscription(div.hintColor.observeAndGet(resolver) { hintColor ->
            setHintTextColor(hintColor)
        })
    }
}

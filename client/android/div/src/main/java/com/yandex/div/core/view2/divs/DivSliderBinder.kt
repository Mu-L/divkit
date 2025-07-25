package com.yandex.div.core.view2.divs

import android.util.DisplayMetrics
import androidx.core.view.doOnPreDraw
import com.yandex.div.core.Div2Logger
import com.yandex.div.core.dagger.ExperimentFlag
import com.yandex.div.core.experiments.Experiment
import com.yandex.div.core.expression.variables.TwoWayIntegerVariableBinder
import com.yandex.div.core.state.DivStatePath
import com.yandex.div.core.util.observeDrawable
import com.yandex.div.core.util.toIntSafely
import com.yandex.div.core.view2.BindingContext
import com.yandex.div.core.view2.DivTypefaceResolver
import com.yandex.div.core.view2.DivViewBinder
import com.yandex.div.core.view2.divs.widgets.DivSliderView
import com.yandex.div.core.view2.errors.ErrorCollector
import com.yandex.div.core.view2.errors.ErrorCollectors
import com.yandex.div.core.view2.getTypeface
import com.yandex.div.internal.widget.slider.SliderTextStyle
import com.yandex.div.internal.widget.slider.SliderView
import com.yandex.div.internal.widget.slider.shapes.TextDrawable
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.Div
import com.yandex.div2.DivDrawable
import com.yandex.div2.DivEdgeInsets
import com.yandex.div2.DivSizeUnit
import com.yandex.div2.DivSlider
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.roundToLong

private const val SLIDER_TICKS_OVERLAP_WARNING = "Slider ticks overlap each other."

internal class DivSliderBinder @Inject constructor(
    baseBinder: DivBaseBinder,
    private val logger: Div2Logger,
    private val typefaceResolver: DivTypefaceResolver,
    private val variableBinder: TwoWayIntegerVariableBinder,
    private val errorCollectors: ErrorCollectors,
    private val horizontalInterceptionAngle: Float,
    @ExperimentFlag(Experiment.VISUAL_ERRORS_ENABLED) private val visualErrorsEnabled: Boolean,
) : DivViewBinder<Div.Slider, DivSlider, DivSliderView>(baseBinder) {

    private var errorCollector: ErrorCollector? = null

    override fun DivSliderView.bind(
        bindingContext: BindingContext,
        div: DivSlider,
        oldDiv: DivSlider?,
        path: DivStatePath
    ) {
        val expressionResolver = bindingContext.expressionResolver
        errorCollector = errorCollectors.getOrCreate(bindingContext.divView.dataTag, bindingContext.divView.divData)

        interceptionAngle = horizontalInterceptionAngle

        addSubscription(
            div.minValue.observeAndGet(expressionResolver) {
                minValue = it.toFloat()
                checkSliderTicks()
            }
        )
        addSubscription(
            div.maxValue.observeAndGet(expressionResolver) {
                maxValue = it.toFloat()
                checkSliderTicks()
            }
        )

        addSubscription(
            div.isEnabled.observeAndGet(expressionResolver) { interactive = it }
        )

        clearOnThumbChangedListener()
        setupThumb(div, bindingContext, path)
        setupSecondaryThumb(div, bindingContext, path)

        setupTrack(div, expressionResolver)
        setupTickMarks(div, expressionResolver)

        setupRanges(div, expressionResolver)
    }

    private fun DivSliderView.setupThumb(
        div: DivSlider,
        bindingContext: BindingContext,
        path: DivStatePath,
    ) {
        observeThumbValue(div, bindingContext, path)
        observeThumbStyle(bindingContext.expressionResolver, div.thumbStyle)
        observeThumbTextStyle(bindingContext.expressionResolver, div.thumbTextStyle)
    }

    private fun DivSliderView.observeThumbValue(
        div: DivSlider,
        bindingContext: BindingContext,
        path: DivStatePath,
    ) {
        val variableName = div.thumbValueVariable ?: return
        val callbacks = object : TwoWayIntegerVariableBinder.Callbacks {
            override fun onVariableChanged(value: Long?) {
                setThumbValue(value?.toFloat() ?: 0f, false)
            }

            override fun setViewStateChangeListener(valueUpdater: (Long) -> Unit) {
                addOnThumbChangedListener(object : SliderView.ChangedListener {
                    override fun onThumbValueChanged(value: Float) {
                        logger.logSliderDrag(bindingContext.divView, this@observeThumbValue, value)
                        valueUpdater(value.roundToLong())
                    }
                })
            }
        }

        addSubscription(variableBinder.bindVariable(bindingContext, variableName, callbacks, path))
    }

    private fun DivSliderView.observeThumbStyle(resolver: ExpressionResolver, thumbStyle: DivDrawable) {
        applyThumbStyle(resolver, thumbStyle)
        observeDrawable(thumbStyle, resolver) {
            applyThumbStyle(resolver, thumbStyle)
        }
    }

    private fun SliderView.applyThumbStyle(resolver: ExpressionResolver, thumbStyle: DivDrawable) {
        thumbDrawable = thumbStyle.toDrawable(resources.displayMetrics, resolver)
    }

    private fun DivSliderView.observeThumbSecondaryStyle(resolver: ExpressionResolver, thumbStyle: DivDrawable?) {
        if (thumbStyle == null) {
            return
        }

        applyThumbSecondaryStyle(resolver, thumbStyle)
        observeDrawable(thumbStyle, resolver) {
            applyThumbSecondaryStyle(resolver, thumbStyle)
        }
    }

    private fun SliderView.applyThumbSecondaryStyle(resolver: ExpressionResolver, thumbStyle: DivDrawable) {
        thumbSecondaryDrawable = thumbStyle.toDrawable(resources.displayMetrics, resolver)
    }

    private fun DivSliderView.observeThumbTextStyle(
        resolver: ExpressionResolver,
        thumbTextStyle: DivSlider.TextStyle?
    ) {
        applyThumbTextStyle(resolver, thumbTextStyle)

        if (thumbTextStyle == null) {
            return
        }

        addSubscription(thumbTextStyle.textColor.observe(resolver) { applyThumbTextStyle(resolver, thumbTextStyle) })
    }

    private fun SliderView.applyThumbTextStyle(
        resolver: ExpressionResolver,
        textStyle: DivSlider.TextStyle?
    ) {
        thumbTextDrawable = textStyle?.let {
            TextDrawable(it.toSliderTextStyle(resources.displayMetrics, typefaceResolver, resolver))
        }
    }

    private fun DivSliderView.observeThumbSecondaryTextStyle(
        resolver: ExpressionResolver,
        thumbTextStyle: DivSlider.TextStyle?
    ) {
        applyThumbSecondaryTextStyle(resolver, thumbTextStyle)

        if (thumbTextStyle == null) {
            return
        }

        addSubscription(
            thumbTextStyle.textColor.observe(resolver) { applyThumbSecondaryTextStyle(resolver, thumbTextStyle) }
        )
    }

    private fun SliderView.applyThumbSecondaryTextStyle(
        resolver: ExpressionResolver,
        textStyle: DivSlider.TextStyle?
    ) {
        thumbSecondTextDrawable = textStyle?.let {
            TextDrawable(it.toSliderTextStyle(resources.displayMetrics, typefaceResolver, resolver))
        }
    }

    private fun DivSliderView.setupSecondaryThumb(
        div: DivSlider,
        bindingContext: BindingContext,
        path: DivStatePath,
    ) {
        val variableName = div.thumbSecondaryValueVariable
        variableName ?: run {
            thumbSecondaryDrawable = null
            setThumbSecondaryValue(null, false)
            return
        }

        val resolver = bindingContext.expressionResolver
        observeThumbSecondaryValue(variableName, bindingContext, path)
        div.thumbSecondaryStyle?.let { observeThumbSecondaryStyle(resolver, it) }
            ?: observeThumbSecondaryStyle(resolver, div.thumbStyle)
        observeThumbSecondaryTextStyle(resolver, div.thumbSecondaryTextStyle)
    }

    private fun DivSliderView.observeThumbSecondaryValue(
        variableName: String,
        bindingContext: BindingContext,
        path: DivStatePath,
    ) {
        val callbacks = object : TwoWayIntegerVariableBinder.Callbacks {
            override fun onVariableChanged(value: Long?) {
                setThumbSecondaryValue(value?.toFloat(), false)
            }

            override fun setViewStateChangeListener(valueUpdater: (Long) -> Unit) {
                addOnThumbChangedListener(object : SliderView.ChangedListener {
                    override fun onThumbSecondaryValueChanged(value: Float?) {
                        logger.logSliderDrag(bindingContext.divView, this@observeThumbSecondaryValue, value)
                        valueUpdater(value?.roundToLong() ?: 0)
                    }
                })
            }
        }

        addSubscription(variableBinder.bindVariable(bindingContext, variableName, callbacks, path))
    }

    private fun DivSliderView.setupTrack(div: DivSlider, resolver: ExpressionResolver) {
        observeTrackActiveStyle(resolver, div.trackActiveStyle)
        observeTrackInactiveStyle(resolver, div.trackInactiveStyle)
    }

    private fun DivSliderView.observeTrackActiveStyle(resolver: ExpressionResolver, trackStyle: DivDrawable) {
        applyTrackActiveStyle(resolver, trackStyle)
        observeDrawable(trackStyle, resolver) {
            applyTrackActiveStyle(resolver, trackStyle)
        }
    }

    private fun SliderView.applyTrackActiveStyle(resolver: ExpressionResolver, trackStyle: DivDrawable) {
        activeTrackDrawable = trackStyle.toDrawable(resources.displayMetrics, resolver)
    }

    private fun DivSliderView.observeTrackInactiveStyle(resolver: ExpressionResolver, trackStyle: DivDrawable) {
        applyTrackInactiveStyle(resolver, trackStyle)
        observeDrawable(trackStyle, resolver) {
            applyTrackInactiveStyle(resolver, trackStyle)
        }
    }

    private fun SliderView.applyTrackInactiveStyle(resolver: ExpressionResolver, trackStyle: DivDrawable) {
        inactiveTrackDrawable = trackStyle.toDrawable(resources.displayMetrics, resolver)
    }

    private fun DivSliderView.setupTickMarks(div: DivSlider, resolver: ExpressionResolver) {
        observeTickMarkActiveStyle(resolver, div.tickMarkActiveStyle)
        observeTickMarkInactiveStyle(resolver, div.tickMarkInactiveStyle)
    }

    private fun DivSliderView.observeTickMarkActiveStyle(resolver: ExpressionResolver, tickMarkStyle: DivDrawable?) {
        applyTickMarkActiveStyle(resolver, tickMarkStyle)
        observeDrawable(tickMarkStyle, resolver) {
            applyTickMarkActiveStyle(resolver, tickMarkStyle)
        }
    }

    private fun DivSliderView.applyTickMarkActiveStyle(resolver: ExpressionResolver, tickMarkStyle: DivDrawable?) {
        activeTickMarkDrawable = tickMarkStyle?.toDrawable(resources.displayMetrics, resolver)
        checkSliderTicks()
    }

    private fun DivSliderView.observeTickMarkInactiveStyle(resolver: ExpressionResolver, tickMarkStyle: DivDrawable?) {
        applyTickMarkInactiveStyle(resolver, tickMarkStyle)
        observeDrawable(tickMarkStyle, resolver) {
            applyTickMarkInactiveStyle(resolver, tickMarkStyle)
        }
    }

    private fun DivSliderView.applyTickMarkInactiveStyle(resolver: ExpressionResolver, tickMarkStyle: DivDrawable?) {
        inactiveTickMarkDrawable = tickMarkStyle?.toDrawable(resources.displayMetrics, resolver)
        checkSliderTicks()
    }

    private fun DivSliderView.checkSliderTicks() {
        if (!visualErrorsEnabled || errorCollector == null) return
        doOnPreDraw {
            if (activeTickMarkDrawable != null || inactiveTickMarkDrawable != null) {
                val ticksNumber = maxValue - minValue
                val activeTicksWidth = activeTickMarkDrawable?.intrinsicWidth ?: 0
                val inactiveTicksWidth = inactiveTickMarkDrawable?.intrinsicWidth ?: 0
                val tickWidth = max(activeTicksWidth, inactiveTicksWidth)

                if (tickWidth * ticksNumber > width && errorCollector != null) {
                    val warnings = errorCollector!!.getWarnings()
                    var warningLogged = false
                    for (warning in warnings.iterator()) {
                        if (warning.message == SLIDER_TICKS_OVERLAP_WARNING) {
                            warningLogged = true
                        }
                    }
                    if (!warningLogged) {
                        errorCollector?.logWarning(Throwable(SLIDER_TICKS_OVERLAP_WARNING))
                    }
                }
            }
        }
    }

    private fun DivSliderView.setupRanges(div: DivSlider, resolver: ExpressionResolver) {
        ranges.clear()
        val divRanges = div.ranges ?: return

        val metrics = resources.displayMetrics
        divRanges.forEach { divRange ->
            val range = SliderView.Range()
            ranges.add(range)

            addSubscription((divRange.start ?: div.minValue).observeAndGet(resolver) {
                updateAfter { range.startValue = it.toFloat() }
            })
            addSubscription((divRange.end ?: div.maxValue).observeAndGet(resolver) {
                updateAfter { range.endValue = it.toFloat() }
            })

            val margins = divRange.margins
            if (margins == null) {
                range.marginStart = 0
                range.marginEnd = 0
            } else {
                with(margins) {
                    val useRelativeMargins = start != null || end != null
                    val marginStart = if (useRelativeMargins) start else left
                    val marginEnd = if (useRelativeMargins) end else right

                    marginStart?.let { expr ->
                        addSubscription(expr.observe(resolver) {
                            updateAfter { range.marginStart = applyUnit(it, resolver, metrics) }
                        })
                    }
                    marginEnd?.let { expr ->
                        addSubscription(expr.observe(resolver) {
                            updateAfter { range.marginEnd = applyUnit(it, resolver, metrics) }
                        })
                    }

                    unit.observeAndGet(resolver) { unit ->
                        updateAfter {
                            marginStart?.let { range.marginStart = it.evaluate(resolver).castToUnit(unit, metrics) }
                            marginEnd?.let { range.marginEnd = it.evaluate(resolver).castToUnit(unit, metrics) }
                        }
                    }
                }
            }

            val trackActiveStyle = divRange.trackActiveStyle ?: div.trackActiveStyle
            val applyActiveTrackStyle = { _: Any ->
                updateAfter { range.activeTrackDrawable = trackActiveStyle.toDrawable(metrics, resolver) }
            }
            applyActiveTrackStyle(Unit)
            observeDrawable(trackActiveStyle, resolver, applyActiveTrackStyle)

            val trackInactiveStyle = divRange.trackInactiveStyle ?: div.trackInactiveStyle
            val applyInactiveTrackStyle = { _: Any ->
                updateAfter { range.inactiveTrackDrawable = trackInactiveStyle.toDrawable(metrics, resolver) }
            }
            applyInactiveTrackStyle(Unit)
            observeDrawable(trackInactiveStyle, resolver, applyInactiveTrackStyle)
        }
    }

    private companion object {
        fun DivSlider.TextStyle.toSliderTextStyle(
            metrics: DisplayMetrics,
            typefaceResolver: DivTypefaceResolver,
            resolver: ExpressionResolver
        ): SliderTextStyle {
            val size = fontSize.evaluate(resolver)
            val typefaceProvider = typefaceResolver.getTypefaceProvider(fontFamily?.evaluate(resolver))
            val fontVariations = if (typefaceProvider.isVariable) {
                getFontVariations(fontWeight, fontWeightValue, fontVariationSettings, resolver)
            } else {
                null
            }
            return SliderTextStyle(
                fontSize = size.fontSizeToPx(fontSizeUnit.evaluate(resolver), metrics),
                spacing = letterSpacing.evaluate(resolver).toFloat() / size,
                fontWeight = getTypeface(
                    fontWeight?.evaluate(resolver),
                    fontWeightValue?.evaluate(resolver)?.toIntSafely(),
                    typefaceProvider
                ),
                offsetX = offset?.x?.toPx(metrics, resolver)?.toFloat() ?: 0f,
                offsetY = offset?.y?.toPx(metrics, resolver)?.toFloat() ?: 0f,
                textColor = textColor.evaluate(resolver),
                fontVariations = fontVariations,
            )
        }

        inline fun SliderView.updateAfter(block: () -> Unit) {
            block()
            requestLayout()
            invalidate()
        }

        fun DivEdgeInsets.applyUnit(margin: Long, resolver: ExpressionResolver, metrics: DisplayMetrics) =
            margin.castToUnit(unit.evaluate(resolver), metrics)

        fun Long.castToUnit(unit: DivSizeUnit, metrics: DisplayMetrics) = when (unit) {
            DivSizeUnit.DP -> dpToPx(metrics)
            DivSizeUnit.SP -> spToPx(metrics)
            DivSizeUnit.PX -> toIntSafely()
        }
    }
}

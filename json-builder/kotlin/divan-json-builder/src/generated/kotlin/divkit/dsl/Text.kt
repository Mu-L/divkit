@file:Suppress(
    "unused",
    "UNUSED_PARAMETER",
)

package divkit.dsl

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonValue
import divkit.dsl.annotation.*
import divkit.dsl.core.*
import divkit.dsl.scope.*
import kotlin.Any
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map

/**
 * Text.
 * 
 * Can be created using the method [text].
 * 
 * Required parameters: `type, text`.
 */
@Generated
data class Text internal constructor(
    @JsonIgnore
    val properties: Properties,
) : Div {
    @JsonAnyGetter
    internal fun getJsonProperties(): Map<String, Any> = properties.mergeWith(
        mapOf("type" to "text")
    )

    operator fun plus(additive: Properties): Text = Text(
        Properties(
            text = additive.text ?: properties.text,
            accessibility = additive.accessibility ?: properties.accessibility,
            action = additive.action ?: properties.action,
            actionAnimation = additive.actionAnimation ?: properties.actionAnimation,
            actions = additive.actions ?: properties.actions,
            alignmentHorizontal = additive.alignmentHorizontal ?: properties.alignmentHorizontal,
            alignmentVertical = additive.alignmentVertical ?: properties.alignmentVertical,
            alpha = additive.alpha ?: properties.alpha,
            animators = additive.animators ?: properties.animators,
            autoEllipsize = additive.autoEllipsize ?: properties.autoEllipsize,
            background = additive.background ?: properties.background,
            border = additive.border ?: properties.border,
            captureFocusOnAction = additive.captureFocusOnAction ?: properties.captureFocusOnAction,
            columnSpan = additive.columnSpan ?: properties.columnSpan,
            disappearActions = additive.disappearActions ?: properties.disappearActions,
            doubletapActions = additive.doubletapActions ?: properties.doubletapActions,
            ellipsis = additive.ellipsis ?: properties.ellipsis,
            extensions = additive.extensions ?: properties.extensions,
            focus = additive.focus ?: properties.focus,
            focusedTextColor = additive.focusedTextColor ?: properties.focusedTextColor,
            fontFamily = additive.fontFamily ?: properties.fontFamily,
            fontFeatureSettings = additive.fontFeatureSettings ?: properties.fontFeatureSettings,
            fontSize = additive.fontSize ?: properties.fontSize,
            fontSizeUnit = additive.fontSizeUnit ?: properties.fontSizeUnit,
            fontVariationSettings = additive.fontVariationSettings ?: properties.fontVariationSettings,
            fontWeight = additive.fontWeight ?: properties.fontWeight,
            fontWeightValue = additive.fontWeightValue ?: properties.fontWeightValue,
            functions = additive.functions ?: properties.functions,
            height = additive.height ?: properties.height,
            hoverEndActions = additive.hoverEndActions ?: properties.hoverEndActions,
            hoverStartActions = additive.hoverStartActions ?: properties.hoverStartActions,
            id = additive.id ?: properties.id,
            images = additive.images ?: properties.images,
            layoutProvider = additive.layoutProvider ?: properties.layoutProvider,
            letterSpacing = additive.letterSpacing ?: properties.letterSpacing,
            lineHeight = additive.lineHeight ?: properties.lineHeight,
            longtapActions = additive.longtapActions ?: properties.longtapActions,
            margins = additive.margins ?: properties.margins,
            maxLines = additive.maxLines ?: properties.maxLines,
            minHiddenLines = additive.minHiddenLines ?: properties.minHiddenLines,
            paddings = additive.paddings ?: properties.paddings,
            pressEndActions = additive.pressEndActions ?: properties.pressEndActions,
            pressStartActions = additive.pressStartActions ?: properties.pressStartActions,
            ranges = additive.ranges ?: properties.ranges,
            reuseId = additive.reuseId ?: properties.reuseId,
            rowSpan = additive.rowSpan ?: properties.rowSpan,
            selectable = additive.selectable ?: properties.selectable,
            selectedActions = additive.selectedActions ?: properties.selectedActions,
            strike = additive.strike ?: properties.strike,
            textAlignmentHorizontal = additive.textAlignmentHorizontal ?: properties.textAlignmentHorizontal,
            textAlignmentVertical = additive.textAlignmentVertical ?: properties.textAlignmentVertical,
            textColor = additive.textColor ?: properties.textColor,
            textGradient = additive.textGradient ?: properties.textGradient,
            textShadow = additive.textShadow ?: properties.textShadow,
            tightenWidth = additive.tightenWidth ?: properties.tightenWidth,
            tooltips = additive.tooltips ?: properties.tooltips,
            transform = additive.transform ?: properties.transform,
            transitionChange = additive.transitionChange ?: properties.transitionChange,
            transitionIn = additive.transitionIn ?: properties.transitionIn,
            transitionOut = additive.transitionOut ?: properties.transitionOut,
            transitionTriggers = additive.transitionTriggers ?: properties.transitionTriggers,
            truncate = additive.truncate ?: properties.truncate,
            underline = additive.underline ?: properties.underline,
            variableTriggers = additive.variableTriggers ?: properties.variableTriggers,
            variables = additive.variables ?: properties.variables,
            visibility = additive.visibility ?: properties.visibility,
            visibilityAction = additive.visibilityAction ?: properties.visibilityAction,
            visibilityActions = additive.visibilityActions ?: properties.visibilityActions,
            width = additive.width ?: properties.width,
        )
    )

    data class Properties internal constructor(
        /**
         * Text.
         */
        val text: Property<String>?,
        /**
         * Accessibility settings.
         */
        val accessibility: Property<Accessibility>?,
        /**
         * One action when clicking on an element. Not used if the `actions` parameter is set.
         */
        val action: Property<Action>?,
        /**
         * Click animation. The web only supports the following values: `fade`, `scale`, `native`, `no_animation` and `set`.
         * Default value: `{"name": "fade", "start_value": 1, "end_value": 0.6, "duration": 100 }`.
         */
        val actionAnimation: Property<Animation>?,
        /**
         * Multiple actions when clicking on an element.
         */
        val actions: Property<List<Action>>?,
        /**
         * Horizontal alignment of an element inside the parent element.
         */
        val alignmentHorizontal: Property<AlignmentHorizontal>?,
        /**
         * Vertical alignment of an element inside the parent element.
         */
        val alignmentVertical: Property<AlignmentVertical>?,
        /**
         * Sets transparency of the entire element: `0` — completely transparent, `1` — opaque.
         * Default value: `1.0`.
         */
        val alpha: Property<Double>?,
        /**
         * Declaration of animators that change variable values over time.
         */
        val animators: Property<List<Animator>>?,
        /**
         * Automatic text cropping to fit the container size.
         */
        val autoEllipsize: Property<Boolean>?,
        /**
         * Element background. It can contain multiple layers.
         */
        val background: Property<List<Background>>?,
        /**
         * Element stroke.
         */
        val border: Property<Border>?,
        /**
         * If the value is:<li>`true` - when the element action is activated, the focus will be moved to that element. That means that the accessibility focus will be moved and the virtual keyboard will be hidden, unless the target element implies its presence (e.g. `input`).</li><li>`false` - when you click on an element, the focus will remain on the currently focused element.</li>
         * Default value: `true`.
         */
        val captureFocusOnAction: Property<Boolean>?,
        /**
         * Merges cells in a column of the [grid](div-grid.md) element.
         */
        val columnSpan: Property<Int>?,
        /**
         * Actions when an element disappears from the screen.
         */
        val disappearActions: Property<List<DisappearAction>>?,
        /**
         * Action when double-clicking on an element.
         */
        val doubletapActions: Property<List<Action>>?,
        /**
         * Text cropping marker. It is displayed when text size exceeds the limit on the number of lines.
         */
        val ellipsis: Property<Ellipsis>?,
        /**
         * Extensions for additional processing of an element. The list of extensions is given in  [DivExtension](../../extensions).
         */
        val extensions: Property<List<Extension>>?,
        /**
         * Parameters when focusing on an element or losing focus.
         */
        val focus: Property<Focus>?,
        /**
         * Text color when focusing on the element.
         */
        val focusedTextColor: Property<Color>?,
        /**
         * Font family:<li>`text` — a standard text font;</li><li>`display` — a family of fonts with a large font size.</li>
         */
        val fontFamily: Property<String>?,
        /**
         * List of OpenType font features. The format matches the CSS attribute "font-feature-settings". Learn more: https://www.w3.org/TR/css-fonts-3/#font-feature-settings-prop
         */
        val fontFeatureSettings: Property<String>?,
        /**
         * Font size.
         * Default value: `12`.
         */
        val fontSize: Property<Int>?,
        /**
         * Unit of measurement:<li>`px` — a physical pixel.</li><li>`dp` — a logical pixel that doesn't depend on screen density.</li><li>`sp` — a logical pixel that depends on the font size on a device. Specify height in `sp`. Only available on Android.</li>
         * Default value: `sp`.
         */
        val fontSizeUnit: Property<SizeUnit>?,
        /**
         * List of TrueType/OpenType font features. The object is constructed from pairs of axis tag and style values. The axis tag must contain four ASCII characters.
         */
        val fontVariationSettings: Property<Map<String, Any>>?,
        /**
         * Style.
         */
        val fontWeight: Property<FontWeight>?,
        /**
         * Style. Numeric value.
         */
        val fontWeightValue: Property<Int>?,
        /**
         * User functions.
         */
        val functions: Property<List<Function>>?,
        /**
         * Element height. For Android: if there is text in this or in a child element, specify height in `sp` to scale the element together with the text. To learn more about units of size measurement, see [Layout inside the card](../../layout).
         * Default value: `{"type": "wrap_content"}`.
         */
        val height: Property<Size>?,
        /**
         * Actions performed after hovering over an element. Available on platforms that support pointing devices (such as a mouse or stylus).
         */
        val hoverEndActions: Property<List<Action>>?,
        /**
         * Actions performed when hovering over an element. Available on platforms that support pointing devices (such as a mouse or stylus).
         */
        val hoverStartActions: Property<List<Action>>?,
        /**
         * Element ID. It must be unique within the root element. It is used as `accessibilityIdentifier` on iOS.
         */
        val id: Property<String>?,
        /**
         * Images embedded in text.
         */
        val images: Property<List<Image>>?,
        /**
         * Provides data on the actual size of the element.
         */
        val layoutProvider: Property<LayoutProvider>?,
        /**
         * Spacing between characters.
         * Default value: `0`.
         */
        val letterSpacing: Property<Double>?,
        /**
         * Line spacing of the text.
         */
        val lineHeight: Property<Int>?,
        /**
         * Action when long-clicking an element. Doesn't work on devices that don't support touch gestures.
         */
        val longtapActions: Property<List<Action>>?,
        /**
         * External margins from the element stroke.
         */
        val margins: Property<EdgeInsets>?,
        /**
         * Maximum number of lines not to be cropped when breaking the limits.
         */
        val maxLines: Property<Int>?,
        /**
         * Minimum number of cropped lines when breaking the limits.
         */
        val minHiddenLines: Property<Int>?,
        /**
         * Internal margins from the element stroke.
         */
        val paddings: Property<EdgeInsets>?,
        /**
         * Actions performed after clicking/tapping an element.
         */
        val pressEndActions: Property<List<Action>>?,
        /**
         * Actions performed at the start of a click/tap on an element.
         */
        val pressStartActions: Property<List<Action>>?,
        /**
         * A character range in which additional style parameters can be set. Defined by mandatory `start` and `end` fields.
         */
        val ranges: Property<List<Range>>?,
        /**
         * ID for the div object structure. Used to optimize block reuse. See [block reuse](../../reuse/reuse.md).
         */
        val reuseId: Property<String>?,
        /**
         * Merges cells in a string of the [grid](div-grid.md) element.
         */
        val rowSpan: Property<Int>?,
        /**
         * Ability to select and copy text.
         * Default value: `false`.
         */
        val selectable: Property<Boolean>?,
        /**
         * List of [actions](div-action.md) to be executed when selecting an element in [pager](div-pager.md).
         */
        val selectedActions: Property<List<Action>>?,
        /**
         * Strikethrough.
         * Default value: `none`.
         */
        val strike: Property<LineStyle>?,
        /**
         * Horizontal text alignment.
         * Default value: `start`.
         */
        val textAlignmentHorizontal: Property<AlignmentHorizontal>?,
        /**
         * Vertical text alignment.
         * Default value: `top`.
         */
        val textAlignmentVertical: Property<AlignmentVertical>?,
        /**
         * Text color.
         * Default value: `#FF000000`.
         */
        val textColor: Property<Color>?,
        /**
         * Gradient text color.
         */
        val textGradient: Property<TextGradient>?,
        /**
         * Parameters of the shadow applied to the text.
         */
        val textShadow: Property<Shadow>?,
        /**
         * Limit the text width to the maximum line width. Applies only when the width is set to `wrap_content`, `constrained=true`, and `max_size` is specified.
         * Default value: `false`.
         */
        val tightenWidth: Property<Boolean>?,
        /**
         * Tooltips linked to an element. A tooltip can be shown by `div-action://show_tooltip?id=`, hidden by `div-action://hide_tooltip?id=` where `id` — tooltip id.
         */
        val tooltips: Property<List<Tooltip>>?,
        /**
         * Applies the passed transformation to the element. Content that doesn't fit into the original view area is cut off.
         */
        val transform: Property<Transform>?,
        /**
         * Change animation. It is played when the position or size of an element changes in the new layout.
         */
        val transitionChange: Property<ChangeTransition>?,
        /**
         * Appearance animation. It is played when an element with a new ID appears. To learn more about the concept of transitions, see [Animated transitions](../../interaction#animation/transition-animation).
         */
        val transitionIn: Property<AppearanceTransition>?,
        /**
         * Disappearance animation. It is played when an element disappears in the new layout.
         */
        val transitionOut: Property<AppearanceTransition>?,
        /**
         * Animation starting triggers. Default value: `[state_change, visibility_change]`.
         */
        val transitionTriggers: Property<List<ArrayElement<TransitionTrigger>>>?,
        /**
         * Location of text cropping marker.
         * Default value: `end`.
         */
        val truncate: Property<Truncate>?,
        /**
         * Underline.
         * Default value: `none`.
         */
        val underline: Property<LineStyle>?,
        /**
         * Triggers for changing variables within an element.
         */
        val variableTriggers: Property<List<Trigger>>?,
        /**
         * Declaration of variables that can be used within an element. Variables declared in this array can only be used within the element and its child elements.
         */
        val variables: Property<List<Variable>>?,
        /**
         * Element visibility.
         * Default value: `visible`.
         */
        val visibility: Property<Visibility>?,
        /**
         * Tracking visibility of a single element. Not used if the `visibility_actions` parameter is set.
         */
        val visibilityAction: Property<VisibilityAction>?,
        /**
         * Actions when an element appears on the screen.
         */
        val visibilityActions: Property<List<VisibilityAction>>?,
        /**
         * Element width.
         * Default value: `{"type": "match_parent"}`.
         */
        val width: Property<Size>?,
    ) {
        internal fun mergeWith(properties: Map<String, Any>): Map<String, Any> {
            val result = mutableMapOf<String, Any>()
            result.putAll(properties)
            result.tryPutProperty("text", text)
            result.tryPutProperty("accessibility", accessibility)
            result.tryPutProperty("action", action)
            result.tryPutProperty("action_animation", actionAnimation)
            result.tryPutProperty("actions", actions)
            result.tryPutProperty("alignment_horizontal", alignmentHorizontal)
            result.tryPutProperty("alignment_vertical", alignmentVertical)
            result.tryPutProperty("alpha", alpha)
            result.tryPutProperty("animators", animators)
            result.tryPutProperty("auto_ellipsize", autoEllipsize)
            result.tryPutProperty("background", background)
            result.tryPutProperty("border", border)
            result.tryPutProperty("capture_focus_on_action", captureFocusOnAction)
            result.tryPutProperty("column_span", columnSpan)
            result.tryPutProperty("disappear_actions", disappearActions)
            result.tryPutProperty("doubletap_actions", doubletapActions)
            result.tryPutProperty("ellipsis", ellipsis)
            result.tryPutProperty("extensions", extensions)
            result.tryPutProperty("focus", focus)
            result.tryPutProperty("focused_text_color", focusedTextColor)
            result.tryPutProperty("font_family", fontFamily)
            result.tryPutProperty("font_feature_settings", fontFeatureSettings)
            result.tryPutProperty("font_size", fontSize)
            result.tryPutProperty("font_size_unit", fontSizeUnit)
            result.tryPutProperty("font_variation_settings", fontVariationSettings)
            result.tryPutProperty("font_weight", fontWeight)
            result.tryPutProperty("font_weight_value", fontWeightValue)
            result.tryPutProperty("functions", functions)
            result.tryPutProperty("height", height)
            result.tryPutProperty("hover_end_actions", hoverEndActions)
            result.tryPutProperty("hover_start_actions", hoverStartActions)
            result.tryPutProperty("id", id)
            result.tryPutProperty("images", images)
            result.tryPutProperty("layout_provider", layoutProvider)
            result.tryPutProperty("letter_spacing", letterSpacing)
            result.tryPutProperty("line_height", lineHeight)
            result.tryPutProperty("longtap_actions", longtapActions)
            result.tryPutProperty("margins", margins)
            result.tryPutProperty("max_lines", maxLines)
            result.tryPutProperty("min_hidden_lines", minHiddenLines)
            result.tryPutProperty("paddings", paddings)
            result.tryPutProperty("press_end_actions", pressEndActions)
            result.tryPutProperty("press_start_actions", pressStartActions)
            result.tryPutProperty("ranges", ranges)
            result.tryPutProperty("reuse_id", reuseId)
            result.tryPutProperty("row_span", rowSpan)
            result.tryPutProperty("selectable", selectable)
            result.tryPutProperty("selected_actions", selectedActions)
            result.tryPutProperty("strike", strike)
            result.tryPutProperty("text_alignment_horizontal", textAlignmentHorizontal)
            result.tryPutProperty("text_alignment_vertical", textAlignmentVertical)
            result.tryPutProperty("text_color", textColor)
            result.tryPutProperty("text_gradient", textGradient)
            result.tryPutProperty("text_shadow", textShadow)
            result.tryPutProperty("tighten_width", tightenWidth)
            result.tryPutProperty("tooltips", tooltips)
            result.tryPutProperty("transform", transform)
            result.tryPutProperty("transition_change", transitionChange)
            result.tryPutProperty("transition_in", transitionIn)
            result.tryPutProperty("transition_out", transitionOut)
            result.tryPutProperty("transition_triggers", transitionTriggers)
            result.tryPutProperty("truncate", truncate)
            result.tryPutProperty("underline", underline)
            result.tryPutProperty("variable_triggers", variableTriggers)
            result.tryPutProperty("variables", variables)
            result.tryPutProperty("visibility", visibility)
            result.tryPutProperty("visibility_action", visibilityAction)
            result.tryPutProperty("visibility_actions", visibilityActions)
            result.tryPutProperty("width", width)
            return result
        }
    }

    /**
     * Location of text cropping marker.
     * 
     * Possible values: [none], [start], [end], [middle].
     */
    @Generated
    sealed interface Truncate

    /**
     * Text cropping marker. It is displayed when text size exceeds the limit on the number of lines.
     * 
     * Can be created using the method [textEllipsis].
     * 
     * Required parameters: `text`.
     */
    @Generated
    data class Ellipsis internal constructor(
        @JsonIgnore
        val properties: Properties,
    ) {
        @JsonAnyGetter
        internal fun getJsonProperties(): Map<String, Any> = properties.mergeWith(emptyMap())

        operator fun plus(additive: Properties): Ellipsis = Ellipsis(
            Properties(
                actions = additive.actions ?: properties.actions,
                images = additive.images ?: properties.images,
                ranges = additive.ranges ?: properties.ranges,
                text = additive.text ?: properties.text,
            )
        )

        data class Properties internal constructor(
            /**
             * Actions when clicking on a crop marker.
             */
            val actions: Property<List<Action>>?,
            /**
             * Images embedded in a crop marker.
             */
            val images: Property<List<Image>>?,
            /**
             * Character ranges inside a crop marker with different text styles.
             */
            val ranges: Property<List<Range>>?,
            /**
             * Marker text.
             */
            val text: Property<String>?,
        ) {
            internal fun mergeWith(properties: Map<String, Any>): Map<String, Any> {
                val result = mutableMapOf<String, Any>()
                result.putAll(properties)
                result.tryPutProperty("actions", actions)
                result.tryPutProperty("images", images)
                result.tryPutProperty("ranges", ranges)
                result.tryPutProperty("text", text)
                return result
            }
        }
    }


    /**
     * Image.
     * 
     * Can be created using the method [textImage].
     * 
     * Required parameters: `url, start`.
     */
    @Generated
    data class Image internal constructor(
        @JsonIgnore
        val properties: Properties,
    ) {
        @JsonAnyGetter
        internal fun getJsonProperties(): Map<String, Any> = properties.mergeWith(emptyMap())

        operator fun plus(additive: Properties): Image = Image(
            Properties(
                accessibility = additive.accessibility ?: properties.accessibility,
                alignmentVertical = additive.alignmentVertical ?: properties.alignmentVertical,
                height = additive.height ?: properties.height,
                indexingDirection = additive.indexingDirection ?: properties.indexingDirection,
                preloadRequired = additive.preloadRequired ?: properties.preloadRequired,
                start = additive.start ?: properties.start,
                tintColor = additive.tintColor ?: properties.tintColor,
                tintMode = additive.tintMode ?: properties.tintMode,
                url = additive.url ?: properties.url,
                width = additive.width ?: properties.width,
            )
        )

        data class Properties internal constructor(
            val accessibility: Property<Accessibility>?,
            /**
             * Vertical image alignment within the row.
             * Default value: `center`.
             */
            val alignmentVertical: Property<TextAlignmentVertical>?,
            /**
             * Image height.
             * Default value: `{"type": "fixed","value":20}`.
             */
            val height: Property<FixedSize>?,
            /**
             * Defines the direction in the `start` parameter:
            – `normal` is for regular string indexing ([0, 1, 2, ..., N]). Use it if you need to insert an image by index relative to the beginning of a string.
            – `reversed` is for indexing a string from the end to the beginning ([N, ..., 2, 1, 0]). Use it if you need to insert an image by index relative to the end of a string.
             * Default value: `normal`.
             */
            val indexingDirection: Property<IndexingDirection>?,
            /**
             * Background image must be loaded before the display.
             * Default value: `false`.
             */
            val preloadRequired: Property<Boolean>?,
            /**
             * A symbol to insert prior to an image. To insert an image at the end of the text, specify the number of the last character plus one.
             */
            val start: Property<Int>?,
            /**
             * New color of a contour image.
             */
            val tintColor: Property<Color>?,
            /**
             * Blend mode of the color specified in `tint_color`.
             * Default value: `source_in`.
             */
            val tintMode: Property<BlendMode>?,
            /**
             * Image URL.
             */
            val url: Property<Url>?,
            /**
             * Image width.
             * Default value: `{"type": "fixed","value":20}`.
             */
            val width: Property<FixedSize>?,
        ) {
            internal fun mergeWith(properties: Map<String, Any>): Map<String, Any> {
                val result = mutableMapOf<String, Any>()
                result.putAll(properties)
                result.tryPutProperty("accessibility", accessibility)
                result.tryPutProperty("alignment_vertical", alignmentVertical)
                result.tryPutProperty("height", height)
                result.tryPutProperty("indexing_direction", indexingDirection)
                result.tryPutProperty("preload_required", preloadRequired)
                result.tryPutProperty("start", start)
                result.tryPutProperty("tint_color", tintColor)
                result.tryPutProperty("tint_mode", tintMode)
                result.tryPutProperty("url", url)
                result.tryPutProperty("width", width)
                return result
            }
        }

        /**
         * Defines the direction in the `start` parameter:
        – `normal` is for regular string indexing ([0, 1, 2, ..., N]). Use it if you need to insert an image by index relative to the beginning of a string.
        – `reversed` is for indexing a string from the end to the beginning ([N, ..., 2, 1, 0]). Use it if you need to insert an image by index relative to the end of a string.
         * 
         * Possible values: [normal], [reversed].
         */
        @Generated
        sealed interface IndexingDirection

        /**
         * Can be created using the method [textImageAccessibility].
         */
        @Generated
        data class Accessibility internal constructor(
            @JsonIgnore
            val properties: Properties,
        ) {
            @JsonAnyGetter
            internal fun getJsonProperties(): Map<String, Any> = properties.mergeWith(emptyMap())

            operator fun plus(additive: Properties): Accessibility = Accessibility(
                Properties(
                    description = additive.description ?: properties.description,
                    type = additive.type ?: properties.type,
                )
            )

            data class Properties internal constructor(
                /**
                 * Element description. It is used as the main description for screen reading applications.
                 */
                val description: Property<String>?,
                /**
                 * Element role. Used to correctly identify an element by the accessibility service. For example, the `list` element is used to group list elements into one element.
                 * Default value: `auto`.
                 */
                val type: Property<Type>?,
            ) {
                internal fun mergeWith(properties: Map<String, Any>): Map<String, Any> {
                    val result = mutableMapOf<String, Any>()
                    result.putAll(properties)
                    result.tryPutProperty("description", description)
                    result.tryPutProperty("type", type)
                    return result
                }
            }

            /**
             * Element role. Used to correctly identify an element by the accessibility service. For example, the `list` element is used to group list elements into one element.
             * 
             * Possible values: [none], [button], [image], [text], [auto].
             */
            @Generated
            sealed interface Type
        }

    }


    /**
     * Additional parameters of the character range.
     * 
     * Can be created using the method [textRange].
     */
    @Generated
    data class Range internal constructor(
        @JsonIgnore
        val properties: Properties,
    ) {
        @JsonAnyGetter
        internal fun getJsonProperties(): Map<String, Any> = properties.mergeWith(emptyMap())

        operator fun plus(additive: Properties): Range = Range(
            Properties(
                actions = additive.actions ?: properties.actions,
                alignmentVertical = additive.alignmentVertical ?: properties.alignmentVertical,
                background = additive.background ?: properties.background,
                baselineOffset = additive.baselineOffset ?: properties.baselineOffset,
                border = additive.border ?: properties.border,
                end = additive.end ?: properties.end,
                fontFamily = additive.fontFamily ?: properties.fontFamily,
                fontFeatureSettings = additive.fontFeatureSettings ?: properties.fontFeatureSettings,
                fontSize = additive.fontSize ?: properties.fontSize,
                fontSizeUnit = additive.fontSizeUnit ?: properties.fontSizeUnit,
                fontVariationSettings = additive.fontVariationSettings ?: properties.fontVariationSettings,
                fontWeight = additive.fontWeight ?: properties.fontWeight,
                fontWeightValue = additive.fontWeightValue ?: properties.fontWeightValue,
                letterSpacing = additive.letterSpacing ?: properties.letterSpacing,
                lineHeight = additive.lineHeight ?: properties.lineHeight,
                mask = additive.mask ?: properties.mask,
                start = additive.start ?: properties.start,
                strike = additive.strike ?: properties.strike,
                textColor = additive.textColor ?: properties.textColor,
                textShadow = additive.textShadow ?: properties.textShadow,
                topOffset = additive.topOffset ?: properties.topOffset,
                underline = additive.underline ?: properties.underline,
            )
        )

        data class Properties internal constructor(
            /**
             * Action when clicking on text.
             */
            val actions: Property<List<Action>>?,
            /**
             * Vertical text alignment within the row. Ignored when a baseline offset is specified.
             */
            val alignmentVertical: Property<TextAlignmentVertical>?,
            /**
             * Character range background.
             */
            val background: Property<TextRangeBackground>?,
            /**
             * Vertical baseline offset in a character range. If set, vertical alignment is ignored.
             * Default value: `0`.
             */
            val baselineOffset: Property<Double>?,
            /**
             * Character range border.
             */
            val border: Property<TextRangeBorder>?,
            /**
             * Ordinal number of the last character to be included in the range. If the property is omitted, the range ends at the last character of the text.
             */
            val end: Property<Int>?,
            /**
             * Font family:<li>`text` — a standard text font;</li><li>`display` — a family of fonts with a large font size.</li>
             */
            val fontFamily: Property<String>?,
            /**
             * List of OpenType font features. The format matches the CSS attribute "font-feature-settings". Learn more: https://www.w3.org/TR/css-fonts-3/#font-feature-settings-prop
             */
            val fontFeatureSettings: Property<String>?,
            /**
             * Font size.
             */
            val fontSize: Property<Int>?,
            /**
             * Unit of measurement:<li>`px` — a physical pixel.</li><li>`dp` — a logical pixel that doesn't depend on screen density.</li><li>`sp` — a logical pixel that depends on the font size on a device. Specify height in `sp`. Only available on Android.</li>
             * Default value: `sp`.
             */
            val fontSizeUnit: Property<SizeUnit>?,
            /**
             * List of TrueType/OpenType font features. The object is constructed from pairs of axis tag and style values. The axis tag must contain four ASCII characters.
             */
            val fontVariationSettings: Property<Map<String, Any>>?,
            /**
             * Style.
             */
            val fontWeight: Property<FontWeight>?,
            /**
             * Style. Numeric value.
             */
            val fontWeightValue: Property<Int>?,
            /**
             * Spacing between characters.
             */
            val letterSpacing: Property<Double>?,
            /**
             * Line spacing of the text. Units specified in `font_size_unit`.
             */
            val lineHeight: Property<Int>?,
            /**
             * A mask that hides a part of text. To show the hidden text, disable the mask using the `is_enabled` property.
             */
            val mask: Property<TextRangeMask>?,
            /**
             * Ordinal number of a character which the range begins from. The first character has a number `0`.
             * Default value: `0`.
             */
            val start: Property<Int>?,
            /**
             * Strikethrough.
             */
            val strike: Property<LineStyle>?,
            /**
             * Text color for a specific range. Priority: has the highest priority over `text_gradient` and `text_color`.
             */
            val textColor: Property<Color>?,
            /**
             * Parameters of the shadow applied to the character range.
             */
            val textShadow: Property<Shadow>?,
            /**
             * Top margin of the character range. Units specified in `font_size_unit`.
             */
            val topOffset: Property<Int>?,
            /**
             * Underline.
             */
            val underline: Property<LineStyle>?,
        ) {
            internal fun mergeWith(properties: Map<String, Any>): Map<String, Any> {
                val result = mutableMapOf<String, Any>()
                result.putAll(properties)
                result.tryPutProperty("actions", actions)
                result.tryPutProperty("alignment_vertical", alignmentVertical)
                result.tryPutProperty("background", background)
                result.tryPutProperty("baseline_offset", baselineOffset)
                result.tryPutProperty("border", border)
                result.tryPutProperty("end", end)
                result.tryPutProperty("font_family", fontFamily)
                result.tryPutProperty("font_feature_settings", fontFeatureSettings)
                result.tryPutProperty("font_size", fontSize)
                result.tryPutProperty("font_size_unit", fontSizeUnit)
                result.tryPutProperty("font_variation_settings", fontVariationSettings)
                result.tryPutProperty("font_weight", fontWeight)
                result.tryPutProperty("font_weight_value", fontWeightValue)
                result.tryPutProperty("letter_spacing", letterSpacing)
                result.tryPutProperty("line_height", lineHeight)
                result.tryPutProperty("mask", mask)
                result.tryPutProperty("start", start)
                result.tryPutProperty("strike", strike)
                result.tryPutProperty("text_color", textColor)
                result.tryPutProperty("text_shadow", textShadow)
                result.tryPutProperty("top_offset", topOffset)
                result.tryPutProperty("underline", underline)
                return result
            }
        }
    }

}

/**
 * @param text Text.
 * @param accessibility Accessibility settings.
 * @param action One action when clicking on an element. Not used if the `actions` parameter is set.
 * @param actionAnimation Click animation. The web only supports the following values: `fade`, `scale`, `native`, `no_animation` and `set`.
 * @param actions Multiple actions when clicking on an element.
 * @param alignmentHorizontal Horizontal alignment of an element inside the parent element.
 * @param alignmentVertical Vertical alignment of an element inside the parent element.
 * @param alpha Sets transparency of the entire element: `0` — completely transparent, `1` — opaque.
 * @param animators Declaration of animators that change variable values over time.
 * @param autoEllipsize Automatic text cropping to fit the container size.
 * @param background Element background. It can contain multiple layers.
 * @param border Element stroke.
 * @param captureFocusOnAction If the value is:<li>`true` - when the element action is activated, the focus will be moved to that element. That means that the accessibility focus will be moved and the virtual keyboard will be hidden, unless the target element implies its presence (e.g. `input`).</li><li>`false` - when you click on an element, the focus will remain on the currently focused element.</li>
 * @param columnSpan Merges cells in a column of the [grid](div-grid.md) element.
 * @param disappearActions Actions when an element disappears from the screen.
 * @param doubletapActions Action when double-clicking on an element.
 * @param ellipsis Text cropping marker. It is displayed when text size exceeds the limit on the number of lines.
 * @param extensions Extensions for additional processing of an element. The list of extensions is given in  [DivExtension](../../extensions).
 * @param focus Parameters when focusing on an element or losing focus.
 * @param focusedTextColor Text color when focusing on the element.
 * @param fontFamily Font family:<li>`text` — a standard text font;</li><li>`display` — a family of fonts with a large font size.</li>
 * @param fontFeatureSettings List of OpenType font features. The format matches the CSS attribute "font-feature-settings". Learn more: https://www.w3.org/TR/css-fonts-3/#font-feature-settings-prop
 * @param fontSize Font size.
 * @param fontSizeUnit Unit of measurement:<li>`px` — a physical pixel.</li><li>`dp` — a logical pixel that doesn't depend on screen density.</li><li>`sp` — a logical pixel that depends on the font size on a device. Specify height in `sp`. Only available on Android.</li>
 * @param fontVariationSettings List of TrueType/OpenType font features. The object is constructed from pairs of axis tag and style values. The axis tag must contain four ASCII characters.
 * @param fontWeight Style.
 * @param fontWeightValue Style. Numeric value.
 * @param functions User functions.
 * @param height Element height. For Android: if there is text in this or in a child element, specify height in `sp` to scale the element together with the text. To learn more about units of size measurement, see [Layout inside the card](../../layout).
 * @param hoverEndActions Actions performed after hovering over an element. Available on platforms that support pointing devices (such as a mouse or stylus).
 * @param hoverStartActions Actions performed when hovering over an element. Available on platforms that support pointing devices (such as a mouse or stylus).
 * @param id Element ID. It must be unique within the root element. It is used as `accessibilityIdentifier` on iOS.
 * @param images Images embedded in text.
 * @param layoutProvider Provides data on the actual size of the element.
 * @param letterSpacing Spacing between characters.
 * @param lineHeight Line spacing of the text.
 * @param longtapActions Action when long-clicking an element. Doesn't work on devices that don't support touch gestures.
 * @param margins External margins from the element stroke.
 * @param maxLines Maximum number of lines not to be cropped when breaking the limits.
 * @param minHiddenLines Minimum number of cropped lines when breaking the limits.
 * @param paddings Internal margins from the element stroke.
 * @param pressEndActions Actions performed after clicking/tapping an element.
 * @param pressStartActions Actions performed at the start of a click/tap on an element.
 * @param ranges A character range in which additional style parameters can be set. Defined by mandatory `start` and `end` fields.
 * @param reuseId ID for the div object structure. Used to optimize block reuse. See [block reuse](../../reuse/reuse.md).
 * @param rowSpan Merges cells in a string of the [grid](div-grid.md) element.
 * @param selectable Ability to select and copy text.
 * @param selectedActions List of [actions](div-action.md) to be executed when selecting an element in [pager](div-pager.md).
 * @param strike Strikethrough.
 * @param textAlignmentHorizontal Horizontal text alignment.
 * @param textAlignmentVertical Vertical text alignment.
 * @param textColor Text color.
 * @param textGradient Gradient text color.
 * @param textShadow Parameters of the shadow applied to the text.
 * @param tightenWidth Limit the text width to the maximum line width. Applies only when the width is set to `wrap_content`, `constrained=true`, and `max_size` is specified.
 * @param tooltips Tooltips linked to an element. A tooltip can be shown by `div-action://show_tooltip?id=`, hidden by `div-action://hide_tooltip?id=` where `id` — tooltip id.
 * @param transform Applies the passed transformation to the element. Content that doesn't fit into the original view area is cut off.
 * @param transitionChange Change animation. It is played when the position or size of an element changes in the new layout.
 * @param transitionIn Appearance animation. It is played when an element with a new ID appears. To learn more about the concept of transitions, see [Animated transitions](../../interaction#animation/transition-animation).
 * @param transitionOut Disappearance animation. It is played when an element disappears in the new layout.
 * @param transitionTriggers Animation starting triggers. Default value: `[state_change, visibility_change]`.
 * @param truncate Location of text cropping marker.
 * @param underline Underline.
 * @param variableTriggers Triggers for changing variables within an element.
 * @param variables Declaration of variables that can be used within an element. Variables declared in this array can only be used within the element and its child elements.
 * @param visibility Element visibility.
 * @param visibilityAction Tracking visibility of a single element. Not used if the `visibility_actions` parameter is set.
 * @param visibilityActions Actions when an element appears on the screen.
 * @param width Element width.
 */
@Generated
fun DivScope.text(
    text: String? = null,
    `use named arguments`: Guard = Guard.instance,
    accessibility: Accessibility? = null,
    action: Action? = null,
    actionAnimation: Animation? = null,
    actions: List<Action>? = null,
    alignmentHorizontal: AlignmentHorizontal? = null,
    alignmentVertical: AlignmentVertical? = null,
    alpha: Double? = null,
    animators: List<Animator>? = null,
    autoEllipsize: Boolean? = null,
    background: List<Background>? = null,
    border: Border? = null,
    captureFocusOnAction: Boolean? = null,
    columnSpan: Int? = null,
    disappearActions: List<DisappearAction>? = null,
    doubletapActions: List<Action>? = null,
    ellipsis: Text.Ellipsis? = null,
    extensions: List<Extension>? = null,
    focus: Focus? = null,
    focusedTextColor: Color? = null,
    fontFamily: String? = null,
    fontFeatureSettings: String? = null,
    fontSize: Int? = null,
    fontSizeUnit: SizeUnit? = null,
    fontVariationSettings: Map<String, Any>? = null,
    fontWeight: FontWeight? = null,
    fontWeightValue: Int? = null,
    functions: List<Function>? = null,
    height: Size? = null,
    hoverEndActions: List<Action>? = null,
    hoverStartActions: List<Action>? = null,
    id: String? = null,
    images: List<Text.Image>? = null,
    layoutProvider: LayoutProvider? = null,
    letterSpacing: Double? = null,
    lineHeight: Int? = null,
    longtapActions: List<Action>? = null,
    margins: EdgeInsets? = null,
    maxLines: Int? = null,
    minHiddenLines: Int? = null,
    paddings: EdgeInsets? = null,
    pressEndActions: List<Action>? = null,
    pressStartActions: List<Action>? = null,
    ranges: List<Text.Range>? = null,
    reuseId: String? = null,
    rowSpan: Int? = null,
    selectable: Boolean? = null,
    selectedActions: List<Action>? = null,
    strike: LineStyle? = null,
    textAlignmentHorizontal: AlignmentHorizontal? = null,
    textAlignmentVertical: AlignmentVertical? = null,
    textColor: Color? = null,
    textGradient: TextGradient? = null,
    textShadow: Shadow? = null,
    tightenWidth: Boolean? = null,
    tooltips: List<Tooltip>? = null,
    transform: Transform? = null,
    transitionChange: ChangeTransition? = null,
    transitionIn: AppearanceTransition? = null,
    transitionOut: AppearanceTransition? = null,
    transitionTriggers: List<ArrayElement<TransitionTrigger>>? = null,
    truncate: Text.Truncate? = null,
    underline: LineStyle? = null,
    variableTriggers: List<Trigger>? = null,
    variables: List<Variable>? = null,
    visibility: Visibility? = null,
    visibilityAction: VisibilityAction? = null,
    visibilityActions: List<VisibilityAction>? = null,
    width: Size? = null,
): Text = Text(
    Text.Properties(
        text = valueOrNull(text),
        accessibility = valueOrNull(accessibility),
        action = valueOrNull(action),
        actionAnimation = valueOrNull(actionAnimation),
        actions = valueOrNull(actions),
        alignmentHorizontal = valueOrNull(alignmentHorizontal),
        alignmentVertical = valueOrNull(alignmentVertical),
        alpha = valueOrNull(alpha),
        animators = valueOrNull(animators),
        autoEllipsize = valueOrNull(autoEllipsize),
        background = valueOrNull(background),
        border = valueOrNull(border),
        captureFocusOnAction = valueOrNull(captureFocusOnAction),
        columnSpan = valueOrNull(columnSpan),
        disappearActions = valueOrNull(disappearActions),
        doubletapActions = valueOrNull(doubletapActions),
        ellipsis = valueOrNull(ellipsis),
        extensions = valueOrNull(extensions),
        focus = valueOrNull(focus),
        focusedTextColor = valueOrNull(focusedTextColor),
        fontFamily = valueOrNull(fontFamily),
        fontFeatureSettings = valueOrNull(fontFeatureSettings),
        fontSize = valueOrNull(fontSize),
        fontSizeUnit = valueOrNull(fontSizeUnit),
        fontVariationSettings = valueOrNull(fontVariationSettings),
        fontWeight = valueOrNull(fontWeight),
        fontWeightValue = valueOrNull(fontWeightValue),
        functions = valueOrNull(functions),
        height = valueOrNull(height),
        hoverEndActions = valueOrNull(hoverEndActions),
        hoverStartActions = valueOrNull(hoverStartActions),
        id = valueOrNull(id),
        images = valueOrNull(images),
        layoutProvider = valueOrNull(layoutProvider),
        letterSpacing = valueOrNull(letterSpacing),
        lineHeight = valueOrNull(lineHeight),
        longtapActions = valueOrNull(longtapActions),
        margins = valueOrNull(margins),
        maxLines = valueOrNull(maxLines),
        minHiddenLines = valueOrNull(minHiddenLines),
        paddings = valueOrNull(paddings),
        pressEndActions = valueOrNull(pressEndActions),
        pressStartActions = valueOrNull(pressStartActions),
        ranges = valueOrNull(ranges),
        reuseId = valueOrNull(reuseId),
        rowSpan = valueOrNull(rowSpan),
        selectable = valueOrNull(selectable),
        selectedActions = valueOrNull(selectedActions),
        strike = valueOrNull(strike),
        textAlignmentHorizontal = valueOrNull(textAlignmentHorizontal),
        textAlignmentVertical = valueOrNull(textAlignmentVertical),
        textColor = valueOrNull(textColor),
        textGradient = valueOrNull(textGradient),
        textShadow = valueOrNull(textShadow),
        tightenWidth = valueOrNull(tightenWidth),
        tooltips = valueOrNull(tooltips),
        transform = valueOrNull(transform),
        transitionChange = valueOrNull(transitionChange),
        transitionIn = valueOrNull(transitionIn),
        transitionOut = valueOrNull(transitionOut),
        transitionTriggers = valueOrNull(transitionTriggers),
        truncate = valueOrNull(truncate),
        underline = valueOrNull(underline),
        variableTriggers = valueOrNull(variableTriggers),
        variables = valueOrNull(variables),
        visibility = valueOrNull(visibility),
        visibilityAction = valueOrNull(visibilityAction),
        visibilityActions = valueOrNull(visibilityActions),
        width = valueOrNull(width),
    )
)

/**
 * @param text Text.
 * @param accessibility Accessibility settings.
 * @param action One action when clicking on an element. Not used if the `actions` parameter is set.
 * @param actionAnimation Click animation. The web only supports the following values: `fade`, `scale`, `native`, `no_animation` and `set`.
 * @param actions Multiple actions when clicking on an element.
 * @param alignmentHorizontal Horizontal alignment of an element inside the parent element.
 * @param alignmentVertical Vertical alignment of an element inside the parent element.
 * @param alpha Sets transparency of the entire element: `0` — completely transparent, `1` — opaque.
 * @param animators Declaration of animators that change variable values over time.
 * @param autoEllipsize Automatic text cropping to fit the container size.
 * @param background Element background. It can contain multiple layers.
 * @param border Element stroke.
 * @param captureFocusOnAction If the value is:<li>`true` - when the element action is activated, the focus will be moved to that element. That means that the accessibility focus will be moved and the virtual keyboard will be hidden, unless the target element implies its presence (e.g. `input`).</li><li>`false` - when you click on an element, the focus will remain on the currently focused element.</li>
 * @param columnSpan Merges cells in a column of the [grid](div-grid.md) element.
 * @param disappearActions Actions when an element disappears from the screen.
 * @param doubletapActions Action when double-clicking on an element.
 * @param ellipsis Text cropping marker. It is displayed when text size exceeds the limit on the number of lines.
 * @param extensions Extensions for additional processing of an element. The list of extensions is given in  [DivExtension](../../extensions).
 * @param focus Parameters when focusing on an element or losing focus.
 * @param focusedTextColor Text color when focusing on the element.
 * @param fontFamily Font family:<li>`text` — a standard text font;</li><li>`display` — a family of fonts with a large font size.</li>
 * @param fontFeatureSettings List of OpenType font features. The format matches the CSS attribute "font-feature-settings". Learn more: https://www.w3.org/TR/css-fonts-3/#font-feature-settings-prop
 * @param fontSize Font size.
 * @param fontSizeUnit Unit of measurement:<li>`px` — a physical pixel.</li><li>`dp` — a logical pixel that doesn't depend on screen density.</li><li>`sp` — a logical pixel that depends on the font size on a device. Specify height in `sp`. Only available on Android.</li>
 * @param fontVariationSettings List of TrueType/OpenType font features. The object is constructed from pairs of axis tag and style values. The axis tag must contain four ASCII characters.
 * @param fontWeight Style.
 * @param fontWeightValue Style. Numeric value.
 * @param functions User functions.
 * @param height Element height. For Android: if there is text in this or in a child element, specify height in `sp` to scale the element together with the text. To learn more about units of size measurement, see [Layout inside the card](../../layout).
 * @param hoverEndActions Actions performed after hovering over an element. Available on platforms that support pointing devices (such as a mouse or stylus).
 * @param hoverStartActions Actions performed when hovering over an element. Available on platforms that support pointing devices (such as a mouse or stylus).
 * @param id Element ID. It must be unique within the root element. It is used as `accessibilityIdentifier` on iOS.
 * @param images Images embedded in text.
 * @param layoutProvider Provides data on the actual size of the element.
 * @param letterSpacing Spacing between characters.
 * @param lineHeight Line spacing of the text.
 * @param longtapActions Action when long-clicking an element. Doesn't work on devices that don't support touch gestures.
 * @param margins External margins from the element stroke.
 * @param maxLines Maximum number of lines not to be cropped when breaking the limits.
 * @param minHiddenLines Minimum number of cropped lines when breaking the limits.
 * @param paddings Internal margins from the element stroke.
 * @param pressEndActions Actions performed after clicking/tapping an element.
 * @param pressStartActions Actions performed at the start of a click/tap on an element.
 * @param ranges A character range in which additional style parameters can be set. Defined by mandatory `start` and `end` fields.
 * @param reuseId ID for the div object structure. Used to optimize block reuse. See [block reuse](../../reuse/reuse.md).
 * @param rowSpan Merges cells in a string of the [grid](div-grid.md) element.
 * @param selectable Ability to select and copy text.
 * @param selectedActions List of [actions](div-action.md) to be executed when selecting an element in [pager](div-pager.md).
 * @param strike Strikethrough.
 * @param textAlignmentHorizontal Horizontal text alignment.
 * @param textAlignmentVertical Vertical text alignment.
 * @param textColor Text color.
 * @param textGradient Gradient text color.
 * @param textShadow Parameters of the shadow applied to the text.
 * @param tightenWidth Limit the text width to the maximum line width. Applies only when the width is set to `wrap_content`, `constrained=true`, and `max_size` is specified.
 * @param tooltips Tooltips linked to an element. A tooltip can be shown by `div-action://show_tooltip?id=`, hidden by `div-action://hide_tooltip?id=` where `id` — tooltip id.
 * @param transform Applies the passed transformation to the element. Content that doesn't fit into the original view area is cut off.
 * @param transitionChange Change animation. It is played when the position or size of an element changes in the new layout.
 * @param transitionIn Appearance animation. It is played when an element with a new ID appears. To learn more about the concept of transitions, see [Animated transitions](../../interaction#animation/transition-animation).
 * @param transitionOut Disappearance animation. It is played when an element disappears in the new layout.
 * @param transitionTriggers Animation starting triggers. Default value: `[state_change, visibility_change]`.
 * @param truncate Location of text cropping marker.
 * @param underline Underline.
 * @param variableTriggers Triggers for changing variables within an element.
 * @param variables Declaration of variables that can be used within an element. Variables declared in this array can only be used within the element and its child elements.
 * @param visibility Element visibility.
 * @param visibilityAction Tracking visibility of a single element. Not used if the `visibility_actions` parameter is set.
 * @param visibilityActions Actions when an element appears on the screen.
 * @param width Element width.
 */
@Generated
fun DivScope.textProps(
    `use named arguments`: Guard = Guard.instance,
    text: String? = null,
    accessibility: Accessibility? = null,
    action: Action? = null,
    actionAnimation: Animation? = null,
    actions: List<Action>? = null,
    alignmentHorizontal: AlignmentHorizontal? = null,
    alignmentVertical: AlignmentVertical? = null,
    alpha: Double? = null,
    animators: List<Animator>? = null,
    autoEllipsize: Boolean? = null,
    background: List<Background>? = null,
    border: Border? = null,
    captureFocusOnAction: Boolean? = null,
    columnSpan: Int? = null,
    disappearActions: List<DisappearAction>? = null,
    doubletapActions: List<Action>? = null,
    ellipsis: Text.Ellipsis? = null,
    extensions: List<Extension>? = null,
    focus: Focus? = null,
    focusedTextColor: Color? = null,
    fontFamily: String? = null,
    fontFeatureSettings: String? = null,
    fontSize: Int? = null,
    fontSizeUnit: SizeUnit? = null,
    fontVariationSettings: Map<String, Any>? = null,
    fontWeight: FontWeight? = null,
    fontWeightValue: Int? = null,
    functions: List<Function>? = null,
    height: Size? = null,
    hoverEndActions: List<Action>? = null,
    hoverStartActions: List<Action>? = null,
    id: String? = null,
    images: List<Text.Image>? = null,
    layoutProvider: LayoutProvider? = null,
    letterSpacing: Double? = null,
    lineHeight: Int? = null,
    longtapActions: List<Action>? = null,
    margins: EdgeInsets? = null,
    maxLines: Int? = null,
    minHiddenLines: Int? = null,
    paddings: EdgeInsets? = null,
    pressEndActions: List<Action>? = null,
    pressStartActions: List<Action>? = null,
    ranges: List<Text.Range>? = null,
    reuseId: String? = null,
    rowSpan: Int? = null,
    selectable: Boolean? = null,
    selectedActions: List<Action>? = null,
    strike: LineStyle? = null,
    textAlignmentHorizontal: AlignmentHorizontal? = null,
    textAlignmentVertical: AlignmentVertical? = null,
    textColor: Color? = null,
    textGradient: TextGradient? = null,
    textShadow: Shadow? = null,
    tightenWidth: Boolean? = null,
    tooltips: List<Tooltip>? = null,
    transform: Transform? = null,
    transitionChange: ChangeTransition? = null,
    transitionIn: AppearanceTransition? = null,
    transitionOut: AppearanceTransition? = null,
    transitionTriggers: List<ArrayElement<TransitionTrigger>>? = null,
    truncate: Text.Truncate? = null,
    underline: LineStyle? = null,
    variableTriggers: List<Trigger>? = null,
    variables: List<Variable>? = null,
    visibility: Visibility? = null,
    visibilityAction: VisibilityAction? = null,
    visibilityActions: List<VisibilityAction>? = null,
    width: Size? = null,
) = Text.Properties(
    text = valueOrNull(text),
    accessibility = valueOrNull(accessibility),
    action = valueOrNull(action),
    actionAnimation = valueOrNull(actionAnimation),
    actions = valueOrNull(actions),
    alignmentHorizontal = valueOrNull(alignmentHorizontal),
    alignmentVertical = valueOrNull(alignmentVertical),
    alpha = valueOrNull(alpha),
    animators = valueOrNull(animators),
    autoEllipsize = valueOrNull(autoEllipsize),
    background = valueOrNull(background),
    border = valueOrNull(border),
    captureFocusOnAction = valueOrNull(captureFocusOnAction),
    columnSpan = valueOrNull(columnSpan),
    disappearActions = valueOrNull(disappearActions),
    doubletapActions = valueOrNull(doubletapActions),
    ellipsis = valueOrNull(ellipsis),
    extensions = valueOrNull(extensions),
    focus = valueOrNull(focus),
    focusedTextColor = valueOrNull(focusedTextColor),
    fontFamily = valueOrNull(fontFamily),
    fontFeatureSettings = valueOrNull(fontFeatureSettings),
    fontSize = valueOrNull(fontSize),
    fontSizeUnit = valueOrNull(fontSizeUnit),
    fontVariationSettings = valueOrNull(fontVariationSettings),
    fontWeight = valueOrNull(fontWeight),
    fontWeightValue = valueOrNull(fontWeightValue),
    functions = valueOrNull(functions),
    height = valueOrNull(height),
    hoverEndActions = valueOrNull(hoverEndActions),
    hoverStartActions = valueOrNull(hoverStartActions),
    id = valueOrNull(id),
    images = valueOrNull(images),
    layoutProvider = valueOrNull(layoutProvider),
    letterSpacing = valueOrNull(letterSpacing),
    lineHeight = valueOrNull(lineHeight),
    longtapActions = valueOrNull(longtapActions),
    margins = valueOrNull(margins),
    maxLines = valueOrNull(maxLines),
    minHiddenLines = valueOrNull(minHiddenLines),
    paddings = valueOrNull(paddings),
    pressEndActions = valueOrNull(pressEndActions),
    pressStartActions = valueOrNull(pressStartActions),
    ranges = valueOrNull(ranges),
    reuseId = valueOrNull(reuseId),
    rowSpan = valueOrNull(rowSpan),
    selectable = valueOrNull(selectable),
    selectedActions = valueOrNull(selectedActions),
    strike = valueOrNull(strike),
    textAlignmentHorizontal = valueOrNull(textAlignmentHorizontal),
    textAlignmentVertical = valueOrNull(textAlignmentVertical),
    textColor = valueOrNull(textColor),
    textGradient = valueOrNull(textGradient),
    textShadow = valueOrNull(textShadow),
    tightenWidth = valueOrNull(tightenWidth),
    tooltips = valueOrNull(tooltips),
    transform = valueOrNull(transform),
    transitionChange = valueOrNull(transitionChange),
    transitionIn = valueOrNull(transitionIn),
    transitionOut = valueOrNull(transitionOut),
    transitionTriggers = valueOrNull(transitionTriggers),
    truncate = valueOrNull(truncate),
    underline = valueOrNull(underline),
    variableTriggers = valueOrNull(variableTriggers),
    variables = valueOrNull(variables),
    visibility = valueOrNull(visibility),
    visibilityAction = valueOrNull(visibilityAction),
    visibilityActions = valueOrNull(visibilityActions),
    width = valueOrNull(width),
)

/**
 * @param text Text.
 * @param accessibility Accessibility settings.
 * @param action One action when clicking on an element. Not used if the `actions` parameter is set.
 * @param actionAnimation Click animation. The web only supports the following values: `fade`, `scale`, `native`, `no_animation` and `set`.
 * @param actions Multiple actions when clicking on an element.
 * @param alignmentHorizontal Horizontal alignment of an element inside the parent element.
 * @param alignmentVertical Vertical alignment of an element inside the parent element.
 * @param alpha Sets transparency of the entire element: `0` — completely transparent, `1` — opaque.
 * @param animators Declaration of animators that change variable values over time.
 * @param autoEllipsize Automatic text cropping to fit the container size.
 * @param background Element background. It can contain multiple layers.
 * @param border Element stroke.
 * @param captureFocusOnAction If the value is:<li>`true` - when the element action is activated, the focus will be moved to that element. That means that the accessibility focus will be moved and the virtual keyboard will be hidden, unless the target element implies its presence (e.g. `input`).</li><li>`false` - when you click on an element, the focus will remain on the currently focused element.</li>
 * @param columnSpan Merges cells in a column of the [grid](div-grid.md) element.
 * @param disappearActions Actions when an element disappears from the screen.
 * @param doubletapActions Action when double-clicking on an element.
 * @param ellipsis Text cropping marker. It is displayed when text size exceeds the limit on the number of lines.
 * @param extensions Extensions for additional processing of an element. The list of extensions is given in  [DivExtension](../../extensions).
 * @param focus Parameters when focusing on an element or losing focus.
 * @param focusedTextColor Text color when focusing on the element.
 * @param fontFamily Font family:<li>`text` — a standard text font;</li><li>`display` — a family of fonts with a large font size.</li>
 * @param fontFeatureSettings List of OpenType font features. The format matches the CSS attribute "font-feature-settings". Learn more: https://www.w3.org/TR/css-fonts-3/#font-feature-settings-prop
 * @param fontSize Font size.
 * @param fontSizeUnit Unit of measurement:<li>`px` — a physical pixel.</li><li>`dp` — a logical pixel that doesn't depend on screen density.</li><li>`sp` — a logical pixel that depends on the font size on a device. Specify height in `sp`. Only available on Android.</li>
 * @param fontVariationSettings List of TrueType/OpenType font features. The object is constructed from pairs of axis tag and style values. The axis tag must contain four ASCII characters.
 * @param fontWeight Style.
 * @param fontWeightValue Style. Numeric value.
 * @param functions User functions.
 * @param height Element height. For Android: if there is text in this or in a child element, specify height in `sp` to scale the element together with the text. To learn more about units of size measurement, see [Layout inside the card](../../layout).
 * @param hoverEndActions Actions performed after hovering over an element. Available on platforms that support pointing devices (such as a mouse or stylus).
 * @param hoverStartActions Actions performed when hovering over an element. Available on platforms that support pointing devices (such as a mouse or stylus).
 * @param id Element ID. It must be unique within the root element. It is used as `accessibilityIdentifier` on iOS.
 * @param images Images embedded in text.
 * @param layoutProvider Provides data on the actual size of the element.
 * @param letterSpacing Spacing between characters.
 * @param lineHeight Line spacing of the text.
 * @param longtapActions Action when long-clicking an element. Doesn't work on devices that don't support touch gestures.
 * @param margins External margins from the element stroke.
 * @param maxLines Maximum number of lines not to be cropped when breaking the limits.
 * @param minHiddenLines Minimum number of cropped lines when breaking the limits.
 * @param paddings Internal margins from the element stroke.
 * @param pressEndActions Actions performed after clicking/tapping an element.
 * @param pressStartActions Actions performed at the start of a click/tap on an element.
 * @param ranges A character range in which additional style parameters can be set. Defined by mandatory `start` and `end` fields.
 * @param reuseId ID for the div object structure. Used to optimize block reuse. See [block reuse](../../reuse/reuse.md).
 * @param rowSpan Merges cells in a string of the [grid](div-grid.md) element.
 * @param selectable Ability to select and copy text.
 * @param selectedActions List of [actions](div-action.md) to be executed when selecting an element in [pager](div-pager.md).
 * @param strike Strikethrough.
 * @param textAlignmentHorizontal Horizontal text alignment.
 * @param textAlignmentVertical Vertical text alignment.
 * @param textColor Text color.
 * @param textGradient Gradient text color.
 * @param textShadow Parameters of the shadow applied to the text.
 * @param tightenWidth Limit the text width to the maximum line width. Applies only when the width is set to `wrap_content`, `constrained=true`, and `max_size` is specified.
 * @param tooltips Tooltips linked to an element. A tooltip can be shown by `div-action://show_tooltip?id=`, hidden by `div-action://hide_tooltip?id=` where `id` — tooltip id.
 * @param transform Applies the passed transformation to the element. Content that doesn't fit into the original view area is cut off.
 * @param transitionChange Change animation. It is played when the position or size of an element changes in the new layout.
 * @param transitionIn Appearance animation. It is played when an element with a new ID appears. To learn more about the concept of transitions, see [Animated transitions](../../interaction#animation/transition-animation).
 * @param transitionOut Disappearance animation. It is played when an element disappears in the new layout.
 * @param transitionTriggers Animation starting triggers. Default value: `[state_change, visibility_change]`.
 * @param truncate Location of text cropping marker.
 * @param underline Underline.
 * @param variableTriggers Triggers for changing variables within an element.
 * @param variables Declaration of variables that can be used within an element. Variables declared in this array can only be used within the element and its child elements.
 * @param visibility Element visibility.
 * @param visibilityAction Tracking visibility of a single element. Not used if the `visibility_actions` parameter is set.
 * @param visibilityActions Actions when an element appears on the screen.
 * @param width Element width.
 */
@Generated
fun TemplateScope.textRefs(
    `use named arguments`: Guard = Guard.instance,
    text: ReferenceProperty<String>? = null,
    accessibility: ReferenceProperty<Accessibility>? = null,
    action: ReferenceProperty<Action>? = null,
    actionAnimation: ReferenceProperty<Animation>? = null,
    actions: ReferenceProperty<List<Action>>? = null,
    alignmentHorizontal: ReferenceProperty<AlignmentHorizontal>? = null,
    alignmentVertical: ReferenceProperty<AlignmentVertical>? = null,
    alpha: ReferenceProperty<Double>? = null,
    animators: ReferenceProperty<List<Animator>>? = null,
    autoEllipsize: ReferenceProperty<Boolean>? = null,
    background: ReferenceProperty<List<Background>>? = null,
    border: ReferenceProperty<Border>? = null,
    captureFocusOnAction: ReferenceProperty<Boolean>? = null,
    columnSpan: ReferenceProperty<Int>? = null,
    disappearActions: ReferenceProperty<List<DisappearAction>>? = null,
    doubletapActions: ReferenceProperty<List<Action>>? = null,
    ellipsis: ReferenceProperty<Text.Ellipsis>? = null,
    extensions: ReferenceProperty<List<Extension>>? = null,
    focus: ReferenceProperty<Focus>? = null,
    focusedTextColor: ReferenceProperty<Color>? = null,
    fontFamily: ReferenceProperty<String>? = null,
    fontFeatureSettings: ReferenceProperty<String>? = null,
    fontSize: ReferenceProperty<Int>? = null,
    fontSizeUnit: ReferenceProperty<SizeUnit>? = null,
    fontVariationSettings: ReferenceProperty<Map<String, Any>>? = null,
    fontWeight: ReferenceProperty<FontWeight>? = null,
    fontWeightValue: ReferenceProperty<Int>? = null,
    functions: ReferenceProperty<List<Function>>? = null,
    height: ReferenceProperty<Size>? = null,
    hoverEndActions: ReferenceProperty<List<Action>>? = null,
    hoverStartActions: ReferenceProperty<List<Action>>? = null,
    id: ReferenceProperty<String>? = null,
    images: ReferenceProperty<List<Text.Image>>? = null,
    layoutProvider: ReferenceProperty<LayoutProvider>? = null,
    letterSpacing: ReferenceProperty<Double>? = null,
    lineHeight: ReferenceProperty<Int>? = null,
    longtapActions: ReferenceProperty<List<Action>>? = null,
    margins: ReferenceProperty<EdgeInsets>? = null,
    maxLines: ReferenceProperty<Int>? = null,
    minHiddenLines: ReferenceProperty<Int>? = null,
    paddings: ReferenceProperty<EdgeInsets>? = null,
    pressEndActions: ReferenceProperty<List<Action>>? = null,
    pressStartActions: ReferenceProperty<List<Action>>? = null,
    ranges: ReferenceProperty<List<Text.Range>>? = null,
    reuseId: ReferenceProperty<String>? = null,
    rowSpan: ReferenceProperty<Int>? = null,
    selectable: ReferenceProperty<Boolean>? = null,
    selectedActions: ReferenceProperty<List<Action>>? = null,
    strike: ReferenceProperty<LineStyle>? = null,
    textAlignmentHorizontal: ReferenceProperty<AlignmentHorizontal>? = null,
    textAlignmentVertical: ReferenceProperty<AlignmentVertical>? = null,
    textColor: ReferenceProperty<Color>? = null,
    textGradient: ReferenceProperty<TextGradient>? = null,
    textShadow: ReferenceProperty<Shadow>? = null,
    tightenWidth: ReferenceProperty<Boolean>? = null,
    tooltips: ReferenceProperty<List<Tooltip>>? = null,
    transform: ReferenceProperty<Transform>? = null,
    transitionChange: ReferenceProperty<ChangeTransition>? = null,
    transitionIn: ReferenceProperty<AppearanceTransition>? = null,
    transitionOut: ReferenceProperty<AppearanceTransition>? = null,
    transitionTriggers: ReferenceProperty<List<ArrayElement<TransitionTrigger>>>? = null,
    truncate: ReferenceProperty<Text.Truncate>? = null,
    underline: ReferenceProperty<LineStyle>? = null,
    variableTriggers: ReferenceProperty<List<Trigger>>? = null,
    variables: ReferenceProperty<List<Variable>>? = null,
    visibility: ReferenceProperty<Visibility>? = null,
    visibilityAction: ReferenceProperty<VisibilityAction>? = null,
    visibilityActions: ReferenceProperty<List<VisibilityAction>>? = null,
    width: ReferenceProperty<Size>? = null,
) = Text.Properties(
    text = text,
    accessibility = accessibility,
    action = action,
    actionAnimation = actionAnimation,
    actions = actions,
    alignmentHorizontal = alignmentHorizontal,
    alignmentVertical = alignmentVertical,
    alpha = alpha,
    animators = animators,
    autoEllipsize = autoEllipsize,
    background = background,
    border = border,
    captureFocusOnAction = captureFocusOnAction,
    columnSpan = columnSpan,
    disappearActions = disappearActions,
    doubletapActions = doubletapActions,
    ellipsis = ellipsis,
    extensions = extensions,
    focus = focus,
    focusedTextColor = focusedTextColor,
    fontFamily = fontFamily,
    fontFeatureSettings = fontFeatureSettings,
    fontSize = fontSize,
    fontSizeUnit = fontSizeUnit,
    fontVariationSettings = fontVariationSettings,
    fontWeight = fontWeight,
    fontWeightValue = fontWeightValue,
    functions = functions,
    height = height,
    hoverEndActions = hoverEndActions,
    hoverStartActions = hoverStartActions,
    id = id,
    images = images,
    layoutProvider = layoutProvider,
    letterSpacing = letterSpacing,
    lineHeight = lineHeight,
    longtapActions = longtapActions,
    margins = margins,
    maxLines = maxLines,
    minHiddenLines = minHiddenLines,
    paddings = paddings,
    pressEndActions = pressEndActions,
    pressStartActions = pressStartActions,
    ranges = ranges,
    reuseId = reuseId,
    rowSpan = rowSpan,
    selectable = selectable,
    selectedActions = selectedActions,
    strike = strike,
    textAlignmentHorizontal = textAlignmentHorizontal,
    textAlignmentVertical = textAlignmentVertical,
    textColor = textColor,
    textGradient = textGradient,
    textShadow = textShadow,
    tightenWidth = tightenWidth,
    tooltips = tooltips,
    transform = transform,
    transitionChange = transitionChange,
    transitionIn = transitionIn,
    transitionOut = transitionOut,
    transitionTriggers = transitionTriggers,
    truncate = truncate,
    underline = underline,
    variableTriggers = variableTriggers,
    variables = variables,
    visibility = visibility,
    visibilityAction = visibilityAction,
    visibilityActions = visibilityActions,
    width = width,
)

/**
 * @param text Text.
 * @param accessibility Accessibility settings.
 * @param action One action when clicking on an element. Not used if the `actions` parameter is set.
 * @param actionAnimation Click animation. The web only supports the following values: `fade`, `scale`, `native`, `no_animation` and `set`.
 * @param actions Multiple actions when clicking on an element.
 * @param alignmentHorizontal Horizontal alignment of an element inside the parent element.
 * @param alignmentVertical Vertical alignment of an element inside the parent element.
 * @param alpha Sets transparency of the entire element: `0` — completely transparent, `1` — opaque.
 * @param animators Declaration of animators that change variable values over time.
 * @param autoEllipsize Automatic text cropping to fit the container size.
 * @param background Element background. It can contain multiple layers.
 * @param border Element stroke.
 * @param captureFocusOnAction If the value is:<li>`true` - when the element action is activated, the focus will be moved to that element. That means that the accessibility focus will be moved and the virtual keyboard will be hidden, unless the target element implies its presence (e.g. `input`).</li><li>`false` - when you click on an element, the focus will remain on the currently focused element.</li>
 * @param columnSpan Merges cells in a column of the [grid](div-grid.md) element.
 * @param disappearActions Actions when an element disappears from the screen.
 * @param doubletapActions Action when double-clicking on an element.
 * @param ellipsis Text cropping marker. It is displayed when text size exceeds the limit on the number of lines.
 * @param extensions Extensions for additional processing of an element. The list of extensions is given in  [DivExtension](../../extensions).
 * @param focus Parameters when focusing on an element or losing focus.
 * @param focusedTextColor Text color when focusing on the element.
 * @param fontFamily Font family:<li>`text` — a standard text font;</li><li>`display` — a family of fonts with a large font size.</li>
 * @param fontFeatureSettings List of OpenType font features. The format matches the CSS attribute "font-feature-settings". Learn more: https://www.w3.org/TR/css-fonts-3/#font-feature-settings-prop
 * @param fontSize Font size.
 * @param fontSizeUnit Unit of measurement:<li>`px` — a physical pixel.</li><li>`dp` — a logical pixel that doesn't depend on screen density.</li><li>`sp` — a logical pixel that depends on the font size on a device. Specify height in `sp`. Only available on Android.</li>
 * @param fontVariationSettings List of TrueType/OpenType font features. The object is constructed from pairs of axis tag and style values. The axis tag must contain four ASCII characters.
 * @param fontWeight Style.
 * @param fontWeightValue Style. Numeric value.
 * @param functions User functions.
 * @param height Element height. For Android: if there is text in this or in a child element, specify height in `sp` to scale the element together with the text. To learn more about units of size measurement, see [Layout inside the card](../../layout).
 * @param hoverEndActions Actions performed after hovering over an element. Available on platforms that support pointing devices (such as a mouse or stylus).
 * @param hoverStartActions Actions performed when hovering over an element. Available on platforms that support pointing devices (such as a mouse or stylus).
 * @param id Element ID. It must be unique within the root element. It is used as `accessibilityIdentifier` on iOS.
 * @param images Images embedded in text.
 * @param layoutProvider Provides data on the actual size of the element.
 * @param letterSpacing Spacing between characters.
 * @param lineHeight Line spacing of the text.
 * @param longtapActions Action when long-clicking an element. Doesn't work on devices that don't support touch gestures.
 * @param margins External margins from the element stroke.
 * @param maxLines Maximum number of lines not to be cropped when breaking the limits.
 * @param minHiddenLines Minimum number of cropped lines when breaking the limits.
 * @param paddings Internal margins from the element stroke.
 * @param pressEndActions Actions performed after clicking/tapping an element.
 * @param pressStartActions Actions performed at the start of a click/tap on an element.
 * @param ranges A character range in which additional style parameters can be set. Defined by mandatory `start` and `end` fields.
 * @param reuseId ID for the div object structure. Used to optimize block reuse. See [block reuse](../../reuse/reuse.md).
 * @param rowSpan Merges cells in a string of the [grid](div-grid.md) element.
 * @param selectable Ability to select and copy text.
 * @param selectedActions List of [actions](div-action.md) to be executed when selecting an element in [pager](div-pager.md).
 * @param strike Strikethrough.
 * @param textAlignmentHorizontal Horizontal text alignment.
 * @param textAlignmentVertical Vertical text alignment.
 * @param textColor Text color.
 * @param textGradient Gradient text color.
 * @param textShadow Parameters of the shadow applied to the text.
 * @param tightenWidth Limit the text width to the maximum line width. Applies only when the width is set to `wrap_content`, `constrained=true`, and `max_size` is specified.
 * @param tooltips Tooltips linked to an element. A tooltip can be shown by `div-action://show_tooltip?id=`, hidden by `div-action://hide_tooltip?id=` where `id` — tooltip id.
 * @param transform Applies the passed transformation to the element. Content that doesn't fit into the original view area is cut off.
 * @param transitionChange Change animation. It is played when the position or size of an element changes in the new layout.
 * @param transitionIn Appearance animation. It is played when an element with a new ID appears. To learn more about the concept of transitions, see [Animated transitions](../../interaction#animation/transition-animation).
 * @param transitionOut Disappearance animation. It is played when an element disappears in the new layout.
 * @param transitionTriggers Animation starting triggers. Default value: `[state_change, visibility_change]`.
 * @param truncate Location of text cropping marker.
 * @param underline Underline.
 * @param variableTriggers Triggers for changing variables within an element.
 * @param variables Declaration of variables that can be used within an element. Variables declared in this array can only be used within the element and its child elements.
 * @param visibility Element visibility.
 * @param visibilityAction Tracking visibility of a single element. Not used if the `visibility_actions` parameter is set.
 * @param visibilityActions Actions when an element appears on the screen.
 * @param width Element width.
 */
@Generated
fun Text.override(
    `use named arguments`: Guard = Guard.instance,
    text: String? = null,
    accessibility: Accessibility? = null,
    action: Action? = null,
    actionAnimation: Animation? = null,
    actions: List<Action>? = null,
    alignmentHorizontal: AlignmentHorizontal? = null,
    alignmentVertical: AlignmentVertical? = null,
    alpha: Double? = null,
    animators: List<Animator>? = null,
    autoEllipsize: Boolean? = null,
    background: List<Background>? = null,
    border: Border? = null,
    captureFocusOnAction: Boolean? = null,
    columnSpan: Int? = null,
    disappearActions: List<DisappearAction>? = null,
    doubletapActions: List<Action>? = null,
    ellipsis: Text.Ellipsis? = null,
    extensions: List<Extension>? = null,
    focus: Focus? = null,
    focusedTextColor: Color? = null,
    fontFamily: String? = null,
    fontFeatureSettings: String? = null,
    fontSize: Int? = null,
    fontSizeUnit: SizeUnit? = null,
    fontVariationSettings: Map<String, Any>? = null,
    fontWeight: FontWeight? = null,
    fontWeightValue: Int? = null,
    functions: List<Function>? = null,
    height: Size? = null,
    hoverEndActions: List<Action>? = null,
    hoverStartActions: List<Action>? = null,
    id: String? = null,
    images: List<Text.Image>? = null,
    layoutProvider: LayoutProvider? = null,
    letterSpacing: Double? = null,
    lineHeight: Int? = null,
    longtapActions: List<Action>? = null,
    margins: EdgeInsets? = null,
    maxLines: Int? = null,
    minHiddenLines: Int? = null,
    paddings: EdgeInsets? = null,
    pressEndActions: List<Action>? = null,
    pressStartActions: List<Action>? = null,
    ranges: List<Text.Range>? = null,
    reuseId: String? = null,
    rowSpan: Int? = null,
    selectable: Boolean? = null,
    selectedActions: List<Action>? = null,
    strike: LineStyle? = null,
    textAlignmentHorizontal: AlignmentHorizontal? = null,
    textAlignmentVertical: AlignmentVertical? = null,
    textColor: Color? = null,
    textGradient: TextGradient? = null,
    textShadow: Shadow? = null,
    tightenWidth: Boolean? = null,
    tooltips: List<Tooltip>? = null,
    transform: Transform? = null,
    transitionChange: ChangeTransition? = null,
    transitionIn: AppearanceTransition? = null,
    transitionOut: AppearanceTransition? = null,
    transitionTriggers: List<ArrayElement<TransitionTrigger>>? = null,
    truncate: Text.Truncate? = null,
    underline: LineStyle? = null,
    variableTriggers: List<Trigger>? = null,
    variables: List<Variable>? = null,
    visibility: Visibility? = null,
    visibilityAction: VisibilityAction? = null,
    visibilityActions: List<VisibilityAction>? = null,
    width: Size? = null,
): Text = Text(
    Text.Properties(
        text = valueOrNull(text) ?: properties.text,
        accessibility = valueOrNull(accessibility) ?: properties.accessibility,
        action = valueOrNull(action) ?: properties.action,
        actionAnimation = valueOrNull(actionAnimation) ?: properties.actionAnimation,
        actions = valueOrNull(actions) ?: properties.actions,
        alignmentHorizontal = valueOrNull(alignmentHorizontal) ?: properties.alignmentHorizontal,
        alignmentVertical = valueOrNull(alignmentVertical) ?: properties.alignmentVertical,
        alpha = valueOrNull(alpha) ?: properties.alpha,
        animators = valueOrNull(animators) ?: properties.animators,
        autoEllipsize = valueOrNull(autoEllipsize) ?: properties.autoEllipsize,
        background = valueOrNull(background) ?: properties.background,
        border = valueOrNull(border) ?: properties.border,
        captureFocusOnAction = valueOrNull(captureFocusOnAction) ?: properties.captureFocusOnAction,
        columnSpan = valueOrNull(columnSpan) ?: properties.columnSpan,
        disappearActions = valueOrNull(disappearActions) ?: properties.disappearActions,
        doubletapActions = valueOrNull(doubletapActions) ?: properties.doubletapActions,
        ellipsis = valueOrNull(ellipsis) ?: properties.ellipsis,
        extensions = valueOrNull(extensions) ?: properties.extensions,
        focus = valueOrNull(focus) ?: properties.focus,
        focusedTextColor = valueOrNull(focusedTextColor) ?: properties.focusedTextColor,
        fontFamily = valueOrNull(fontFamily) ?: properties.fontFamily,
        fontFeatureSettings = valueOrNull(fontFeatureSettings) ?: properties.fontFeatureSettings,
        fontSize = valueOrNull(fontSize) ?: properties.fontSize,
        fontSizeUnit = valueOrNull(fontSizeUnit) ?: properties.fontSizeUnit,
        fontVariationSettings = valueOrNull(fontVariationSettings) ?: properties.fontVariationSettings,
        fontWeight = valueOrNull(fontWeight) ?: properties.fontWeight,
        fontWeightValue = valueOrNull(fontWeightValue) ?: properties.fontWeightValue,
        functions = valueOrNull(functions) ?: properties.functions,
        height = valueOrNull(height) ?: properties.height,
        hoverEndActions = valueOrNull(hoverEndActions) ?: properties.hoverEndActions,
        hoverStartActions = valueOrNull(hoverStartActions) ?: properties.hoverStartActions,
        id = valueOrNull(id) ?: properties.id,
        images = valueOrNull(images) ?: properties.images,
        layoutProvider = valueOrNull(layoutProvider) ?: properties.layoutProvider,
        letterSpacing = valueOrNull(letterSpacing) ?: properties.letterSpacing,
        lineHeight = valueOrNull(lineHeight) ?: properties.lineHeight,
        longtapActions = valueOrNull(longtapActions) ?: properties.longtapActions,
        margins = valueOrNull(margins) ?: properties.margins,
        maxLines = valueOrNull(maxLines) ?: properties.maxLines,
        minHiddenLines = valueOrNull(minHiddenLines) ?: properties.minHiddenLines,
        paddings = valueOrNull(paddings) ?: properties.paddings,
        pressEndActions = valueOrNull(pressEndActions) ?: properties.pressEndActions,
        pressStartActions = valueOrNull(pressStartActions) ?: properties.pressStartActions,
        ranges = valueOrNull(ranges) ?: properties.ranges,
        reuseId = valueOrNull(reuseId) ?: properties.reuseId,
        rowSpan = valueOrNull(rowSpan) ?: properties.rowSpan,
        selectable = valueOrNull(selectable) ?: properties.selectable,
        selectedActions = valueOrNull(selectedActions) ?: properties.selectedActions,
        strike = valueOrNull(strike) ?: properties.strike,
        textAlignmentHorizontal = valueOrNull(textAlignmentHorizontal) ?: properties.textAlignmentHorizontal,
        textAlignmentVertical = valueOrNull(textAlignmentVertical) ?: properties.textAlignmentVertical,
        textColor = valueOrNull(textColor) ?: properties.textColor,
        textGradient = valueOrNull(textGradient) ?: properties.textGradient,
        textShadow = valueOrNull(textShadow) ?: properties.textShadow,
        tightenWidth = valueOrNull(tightenWidth) ?: properties.tightenWidth,
        tooltips = valueOrNull(tooltips) ?: properties.tooltips,
        transform = valueOrNull(transform) ?: properties.transform,
        transitionChange = valueOrNull(transitionChange) ?: properties.transitionChange,
        transitionIn = valueOrNull(transitionIn) ?: properties.transitionIn,
        transitionOut = valueOrNull(transitionOut) ?: properties.transitionOut,
        transitionTriggers = valueOrNull(transitionTriggers) ?: properties.transitionTriggers,
        truncate = valueOrNull(truncate) ?: properties.truncate,
        underline = valueOrNull(underline) ?: properties.underline,
        variableTriggers = valueOrNull(variableTriggers) ?: properties.variableTriggers,
        variables = valueOrNull(variables) ?: properties.variables,
        visibility = valueOrNull(visibility) ?: properties.visibility,
        visibilityAction = valueOrNull(visibilityAction) ?: properties.visibilityAction,
        visibilityActions = valueOrNull(visibilityActions) ?: properties.visibilityActions,
        width = valueOrNull(width) ?: properties.width,
    )
)

/**
 * @param text Text.
 * @param accessibility Accessibility settings.
 * @param action One action when clicking on an element. Not used if the `actions` parameter is set.
 * @param actionAnimation Click animation. The web only supports the following values: `fade`, `scale`, `native`, `no_animation` and `set`.
 * @param actions Multiple actions when clicking on an element.
 * @param alignmentHorizontal Horizontal alignment of an element inside the parent element.
 * @param alignmentVertical Vertical alignment of an element inside the parent element.
 * @param alpha Sets transparency of the entire element: `0` — completely transparent, `1` — opaque.
 * @param animators Declaration of animators that change variable values over time.
 * @param autoEllipsize Automatic text cropping to fit the container size.
 * @param background Element background. It can contain multiple layers.
 * @param border Element stroke.
 * @param captureFocusOnAction If the value is:<li>`true` - when the element action is activated, the focus will be moved to that element. That means that the accessibility focus will be moved and the virtual keyboard will be hidden, unless the target element implies its presence (e.g. `input`).</li><li>`false` - when you click on an element, the focus will remain on the currently focused element.</li>
 * @param columnSpan Merges cells in a column of the [grid](div-grid.md) element.
 * @param disappearActions Actions when an element disappears from the screen.
 * @param doubletapActions Action when double-clicking on an element.
 * @param ellipsis Text cropping marker. It is displayed when text size exceeds the limit on the number of lines.
 * @param extensions Extensions for additional processing of an element. The list of extensions is given in  [DivExtension](../../extensions).
 * @param focus Parameters when focusing on an element or losing focus.
 * @param focusedTextColor Text color when focusing on the element.
 * @param fontFamily Font family:<li>`text` — a standard text font;</li><li>`display` — a family of fonts with a large font size.</li>
 * @param fontFeatureSettings List of OpenType font features. The format matches the CSS attribute "font-feature-settings". Learn more: https://www.w3.org/TR/css-fonts-3/#font-feature-settings-prop
 * @param fontSize Font size.
 * @param fontSizeUnit Unit of measurement:<li>`px` — a physical pixel.</li><li>`dp` — a logical pixel that doesn't depend on screen density.</li><li>`sp` — a logical pixel that depends on the font size on a device. Specify height in `sp`. Only available on Android.</li>
 * @param fontVariationSettings List of TrueType/OpenType font features. The object is constructed from pairs of axis tag and style values. The axis tag must contain four ASCII characters.
 * @param fontWeight Style.
 * @param fontWeightValue Style. Numeric value.
 * @param functions User functions.
 * @param height Element height. For Android: if there is text in this or in a child element, specify height in `sp` to scale the element together with the text. To learn more about units of size measurement, see [Layout inside the card](../../layout).
 * @param hoverEndActions Actions performed after hovering over an element. Available on platforms that support pointing devices (such as a mouse or stylus).
 * @param hoverStartActions Actions performed when hovering over an element. Available on platforms that support pointing devices (such as a mouse or stylus).
 * @param id Element ID. It must be unique within the root element. It is used as `accessibilityIdentifier` on iOS.
 * @param images Images embedded in text.
 * @param layoutProvider Provides data on the actual size of the element.
 * @param letterSpacing Spacing between characters.
 * @param lineHeight Line spacing of the text.
 * @param longtapActions Action when long-clicking an element. Doesn't work on devices that don't support touch gestures.
 * @param margins External margins from the element stroke.
 * @param maxLines Maximum number of lines not to be cropped when breaking the limits.
 * @param minHiddenLines Minimum number of cropped lines when breaking the limits.
 * @param paddings Internal margins from the element stroke.
 * @param pressEndActions Actions performed after clicking/tapping an element.
 * @param pressStartActions Actions performed at the start of a click/tap on an element.
 * @param ranges A character range in which additional style parameters can be set. Defined by mandatory `start` and `end` fields.
 * @param reuseId ID for the div object structure. Used to optimize block reuse. See [block reuse](../../reuse/reuse.md).
 * @param rowSpan Merges cells in a string of the [grid](div-grid.md) element.
 * @param selectable Ability to select and copy text.
 * @param selectedActions List of [actions](div-action.md) to be executed when selecting an element in [pager](div-pager.md).
 * @param strike Strikethrough.
 * @param textAlignmentHorizontal Horizontal text alignment.
 * @param textAlignmentVertical Vertical text alignment.
 * @param textColor Text color.
 * @param textGradient Gradient text color.
 * @param textShadow Parameters of the shadow applied to the text.
 * @param tightenWidth Limit the text width to the maximum line width. Applies only when the width is set to `wrap_content`, `constrained=true`, and `max_size` is specified.
 * @param tooltips Tooltips linked to an element. A tooltip can be shown by `div-action://show_tooltip?id=`, hidden by `div-action://hide_tooltip?id=` where `id` — tooltip id.
 * @param transform Applies the passed transformation to the element. Content that doesn't fit into the original view area is cut off.
 * @param transitionChange Change animation. It is played when the position or size of an element changes in the new layout.
 * @param transitionIn Appearance animation. It is played when an element with a new ID appears. To learn more about the concept of transitions, see [Animated transitions](../../interaction#animation/transition-animation).
 * @param transitionOut Disappearance animation. It is played when an element disappears in the new layout.
 * @param transitionTriggers Animation starting triggers. Default value: `[state_change, visibility_change]`.
 * @param truncate Location of text cropping marker.
 * @param underline Underline.
 * @param variableTriggers Triggers for changing variables within an element.
 * @param variables Declaration of variables that can be used within an element. Variables declared in this array can only be used within the element and its child elements.
 * @param visibility Element visibility.
 * @param visibilityAction Tracking visibility of a single element. Not used if the `visibility_actions` parameter is set.
 * @param visibilityActions Actions when an element appears on the screen.
 * @param width Element width.
 */
@Generated
fun Text.defer(
    `use named arguments`: Guard = Guard.instance,
    text: ReferenceProperty<String>? = null,
    accessibility: ReferenceProperty<Accessibility>? = null,
    action: ReferenceProperty<Action>? = null,
    actionAnimation: ReferenceProperty<Animation>? = null,
    actions: ReferenceProperty<List<Action>>? = null,
    alignmentHorizontal: ReferenceProperty<AlignmentHorizontal>? = null,
    alignmentVertical: ReferenceProperty<AlignmentVertical>? = null,
    alpha: ReferenceProperty<Double>? = null,
    animators: ReferenceProperty<List<Animator>>? = null,
    autoEllipsize: ReferenceProperty<Boolean>? = null,
    background: ReferenceProperty<List<Background>>? = null,
    border: ReferenceProperty<Border>? = null,
    captureFocusOnAction: ReferenceProperty<Boolean>? = null,
    columnSpan: ReferenceProperty<Int>? = null,
    disappearActions: ReferenceProperty<List<DisappearAction>>? = null,
    doubletapActions: ReferenceProperty<List<Action>>? = null,
    ellipsis: ReferenceProperty<Text.Ellipsis>? = null,
    extensions: ReferenceProperty<List<Extension>>? = null,
    focus: ReferenceProperty<Focus>? = null,
    focusedTextColor: ReferenceProperty<Color>? = null,
    fontFamily: ReferenceProperty<String>? = null,
    fontFeatureSettings: ReferenceProperty<String>? = null,
    fontSize: ReferenceProperty<Int>? = null,
    fontSizeUnit: ReferenceProperty<SizeUnit>? = null,
    fontVariationSettings: ReferenceProperty<Map<String, Any>>? = null,
    fontWeight: ReferenceProperty<FontWeight>? = null,
    fontWeightValue: ReferenceProperty<Int>? = null,
    functions: ReferenceProperty<List<Function>>? = null,
    height: ReferenceProperty<Size>? = null,
    hoverEndActions: ReferenceProperty<List<Action>>? = null,
    hoverStartActions: ReferenceProperty<List<Action>>? = null,
    id: ReferenceProperty<String>? = null,
    images: ReferenceProperty<List<Text.Image>>? = null,
    layoutProvider: ReferenceProperty<LayoutProvider>? = null,
    letterSpacing: ReferenceProperty<Double>? = null,
    lineHeight: ReferenceProperty<Int>? = null,
    longtapActions: ReferenceProperty<List<Action>>? = null,
    margins: ReferenceProperty<EdgeInsets>? = null,
    maxLines: ReferenceProperty<Int>? = null,
    minHiddenLines: ReferenceProperty<Int>? = null,
    paddings: ReferenceProperty<EdgeInsets>? = null,
    pressEndActions: ReferenceProperty<List<Action>>? = null,
    pressStartActions: ReferenceProperty<List<Action>>? = null,
    ranges: ReferenceProperty<List<Text.Range>>? = null,
    reuseId: ReferenceProperty<String>? = null,
    rowSpan: ReferenceProperty<Int>? = null,
    selectable: ReferenceProperty<Boolean>? = null,
    selectedActions: ReferenceProperty<List<Action>>? = null,
    strike: ReferenceProperty<LineStyle>? = null,
    textAlignmentHorizontal: ReferenceProperty<AlignmentHorizontal>? = null,
    textAlignmentVertical: ReferenceProperty<AlignmentVertical>? = null,
    textColor: ReferenceProperty<Color>? = null,
    textGradient: ReferenceProperty<TextGradient>? = null,
    textShadow: ReferenceProperty<Shadow>? = null,
    tightenWidth: ReferenceProperty<Boolean>? = null,
    tooltips: ReferenceProperty<List<Tooltip>>? = null,
    transform: ReferenceProperty<Transform>? = null,
    transitionChange: ReferenceProperty<ChangeTransition>? = null,
    transitionIn: ReferenceProperty<AppearanceTransition>? = null,
    transitionOut: ReferenceProperty<AppearanceTransition>? = null,
    transitionTriggers: ReferenceProperty<List<ArrayElement<TransitionTrigger>>>? = null,
    truncate: ReferenceProperty<Text.Truncate>? = null,
    underline: ReferenceProperty<LineStyle>? = null,
    variableTriggers: ReferenceProperty<List<Trigger>>? = null,
    variables: ReferenceProperty<List<Variable>>? = null,
    visibility: ReferenceProperty<Visibility>? = null,
    visibilityAction: ReferenceProperty<VisibilityAction>? = null,
    visibilityActions: ReferenceProperty<List<VisibilityAction>>? = null,
    width: ReferenceProperty<Size>? = null,
): Text = Text(
    Text.Properties(
        text = text ?: properties.text,
        accessibility = accessibility ?: properties.accessibility,
        action = action ?: properties.action,
        actionAnimation = actionAnimation ?: properties.actionAnimation,
        actions = actions ?: properties.actions,
        alignmentHorizontal = alignmentHorizontal ?: properties.alignmentHorizontal,
        alignmentVertical = alignmentVertical ?: properties.alignmentVertical,
        alpha = alpha ?: properties.alpha,
        animators = animators ?: properties.animators,
        autoEllipsize = autoEllipsize ?: properties.autoEllipsize,
        background = background ?: properties.background,
        border = border ?: properties.border,
        captureFocusOnAction = captureFocusOnAction ?: properties.captureFocusOnAction,
        columnSpan = columnSpan ?: properties.columnSpan,
        disappearActions = disappearActions ?: properties.disappearActions,
        doubletapActions = doubletapActions ?: properties.doubletapActions,
        ellipsis = ellipsis ?: properties.ellipsis,
        extensions = extensions ?: properties.extensions,
        focus = focus ?: properties.focus,
        focusedTextColor = focusedTextColor ?: properties.focusedTextColor,
        fontFamily = fontFamily ?: properties.fontFamily,
        fontFeatureSettings = fontFeatureSettings ?: properties.fontFeatureSettings,
        fontSize = fontSize ?: properties.fontSize,
        fontSizeUnit = fontSizeUnit ?: properties.fontSizeUnit,
        fontVariationSettings = fontVariationSettings ?: properties.fontVariationSettings,
        fontWeight = fontWeight ?: properties.fontWeight,
        fontWeightValue = fontWeightValue ?: properties.fontWeightValue,
        functions = functions ?: properties.functions,
        height = height ?: properties.height,
        hoverEndActions = hoverEndActions ?: properties.hoverEndActions,
        hoverStartActions = hoverStartActions ?: properties.hoverStartActions,
        id = id ?: properties.id,
        images = images ?: properties.images,
        layoutProvider = layoutProvider ?: properties.layoutProvider,
        letterSpacing = letterSpacing ?: properties.letterSpacing,
        lineHeight = lineHeight ?: properties.lineHeight,
        longtapActions = longtapActions ?: properties.longtapActions,
        margins = margins ?: properties.margins,
        maxLines = maxLines ?: properties.maxLines,
        minHiddenLines = minHiddenLines ?: properties.minHiddenLines,
        paddings = paddings ?: properties.paddings,
        pressEndActions = pressEndActions ?: properties.pressEndActions,
        pressStartActions = pressStartActions ?: properties.pressStartActions,
        ranges = ranges ?: properties.ranges,
        reuseId = reuseId ?: properties.reuseId,
        rowSpan = rowSpan ?: properties.rowSpan,
        selectable = selectable ?: properties.selectable,
        selectedActions = selectedActions ?: properties.selectedActions,
        strike = strike ?: properties.strike,
        textAlignmentHorizontal = textAlignmentHorizontal ?: properties.textAlignmentHorizontal,
        textAlignmentVertical = textAlignmentVertical ?: properties.textAlignmentVertical,
        textColor = textColor ?: properties.textColor,
        textGradient = textGradient ?: properties.textGradient,
        textShadow = textShadow ?: properties.textShadow,
        tightenWidth = tightenWidth ?: properties.tightenWidth,
        tooltips = tooltips ?: properties.tooltips,
        transform = transform ?: properties.transform,
        transitionChange = transitionChange ?: properties.transitionChange,
        transitionIn = transitionIn ?: properties.transitionIn,
        transitionOut = transitionOut ?: properties.transitionOut,
        transitionTriggers = transitionTriggers ?: properties.transitionTriggers,
        truncate = truncate ?: properties.truncate,
        underline = underline ?: properties.underline,
        variableTriggers = variableTriggers ?: properties.variableTriggers,
        variables = variables ?: properties.variables,
        visibility = visibility ?: properties.visibility,
        visibilityAction = visibilityAction ?: properties.visibilityAction,
        visibilityActions = visibilityActions ?: properties.visibilityActions,
        width = width ?: properties.width,
    )
)

/**
 * @param text Text.
 * @param accessibility Accessibility settings.
 * @param action One action when clicking on an element. Not used if the `actions` parameter is set.
 * @param actionAnimation Click animation. The web only supports the following values: `fade`, `scale`, `native`, `no_animation` and `set`.
 * @param actions Multiple actions when clicking on an element.
 * @param alignmentHorizontal Horizontal alignment of an element inside the parent element.
 * @param alignmentVertical Vertical alignment of an element inside the parent element.
 * @param alpha Sets transparency of the entire element: `0` — completely transparent, `1` — opaque.
 * @param animators Declaration of animators that change variable values over time.
 * @param autoEllipsize Automatic text cropping to fit the container size.
 * @param background Element background. It can contain multiple layers.
 * @param border Element stroke.
 * @param captureFocusOnAction If the value is:<li>`true` - when the element action is activated, the focus will be moved to that element. That means that the accessibility focus will be moved and the virtual keyboard will be hidden, unless the target element implies its presence (e.g. `input`).</li><li>`false` - when you click on an element, the focus will remain on the currently focused element.</li>
 * @param columnSpan Merges cells in a column of the [grid](div-grid.md) element.
 * @param disappearActions Actions when an element disappears from the screen.
 * @param doubletapActions Action when double-clicking on an element.
 * @param ellipsis Text cropping marker. It is displayed when text size exceeds the limit on the number of lines.
 * @param extensions Extensions for additional processing of an element. The list of extensions is given in  [DivExtension](../../extensions).
 * @param focus Parameters when focusing on an element or losing focus.
 * @param focusedTextColor Text color when focusing on the element.
 * @param fontFamily Font family:<li>`text` — a standard text font;</li><li>`display` — a family of fonts with a large font size.</li>
 * @param fontFeatureSettings List of OpenType font features. The format matches the CSS attribute "font-feature-settings". Learn more: https://www.w3.org/TR/css-fonts-3/#font-feature-settings-prop
 * @param fontSize Font size.
 * @param fontSizeUnit Unit of measurement:<li>`px` — a physical pixel.</li><li>`dp` — a logical pixel that doesn't depend on screen density.</li><li>`sp` — a logical pixel that depends on the font size on a device. Specify height in `sp`. Only available on Android.</li>
 * @param fontVariationSettings List of TrueType/OpenType font features. The object is constructed from pairs of axis tag and style values. The axis tag must contain four ASCII characters.
 * @param fontWeight Style.
 * @param fontWeightValue Style. Numeric value.
 * @param functions User functions.
 * @param height Element height. For Android: if there is text in this or in a child element, specify height in `sp` to scale the element together with the text. To learn more about units of size measurement, see [Layout inside the card](../../layout).
 * @param hoverEndActions Actions performed after hovering over an element. Available on platforms that support pointing devices (such as a mouse or stylus).
 * @param hoverStartActions Actions performed when hovering over an element. Available on platforms that support pointing devices (such as a mouse or stylus).
 * @param id Element ID. It must be unique within the root element. It is used as `accessibilityIdentifier` on iOS.
 * @param images Images embedded in text.
 * @param layoutProvider Provides data on the actual size of the element.
 * @param letterSpacing Spacing between characters.
 * @param lineHeight Line spacing of the text.
 * @param longtapActions Action when long-clicking an element. Doesn't work on devices that don't support touch gestures.
 * @param margins External margins from the element stroke.
 * @param maxLines Maximum number of lines not to be cropped when breaking the limits.
 * @param minHiddenLines Minimum number of cropped lines when breaking the limits.
 * @param paddings Internal margins from the element stroke.
 * @param pressEndActions Actions performed after clicking/tapping an element.
 * @param pressStartActions Actions performed at the start of a click/tap on an element.
 * @param ranges A character range in which additional style parameters can be set. Defined by mandatory `start` and `end` fields.
 * @param reuseId ID for the div object structure. Used to optimize block reuse. See [block reuse](../../reuse/reuse.md).
 * @param rowSpan Merges cells in a string of the [grid](div-grid.md) element.
 * @param selectable Ability to select and copy text.
 * @param selectedActions List of [actions](div-action.md) to be executed when selecting an element in [pager](div-pager.md).
 * @param strike Strikethrough.
 * @param textAlignmentHorizontal Horizontal text alignment.
 * @param textAlignmentVertical Vertical text alignment.
 * @param textColor Text color.
 * @param textGradient Gradient text color.
 * @param textShadow Parameters of the shadow applied to the text.
 * @param tightenWidth Limit the text width to the maximum line width. Applies only when the width is set to `wrap_content`, `constrained=true`, and `max_size` is specified.
 * @param tooltips Tooltips linked to an element. A tooltip can be shown by `div-action://show_tooltip?id=`, hidden by `div-action://hide_tooltip?id=` where `id` — tooltip id.
 * @param transform Applies the passed transformation to the element. Content that doesn't fit into the original view area is cut off.
 * @param transitionChange Change animation. It is played when the position or size of an element changes in the new layout.
 * @param transitionIn Appearance animation. It is played when an element with a new ID appears. To learn more about the concept of transitions, see [Animated transitions](../../interaction#animation/transition-animation).
 * @param transitionOut Disappearance animation. It is played when an element disappears in the new layout.
 * @param transitionTriggers Animation starting triggers. Default value: `[state_change, visibility_change]`.
 * @param truncate Location of text cropping marker.
 * @param underline Underline.
 * @param variableTriggers Triggers for changing variables within an element.
 * @param variables Declaration of variables that can be used within an element. Variables declared in this array can only be used within the element and its child elements.
 * @param visibility Element visibility.
 * @param visibilityAction Tracking visibility of a single element. Not used if the `visibility_actions` parameter is set.
 * @param visibilityActions Actions when an element appears on the screen.
 * @param width Element width.
 */
@Generated
fun Text.modify(
    `use named arguments`: Guard = Guard.instance,
    text: Property<String>? = null,
    accessibility: Property<Accessibility>? = null,
    action: Property<Action>? = null,
    actionAnimation: Property<Animation>? = null,
    actions: Property<List<Action>>? = null,
    alignmentHorizontal: Property<AlignmentHorizontal>? = null,
    alignmentVertical: Property<AlignmentVertical>? = null,
    alpha: Property<Double>? = null,
    animators: Property<List<Animator>>? = null,
    autoEllipsize: Property<Boolean>? = null,
    background: Property<List<Background>>? = null,
    border: Property<Border>? = null,
    captureFocusOnAction: Property<Boolean>? = null,
    columnSpan: Property<Int>? = null,
    disappearActions: Property<List<DisappearAction>>? = null,
    doubletapActions: Property<List<Action>>? = null,
    ellipsis: Property<Text.Ellipsis>? = null,
    extensions: Property<List<Extension>>? = null,
    focus: Property<Focus>? = null,
    focusedTextColor: Property<Color>? = null,
    fontFamily: Property<String>? = null,
    fontFeatureSettings: Property<String>? = null,
    fontSize: Property<Int>? = null,
    fontSizeUnit: Property<SizeUnit>? = null,
    fontVariationSettings: Property<Map<String, Any>>? = null,
    fontWeight: Property<FontWeight>? = null,
    fontWeightValue: Property<Int>? = null,
    functions: Property<List<Function>>? = null,
    height: Property<Size>? = null,
    hoverEndActions: Property<List<Action>>? = null,
    hoverStartActions: Property<List<Action>>? = null,
    id: Property<String>? = null,
    images: Property<List<Text.Image>>? = null,
    layoutProvider: Property<LayoutProvider>? = null,
    letterSpacing: Property<Double>? = null,
    lineHeight: Property<Int>? = null,
    longtapActions: Property<List<Action>>? = null,
    margins: Property<EdgeInsets>? = null,
    maxLines: Property<Int>? = null,
    minHiddenLines: Property<Int>? = null,
    paddings: Property<EdgeInsets>? = null,
    pressEndActions: Property<List<Action>>? = null,
    pressStartActions: Property<List<Action>>? = null,
    ranges: Property<List<Text.Range>>? = null,
    reuseId: Property<String>? = null,
    rowSpan: Property<Int>? = null,
    selectable: Property<Boolean>? = null,
    selectedActions: Property<List<Action>>? = null,
    strike: Property<LineStyle>? = null,
    textAlignmentHorizontal: Property<AlignmentHorizontal>? = null,
    textAlignmentVertical: Property<AlignmentVertical>? = null,
    textColor: Property<Color>? = null,
    textGradient: Property<TextGradient>? = null,
    textShadow: Property<Shadow>? = null,
    tightenWidth: Property<Boolean>? = null,
    tooltips: Property<List<Tooltip>>? = null,
    transform: Property<Transform>? = null,
    transitionChange: Property<ChangeTransition>? = null,
    transitionIn: Property<AppearanceTransition>? = null,
    transitionOut: Property<AppearanceTransition>? = null,
    transitionTriggers: Property<List<ArrayElement<TransitionTrigger>>>? = null,
    truncate: Property<Text.Truncate>? = null,
    underline: Property<LineStyle>? = null,
    variableTriggers: Property<List<Trigger>>? = null,
    variables: Property<List<Variable>>? = null,
    visibility: Property<Visibility>? = null,
    visibilityAction: Property<VisibilityAction>? = null,
    visibilityActions: Property<List<VisibilityAction>>? = null,
    width: Property<Size>? = null,
): Text = Text(
    Text.Properties(
        text = text ?: properties.text,
        accessibility = accessibility ?: properties.accessibility,
        action = action ?: properties.action,
        actionAnimation = actionAnimation ?: properties.actionAnimation,
        actions = actions ?: properties.actions,
        alignmentHorizontal = alignmentHorizontal ?: properties.alignmentHorizontal,
        alignmentVertical = alignmentVertical ?: properties.alignmentVertical,
        alpha = alpha ?: properties.alpha,
        animators = animators ?: properties.animators,
        autoEllipsize = autoEllipsize ?: properties.autoEllipsize,
        background = background ?: properties.background,
        border = border ?: properties.border,
        captureFocusOnAction = captureFocusOnAction ?: properties.captureFocusOnAction,
        columnSpan = columnSpan ?: properties.columnSpan,
        disappearActions = disappearActions ?: properties.disappearActions,
        doubletapActions = doubletapActions ?: properties.doubletapActions,
        ellipsis = ellipsis ?: properties.ellipsis,
        extensions = extensions ?: properties.extensions,
        focus = focus ?: properties.focus,
        focusedTextColor = focusedTextColor ?: properties.focusedTextColor,
        fontFamily = fontFamily ?: properties.fontFamily,
        fontFeatureSettings = fontFeatureSettings ?: properties.fontFeatureSettings,
        fontSize = fontSize ?: properties.fontSize,
        fontSizeUnit = fontSizeUnit ?: properties.fontSizeUnit,
        fontVariationSettings = fontVariationSettings ?: properties.fontVariationSettings,
        fontWeight = fontWeight ?: properties.fontWeight,
        fontWeightValue = fontWeightValue ?: properties.fontWeightValue,
        functions = functions ?: properties.functions,
        height = height ?: properties.height,
        hoverEndActions = hoverEndActions ?: properties.hoverEndActions,
        hoverStartActions = hoverStartActions ?: properties.hoverStartActions,
        id = id ?: properties.id,
        images = images ?: properties.images,
        layoutProvider = layoutProvider ?: properties.layoutProvider,
        letterSpacing = letterSpacing ?: properties.letterSpacing,
        lineHeight = lineHeight ?: properties.lineHeight,
        longtapActions = longtapActions ?: properties.longtapActions,
        margins = margins ?: properties.margins,
        maxLines = maxLines ?: properties.maxLines,
        minHiddenLines = minHiddenLines ?: properties.minHiddenLines,
        paddings = paddings ?: properties.paddings,
        pressEndActions = pressEndActions ?: properties.pressEndActions,
        pressStartActions = pressStartActions ?: properties.pressStartActions,
        ranges = ranges ?: properties.ranges,
        reuseId = reuseId ?: properties.reuseId,
        rowSpan = rowSpan ?: properties.rowSpan,
        selectable = selectable ?: properties.selectable,
        selectedActions = selectedActions ?: properties.selectedActions,
        strike = strike ?: properties.strike,
        textAlignmentHorizontal = textAlignmentHorizontal ?: properties.textAlignmentHorizontal,
        textAlignmentVertical = textAlignmentVertical ?: properties.textAlignmentVertical,
        textColor = textColor ?: properties.textColor,
        textGradient = textGradient ?: properties.textGradient,
        textShadow = textShadow ?: properties.textShadow,
        tightenWidth = tightenWidth ?: properties.tightenWidth,
        tooltips = tooltips ?: properties.tooltips,
        transform = transform ?: properties.transform,
        transitionChange = transitionChange ?: properties.transitionChange,
        transitionIn = transitionIn ?: properties.transitionIn,
        transitionOut = transitionOut ?: properties.transitionOut,
        transitionTriggers = transitionTriggers ?: properties.transitionTriggers,
        truncate = truncate ?: properties.truncate,
        underline = underline ?: properties.underline,
        variableTriggers = variableTriggers ?: properties.variableTriggers,
        variables = variables ?: properties.variables,
        visibility = visibility ?: properties.visibility,
        visibilityAction = visibilityAction ?: properties.visibilityAction,
        visibilityActions = visibilityActions ?: properties.visibilityActions,
        width = width ?: properties.width,
    )
)

/**
 * @param text Text.
 * @param alignmentHorizontal Horizontal alignment of an element inside the parent element.
 * @param alignmentVertical Vertical alignment of an element inside the parent element.
 * @param alpha Sets transparency of the entire element: `0` — completely transparent, `1` — opaque.
 * @param autoEllipsize Automatic text cropping to fit the container size.
 * @param captureFocusOnAction If the value is:<li>`true` - when the element action is activated, the focus will be moved to that element. That means that the accessibility focus will be moved and the virtual keyboard will be hidden, unless the target element implies its presence (e.g. `input`).</li><li>`false` - when you click on an element, the focus will remain on the currently focused element.</li>
 * @param columnSpan Merges cells in a column of the [grid](div-grid.md) element.
 * @param focusedTextColor Text color when focusing on the element.
 * @param fontFamily Font family:<li>`text` — a standard text font;</li><li>`display` — a family of fonts with a large font size.</li>
 * @param fontFeatureSettings List of OpenType font features. The format matches the CSS attribute "font-feature-settings". Learn more: https://www.w3.org/TR/css-fonts-3/#font-feature-settings-prop
 * @param fontSize Font size.
 * @param fontSizeUnit Unit of measurement:<li>`px` — a physical pixel.</li><li>`dp` — a logical pixel that doesn't depend on screen density.</li><li>`sp` — a logical pixel that depends on the font size on a device. Specify height in `sp`. Only available on Android.</li>
 * @param fontWeight Style.
 * @param fontWeightValue Style. Numeric value.
 * @param letterSpacing Spacing between characters.
 * @param lineHeight Line spacing of the text.
 * @param maxLines Maximum number of lines not to be cropped when breaking the limits.
 * @param minHiddenLines Minimum number of cropped lines when breaking the limits.
 * @param reuseId ID for the div object structure. Used to optimize block reuse. See [block reuse](../../reuse/reuse.md).
 * @param rowSpan Merges cells in a string of the [grid](div-grid.md) element.
 * @param selectable Ability to select and copy text.
 * @param strike Strikethrough.
 * @param textAlignmentHorizontal Horizontal text alignment.
 * @param textAlignmentVertical Vertical text alignment.
 * @param textColor Text color.
 * @param tightenWidth Limit the text width to the maximum line width. Applies only when the width is set to `wrap_content`, `constrained=true`, and `max_size` is specified.
 * @param truncate Location of text cropping marker.
 * @param underline Underline.
 * @param visibility Element visibility.
 */
@Generated
fun Text.evaluate(
    `use named arguments`: Guard = Guard.instance,
    text: ExpressionProperty<String>? = null,
    alignmentHorizontal: ExpressionProperty<AlignmentHorizontal>? = null,
    alignmentVertical: ExpressionProperty<AlignmentVertical>? = null,
    alpha: ExpressionProperty<Double>? = null,
    autoEllipsize: ExpressionProperty<Boolean>? = null,
    captureFocusOnAction: ExpressionProperty<Boolean>? = null,
    columnSpan: ExpressionProperty<Int>? = null,
    focusedTextColor: ExpressionProperty<Color>? = null,
    fontFamily: ExpressionProperty<String>? = null,
    fontFeatureSettings: ExpressionProperty<String>? = null,
    fontSize: ExpressionProperty<Int>? = null,
    fontSizeUnit: ExpressionProperty<SizeUnit>? = null,
    fontWeight: ExpressionProperty<FontWeight>? = null,
    fontWeightValue: ExpressionProperty<Int>? = null,
    letterSpacing: ExpressionProperty<Double>? = null,
    lineHeight: ExpressionProperty<Int>? = null,
    maxLines: ExpressionProperty<Int>? = null,
    minHiddenLines: ExpressionProperty<Int>? = null,
    reuseId: ExpressionProperty<String>? = null,
    rowSpan: ExpressionProperty<Int>? = null,
    selectable: ExpressionProperty<Boolean>? = null,
    strike: ExpressionProperty<LineStyle>? = null,
    textAlignmentHorizontal: ExpressionProperty<AlignmentHorizontal>? = null,
    textAlignmentVertical: ExpressionProperty<AlignmentVertical>? = null,
    textColor: ExpressionProperty<Color>? = null,
    tightenWidth: ExpressionProperty<Boolean>? = null,
    truncate: ExpressionProperty<Text.Truncate>? = null,
    underline: ExpressionProperty<LineStyle>? = null,
    visibility: ExpressionProperty<Visibility>? = null,
): Text = Text(
    Text.Properties(
        text = text ?: properties.text,
        accessibility = properties.accessibility,
        action = properties.action,
        actionAnimation = properties.actionAnimation,
        actions = properties.actions,
        alignmentHorizontal = alignmentHorizontal ?: properties.alignmentHorizontal,
        alignmentVertical = alignmentVertical ?: properties.alignmentVertical,
        alpha = alpha ?: properties.alpha,
        animators = properties.animators,
        autoEllipsize = autoEllipsize ?: properties.autoEllipsize,
        background = properties.background,
        border = properties.border,
        captureFocusOnAction = captureFocusOnAction ?: properties.captureFocusOnAction,
        columnSpan = columnSpan ?: properties.columnSpan,
        disappearActions = properties.disappearActions,
        doubletapActions = properties.doubletapActions,
        ellipsis = properties.ellipsis,
        extensions = properties.extensions,
        focus = properties.focus,
        focusedTextColor = focusedTextColor ?: properties.focusedTextColor,
        fontFamily = fontFamily ?: properties.fontFamily,
        fontFeatureSettings = fontFeatureSettings ?: properties.fontFeatureSettings,
        fontSize = fontSize ?: properties.fontSize,
        fontSizeUnit = fontSizeUnit ?: properties.fontSizeUnit,
        fontVariationSettings = properties.fontVariationSettings,
        fontWeight = fontWeight ?: properties.fontWeight,
        fontWeightValue = fontWeightValue ?: properties.fontWeightValue,
        functions = properties.functions,
        height = properties.height,
        hoverEndActions = properties.hoverEndActions,
        hoverStartActions = properties.hoverStartActions,
        id = properties.id,
        images = properties.images,
        layoutProvider = properties.layoutProvider,
        letterSpacing = letterSpacing ?: properties.letterSpacing,
        lineHeight = lineHeight ?: properties.lineHeight,
        longtapActions = properties.longtapActions,
        margins = properties.margins,
        maxLines = maxLines ?: properties.maxLines,
        minHiddenLines = minHiddenLines ?: properties.minHiddenLines,
        paddings = properties.paddings,
        pressEndActions = properties.pressEndActions,
        pressStartActions = properties.pressStartActions,
        ranges = properties.ranges,
        reuseId = reuseId ?: properties.reuseId,
        rowSpan = rowSpan ?: properties.rowSpan,
        selectable = selectable ?: properties.selectable,
        selectedActions = properties.selectedActions,
        strike = strike ?: properties.strike,
        textAlignmentHorizontal = textAlignmentHorizontal ?: properties.textAlignmentHorizontal,
        textAlignmentVertical = textAlignmentVertical ?: properties.textAlignmentVertical,
        textColor = textColor ?: properties.textColor,
        textGradient = properties.textGradient,
        textShadow = properties.textShadow,
        tightenWidth = tightenWidth ?: properties.tightenWidth,
        tooltips = properties.tooltips,
        transform = properties.transform,
        transitionChange = properties.transitionChange,
        transitionIn = properties.transitionIn,
        transitionOut = properties.transitionOut,
        transitionTriggers = properties.transitionTriggers,
        truncate = truncate ?: properties.truncate,
        underline = underline ?: properties.underline,
        variableTriggers = properties.variableTriggers,
        variables = properties.variables,
        visibility = visibility ?: properties.visibility,
        visibilityAction = properties.visibilityAction,
        visibilityActions = properties.visibilityActions,
        width = properties.width,
    )
)

/**
 * @param text Text.
 * @param accessibility Accessibility settings.
 * @param action One action when clicking on an element. Not used if the `actions` parameter is set.
 * @param actionAnimation Click animation. The web only supports the following values: `fade`, `scale`, `native`, `no_animation` and `set`.
 * @param actions Multiple actions when clicking on an element.
 * @param alignmentHorizontal Horizontal alignment of an element inside the parent element.
 * @param alignmentVertical Vertical alignment of an element inside the parent element.
 * @param alpha Sets transparency of the entire element: `0` — completely transparent, `1` — opaque.
 * @param animators Declaration of animators that change variable values over time.
 * @param autoEllipsize Automatic text cropping to fit the container size.
 * @param background Element background. It can contain multiple layers.
 * @param border Element stroke.
 * @param captureFocusOnAction If the value is:<li>`true` - when the element action is activated, the focus will be moved to that element. That means that the accessibility focus will be moved and the virtual keyboard will be hidden, unless the target element implies its presence (e.g. `input`).</li><li>`false` - when you click on an element, the focus will remain on the currently focused element.</li>
 * @param columnSpan Merges cells in a column of the [grid](div-grid.md) element.
 * @param disappearActions Actions when an element disappears from the screen.
 * @param doubletapActions Action when double-clicking on an element.
 * @param ellipsis Text cropping marker. It is displayed when text size exceeds the limit on the number of lines.
 * @param extensions Extensions for additional processing of an element. The list of extensions is given in  [DivExtension](../../extensions).
 * @param focus Parameters when focusing on an element or losing focus.
 * @param focusedTextColor Text color when focusing on the element.
 * @param fontFamily Font family:<li>`text` — a standard text font;</li><li>`display` — a family of fonts with a large font size.</li>
 * @param fontFeatureSettings List of OpenType font features. The format matches the CSS attribute "font-feature-settings". Learn more: https://www.w3.org/TR/css-fonts-3/#font-feature-settings-prop
 * @param fontSize Font size.
 * @param fontSizeUnit Unit of measurement:<li>`px` — a physical pixel.</li><li>`dp` — a logical pixel that doesn't depend on screen density.</li><li>`sp` — a logical pixel that depends on the font size on a device. Specify height in `sp`. Only available on Android.</li>
 * @param fontVariationSettings List of TrueType/OpenType font features. The object is constructed from pairs of axis tag and style values. The axis tag must contain four ASCII characters.
 * @param fontWeight Style.
 * @param fontWeightValue Style. Numeric value.
 * @param functions User functions.
 * @param height Element height. For Android: if there is text in this or in a child element, specify height in `sp` to scale the element together with the text. To learn more about units of size measurement, see [Layout inside the card](../../layout).
 * @param hoverEndActions Actions performed after hovering over an element. Available on platforms that support pointing devices (such as a mouse or stylus).
 * @param hoverStartActions Actions performed when hovering over an element. Available on platforms that support pointing devices (such as a mouse or stylus).
 * @param id Element ID. It must be unique within the root element. It is used as `accessibilityIdentifier` on iOS.
 * @param images Images embedded in text.
 * @param layoutProvider Provides data on the actual size of the element.
 * @param letterSpacing Spacing between characters.
 * @param lineHeight Line spacing of the text.
 * @param longtapActions Action when long-clicking an element. Doesn't work on devices that don't support touch gestures.
 * @param margins External margins from the element stroke.
 * @param maxLines Maximum number of lines not to be cropped when breaking the limits.
 * @param minHiddenLines Minimum number of cropped lines when breaking the limits.
 * @param paddings Internal margins from the element stroke.
 * @param pressEndActions Actions performed after clicking/tapping an element.
 * @param pressStartActions Actions performed at the start of a click/tap on an element.
 * @param ranges A character range in which additional style parameters can be set. Defined by mandatory `start` and `end` fields.
 * @param reuseId ID for the div object structure. Used to optimize block reuse. See [block reuse](../../reuse/reuse.md).
 * @param rowSpan Merges cells in a string of the [grid](div-grid.md) element.
 * @param selectable Ability to select and copy text.
 * @param selectedActions List of [actions](div-action.md) to be executed when selecting an element in [pager](div-pager.md).
 * @param strike Strikethrough.
 * @param textAlignmentHorizontal Horizontal text alignment.
 * @param textAlignmentVertical Vertical text alignment.
 * @param textColor Text color.
 * @param textGradient Gradient text color.
 * @param textShadow Parameters of the shadow applied to the text.
 * @param tightenWidth Limit the text width to the maximum line width. Applies only when the width is set to `wrap_content`, `constrained=true`, and `max_size` is specified.
 * @param tooltips Tooltips linked to an element. A tooltip can be shown by `div-action://show_tooltip?id=`, hidden by `div-action://hide_tooltip?id=` where `id` — tooltip id.
 * @param transform Applies the passed transformation to the element. Content that doesn't fit into the original view area is cut off.
 * @param transitionChange Change animation. It is played when the position or size of an element changes in the new layout.
 * @param transitionIn Appearance animation. It is played when an element with a new ID appears. To learn more about the concept of transitions, see [Animated transitions](../../interaction#animation/transition-animation).
 * @param transitionOut Disappearance animation. It is played when an element disappears in the new layout.
 * @param transitionTriggers Animation starting triggers. Default value: `[state_change, visibility_change]`.
 * @param truncate Location of text cropping marker.
 * @param underline Underline.
 * @param variableTriggers Triggers for changing variables within an element.
 * @param variables Declaration of variables that can be used within an element. Variables declared in this array can only be used within the element and its child elements.
 * @param visibility Element visibility.
 * @param visibilityAction Tracking visibility of a single element. Not used if the `visibility_actions` parameter is set.
 * @param visibilityActions Actions when an element appears on the screen.
 * @param width Element width.
 */
@Generated
fun Component<Text>.override(
    `use named arguments`: Guard = Guard.instance,
    text: String? = null,
    accessibility: Accessibility? = null,
    action: Action? = null,
    actionAnimation: Animation? = null,
    actions: List<Action>? = null,
    alignmentHorizontal: AlignmentHorizontal? = null,
    alignmentVertical: AlignmentVertical? = null,
    alpha: Double? = null,
    animators: List<Animator>? = null,
    autoEllipsize: Boolean? = null,
    background: List<Background>? = null,
    border: Border? = null,
    captureFocusOnAction: Boolean? = null,
    columnSpan: Int? = null,
    disappearActions: List<DisappearAction>? = null,
    doubletapActions: List<Action>? = null,
    ellipsis: Text.Ellipsis? = null,
    extensions: List<Extension>? = null,
    focus: Focus? = null,
    focusedTextColor: Color? = null,
    fontFamily: String? = null,
    fontFeatureSettings: String? = null,
    fontSize: Int? = null,
    fontSizeUnit: SizeUnit? = null,
    fontVariationSettings: Map<String, Any>? = null,
    fontWeight: FontWeight? = null,
    fontWeightValue: Int? = null,
    functions: List<Function>? = null,
    height: Size? = null,
    hoverEndActions: List<Action>? = null,
    hoverStartActions: List<Action>? = null,
    id: String? = null,
    images: List<Text.Image>? = null,
    layoutProvider: LayoutProvider? = null,
    letterSpacing: Double? = null,
    lineHeight: Int? = null,
    longtapActions: List<Action>? = null,
    margins: EdgeInsets? = null,
    maxLines: Int? = null,
    minHiddenLines: Int? = null,
    paddings: EdgeInsets? = null,
    pressEndActions: List<Action>? = null,
    pressStartActions: List<Action>? = null,
    ranges: List<Text.Range>? = null,
    reuseId: String? = null,
    rowSpan: Int? = null,
    selectable: Boolean? = null,
    selectedActions: List<Action>? = null,
    strike: LineStyle? = null,
    textAlignmentHorizontal: AlignmentHorizontal? = null,
    textAlignmentVertical: AlignmentVertical? = null,
    textColor: Color? = null,
    textGradient: TextGradient? = null,
    textShadow: Shadow? = null,
    tightenWidth: Boolean? = null,
    tooltips: List<Tooltip>? = null,
    transform: Transform? = null,
    transitionChange: ChangeTransition? = null,
    transitionIn: AppearanceTransition? = null,
    transitionOut: AppearanceTransition? = null,
    transitionTriggers: List<ArrayElement<TransitionTrigger>>? = null,
    truncate: Text.Truncate? = null,
    underline: LineStyle? = null,
    variableTriggers: List<Trigger>? = null,
    variables: List<Variable>? = null,
    visibility: Visibility? = null,
    visibilityAction: VisibilityAction? = null,
    visibilityActions: List<VisibilityAction>? = null,
    width: Size? = null,
): Component<Text> = Component(
    template = template,
    properties = Text.Properties(
        text = valueOrNull(text),
        accessibility = valueOrNull(accessibility),
        action = valueOrNull(action),
        actionAnimation = valueOrNull(actionAnimation),
        actions = valueOrNull(actions),
        alignmentHorizontal = valueOrNull(alignmentHorizontal),
        alignmentVertical = valueOrNull(alignmentVertical),
        alpha = valueOrNull(alpha),
        animators = valueOrNull(animators),
        autoEllipsize = valueOrNull(autoEllipsize),
        background = valueOrNull(background),
        border = valueOrNull(border),
        captureFocusOnAction = valueOrNull(captureFocusOnAction),
        columnSpan = valueOrNull(columnSpan),
        disappearActions = valueOrNull(disappearActions),
        doubletapActions = valueOrNull(doubletapActions),
        ellipsis = valueOrNull(ellipsis),
        extensions = valueOrNull(extensions),
        focus = valueOrNull(focus),
        focusedTextColor = valueOrNull(focusedTextColor),
        fontFamily = valueOrNull(fontFamily),
        fontFeatureSettings = valueOrNull(fontFeatureSettings),
        fontSize = valueOrNull(fontSize),
        fontSizeUnit = valueOrNull(fontSizeUnit),
        fontVariationSettings = valueOrNull(fontVariationSettings),
        fontWeight = valueOrNull(fontWeight),
        fontWeightValue = valueOrNull(fontWeightValue),
        functions = valueOrNull(functions),
        height = valueOrNull(height),
        hoverEndActions = valueOrNull(hoverEndActions),
        hoverStartActions = valueOrNull(hoverStartActions),
        id = valueOrNull(id),
        images = valueOrNull(images),
        layoutProvider = valueOrNull(layoutProvider),
        letterSpacing = valueOrNull(letterSpacing),
        lineHeight = valueOrNull(lineHeight),
        longtapActions = valueOrNull(longtapActions),
        margins = valueOrNull(margins),
        maxLines = valueOrNull(maxLines),
        minHiddenLines = valueOrNull(minHiddenLines),
        paddings = valueOrNull(paddings),
        pressEndActions = valueOrNull(pressEndActions),
        pressStartActions = valueOrNull(pressStartActions),
        ranges = valueOrNull(ranges),
        reuseId = valueOrNull(reuseId),
        rowSpan = valueOrNull(rowSpan),
        selectable = valueOrNull(selectable),
        selectedActions = valueOrNull(selectedActions),
        strike = valueOrNull(strike),
        textAlignmentHorizontal = valueOrNull(textAlignmentHorizontal),
        textAlignmentVertical = valueOrNull(textAlignmentVertical),
        textColor = valueOrNull(textColor),
        textGradient = valueOrNull(textGradient),
        textShadow = valueOrNull(textShadow),
        tightenWidth = valueOrNull(tightenWidth),
        tooltips = valueOrNull(tooltips),
        transform = valueOrNull(transform),
        transitionChange = valueOrNull(transitionChange),
        transitionIn = valueOrNull(transitionIn),
        transitionOut = valueOrNull(transitionOut),
        transitionTriggers = valueOrNull(transitionTriggers),
        truncate = valueOrNull(truncate),
        underline = valueOrNull(underline),
        variableTriggers = valueOrNull(variableTriggers),
        variables = valueOrNull(variables),
        visibility = valueOrNull(visibility),
        visibilityAction = valueOrNull(visibilityAction),
        visibilityActions = valueOrNull(visibilityActions),
        width = valueOrNull(width),
    ).mergeWith(properties)
)

/**
 * @param text Text.
 * @param accessibility Accessibility settings.
 * @param action One action when clicking on an element. Not used if the `actions` parameter is set.
 * @param actionAnimation Click animation. The web only supports the following values: `fade`, `scale`, `native`, `no_animation` and `set`.
 * @param actions Multiple actions when clicking on an element.
 * @param alignmentHorizontal Horizontal alignment of an element inside the parent element.
 * @param alignmentVertical Vertical alignment of an element inside the parent element.
 * @param alpha Sets transparency of the entire element: `0` — completely transparent, `1` — opaque.
 * @param animators Declaration of animators that change variable values over time.
 * @param autoEllipsize Automatic text cropping to fit the container size.
 * @param background Element background. It can contain multiple layers.
 * @param border Element stroke.
 * @param captureFocusOnAction If the value is:<li>`true` - when the element action is activated, the focus will be moved to that element. That means that the accessibility focus will be moved and the virtual keyboard will be hidden, unless the target element implies its presence (e.g. `input`).</li><li>`false` - when you click on an element, the focus will remain on the currently focused element.</li>
 * @param columnSpan Merges cells in a column of the [grid](div-grid.md) element.
 * @param disappearActions Actions when an element disappears from the screen.
 * @param doubletapActions Action when double-clicking on an element.
 * @param ellipsis Text cropping marker. It is displayed when text size exceeds the limit on the number of lines.
 * @param extensions Extensions for additional processing of an element. The list of extensions is given in  [DivExtension](../../extensions).
 * @param focus Parameters when focusing on an element or losing focus.
 * @param focusedTextColor Text color when focusing on the element.
 * @param fontFamily Font family:<li>`text` — a standard text font;</li><li>`display` — a family of fonts with a large font size.</li>
 * @param fontFeatureSettings List of OpenType font features. The format matches the CSS attribute "font-feature-settings". Learn more: https://www.w3.org/TR/css-fonts-3/#font-feature-settings-prop
 * @param fontSize Font size.
 * @param fontSizeUnit Unit of measurement:<li>`px` — a physical pixel.</li><li>`dp` — a logical pixel that doesn't depend on screen density.</li><li>`sp` — a logical pixel that depends on the font size on a device. Specify height in `sp`. Only available on Android.</li>
 * @param fontVariationSettings List of TrueType/OpenType font features. The object is constructed from pairs of axis tag and style values. The axis tag must contain four ASCII characters.
 * @param fontWeight Style.
 * @param fontWeightValue Style. Numeric value.
 * @param functions User functions.
 * @param height Element height. For Android: if there is text in this or in a child element, specify height in `sp` to scale the element together with the text. To learn more about units of size measurement, see [Layout inside the card](../../layout).
 * @param hoverEndActions Actions performed after hovering over an element. Available on platforms that support pointing devices (such as a mouse or stylus).
 * @param hoverStartActions Actions performed when hovering over an element. Available on platforms that support pointing devices (such as a mouse or stylus).
 * @param id Element ID. It must be unique within the root element. It is used as `accessibilityIdentifier` on iOS.
 * @param images Images embedded in text.
 * @param layoutProvider Provides data on the actual size of the element.
 * @param letterSpacing Spacing between characters.
 * @param lineHeight Line spacing of the text.
 * @param longtapActions Action when long-clicking an element. Doesn't work on devices that don't support touch gestures.
 * @param margins External margins from the element stroke.
 * @param maxLines Maximum number of lines not to be cropped when breaking the limits.
 * @param minHiddenLines Minimum number of cropped lines when breaking the limits.
 * @param paddings Internal margins from the element stroke.
 * @param pressEndActions Actions performed after clicking/tapping an element.
 * @param pressStartActions Actions performed at the start of a click/tap on an element.
 * @param ranges A character range in which additional style parameters can be set. Defined by mandatory `start` and `end` fields.
 * @param reuseId ID for the div object structure. Used to optimize block reuse. See [block reuse](../../reuse/reuse.md).
 * @param rowSpan Merges cells in a string of the [grid](div-grid.md) element.
 * @param selectable Ability to select and copy text.
 * @param selectedActions List of [actions](div-action.md) to be executed when selecting an element in [pager](div-pager.md).
 * @param strike Strikethrough.
 * @param textAlignmentHorizontal Horizontal text alignment.
 * @param textAlignmentVertical Vertical text alignment.
 * @param textColor Text color.
 * @param textGradient Gradient text color.
 * @param textShadow Parameters of the shadow applied to the text.
 * @param tightenWidth Limit the text width to the maximum line width. Applies only when the width is set to `wrap_content`, `constrained=true`, and `max_size` is specified.
 * @param tooltips Tooltips linked to an element. A tooltip can be shown by `div-action://show_tooltip?id=`, hidden by `div-action://hide_tooltip?id=` where `id` — tooltip id.
 * @param transform Applies the passed transformation to the element. Content that doesn't fit into the original view area is cut off.
 * @param transitionChange Change animation. It is played when the position or size of an element changes in the new layout.
 * @param transitionIn Appearance animation. It is played when an element with a new ID appears. To learn more about the concept of transitions, see [Animated transitions](../../interaction#animation/transition-animation).
 * @param transitionOut Disappearance animation. It is played when an element disappears in the new layout.
 * @param transitionTriggers Animation starting triggers. Default value: `[state_change, visibility_change]`.
 * @param truncate Location of text cropping marker.
 * @param underline Underline.
 * @param variableTriggers Triggers for changing variables within an element.
 * @param variables Declaration of variables that can be used within an element. Variables declared in this array can only be used within the element and its child elements.
 * @param visibility Element visibility.
 * @param visibilityAction Tracking visibility of a single element. Not used if the `visibility_actions` parameter is set.
 * @param visibilityActions Actions when an element appears on the screen.
 * @param width Element width.
 */
@Generated
fun Component<Text>.defer(
    `use named arguments`: Guard = Guard.instance,
    text: ReferenceProperty<String>? = null,
    accessibility: ReferenceProperty<Accessibility>? = null,
    action: ReferenceProperty<Action>? = null,
    actionAnimation: ReferenceProperty<Animation>? = null,
    actions: ReferenceProperty<List<Action>>? = null,
    alignmentHorizontal: ReferenceProperty<AlignmentHorizontal>? = null,
    alignmentVertical: ReferenceProperty<AlignmentVertical>? = null,
    alpha: ReferenceProperty<Double>? = null,
    animators: ReferenceProperty<List<Animator>>? = null,
    autoEllipsize: ReferenceProperty<Boolean>? = null,
    background: ReferenceProperty<List<Background>>? = null,
    border: ReferenceProperty<Border>? = null,
    captureFocusOnAction: ReferenceProperty<Boolean>? = null,
    columnSpan: ReferenceProperty<Int>? = null,
    disappearActions: ReferenceProperty<List<DisappearAction>>? = null,
    doubletapActions: ReferenceProperty<List<Action>>? = null,
    ellipsis: ReferenceProperty<Text.Ellipsis>? = null,
    extensions: ReferenceProperty<List<Extension>>? = null,
    focus: ReferenceProperty<Focus>? = null,
    focusedTextColor: ReferenceProperty<Color>? = null,
    fontFamily: ReferenceProperty<String>? = null,
    fontFeatureSettings: ReferenceProperty<String>? = null,
    fontSize: ReferenceProperty<Int>? = null,
    fontSizeUnit: ReferenceProperty<SizeUnit>? = null,
    fontVariationSettings: ReferenceProperty<Map<String, Any>>? = null,
    fontWeight: ReferenceProperty<FontWeight>? = null,
    fontWeightValue: ReferenceProperty<Int>? = null,
    functions: ReferenceProperty<List<Function>>? = null,
    height: ReferenceProperty<Size>? = null,
    hoverEndActions: ReferenceProperty<List<Action>>? = null,
    hoverStartActions: ReferenceProperty<List<Action>>? = null,
    id: ReferenceProperty<String>? = null,
    images: ReferenceProperty<List<Text.Image>>? = null,
    layoutProvider: ReferenceProperty<LayoutProvider>? = null,
    letterSpacing: ReferenceProperty<Double>? = null,
    lineHeight: ReferenceProperty<Int>? = null,
    longtapActions: ReferenceProperty<List<Action>>? = null,
    margins: ReferenceProperty<EdgeInsets>? = null,
    maxLines: ReferenceProperty<Int>? = null,
    minHiddenLines: ReferenceProperty<Int>? = null,
    paddings: ReferenceProperty<EdgeInsets>? = null,
    pressEndActions: ReferenceProperty<List<Action>>? = null,
    pressStartActions: ReferenceProperty<List<Action>>? = null,
    ranges: ReferenceProperty<List<Text.Range>>? = null,
    reuseId: ReferenceProperty<String>? = null,
    rowSpan: ReferenceProperty<Int>? = null,
    selectable: ReferenceProperty<Boolean>? = null,
    selectedActions: ReferenceProperty<List<Action>>? = null,
    strike: ReferenceProperty<LineStyle>? = null,
    textAlignmentHorizontal: ReferenceProperty<AlignmentHorizontal>? = null,
    textAlignmentVertical: ReferenceProperty<AlignmentVertical>? = null,
    textColor: ReferenceProperty<Color>? = null,
    textGradient: ReferenceProperty<TextGradient>? = null,
    textShadow: ReferenceProperty<Shadow>? = null,
    tightenWidth: ReferenceProperty<Boolean>? = null,
    tooltips: ReferenceProperty<List<Tooltip>>? = null,
    transform: ReferenceProperty<Transform>? = null,
    transitionChange: ReferenceProperty<ChangeTransition>? = null,
    transitionIn: ReferenceProperty<AppearanceTransition>? = null,
    transitionOut: ReferenceProperty<AppearanceTransition>? = null,
    transitionTriggers: ReferenceProperty<List<ArrayElement<TransitionTrigger>>>? = null,
    truncate: ReferenceProperty<Text.Truncate>? = null,
    underline: ReferenceProperty<LineStyle>? = null,
    variableTriggers: ReferenceProperty<List<Trigger>>? = null,
    variables: ReferenceProperty<List<Variable>>? = null,
    visibility: ReferenceProperty<Visibility>? = null,
    visibilityAction: ReferenceProperty<VisibilityAction>? = null,
    visibilityActions: ReferenceProperty<List<VisibilityAction>>? = null,
    width: ReferenceProperty<Size>? = null,
): Component<Text> = Component(
    template = template,
    properties = Text.Properties(
        text = text,
        accessibility = accessibility,
        action = action,
        actionAnimation = actionAnimation,
        actions = actions,
        alignmentHorizontal = alignmentHorizontal,
        alignmentVertical = alignmentVertical,
        alpha = alpha,
        animators = animators,
        autoEllipsize = autoEllipsize,
        background = background,
        border = border,
        captureFocusOnAction = captureFocusOnAction,
        columnSpan = columnSpan,
        disappearActions = disappearActions,
        doubletapActions = doubletapActions,
        ellipsis = ellipsis,
        extensions = extensions,
        focus = focus,
        focusedTextColor = focusedTextColor,
        fontFamily = fontFamily,
        fontFeatureSettings = fontFeatureSettings,
        fontSize = fontSize,
        fontSizeUnit = fontSizeUnit,
        fontVariationSettings = fontVariationSettings,
        fontWeight = fontWeight,
        fontWeightValue = fontWeightValue,
        functions = functions,
        height = height,
        hoverEndActions = hoverEndActions,
        hoverStartActions = hoverStartActions,
        id = id,
        images = images,
        layoutProvider = layoutProvider,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        longtapActions = longtapActions,
        margins = margins,
        maxLines = maxLines,
        minHiddenLines = minHiddenLines,
        paddings = paddings,
        pressEndActions = pressEndActions,
        pressStartActions = pressStartActions,
        ranges = ranges,
        reuseId = reuseId,
        rowSpan = rowSpan,
        selectable = selectable,
        selectedActions = selectedActions,
        strike = strike,
        textAlignmentHorizontal = textAlignmentHorizontal,
        textAlignmentVertical = textAlignmentVertical,
        textColor = textColor,
        textGradient = textGradient,
        textShadow = textShadow,
        tightenWidth = tightenWidth,
        tooltips = tooltips,
        transform = transform,
        transitionChange = transitionChange,
        transitionIn = transitionIn,
        transitionOut = transitionOut,
        transitionTriggers = transitionTriggers,
        truncate = truncate,
        underline = underline,
        variableTriggers = variableTriggers,
        variables = variables,
        visibility = visibility,
        visibilityAction = visibilityAction,
        visibilityActions = visibilityActions,
        width = width,
    ).mergeWith(properties)
)

/**
 * @param text Text.
 * @param alignmentHorizontal Horizontal alignment of an element inside the parent element.
 * @param alignmentVertical Vertical alignment of an element inside the parent element.
 * @param alpha Sets transparency of the entire element: `0` — completely transparent, `1` — opaque.
 * @param autoEllipsize Automatic text cropping to fit the container size.
 * @param captureFocusOnAction If the value is:<li>`true` - when the element action is activated, the focus will be moved to that element. That means that the accessibility focus will be moved and the virtual keyboard will be hidden, unless the target element implies its presence (e.g. `input`).</li><li>`false` - when you click on an element, the focus will remain on the currently focused element.</li>
 * @param columnSpan Merges cells in a column of the [grid](div-grid.md) element.
 * @param focusedTextColor Text color when focusing on the element.
 * @param fontFamily Font family:<li>`text` — a standard text font;</li><li>`display` — a family of fonts with a large font size.</li>
 * @param fontFeatureSettings List of OpenType font features. The format matches the CSS attribute "font-feature-settings". Learn more: https://www.w3.org/TR/css-fonts-3/#font-feature-settings-prop
 * @param fontSize Font size.
 * @param fontSizeUnit Unit of measurement:<li>`px` — a physical pixel.</li><li>`dp` — a logical pixel that doesn't depend on screen density.</li><li>`sp` — a logical pixel that depends on the font size on a device. Specify height in `sp`. Only available on Android.</li>
 * @param fontWeight Style.
 * @param fontWeightValue Style. Numeric value.
 * @param letterSpacing Spacing between characters.
 * @param lineHeight Line spacing of the text.
 * @param maxLines Maximum number of lines not to be cropped when breaking the limits.
 * @param minHiddenLines Minimum number of cropped lines when breaking the limits.
 * @param reuseId ID for the div object structure. Used to optimize block reuse. See [block reuse](../../reuse/reuse.md).
 * @param rowSpan Merges cells in a string of the [grid](div-grid.md) element.
 * @param selectable Ability to select and copy text.
 * @param strike Strikethrough.
 * @param textAlignmentHorizontal Horizontal text alignment.
 * @param textAlignmentVertical Vertical text alignment.
 * @param textColor Text color.
 * @param tightenWidth Limit the text width to the maximum line width. Applies only when the width is set to `wrap_content`, `constrained=true`, and `max_size` is specified.
 * @param truncate Location of text cropping marker.
 * @param underline Underline.
 * @param visibility Element visibility.
 */
@Generated
fun Component<Text>.evaluate(
    `use named arguments`: Guard = Guard.instance,
    text: ExpressionProperty<String>? = null,
    alignmentHorizontal: ExpressionProperty<AlignmentHorizontal>? = null,
    alignmentVertical: ExpressionProperty<AlignmentVertical>? = null,
    alpha: ExpressionProperty<Double>? = null,
    autoEllipsize: ExpressionProperty<Boolean>? = null,
    captureFocusOnAction: ExpressionProperty<Boolean>? = null,
    columnSpan: ExpressionProperty<Int>? = null,
    focusedTextColor: ExpressionProperty<Color>? = null,
    fontFamily: ExpressionProperty<String>? = null,
    fontFeatureSettings: ExpressionProperty<String>? = null,
    fontSize: ExpressionProperty<Int>? = null,
    fontSizeUnit: ExpressionProperty<SizeUnit>? = null,
    fontWeight: ExpressionProperty<FontWeight>? = null,
    fontWeightValue: ExpressionProperty<Int>? = null,
    letterSpacing: ExpressionProperty<Double>? = null,
    lineHeight: ExpressionProperty<Int>? = null,
    maxLines: ExpressionProperty<Int>? = null,
    minHiddenLines: ExpressionProperty<Int>? = null,
    reuseId: ExpressionProperty<String>? = null,
    rowSpan: ExpressionProperty<Int>? = null,
    selectable: ExpressionProperty<Boolean>? = null,
    strike: ExpressionProperty<LineStyle>? = null,
    textAlignmentHorizontal: ExpressionProperty<AlignmentHorizontal>? = null,
    textAlignmentVertical: ExpressionProperty<AlignmentVertical>? = null,
    textColor: ExpressionProperty<Color>? = null,
    tightenWidth: ExpressionProperty<Boolean>? = null,
    truncate: ExpressionProperty<Text.Truncate>? = null,
    underline: ExpressionProperty<LineStyle>? = null,
    visibility: ExpressionProperty<Visibility>? = null,
): Component<Text> = Component(
    template = template,
    properties = Text.Properties(
        text = text,
        accessibility = null,
        action = null,
        actionAnimation = null,
        actions = null,
        alignmentHorizontal = alignmentHorizontal,
        alignmentVertical = alignmentVertical,
        alpha = alpha,
        animators = null,
        autoEllipsize = autoEllipsize,
        background = null,
        border = null,
        captureFocusOnAction = captureFocusOnAction,
        columnSpan = columnSpan,
        disappearActions = null,
        doubletapActions = null,
        ellipsis = null,
        extensions = null,
        focus = null,
        focusedTextColor = focusedTextColor,
        fontFamily = fontFamily,
        fontFeatureSettings = fontFeatureSettings,
        fontSize = fontSize,
        fontSizeUnit = fontSizeUnit,
        fontVariationSettings = null,
        fontWeight = fontWeight,
        fontWeightValue = fontWeightValue,
        functions = null,
        height = null,
        hoverEndActions = null,
        hoverStartActions = null,
        id = null,
        images = null,
        layoutProvider = null,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        longtapActions = null,
        margins = null,
        maxLines = maxLines,
        minHiddenLines = minHiddenLines,
        paddings = null,
        pressEndActions = null,
        pressStartActions = null,
        ranges = null,
        reuseId = reuseId,
        rowSpan = rowSpan,
        selectable = selectable,
        selectedActions = null,
        strike = strike,
        textAlignmentHorizontal = textAlignmentHorizontal,
        textAlignmentVertical = textAlignmentVertical,
        textColor = textColor,
        textGradient = null,
        textShadow = null,
        tightenWidth = tightenWidth,
        tooltips = null,
        transform = null,
        transitionChange = null,
        transitionIn = null,
        transitionOut = null,
        transitionTriggers = null,
        truncate = truncate,
        underline = underline,
        variableTriggers = null,
        variables = null,
        visibility = visibility,
        visibilityAction = null,
        visibilityActions = null,
        width = null,
    ).mergeWith(properties)
)

/**
 * @param text Text.
 * @param accessibility Accessibility settings.
 * @param action One action when clicking on an element. Not used if the `actions` parameter is set.
 * @param actionAnimation Click animation. The web only supports the following values: `fade`, `scale`, `native`, `no_animation` and `set`.
 * @param actions Multiple actions when clicking on an element.
 * @param alignmentHorizontal Horizontal alignment of an element inside the parent element.
 * @param alignmentVertical Vertical alignment of an element inside the parent element.
 * @param alpha Sets transparency of the entire element: `0` — completely transparent, `1` — opaque.
 * @param animators Declaration of animators that change variable values over time.
 * @param autoEllipsize Automatic text cropping to fit the container size.
 * @param background Element background. It can contain multiple layers.
 * @param border Element stroke.
 * @param captureFocusOnAction If the value is:<li>`true` - when the element action is activated, the focus will be moved to that element. That means that the accessibility focus will be moved and the virtual keyboard will be hidden, unless the target element implies its presence (e.g. `input`).</li><li>`false` - when you click on an element, the focus will remain on the currently focused element.</li>
 * @param columnSpan Merges cells in a column of the [grid](div-grid.md) element.
 * @param disappearActions Actions when an element disappears from the screen.
 * @param doubletapActions Action when double-clicking on an element.
 * @param ellipsis Text cropping marker. It is displayed when text size exceeds the limit on the number of lines.
 * @param extensions Extensions for additional processing of an element. The list of extensions is given in  [DivExtension](../../extensions).
 * @param focus Parameters when focusing on an element or losing focus.
 * @param focusedTextColor Text color when focusing on the element.
 * @param fontFamily Font family:<li>`text` — a standard text font;</li><li>`display` — a family of fonts with a large font size.</li>
 * @param fontFeatureSettings List of OpenType font features. The format matches the CSS attribute "font-feature-settings". Learn more: https://www.w3.org/TR/css-fonts-3/#font-feature-settings-prop
 * @param fontSize Font size.
 * @param fontSizeUnit Unit of measurement:<li>`px` — a physical pixel.</li><li>`dp` — a logical pixel that doesn't depend on screen density.</li><li>`sp` — a logical pixel that depends on the font size on a device. Specify height in `sp`. Only available on Android.</li>
 * @param fontVariationSettings List of TrueType/OpenType font features. The object is constructed from pairs of axis tag and style values. The axis tag must contain four ASCII characters.
 * @param fontWeight Style.
 * @param fontWeightValue Style. Numeric value.
 * @param functions User functions.
 * @param height Element height. For Android: if there is text in this or in a child element, specify height in `sp` to scale the element together with the text. To learn more about units of size measurement, see [Layout inside the card](../../layout).
 * @param hoverEndActions Actions performed after hovering over an element. Available on platforms that support pointing devices (such as a mouse or stylus).
 * @param hoverStartActions Actions performed when hovering over an element. Available on platforms that support pointing devices (such as a mouse or stylus).
 * @param id Element ID. It must be unique within the root element. It is used as `accessibilityIdentifier` on iOS.
 * @param images Images embedded in text.
 * @param layoutProvider Provides data on the actual size of the element.
 * @param letterSpacing Spacing between characters.
 * @param lineHeight Line spacing of the text.
 * @param longtapActions Action when long-clicking an element. Doesn't work on devices that don't support touch gestures.
 * @param margins External margins from the element stroke.
 * @param maxLines Maximum number of lines not to be cropped when breaking the limits.
 * @param minHiddenLines Minimum number of cropped lines when breaking the limits.
 * @param paddings Internal margins from the element stroke.
 * @param pressEndActions Actions performed after clicking/tapping an element.
 * @param pressStartActions Actions performed at the start of a click/tap on an element.
 * @param ranges A character range in which additional style parameters can be set. Defined by mandatory `start` and `end` fields.
 * @param reuseId ID for the div object structure. Used to optimize block reuse. See [block reuse](../../reuse/reuse.md).
 * @param rowSpan Merges cells in a string of the [grid](div-grid.md) element.
 * @param selectable Ability to select and copy text.
 * @param selectedActions List of [actions](div-action.md) to be executed when selecting an element in [pager](div-pager.md).
 * @param strike Strikethrough.
 * @param textAlignmentHorizontal Horizontal text alignment.
 * @param textAlignmentVertical Vertical text alignment.
 * @param textColor Text color.
 * @param textGradient Gradient text color.
 * @param textShadow Parameters of the shadow applied to the text.
 * @param tightenWidth Limit the text width to the maximum line width. Applies only when the width is set to `wrap_content`, `constrained=true`, and `max_size` is specified.
 * @param tooltips Tooltips linked to an element. A tooltip can be shown by `div-action://show_tooltip?id=`, hidden by `div-action://hide_tooltip?id=` where `id` — tooltip id.
 * @param transform Applies the passed transformation to the element. Content that doesn't fit into the original view area is cut off.
 * @param transitionChange Change animation. It is played when the position or size of an element changes in the new layout.
 * @param transitionIn Appearance animation. It is played when an element with a new ID appears. To learn more about the concept of transitions, see [Animated transitions](../../interaction#animation/transition-animation).
 * @param transitionOut Disappearance animation. It is played when an element disappears in the new layout.
 * @param transitionTriggers Animation starting triggers. Default value: `[state_change, visibility_change]`.
 * @param truncate Location of text cropping marker.
 * @param underline Underline.
 * @param variableTriggers Triggers for changing variables within an element.
 * @param variables Declaration of variables that can be used within an element. Variables declared in this array can only be used within the element and its child elements.
 * @param visibility Element visibility.
 * @param visibilityAction Tracking visibility of a single element. Not used if the `visibility_actions` parameter is set.
 * @param visibilityActions Actions when an element appears on the screen.
 * @param width Element width.
 */
@Generated
fun Component<Text>.modify(
    `use named arguments`: Guard = Guard.instance,
    text: Property<String>? = null,
    accessibility: Property<Accessibility>? = null,
    action: Property<Action>? = null,
    actionAnimation: Property<Animation>? = null,
    actions: Property<List<Action>>? = null,
    alignmentHorizontal: Property<AlignmentHorizontal>? = null,
    alignmentVertical: Property<AlignmentVertical>? = null,
    alpha: Property<Double>? = null,
    animators: Property<List<Animator>>? = null,
    autoEllipsize: Property<Boolean>? = null,
    background: Property<List<Background>>? = null,
    border: Property<Border>? = null,
    captureFocusOnAction: Property<Boolean>? = null,
    columnSpan: Property<Int>? = null,
    disappearActions: Property<List<DisappearAction>>? = null,
    doubletapActions: Property<List<Action>>? = null,
    ellipsis: Property<Text.Ellipsis>? = null,
    extensions: Property<List<Extension>>? = null,
    focus: Property<Focus>? = null,
    focusedTextColor: Property<Color>? = null,
    fontFamily: Property<String>? = null,
    fontFeatureSettings: Property<String>? = null,
    fontSize: Property<Int>? = null,
    fontSizeUnit: Property<SizeUnit>? = null,
    fontVariationSettings: Property<Map<String, Any>>? = null,
    fontWeight: Property<FontWeight>? = null,
    fontWeightValue: Property<Int>? = null,
    functions: Property<List<Function>>? = null,
    height: Property<Size>? = null,
    hoverEndActions: Property<List<Action>>? = null,
    hoverStartActions: Property<List<Action>>? = null,
    id: Property<String>? = null,
    images: Property<List<Text.Image>>? = null,
    layoutProvider: Property<LayoutProvider>? = null,
    letterSpacing: Property<Double>? = null,
    lineHeight: Property<Int>? = null,
    longtapActions: Property<List<Action>>? = null,
    margins: Property<EdgeInsets>? = null,
    maxLines: Property<Int>? = null,
    minHiddenLines: Property<Int>? = null,
    paddings: Property<EdgeInsets>? = null,
    pressEndActions: Property<List<Action>>? = null,
    pressStartActions: Property<List<Action>>? = null,
    ranges: Property<List<Text.Range>>? = null,
    reuseId: Property<String>? = null,
    rowSpan: Property<Int>? = null,
    selectable: Property<Boolean>? = null,
    selectedActions: Property<List<Action>>? = null,
    strike: Property<LineStyle>? = null,
    textAlignmentHorizontal: Property<AlignmentHorizontal>? = null,
    textAlignmentVertical: Property<AlignmentVertical>? = null,
    textColor: Property<Color>? = null,
    textGradient: Property<TextGradient>? = null,
    textShadow: Property<Shadow>? = null,
    tightenWidth: Property<Boolean>? = null,
    tooltips: Property<List<Tooltip>>? = null,
    transform: Property<Transform>? = null,
    transitionChange: Property<ChangeTransition>? = null,
    transitionIn: Property<AppearanceTransition>? = null,
    transitionOut: Property<AppearanceTransition>? = null,
    transitionTriggers: Property<List<ArrayElement<TransitionTrigger>>>? = null,
    truncate: Property<Text.Truncate>? = null,
    underline: Property<LineStyle>? = null,
    variableTriggers: Property<List<Trigger>>? = null,
    variables: Property<List<Variable>>? = null,
    visibility: Property<Visibility>? = null,
    visibilityAction: Property<VisibilityAction>? = null,
    visibilityActions: Property<List<VisibilityAction>>? = null,
    width: Property<Size>? = null,
): Component<Text> = Component(
    template = template,
    properties = Text.Properties(
        text = text,
        accessibility = accessibility,
        action = action,
        actionAnimation = actionAnimation,
        actions = actions,
        alignmentHorizontal = alignmentHorizontal,
        alignmentVertical = alignmentVertical,
        alpha = alpha,
        animators = animators,
        autoEllipsize = autoEllipsize,
        background = background,
        border = border,
        captureFocusOnAction = captureFocusOnAction,
        columnSpan = columnSpan,
        disappearActions = disappearActions,
        doubletapActions = doubletapActions,
        ellipsis = ellipsis,
        extensions = extensions,
        focus = focus,
        focusedTextColor = focusedTextColor,
        fontFamily = fontFamily,
        fontFeatureSettings = fontFeatureSettings,
        fontSize = fontSize,
        fontSizeUnit = fontSizeUnit,
        fontVariationSettings = fontVariationSettings,
        fontWeight = fontWeight,
        fontWeightValue = fontWeightValue,
        functions = functions,
        height = height,
        hoverEndActions = hoverEndActions,
        hoverStartActions = hoverStartActions,
        id = id,
        images = images,
        layoutProvider = layoutProvider,
        letterSpacing = letterSpacing,
        lineHeight = lineHeight,
        longtapActions = longtapActions,
        margins = margins,
        maxLines = maxLines,
        minHiddenLines = minHiddenLines,
        paddings = paddings,
        pressEndActions = pressEndActions,
        pressStartActions = pressStartActions,
        ranges = ranges,
        reuseId = reuseId,
        rowSpan = rowSpan,
        selectable = selectable,
        selectedActions = selectedActions,
        strike = strike,
        textAlignmentHorizontal = textAlignmentHorizontal,
        textAlignmentVertical = textAlignmentVertical,
        textColor = textColor,
        textGradient = textGradient,
        textShadow = textShadow,
        tightenWidth = tightenWidth,
        tooltips = tooltips,
        transform = transform,
        transitionChange = transitionChange,
        transitionIn = transitionIn,
        transitionOut = transitionOut,
        transitionTriggers = transitionTriggers,
        truncate = truncate,
        underline = underline,
        variableTriggers = variableTriggers,
        variables = variables,
        visibility = visibility,
        visibilityAction = visibilityAction,
        visibilityActions = visibilityActions,
        width = width,
    ).mergeWith(properties)
)

@Generated
operator fun Component<Text>.plus(additive: Text.Properties): Component<Text> = Component(
    template = template,
    properties = additive.mergeWith(properties)
)

@Generated
fun Text.asList() = listOf(this)

/**
 * @param actions Actions when clicking on a crop marker.
 * @param images Images embedded in a crop marker.
 * @param ranges Character ranges inside a crop marker with different text styles.
 * @param text Marker text.
 */
@Generated
fun DivScope.textEllipsis(
    `use named arguments`: Guard = Guard.instance,
    actions: List<Action>? = null,
    images: List<Text.Image>? = null,
    ranges: List<Text.Range>? = null,
    text: String? = null,
): Text.Ellipsis = Text.Ellipsis(
    Text.Ellipsis.Properties(
        actions = valueOrNull(actions),
        images = valueOrNull(images),
        ranges = valueOrNull(ranges),
        text = valueOrNull(text),
    )
)

/**
 * @param actions Actions when clicking on a crop marker.
 * @param images Images embedded in a crop marker.
 * @param ranges Character ranges inside a crop marker with different text styles.
 * @param text Marker text.
 */
@Generated
fun DivScope.textEllipsisProps(
    `use named arguments`: Guard = Guard.instance,
    actions: List<Action>? = null,
    images: List<Text.Image>? = null,
    ranges: List<Text.Range>? = null,
    text: String? = null,
) = Text.Ellipsis.Properties(
    actions = valueOrNull(actions),
    images = valueOrNull(images),
    ranges = valueOrNull(ranges),
    text = valueOrNull(text),
)

/**
 * @param actions Actions when clicking on a crop marker.
 * @param images Images embedded in a crop marker.
 * @param ranges Character ranges inside a crop marker with different text styles.
 * @param text Marker text.
 */
@Generated
fun TemplateScope.textEllipsisRefs(
    `use named arguments`: Guard = Guard.instance,
    actions: ReferenceProperty<List<Action>>? = null,
    images: ReferenceProperty<List<Text.Image>>? = null,
    ranges: ReferenceProperty<List<Text.Range>>? = null,
    text: ReferenceProperty<String>? = null,
) = Text.Ellipsis.Properties(
    actions = actions,
    images = images,
    ranges = ranges,
    text = text,
)

/**
 * @param actions Actions when clicking on a crop marker.
 * @param images Images embedded in a crop marker.
 * @param ranges Character ranges inside a crop marker with different text styles.
 * @param text Marker text.
 */
@Generated
fun Text.Ellipsis.override(
    `use named arguments`: Guard = Guard.instance,
    actions: List<Action>? = null,
    images: List<Text.Image>? = null,
    ranges: List<Text.Range>? = null,
    text: String? = null,
): Text.Ellipsis = Text.Ellipsis(
    Text.Ellipsis.Properties(
        actions = valueOrNull(actions) ?: properties.actions,
        images = valueOrNull(images) ?: properties.images,
        ranges = valueOrNull(ranges) ?: properties.ranges,
        text = valueOrNull(text) ?: properties.text,
    )
)

/**
 * @param actions Actions when clicking on a crop marker.
 * @param images Images embedded in a crop marker.
 * @param ranges Character ranges inside a crop marker with different text styles.
 * @param text Marker text.
 */
@Generated
fun Text.Ellipsis.defer(
    `use named arguments`: Guard = Guard.instance,
    actions: ReferenceProperty<List<Action>>? = null,
    images: ReferenceProperty<List<Text.Image>>? = null,
    ranges: ReferenceProperty<List<Text.Range>>? = null,
    text: ReferenceProperty<String>? = null,
): Text.Ellipsis = Text.Ellipsis(
    Text.Ellipsis.Properties(
        actions = actions ?: properties.actions,
        images = images ?: properties.images,
        ranges = ranges ?: properties.ranges,
        text = text ?: properties.text,
    )
)

/**
 * @param actions Actions when clicking on a crop marker.
 * @param images Images embedded in a crop marker.
 * @param ranges Character ranges inside a crop marker with different text styles.
 * @param text Marker text.
 */
@Generated
fun Text.Ellipsis.modify(
    `use named arguments`: Guard = Guard.instance,
    actions: Property<List<Action>>? = null,
    images: Property<List<Text.Image>>? = null,
    ranges: Property<List<Text.Range>>? = null,
    text: Property<String>? = null,
): Text.Ellipsis = Text.Ellipsis(
    Text.Ellipsis.Properties(
        actions = actions ?: properties.actions,
        images = images ?: properties.images,
        ranges = ranges ?: properties.ranges,
        text = text ?: properties.text,
    )
)

/**
 * @param text Marker text.
 */
@Generated
fun Text.Ellipsis.evaluate(
    `use named arguments`: Guard = Guard.instance,
    text: ExpressionProperty<String>? = null,
): Text.Ellipsis = Text.Ellipsis(
    Text.Ellipsis.Properties(
        actions = properties.actions,
        images = properties.images,
        ranges = properties.ranges,
        text = text ?: properties.text,
    )
)

@Generated
fun Text.Ellipsis.asList() = listOf(this)

/**
 * @param alignmentVertical Vertical image alignment within the row.
 * @param height Image height.
 * @param indexingDirection Defines the direction in the `start` parameter:
– `normal` is for regular string indexing ([0, 1, 2, ..., N]). Use it if you need to insert an image by index relative to the beginning of a string.
– `reversed` is for indexing a string from the end to the beginning ([N, ..., 2, 1, 0]). Use it if you need to insert an image by index relative to the end of a string.
 * @param preloadRequired Background image must be loaded before the display.
 * @param start A symbol to insert prior to an image. To insert an image at the end of the text, specify the number of the last character plus one.
 * @param tintColor New color of a contour image.
 * @param tintMode Blend mode of the color specified in `tint_color`.
 * @param url Image URL.
 * @param width Image width.
 */
@Generated
fun DivScope.textImage(
    `use named arguments`: Guard = Guard.instance,
    accessibility: Text.Image.Accessibility? = null,
    alignmentVertical: TextAlignmentVertical? = null,
    height: FixedSize? = null,
    indexingDirection: Text.Image.IndexingDirection? = null,
    preloadRequired: Boolean? = null,
    start: Int? = null,
    tintColor: Color? = null,
    tintMode: BlendMode? = null,
    url: Url? = null,
    width: FixedSize? = null,
): Text.Image = Text.Image(
    Text.Image.Properties(
        accessibility = valueOrNull(accessibility),
        alignmentVertical = valueOrNull(alignmentVertical),
        height = valueOrNull(height),
        indexingDirection = valueOrNull(indexingDirection),
        preloadRequired = valueOrNull(preloadRequired),
        start = valueOrNull(start),
        tintColor = valueOrNull(tintColor),
        tintMode = valueOrNull(tintMode),
        url = valueOrNull(url),
        width = valueOrNull(width),
    )
)

/**
 * @param alignmentVertical Vertical image alignment within the row.
 * @param height Image height.
 * @param indexingDirection Defines the direction in the `start` parameter:
– `normal` is for regular string indexing ([0, 1, 2, ..., N]). Use it if you need to insert an image by index relative to the beginning of a string.
– `reversed` is for indexing a string from the end to the beginning ([N, ..., 2, 1, 0]). Use it if you need to insert an image by index relative to the end of a string.
 * @param preloadRequired Background image must be loaded before the display.
 * @param start A symbol to insert prior to an image. To insert an image at the end of the text, specify the number of the last character plus one.
 * @param tintColor New color of a contour image.
 * @param tintMode Blend mode of the color specified in `tint_color`.
 * @param url Image URL.
 * @param width Image width.
 */
@Generated
fun DivScope.textImageProps(
    `use named arguments`: Guard = Guard.instance,
    accessibility: Text.Image.Accessibility? = null,
    alignmentVertical: TextAlignmentVertical? = null,
    height: FixedSize? = null,
    indexingDirection: Text.Image.IndexingDirection? = null,
    preloadRequired: Boolean? = null,
    start: Int? = null,
    tintColor: Color? = null,
    tintMode: BlendMode? = null,
    url: Url? = null,
    width: FixedSize? = null,
) = Text.Image.Properties(
    accessibility = valueOrNull(accessibility),
    alignmentVertical = valueOrNull(alignmentVertical),
    height = valueOrNull(height),
    indexingDirection = valueOrNull(indexingDirection),
    preloadRequired = valueOrNull(preloadRequired),
    start = valueOrNull(start),
    tintColor = valueOrNull(tintColor),
    tintMode = valueOrNull(tintMode),
    url = valueOrNull(url),
    width = valueOrNull(width),
)

/**
 * @param alignmentVertical Vertical image alignment within the row.
 * @param height Image height.
 * @param indexingDirection Defines the direction in the `start` parameter:
– `normal` is for regular string indexing ([0, 1, 2, ..., N]). Use it if you need to insert an image by index relative to the beginning of a string.
– `reversed` is for indexing a string from the end to the beginning ([N, ..., 2, 1, 0]). Use it if you need to insert an image by index relative to the end of a string.
 * @param preloadRequired Background image must be loaded before the display.
 * @param start A symbol to insert prior to an image. To insert an image at the end of the text, specify the number of the last character plus one.
 * @param tintColor New color of a contour image.
 * @param tintMode Blend mode of the color specified in `tint_color`.
 * @param url Image URL.
 * @param width Image width.
 */
@Generated
fun TemplateScope.textImageRefs(
    `use named arguments`: Guard = Guard.instance,
    accessibility: ReferenceProperty<Text.Image.Accessibility>? = null,
    alignmentVertical: ReferenceProperty<TextAlignmentVertical>? = null,
    height: ReferenceProperty<FixedSize>? = null,
    indexingDirection: ReferenceProperty<Text.Image.IndexingDirection>? = null,
    preloadRequired: ReferenceProperty<Boolean>? = null,
    start: ReferenceProperty<Int>? = null,
    tintColor: ReferenceProperty<Color>? = null,
    tintMode: ReferenceProperty<BlendMode>? = null,
    url: ReferenceProperty<Url>? = null,
    width: ReferenceProperty<FixedSize>? = null,
) = Text.Image.Properties(
    accessibility = accessibility,
    alignmentVertical = alignmentVertical,
    height = height,
    indexingDirection = indexingDirection,
    preloadRequired = preloadRequired,
    start = start,
    tintColor = tintColor,
    tintMode = tintMode,
    url = url,
    width = width,
)

/**
 * @param alignmentVertical Vertical image alignment within the row.
 * @param height Image height.
 * @param indexingDirection Defines the direction in the `start` parameter:
– `normal` is for regular string indexing ([0, 1, 2, ..., N]). Use it if you need to insert an image by index relative to the beginning of a string.
– `reversed` is for indexing a string from the end to the beginning ([N, ..., 2, 1, 0]). Use it if you need to insert an image by index relative to the end of a string.
 * @param preloadRequired Background image must be loaded before the display.
 * @param start A symbol to insert prior to an image. To insert an image at the end of the text, specify the number of the last character plus one.
 * @param tintColor New color of a contour image.
 * @param tintMode Blend mode of the color specified in `tint_color`.
 * @param url Image URL.
 * @param width Image width.
 */
@Generated
fun Text.Image.override(
    `use named arguments`: Guard = Guard.instance,
    accessibility: Text.Image.Accessibility? = null,
    alignmentVertical: TextAlignmentVertical? = null,
    height: FixedSize? = null,
    indexingDirection: Text.Image.IndexingDirection? = null,
    preloadRequired: Boolean? = null,
    start: Int? = null,
    tintColor: Color? = null,
    tintMode: BlendMode? = null,
    url: Url? = null,
    width: FixedSize? = null,
): Text.Image = Text.Image(
    Text.Image.Properties(
        accessibility = valueOrNull(accessibility) ?: properties.accessibility,
        alignmentVertical = valueOrNull(alignmentVertical) ?: properties.alignmentVertical,
        height = valueOrNull(height) ?: properties.height,
        indexingDirection = valueOrNull(indexingDirection) ?: properties.indexingDirection,
        preloadRequired = valueOrNull(preloadRequired) ?: properties.preloadRequired,
        start = valueOrNull(start) ?: properties.start,
        tintColor = valueOrNull(tintColor) ?: properties.tintColor,
        tintMode = valueOrNull(tintMode) ?: properties.tintMode,
        url = valueOrNull(url) ?: properties.url,
        width = valueOrNull(width) ?: properties.width,
    )
)

/**
 * @param alignmentVertical Vertical image alignment within the row.
 * @param height Image height.
 * @param indexingDirection Defines the direction in the `start` parameter:
– `normal` is for regular string indexing ([0, 1, 2, ..., N]). Use it if you need to insert an image by index relative to the beginning of a string.
– `reversed` is for indexing a string from the end to the beginning ([N, ..., 2, 1, 0]). Use it if you need to insert an image by index relative to the end of a string.
 * @param preloadRequired Background image must be loaded before the display.
 * @param start A symbol to insert prior to an image. To insert an image at the end of the text, specify the number of the last character plus one.
 * @param tintColor New color of a contour image.
 * @param tintMode Blend mode of the color specified in `tint_color`.
 * @param url Image URL.
 * @param width Image width.
 */
@Generated
fun Text.Image.defer(
    `use named arguments`: Guard = Guard.instance,
    accessibility: ReferenceProperty<Text.Image.Accessibility>? = null,
    alignmentVertical: ReferenceProperty<TextAlignmentVertical>? = null,
    height: ReferenceProperty<FixedSize>? = null,
    indexingDirection: ReferenceProperty<Text.Image.IndexingDirection>? = null,
    preloadRequired: ReferenceProperty<Boolean>? = null,
    start: ReferenceProperty<Int>? = null,
    tintColor: ReferenceProperty<Color>? = null,
    tintMode: ReferenceProperty<BlendMode>? = null,
    url: ReferenceProperty<Url>? = null,
    width: ReferenceProperty<FixedSize>? = null,
): Text.Image = Text.Image(
    Text.Image.Properties(
        accessibility = accessibility ?: properties.accessibility,
        alignmentVertical = alignmentVertical ?: properties.alignmentVertical,
        height = height ?: properties.height,
        indexingDirection = indexingDirection ?: properties.indexingDirection,
        preloadRequired = preloadRequired ?: properties.preloadRequired,
        start = start ?: properties.start,
        tintColor = tintColor ?: properties.tintColor,
        tintMode = tintMode ?: properties.tintMode,
        url = url ?: properties.url,
        width = width ?: properties.width,
    )
)

/**
 * @param alignmentVertical Vertical image alignment within the row.
 * @param height Image height.
 * @param indexingDirection Defines the direction in the `start` parameter:
– `normal` is for regular string indexing ([0, 1, 2, ..., N]). Use it if you need to insert an image by index relative to the beginning of a string.
– `reversed` is for indexing a string from the end to the beginning ([N, ..., 2, 1, 0]). Use it if you need to insert an image by index relative to the end of a string.
 * @param preloadRequired Background image must be loaded before the display.
 * @param start A symbol to insert prior to an image. To insert an image at the end of the text, specify the number of the last character plus one.
 * @param tintColor New color of a contour image.
 * @param tintMode Blend mode of the color specified in `tint_color`.
 * @param url Image URL.
 * @param width Image width.
 */
@Generated
fun Text.Image.modify(
    `use named arguments`: Guard = Guard.instance,
    accessibility: Property<Text.Image.Accessibility>? = null,
    alignmentVertical: Property<TextAlignmentVertical>? = null,
    height: Property<FixedSize>? = null,
    indexingDirection: Property<Text.Image.IndexingDirection>? = null,
    preloadRequired: Property<Boolean>? = null,
    start: Property<Int>? = null,
    tintColor: Property<Color>? = null,
    tintMode: Property<BlendMode>? = null,
    url: Property<Url>? = null,
    width: Property<FixedSize>? = null,
): Text.Image = Text.Image(
    Text.Image.Properties(
        accessibility = accessibility ?: properties.accessibility,
        alignmentVertical = alignmentVertical ?: properties.alignmentVertical,
        height = height ?: properties.height,
        indexingDirection = indexingDirection ?: properties.indexingDirection,
        preloadRequired = preloadRequired ?: properties.preloadRequired,
        start = start ?: properties.start,
        tintColor = tintColor ?: properties.tintColor,
        tintMode = tintMode ?: properties.tintMode,
        url = url ?: properties.url,
        width = width ?: properties.width,
    )
)

/**
 * @param alignmentVertical Vertical image alignment within the row.
 * @param indexingDirection Defines the direction in the `start` parameter:
– `normal` is for regular string indexing ([0, 1, 2, ..., N]). Use it if you need to insert an image by index relative to the beginning of a string.
– `reversed` is for indexing a string from the end to the beginning ([N, ..., 2, 1, 0]). Use it if you need to insert an image by index relative to the end of a string.
 * @param preloadRequired Background image must be loaded before the display.
 * @param start A symbol to insert prior to an image. To insert an image at the end of the text, specify the number of the last character plus one.
 * @param tintColor New color of a contour image.
 * @param tintMode Blend mode of the color specified in `tint_color`.
 * @param url Image URL.
 */
@Generated
fun Text.Image.evaluate(
    `use named arguments`: Guard = Guard.instance,
    alignmentVertical: ExpressionProperty<TextAlignmentVertical>? = null,
    indexingDirection: ExpressionProperty<Text.Image.IndexingDirection>? = null,
    preloadRequired: ExpressionProperty<Boolean>? = null,
    start: ExpressionProperty<Int>? = null,
    tintColor: ExpressionProperty<Color>? = null,
    tintMode: ExpressionProperty<BlendMode>? = null,
    url: ExpressionProperty<Url>? = null,
): Text.Image = Text.Image(
    Text.Image.Properties(
        accessibility = properties.accessibility,
        alignmentVertical = alignmentVertical ?: properties.alignmentVertical,
        height = properties.height,
        indexingDirection = indexingDirection ?: properties.indexingDirection,
        preloadRequired = preloadRequired ?: properties.preloadRequired,
        start = start ?: properties.start,
        tintColor = tintColor ?: properties.tintColor,
        tintMode = tintMode ?: properties.tintMode,
        url = url ?: properties.url,
        width = properties.width,
    )
)

@Generated
fun Text.Image.asList() = listOf(this)

/**
 * @param actions Action when clicking on text.
 * @param alignmentVertical Vertical text alignment within the row. Ignored when a baseline offset is specified.
 * @param background Character range background.
 * @param baselineOffset Vertical baseline offset in a character range. If set, vertical alignment is ignored.
 * @param border Character range border.
 * @param end Ordinal number of the last character to be included in the range. If the property is omitted, the range ends at the last character of the text.
 * @param fontFamily Font family:<li>`text` — a standard text font;</li><li>`display` — a family of fonts with a large font size.</li>
 * @param fontFeatureSettings List of OpenType font features. The format matches the CSS attribute "font-feature-settings". Learn more: https://www.w3.org/TR/css-fonts-3/#font-feature-settings-prop
 * @param fontSize Font size.
 * @param fontSizeUnit Unit of measurement:<li>`px` — a physical pixel.</li><li>`dp` — a logical pixel that doesn't depend on screen density.</li><li>`sp` — a logical pixel that depends on the font size on a device. Specify height in `sp`. Only available on Android.</li>
 * @param fontVariationSettings List of TrueType/OpenType font features. The object is constructed from pairs of axis tag and style values. The axis tag must contain four ASCII characters.
 * @param fontWeight Style.
 * @param fontWeightValue Style. Numeric value.
 * @param letterSpacing Spacing between characters.
 * @param lineHeight Line spacing of the text. Units specified in `font_size_unit`.
 * @param mask A mask that hides a part of text. To show the hidden text, disable the mask using the `is_enabled` property.
 * @param start Ordinal number of a character which the range begins from. The first character has a number `0`.
 * @param strike Strikethrough.
 * @param textColor Text color for a specific range. Priority: has the highest priority over `text_gradient` and `text_color`.
 * @param textShadow Parameters of the shadow applied to the character range.
 * @param topOffset Top margin of the character range. Units specified in `font_size_unit`.
 * @param underline Underline.
 */
@Generated
fun DivScope.textRange(
    `use named arguments`: Guard = Guard.instance,
    actions: List<Action>? = null,
    alignmentVertical: TextAlignmentVertical? = null,
    background: TextRangeBackground? = null,
    baselineOffset: Double? = null,
    border: TextRangeBorder? = null,
    end: Int? = null,
    fontFamily: String? = null,
    fontFeatureSettings: String? = null,
    fontSize: Int? = null,
    fontSizeUnit: SizeUnit? = null,
    fontVariationSettings: Map<String, Any>? = null,
    fontWeight: FontWeight? = null,
    fontWeightValue: Int? = null,
    letterSpacing: Double? = null,
    lineHeight: Int? = null,
    mask: TextRangeMask? = null,
    start: Int? = null,
    strike: LineStyle? = null,
    textColor: Color? = null,
    textShadow: Shadow? = null,
    topOffset: Int? = null,
    underline: LineStyle? = null,
): Text.Range = Text.Range(
    Text.Range.Properties(
        actions = valueOrNull(actions),
        alignmentVertical = valueOrNull(alignmentVertical),
        background = valueOrNull(background),
        baselineOffset = valueOrNull(baselineOffset),
        border = valueOrNull(border),
        end = valueOrNull(end),
        fontFamily = valueOrNull(fontFamily),
        fontFeatureSettings = valueOrNull(fontFeatureSettings),
        fontSize = valueOrNull(fontSize),
        fontSizeUnit = valueOrNull(fontSizeUnit),
        fontVariationSettings = valueOrNull(fontVariationSettings),
        fontWeight = valueOrNull(fontWeight),
        fontWeightValue = valueOrNull(fontWeightValue),
        letterSpacing = valueOrNull(letterSpacing),
        lineHeight = valueOrNull(lineHeight),
        mask = valueOrNull(mask),
        start = valueOrNull(start),
        strike = valueOrNull(strike),
        textColor = valueOrNull(textColor),
        textShadow = valueOrNull(textShadow),
        topOffset = valueOrNull(topOffset),
        underline = valueOrNull(underline),
    )
)

/**
 * @param actions Action when clicking on text.
 * @param alignmentVertical Vertical text alignment within the row. Ignored when a baseline offset is specified.
 * @param background Character range background.
 * @param baselineOffset Vertical baseline offset in a character range. If set, vertical alignment is ignored.
 * @param border Character range border.
 * @param end Ordinal number of the last character to be included in the range. If the property is omitted, the range ends at the last character of the text.
 * @param fontFamily Font family:<li>`text` — a standard text font;</li><li>`display` — a family of fonts with a large font size.</li>
 * @param fontFeatureSettings List of OpenType font features. The format matches the CSS attribute "font-feature-settings". Learn more: https://www.w3.org/TR/css-fonts-3/#font-feature-settings-prop
 * @param fontSize Font size.
 * @param fontSizeUnit Unit of measurement:<li>`px` — a physical pixel.</li><li>`dp` — a logical pixel that doesn't depend on screen density.</li><li>`sp` — a logical pixel that depends on the font size on a device. Specify height in `sp`. Only available on Android.</li>
 * @param fontVariationSettings List of TrueType/OpenType font features. The object is constructed from pairs of axis tag and style values. The axis tag must contain four ASCII characters.
 * @param fontWeight Style.
 * @param fontWeightValue Style. Numeric value.
 * @param letterSpacing Spacing between characters.
 * @param lineHeight Line spacing of the text. Units specified in `font_size_unit`.
 * @param mask A mask that hides a part of text. To show the hidden text, disable the mask using the `is_enabled` property.
 * @param start Ordinal number of a character which the range begins from. The first character has a number `0`.
 * @param strike Strikethrough.
 * @param textColor Text color for a specific range. Priority: has the highest priority over `text_gradient` and `text_color`.
 * @param textShadow Parameters of the shadow applied to the character range.
 * @param topOffset Top margin of the character range. Units specified in `font_size_unit`.
 * @param underline Underline.
 */
@Generated
fun DivScope.textRangeProps(
    `use named arguments`: Guard = Guard.instance,
    actions: List<Action>? = null,
    alignmentVertical: TextAlignmentVertical? = null,
    background: TextRangeBackground? = null,
    baselineOffset: Double? = null,
    border: TextRangeBorder? = null,
    end: Int? = null,
    fontFamily: String? = null,
    fontFeatureSettings: String? = null,
    fontSize: Int? = null,
    fontSizeUnit: SizeUnit? = null,
    fontVariationSettings: Map<String, Any>? = null,
    fontWeight: FontWeight? = null,
    fontWeightValue: Int? = null,
    letterSpacing: Double? = null,
    lineHeight: Int? = null,
    mask: TextRangeMask? = null,
    start: Int? = null,
    strike: LineStyle? = null,
    textColor: Color? = null,
    textShadow: Shadow? = null,
    topOffset: Int? = null,
    underline: LineStyle? = null,
) = Text.Range.Properties(
    actions = valueOrNull(actions),
    alignmentVertical = valueOrNull(alignmentVertical),
    background = valueOrNull(background),
    baselineOffset = valueOrNull(baselineOffset),
    border = valueOrNull(border),
    end = valueOrNull(end),
    fontFamily = valueOrNull(fontFamily),
    fontFeatureSettings = valueOrNull(fontFeatureSettings),
    fontSize = valueOrNull(fontSize),
    fontSizeUnit = valueOrNull(fontSizeUnit),
    fontVariationSettings = valueOrNull(fontVariationSettings),
    fontWeight = valueOrNull(fontWeight),
    fontWeightValue = valueOrNull(fontWeightValue),
    letterSpacing = valueOrNull(letterSpacing),
    lineHeight = valueOrNull(lineHeight),
    mask = valueOrNull(mask),
    start = valueOrNull(start),
    strike = valueOrNull(strike),
    textColor = valueOrNull(textColor),
    textShadow = valueOrNull(textShadow),
    topOffset = valueOrNull(topOffset),
    underline = valueOrNull(underline),
)

/**
 * @param actions Action when clicking on text.
 * @param alignmentVertical Vertical text alignment within the row. Ignored when a baseline offset is specified.
 * @param background Character range background.
 * @param baselineOffset Vertical baseline offset in a character range. If set, vertical alignment is ignored.
 * @param border Character range border.
 * @param end Ordinal number of the last character to be included in the range. If the property is omitted, the range ends at the last character of the text.
 * @param fontFamily Font family:<li>`text` — a standard text font;</li><li>`display` — a family of fonts with a large font size.</li>
 * @param fontFeatureSettings List of OpenType font features. The format matches the CSS attribute "font-feature-settings". Learn more: https://www.w3.org/TR/css-fonts-3/#font-feature-settings-prop
 * @param fontSize Font size.
 * @param fontSizeUnit Unit of measurement:<li>`px` — a physical pixel.</li><li>`dp` — a logical pixel that doesn't depend on screen density.</li><li>`sp` — a logical pixel that depends on the font size on a device. Specify height in `sp`. Only available on Android.</li>
 * @param fontVariationSettings List of TrueType/OpenType font features. The object is constructed from pairs of axis tag and style values. The axis tag must contain four ASCII characters.
 * @param fontWeight Style.
 * @param fontWeightValue Style. Numeric value.
 * @param letterSpacing Spacing between characters.
 * @param lineHeight Line spacing of the text. Units specified in `font_size_unit`.
 * @param mask A mask that hides a part of text. To show the hidden text, disable the mask using the `is_enabled` property.
 * @param start Ordinal number of a character which the range begins from. The first character has a number `0`.
 * @param strike Strikethrough.
 * @param textColor Text color for a specific range. Priority: has the highest priority over `text_gradient` and `text_color`.
 * @param textShadow Parameters of the shadow applied to the character range.
 * @param topOffset Top margin of the character range. Units specified in `font_size_unit`.
 * @param underline Underline.
 */
@Generated
fun TemplateScope.textRangeRefs(
    `use named arguments`: Guard = Guard.instance,
    actions: ReferenceProperty<List<Action>>? = null,
    alignmentVertical: ReferenceProperty<TextAlignmentVertical>? = null,
    background: ReferenceProperty<TextRangeBackground>? = null,
    baselineOffset: ReferenceProperty<Double>? = null,
    border: ReferenceProperty<TextRangeBorder>? = null,
    end: ReferenceProperty<Int>? = null,
    fontFamily: ReferenceProperty<String>? = null,
    fontFeatureSettings: ReferenceProperty<String>? = null,
    fontSize: ReferenceProperty<Int>? = null,
    fontSizeUnit: ReferenceProperty<SizeUnit>? = null,
    fontVariationSettings: ReferenceProperty<Map<String, Any>>? = null,
    fontWeight: ReferenceProperty<FontWeight>? = null,
    fontWeightValue: ReferenceProperty<Int>? = null,
    letterSpacing: ReferenceProperty<Double>? = null,
    lineHeight: ReferenceProperty<Int>? = null,
    mask: ReferenceProperty<TextRangeMask>? = null,
    start: ReferenceProperty<Int>? = null,
    strike: ReferenceProperty<LineStyle>? = null,
    textColor: ReferenceProperty<Color>? = null,
    textShadow: ReferenceProperty<Shadow>? = null,
    topOffset: ReferenceProperty<Int>? = null,
    underline: ReferenceProperty<LineStyle>? = null,
) = Text.Range.Properties(
    actions = actions,
    alignmentVertical = alignmentVertical,
    background = background,
    baselineOffset = baselineOffset,
    border = border,
    end = end,
    fontFamily = fontFamily,
    fontFeatureSettings = fontFeatureSettings,
    fontSize = fontSize,
    fontSizeUnit = fontSizeUnit,
    fontVariationSettings = fontVariationSettings,
    fontWeight = fontWeight,
    fontWeightValue = fontWeightValue,
    letterSpacing = letterSpacing,
    lineHeight = lineHeight,
    mask = mask,
    start = start,
    strike = strike,
    textColor = textColor,
    textShadow = textShadow,
    topOffset = topOffset,
    underline = underline,
)

/**
 * @param actions Action when clicking on text.
 * @param alignmentVertical Vertical text alignment within the row. Ignored when a baseline offset is specified.
 * @param background Character range background.
 * @param baselineOffset Vertical baseline offset in a character range. If set, vertical alignment is ignored.
 * @param border Character range border.
 * @param end Ordinal number of the last character to be included in the range. If the property is omitted, the range ends at the last character of the text.
 * @param fontFamily Font family:<li>`text` — a standard text font;</li><li>`display` — a family of fonts with a large font size.</li>
 * @param fontFeatureSettings List of OpenType font features. The format matches the CSS attribute "font-feature-settings". Learn more: https://www.w3.org/TR/css-fonts-3/#font-feature-settings-prop
 * @param fontSize Font size.
 * @param fontSizeUnit Unit of measurement:<li>`px` — a physical pixel.</li><li>`dp` — a logical pixel that doesn't depend on screen density.</li><li>`sp` — a logical pixel that depends on the font size on a device. Specify height in `sp`. Only available on Android.</li>
 * @param fontVariationSettings List of TrueType/OpenType font features. The object is constructed from pairs of axis tag and style values. The axis tag must contain four ASCII characters.
 * @param fontWeight Style.
 * @param fontWeightValue Style. Numeric value.
 * @param letterSpacing Spacing between characters.
 * @param lineHeight Line spacing of the text. Units specified in `font_size_unit`.
 * @param mask A mask that hides a part of text. To show the hidden text, disable the mask using the `is_enabled` property.
 * @param start Ordinal number of a character which the range begins from. The first character has a number `0`.
 * @param strike Strikethrough.
 * @param textColor Text color for a specific range. Priority: has the highest priority over `text_gradient` and `text_color`.
 * @param textShadow Parameters of the shadow applied to the character range.
 * @param topOffset Top margin of the character range. Units specified in `font_size_unit`.
 * @param underline Underline.
 */
@Generated
fun Text.Range.override(
    `use named arguments`: Guard = Guard.instance,
    actions: List<Action>? = null,
    alignmentVertical: TextAlignmentVertical? = null,
    background: TextRangeBackground? = null,
    baselineOffset: Double? = null,
    border: TextRangeBorder? = null,
    end: Int? = null,
    fontFamily: String? = null,
    fontFeatureSettings: String? = null,
    fontSize: Int? = null,
    fontSizeUnit: SizeUnit? = null,
    fontVariationSettings: Map<String, Any>? = null,
    fontWeight: FontWeight? = null,
    fontWeightValue: Int? = null,
    letterSpacing: Double? = null,
    lineHeight: Int? = null,
    mask: TextRangeMask? = null,
    start: Int? = null,
    strike: LineStyle? = null,
    textColor: Color? = null,
    textShadow: Shadow? = null,
    topOffset: Int? = null,
    underline: LineStyle? = null,
): Text.Range = Text.Range(
    Text.Range.Properties(
        actions = valueOrNull(actions) ?: properties.actions,
        alignmentVertical = valueOrNull(alignmentVertical) ?: properties.alignmentVertical,
        background = valueOrNull(background) ?: properties.background,
        baselineOffset = valueOrNull(baselineOffset) ?: properties.baselineOffset,
        border = valueOrNull(border) ?: properties.border,
        end = valueOrNull(end) ?: properties.end,
        fontFamily = valueOrNull(fontFamily) ?: properties.fontFamily,
        fontFeatureSettings = valueOrNull(fontFeatureSettings) ?: properties.fontFeatureSettings,
        fontSize = valueOrNull(fontSize) ?: properties.fontSize,
        fontSizeUnit = valueOrNull(fontSizeUnit) ?: properties.fontSizeUnit,
        fontVariationSettings = valueOrNull(fontVariationSettings) ?: properties.fontVariationSettings,
        fontWeight = valueOrNull(fontWeight) ?: properties.fontWeight,
        fontWeightValue = valueOrNull(fontWeightValue) ?: properties.fontWeightValue,
        letterSpacing = valueOrNull(letterSpacing) ?: properties.letterSpacing,
        lineHeight = valueOrNull(lineHeight) ?: properties.lineHeight,
        mask = valueOrNull(mask) ?: properties.mask,
        start = valueOrNull(start) ?: properties.start,
        strike = valueOrNull(strike) ?: properties.strike,
        textColor = valueOrNull(textColor) ?: properties.textColor,
        textShadow = valueOrNull(textShadow) ?: properties.textShadow,
        topOffset = valueOrNull(topOffset) ?: properties.topOffset,
        underline = valueOrNull(underline) ?: properties.underline,
    )
)

/**
 * @param actions Action when clicking on text.
 * @param alignmentVertical Vertical text alignment within the row. Ignored when a baseline offset is specified.
 * @param background Character range background.
 * @param baselineOffset Vertical baseline offset in a character range. If set, vertical alignment is ignored.
 * @param border Character range border.
 * @param end Ordinal number of the last character to be included in the range. If the property is omitted, the range ends at the last character of the text.
 * @param fontFamily Font family:<li>`text` — a standard text font;</li><li>`display` — a family of fonts with a large font size.</li>
 * @param fontFeatureSettings List of OpenType font features. The format matches the CSS attribute "font-feature-settings". Learn more: https://www.w3.org/TR/css-fonts-3/#font-feature-settings-prop
 * @param fontSize Font size.
 * @param fontSizeUnit Unit of measurement:<li>`px` — a physical pixel.</li><li>`dp` — a logical pixel that doesn't depend on screen density.</li><li>`sp` — a logical pixel that depends on the font size on a device. Specify height in `sp`. Only available on Android.</li>
 * @param fontVariationSettings List of TrueType/OpenType font features. The object is constructed from pairs of axis tag and style values. The axis tag must contain four ASCII characters.
 * @param fontWeight Style.
 * @param fontWeightValue Style. Numeric value.
 * @param letterSpacing Spacing between characters.
 * @param lineHeight Line spacing of the text. Units specified in `font_size_unit`.
 * @param mask A mask that hides a part of text. To show the hidden text, disable the mask using the `is_enabled` property.
 * @param start Ordinal number of a character which the range begins from. The first character has a number `0`.
 * @param strike Strikethrough.
 * @param textColor Text color for a specific range. Priority: has the highest priority over `text_gradient` and `text_color`.
 * @param textShadow Parameters of the shadow applied to the character range.
 * @param topOffset Top margin of the character range. Units specified in `font_size_unit`.
 * @param underline Underline.
 */
@Generated
fun Text.Range.defer(
    `use named arguments`: Guard = Guard.instance,
    actions: ReferenceProperty<List<Action>>? = null,
    alignmentVertical: ReferenceProperty<TextAlignmentVertical>? = null,
    background: ReferenceProperty<TextRangeBackground>? = null,
    baselineOffset: ReferenceProperty<Double>? = null,
    border: ReferenceProperty<TextRangeBorder>? = null,
    end: ReferenceProperty<Int>? = null,
    fontFamily: ReferenceProperty<String>? = null,
    fontFeatureSettings: ReferenceProperty<String>? = null,
    fontSize: ReferenceProperty<Int>? = null,
    fontSizeUnit: ReferenceProperty<SizeUnit>? = null,
    fontVariationSettings: ReferenceProperty<Map<String, Any>>? = null,
    fontWeight: ReferenceProperty<FontWeight>? = null,
    fontWeightValue: ReferenceProperty<Int>? = null,
    letterSpacing: ReferenceProperty<Double>? = null,
    lineHeight: ReferenceProperty<Int>? = null,
    mask: ReferenceProperty<TextRangeMask>? = null,
    start: ReferenceProperty<Int>? = null,
    strike: ReferenceProperty<LineStyle>? = null,
    textColor: ReferenceProperty<Color>? = null,
    textShadow: ReferenceProperty<Shadow>? = null,
    topOffset: ReferenceProperty<Int>? = null,
    underline: ReferenceProperty<LineStyle>? = null,
): Text.Range = Text.Range(
    Text.Range.Properties(
        actions = actions ?: properties.actions,
        alignmentVertical = alignmentVertical ?: properties.alignmentVertical,
        background = background ?: properties.background,
        baselineOffset = baselineOffset ?: properties.baselineOffset,
        border = border ?: properties.border,
        end = end ?: properties.end,
        fontFamily = fontFamily ?: properties.fontFamily,
        fontFeatureSettings = fontFeatureSettings ?: properties.fontFeatureSettings,
        fontSize = fontSize ?: properties.fontSize,
        fontSizeUnit = fontSizeUnit ?: properties.fontSizeUnit,
        fontVariationSettings = fontVariationSettings ?: properties.fontVariationSettings,
        fontWeight = fontWeight ?: properties.fontWeight,
        fontWeightValue = fontWeightValue ?: properties.fontWeightValue,
        letterSpacing = letterSpacing ?: properties.letterSpacing,
        lineHeight = lineHeight ?: properties.lineHeight,
        mask = mask ?: properties.mask,
        start = start ?: properties.start,
        strike = strike ?: properties.strike,
        textColor = textColor ?: properties.textColor,
        textShadow = textShadow ?: properties.textShadow,
        topOffset = topOffset ?: properties.topOffset,
        underline = underline ?: properties.underline,
    )
)

/**
 * @param actions Action when clicking on text.
 * @param alignmentVertical Vertical text alignment within the row. Ignored when a baseline offset is specified.
 * @param background Character range background.
 * @param baselineOffset Vertical baseline offset in a character range. If set, vertical alignment is ignored.
 * @param border Character range border.
 * @param end Ordinal number of the last character to be included in the range. If the property is omitted, the range ends at the last character of the text.
 * @param fontFamily Font family:<li>`text` — a standard text font;</li><li>`display` — a family of fonts with a large font size.</li>
 * @param fontFeatureSettings List of OpenType font features. The format matches the CSS attribute "font-feature-settings". Learn more: https://www.w3.org/TR/css-fonts-3/#font-feature-settings-prop
 * @param fontSize Font size.
 * @param fontSizeUnit Unit of measurement:<li>`px` — a physical pixel.</li><li>`dp` — a logical pixel that doesn't depend on screen density.</li><li>`sp` — a logical pixel that depends on the font size on a device. Specify height in `sp`. Only available on Android.</li>
 * @param fontVariationSettings List of TrueType/OpenType font features. The object is constructed from pairs of axis tag and style values. The axis tag must contain four ASCII characters.
 * @param fontWeight Style.
 * @param fontWeightValue Style. Numeric value.
 * @param letterSpacing Spacing between characters.
 * @param lineHeight Line spacing of the text. Units specified in `font_size_unit`.
 * @param mask A mask that hides a part of text. To show the hidden text, disable the mask using the `is_enabled` property.
 * @param start Ordinal number of a character which the range begins from. The first character has a number `0`.
 * @param strike Strikethrough.
 * @param textColor Text color for a specific range. Priority: has the highest priority over `text_gradient` and `text_color`.
 * @param textShadow Parameters of the shadow applied to the character range.
 * @param topOffset Top margin of the character range. Units specified in `font_size_unit`.
 * @param underline Underline.
 */
@Generated
fun Text.Range.modify(
    `use named arguments`: Guard = Guard.instance,
    actions: Property<List<Action>>? = null,
    alignmentVertical: Property<TextAlignmentVertical>? = null,
    background: Property<TextRangeBackground>? = null,
    baselineOffset: Property<Double>? = null,
    border: Property<TextRangeBorder>? = null,
    end: Property<Int>? = null,
    fontFamily: Property<String>? = null,
    fontFeatureSettings: Property<String>? = null,
    fontSize: Property<Int>? = null,
    fontSizeUnit: Property<SizeUnit>? = null,
    fontVariationSettings: Property<Map<String, Any>>? = null,
    fontWeight: Property<FontWeight>? = null,
    fontWeightValue: Property<Int>? = null,
    letterSpacing: Property<Double>? = null,
    lineHeight: Property<Int>? = null,
    mask: Property<TextRangeMask>? = null,
    start: Property<Int>? = null,
    strike: Property<LineStyle>? = null,
    textColor: Property<Color>? = null,
    textShadow: Property<Shadow>? = null,
    topOffset: Property<Int>? = null,
    underline: Property<LineStyle>? = null,
): Text.Range = Text.Range(
    Text.Range.Properties(
        actions = actions ?: properties.actions,
        alignmentVertical = alignmentVertical ?: properties.alignmentVertical,
        background = background ?: properties.background,
        baselineOffset = baselineOffset ?: properties.baselineOffset,
        border = border ?: properties.border,
        end = end ?: properties.end,
        fontFamily = fontFamily ?: properties.fontFamily,
        fontFeatureSettings = fontFeatureSettings ?: properties.fontFeatureSettings,
        fontSize = fontSize ?: properties.fontSize,
        fontSizeUnit = fontSizeUnit ?: properties.fontSizeUnit,
        fontVariationSettings = fontVariationSettings ?: properties.fontVariationSettings,
        fontWeight = fontWeight ?: properties.fontWeight,
        fontWeightValue = fontWeightValue ?: properties.fontWeightValue,
        letterSpacing = letterSpacing ?: properties.letterSpacing,
        lineHeight = lineHeight ?: properties.lineHeight,
        mask = mask ?: properties.mask,
        start = start ?: properties.start,
        strike = strike ?: properties.strike,
        textColor = textColor ?: properties.textColor,
        textShadow = textShadow ?: properties.textShadow,
        topOffset = topOffset ?: properties.topOffset,
        underline = underline ?: properties.underline,
    )
)

/**
 * @param alignmentVertical Vertical text alignment within the row. Ignored when a baseline offset is specified.
 * @param baselineOffset Vertical baseline offset in a character range. If set, vertical alignment is ignored.
 * @param end Ordinal number of the last character to be included in the range. If the property is omitted, the range ends at the last character of the text.
 * @param fontFamily Font family:<li>`text` — a standard text font;</li><li>`display` — a family of fonts with a large font size.</li>
 * @param fontFeatureSettings List of OpenType font features. The format matches the CSS attribute "font-feature-settings". Learn more: https://www.w3.org/TR/css-fonts-3/#font-feature-settings-prop
 * @param fontSize Font size.
 * @param fontSizeUnit Unit of measurement:<li>`px` — a physical pixel.</li><li>`dp` — a logical pixel that doesn't depend on screen density.</li><li>`sp` — a logical pixel that depends on the font size on a device. Specify height in `sp`. Only available on Android.</li>
 * @param fontWeight Style.
 * @param fontWeightValue Style. Numeric value.
 * @param letterSpacing Spacing between characters.
 * @param lineHeight Line spacing of the text. Units specified in `font_size_unit`.
 * @param start Ordinal number of a character which the range begins from. The first character has a number `0`.
 * @param strike Strikethrough.
 * @param textColor Text color for a specific range. Priority: has the highest priority over `text_gradient` and `text_color`.
 * @param topOffset Top margin of the character range. Units specified in `font_size_unit`.
 * @param underline Underline.
 */
@Generated
fun Text.Range.evaluate(
    `use named arguments`: Guard = Guard.instance,
    alignmentVertical: ExpressionProperty<TextAlignmentVertical>? = null,
    baselineOffset: ExpressionProperty<Double>? = null,
    end: ExpressionProperty<Int>? = null,
    fontFamily: ExpressionProperty<String>? = null,
    fontFeatureSettings: ExpressionProperty<String>? = null,
    fontSize: ExpressionProperty<Int>? = null,
    fontSizeUnit: ExpressionProperty<SizeUnit>? = null,
    fontWeight: ExpressionProperty<FontWeight>? = null,
    fontWeightValue: ExpressionProperty<Int>? = null,
    letterSpacing: ExpressionProperty<Double>? = null,
    lineHeight: ExpressionProperty<Int>? = null,
    start: ExpressionProperty<Int>? = null,
    strike: ExpressionProperty<LineStyle>? = null,
    textColor: ExpressionProperty<Color>? = null,
    topOffset: ExpressionProperty<Int>? = null,
    underline: ExpressionProperty<LineStyle>? = null,
): Text.Range = Text.Range(
    Text.Range.Properties(
        actions = properties.actions,
        alignmentVertical = alignmentVertical ?: properties.alignmentVertical,
        background = properties.background,
        baselineOffset = baselineOffset ?: properties.baselineOffset,
        border = properties.border,
        end = end ?: properties.end,
        fontFamily = fontFamily ?: properties.fontFamily,
        fontFeatureSettings = fontFeatureSettings ?: properties.fontFeatureSettings,
        fontSize = fontSize ?: properties.fontSize,
        fontSizeUnit = fontSizeUnit ?: properties.fontSizeUnit,
        fontVariationSettings = properties.fontVariationSettings,
        fontWeight = fontWeight ?: properties.fontWeight,
        fontWeightValue = fontWeightValue ?: properties.fontWeightValue,
        letterSpacing = letterSpacing ?: properties.letterSpacing,
        lineHeight = lineHeight ?: properties.lineHeight,
        mask = properties.mask,
        start = start ?: properties.start,
        strike = strike ?: properties.strike,
        textColor = textColor ?: properties.textColor,
        textShadow = properties.textShadow,
        topOffset = topOffset ?: properties.topOffset,
        underline = underline ?: properties.underline,
    )
)

@Generated
fun Text.Range.asList() = listOf(this)

@Generated
fun Text.Truncate.asList() = listOf(this)

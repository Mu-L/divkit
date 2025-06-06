<script lang="ts" context="module">
    const FALLBACK_IMAGE = 'data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7';
    const EMPTY_IMAGE = 'empty://';
    // const DEFAULT_PLACEHOLDER_COLOR = correctColor('#14000000');
    const DEFAULT_PLACEHOLDER_COLOR = 'rgba(0,0,0,0.08)';

    const STATE_LOADING = 0;
    const STATE_LOADED = 1;
    const STATE_ERROR = 2;
</script>

<script lang="ts">
    import { getContext, onDestroy } from 'svelte';

    import css from './Image.module.css';

    import type { LayoutParams } from '../../types/layoutParams';
    import type { DivImageData, TintMode } from '../../types/image';
    import type { AlignmentHorizontal, AlignmentVertical } from '../../types/alignment';
    import type { ComponentContext } from '../../types/componentContext';
    import { makeStyle } from '../../utils/makeStyle';
    import { genClassName } from '../../utils/genClassName';
    import { ROOT_CTX, type RootCtxValue } from '../../context/root';
    import { wrapError } from '../../utils/wrapError';
    import { imageSize } from '../../utils/background';
    import { correctImagePosition } from '../../utils/correctImagePosition';
    import { isPositiveNumber } from '../../utils/isPositiveNumber';
    import { correctColor } from '../../utils/correctColor';
    import { correctCSSInterpolator } from '../../utils/correctCSSInterpolator';
    import { correctNonNegativeNumber } from '../../utils/correctNonNegativeNumber';
    import { correctTintMode } from '../../utils/correctTintMode';
    import { getCssFilter } from '../../utils/filters';
    import { prepareBase64 } from '../../utils/prepareBase64';
    import { correctBooleanInt } from '../../utils/correctBooleanInt';
    import Outer from '../utilities/Outer.svelte';
    import DevtoolHolder from '../utilities/DevtoolHolder.svelte';

    export let componentContext: ComponentContext<DivImageData>;
    export let layoutParams: LayoutParams | undefined = undefined;

    const rootCtx = getContext<RootCtxValue>(ROOT_CTX);

    const direction = rootCtx.direction;

    let img: HTMLImageElement;
    let state = STATE_LOADING;
    let isEmpty = false;
    let placeholderColor = DEFAULT_PLACEHOLDER_COLOR;

    let hasError = false;
    let imageUrl: string | undefined;
    let backgroundImage = '';
    // Exactly "none", "scale-down" would not match android
    let scale = 'none';
    let position = '50% 50%';
    let aspectPaddingTop = '0';
    let aspectPaddingBottom = '0';
    let aspectContent = false;
    let aspectContentVAlign = 'center';
    let tintColor: string | undefined = undefined;
    let tintMode: TintMode = 'source_in';
    let svgFilterId = '';
    let animationInterpolator = '';
    let animationFadeStart = 0;
    let animationDelay = 0;
    let animationDuration = 0;
    let filter = '';
    let filterClipPath = '';
    let isRTLMirror = false;
    let highPriorityPreview = false;
    let highPrority = false;

    $: origJson = componentContext.origJson;

    function rebind(): void {
        aspectContent = false;
        scale = 'none';
        position = '50% 50%';
        tintMode = 'source_in';
        highPriorityPreview = false;
        highPrority = false;
    }

    $: if (origJson) {
        rebind();
    }

    $: jsonImageUrl = componentContext.getDerivedFromVars(componentContext.json.image_url);
    $: jsonGifUrl = componentContext.getDerivedFromVars(componentContext.json.gif_url);
    $: jsonWidth = componentContext.getDerivedFromVars(componentContext.json.width);
    $: jsonHeight = componentContext.getDerivedFromVars(componentContext.json.height);
    $: jsonPreview = componentContext.getDerivedFromVars(componentContext.json.preview);
    $: jsonPlaceholderColor = componentContext.getDerivedFromVars(componentContext.json.placeholder_color);
    $: jsonScale = componentContext.getDerivedFromVars(componentContext.json.scale);
    $: jsonPosition = componentContext.getDerivedFromVars({
        content_alignment_horizontal: componentContext.json.content_alignment_horizontal,
        content_alignment_vertical: componentContext.json.content_alignment_vertical
    });
    $: jsonA11y = componentContext.getDerivedFromVars(componentContext.json.accessibility);
    $: jsonAspect = componentContext.getDerivedFromVars(componentContext.json.aspect);
    $: jsonTintColor = componentContext.getDerivedFromVars(componentContext.json.tint_color);
    $: jsonTintMode = componentContext.getDerivedFromVars(componentContext.json.tint_mode);
    $: jsonAppearanceAnimation = componentContext.getDerivedFromVars(componentContext.json.appearance_animation);
    $: jsonFilters = componentContext.getDerivedFromVars(componentContext.json.filters);
    $: jsonPreloadRequired = componentContext.getDerivedFromVars(componentContext.json.preload_required);
    $: jsonHighPriorityPreviewShow =
        componentContext.getDerivedFromVars(componentContext.json.high_priority_preview_show);

    $: {
        let img = componentContext.json.type === 'gif' ? $jsonGifUrl : $jsonImageUrl;
        isEmpty = img === EMPTY_IMAGE;
        if (isEmpty) {
            img = FALLBACK_IMAGE;
        }
        imageUrl = img;
    }

    function updateImageUrl(_url: string | undefined): void {
        state = STATE_LOADING;
    }
    $: updateImageUrl(imageUrl);

    $: {
        highPriorityPreview = correctBooleanInt($jsonHighPriorityPreviewShow, highPriorityPreview);
    }

    $: {
        if (!imageUrl) {
            hasError = true;
            componentContext.logError(wrapError(new Error(`Missing "${componentContext.json.type === 'gif' ? 'gif_url' : 'image_url'}" for "${componentContext.json.type}"`)));
        } else {
            hasError = false;
        }
    }

    $: isWidthContent = $jsonWidth?.type === 'wrap_content';

    $: isHeightContent = $jsonHeight?.type === 'wrap_content';

    $: {
        const preview = $jsonPreview;

        if ((state === STATE_LOADING || state === STATE_ERROR || isEmpty) && preview) {
            backgroundImage = `url("${prepareBase64(preview)}")`;
            highPrority = highPriorityPreview;
        } else {
            backgroundImage = '';
            highPrority = false;
        }
    }

    $: if (state === STATE_LOADING || state === STATE_ERROR || isEmpty) {
        placeholderColor = correctColor($jsonPlaceholderColor, 1, placeholderColor);
    } else {
        placeholderColor = '';
    }

    $: {
        scale = imageSize($jsonScale) || scale;
    }

    function updatePosition(pos: {
        content_alignment_horizontal?: AlignmentHorizontal;
        content_alignment_vertical?: AlignmentVertical;
    }): void {
        position = correctImagePosition(pos, $direction, position);
    }
    $: updatePosition($jsonPosition);

    $: alt = $jsonA11y?.description || '';

    $: {
        aspectContentVAlign = 'center';
        aspectPaddingTop = '0';
        aspectPaddingBottom = '0';

        const newRatio = $jsonAspect?.ratio;
        if (newRatio && isPositiveNumber(newRatio)) {
            const height = 100 / Number(newRatio);
            aspectContent = componentContext.json.width?.type === 'wrap_content';

            if (aspectContent) {
                if ($jsonPosition.content_alignment_vertical === 'top') {
                    aspectPaddingBottom = height.toFixed(2);
                    aspectContentVAlign = 'top';
                } else if ($jsonPosition.content_alignment_vertical === 'bottom') {
                    aspectPaddingTop = height.toFixed(2);
                    aspectContentVAlign = 'bottom';
                } else {
                    aspectPaddingTop = aspectPaddingBottom = (height / 2).toFixed(2);
                }
            } else {
                aspectPaddingBottom = height.toFixed(2);
            }
        } else {
            aspectPaddingBottom = '0';
        }
    }

    $: {
        const val = $jsonTintColor;
        const newTintColor = val ? correctColor(val) : undefined;
        const newTintMode = correctTintMode($jsonTintMode, tintMode);
        if (newTintColor !== tintColor || newTintMode !== tintMode) {
            rootCtx.removeSvgFilter(tintColor, tintMode);
            svgFilterId = newTintColor ? rootCtx.addSvgFilter(newTintColor, newTintMode) : '';
            tintColor = newTintColor;
            tintMode = newTintMode;
        }
    }

    $: if ($jsonAppearanceAnimation && $jsonAppearanceAnimation.type === 'fade') {
        const animation = $jsonAppearanceAnimation;

        animationInterpolator = correctCSSInterpolator(animation.interpolator, 'ease_in_out').replace(/_/g, '-');
        animationDuration = correctNonNegativeNumber(animation.duration, 300);
        animationDelay = correctNonNegativeNumber(animation.start_delay, 0);
        animationFadeStart = correctNonNegativeNumber(animation.alpha, 0);
    }

    $: {
        let newFilter = '';
        let newClipPath = '';
        if (Array.isArray($jsonFilters) && $jsonFilters.length) {
            newFilter = getCssFilter($jsonFilters, componentContext.logError);
        }
        if (newFilter) {
            newClipPath = 'polygon(0% 0%, 0% 100%, 100% 100%, 100% 0%)';
        }
        filter = newFilter;
        filterClipPath = newClipPath;
        isRTLMirror = $direction === 'rtl' && Array.isArray($jsonFilters) && $jsonFilters.some(it => it.type === 'rtl_mirror');
    }

    $: mods = {
        aspect: aspectPaddingBottom !== '0' || aspectPaddingTop !== '0',
        'aspect-content': aspectContent,
        'aspect-valign': aspectContentVAlign !== 'center' ? aspectContentVAlign : undefined,
        'is-width-content': isWidthContent,
        'is-height-content': isHeightContent,
        loaded: state === STATE_LOADED,
        'before-appearance': Boolean(animationInterpolator) && state === STATE_LOADING,
        'is-rtl-mirror': isRTLMirror
    };

    $: style = {
        // Image preview shows, if loading of original image is failed
        'background-image': backgroundImage,
        'background-color': backgroundImage ? undefined : placeholderColor,
        'background-size': scale,
        'clip-path': filterClipPath || undefined,
        'object-fit': scale,
        'object-position': position,
        filter: [
            state === STATE_LOADED && svgFilterId ? `url(#${svgFilterId})` : '',
            filter
        ].filter(Boolean).join(' '),
        '--divkit-appearance-interpolator': animationInterpolator || undefined,
        '--divkit-appearance-fade-start': animationInterpolator ? animationFadeStart : undefined,
        '--divkit-appearance-delay': animationInterpolator ? `${animationDelay}ms` : undefined,
        '--divkit-appearance-duration': animationInterpolator ? `${animationDuration}ms` : undefined
    };

    function onLoad(): void {
        if (state === STATE_LOADING) {
            state = STATE_LOADED;
        }
    }

    function onError(): void {
        if (state === STATE_LOADING) {
            state = STATE_ERROR;
        }
    }

    onDestroy(() => {
        rootCtx.removeSvgFilter(tintColor, tintMode);
    });
</script>

{#if !hasError}
    <Outer
        cls={genClassName('image', css, mods)}
        {componentContext}
        {layoutParams}
        customDescription={true}
    >
        <!-- Safari does not redraw images when changing the svg filter, a complete reconstruction of the DOM is required -->
        {#key svgFilterId}
            {#if mods.aspect}
                <span
                    class={css['image__aspect-wrapper']}
                    style:padding-top="{aspectPaddingTop}%"
                    style:padding-bottom="{aspectPaddingBottom}%"
                >
                    <img
                        bind:this={img}
                        class={css.image__image}
                        src={state === STATE_ERROR ? FALLBACK_IMAGE : imageUrl}
                        loading={($jsonPreloadRequired || highPrority) ? 'eager' : 'lazy'}
                        decoding={highPrority ? 'sync' : 'async'}
                        style={makeStyle(style)}
                        {alt}
                        aria-hidden={alt ? null : 'true'}
                        on:load={onLoad}
                        on:error={onError}
                    >
                </span>
            {:else}
                <img
                    bind:this={img}
                    class={css.image__image}
                    src={state === STATE_ERROR ? FALLBACK_IMAGE : imageUrl}
                    loading={($jsonPreloadRequired || highPrority) ? 'eager' : 'lazy'}
                    decoding={highPrority ? 'sync' : 'async'}
                    style={makeStyle(style)}
                    {alt}
                    aria-hidden={alt ? null : 'true'}
                    on:load={onLoad}
                    on:error={onError}
                >
            {/if}
        {/key}
    </Outer>
{:else if process.env.DEVTOOL}
    <DevtoolHolder
        {componentContext}
    />
{/if}

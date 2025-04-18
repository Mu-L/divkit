<script lang="ts">
    import { getContext, onDestroy } from 'svelte';

    import css from './Select.module.css';

    import type { LayoutParams } from '../../types/layoutParams';
    import type { ComponentContext } from '../../types/componentContext';
    import type { DivSelectData } from '../../types/select';
    import type { EdgeInsets } from '../../types/edgeInserts';
    import { ROOT_CTX, type RootCtxValue } from '../../context/root';
    import { ACTION_CTX, type ActionCtxValue } from '../../context/action';
    import { genClassName } from '../../utils/genClassName';
    import { pxToEm } from '../../utils/pxToEm';
    import { wrapError } from '../../utils/wrapError';
    import { correctColor } from '../../utils/correctColor';
    import { correctPositiveNumber } from '../../utils/correctPositiveNumber';
    import { correctFontWeight } from '../../utils/correctFontWeight';
    import { isPositiveNumber } from '../../utils/isPositiveNumber';
    import { isNumber } from '../../utils/isNumber';
    import { createVariable } from '../../expressions/variable';
    import { correctEdgeInsertsObject } from '../../utils/correctEdgeInsertsObject';
    import { edgeInsertsToCss } from '../../utils/edgeInsertsToCss';
    import { makeStyle } from '../../utils/makeStyle';
    import { composeAccessibilityDescription } from '../../utils/composeAccessibilityDescription';
    import Outer from '../utilities/Outer.svelte';
    import DevtoolHolder from '../utilities/DevtoolHolder.svelte';

    export let componentContext: ComponentContext<DivSelectData>;
    export let layoutParams: LayoutParams | undefined = undefined;

    const rootCtx = getContext<RootCtxValue>(ROOT_CTX);
    const actionCtx = getContext<ActionCtxValue>(ACTION_CTX);

    const direction = rootCtx.direction;

    let prevId: string | undefined;
    let select: HTMLSelectElement;
    let hasError = false;
    let selectText = '';
    let selfPadding: EdgeInsets | null = null;
    let padding = '';
    let hintColor = 'rgba(0,0,0,.45)';
    let fontSize = 12;
    let fontWeight: number | undefined = undefined;
    let fontFamily = '';
    let lineHeight: number | undefined = undefined;
    let letterSpacing = '';
    let textColor = '#000';
    let description = '';
    let prevWarnValue: string | undefined;

    $: origJson = componentContext.origJson;

    function rebind(): void {
        selfPadding = null;
        hintColor = 'rgba(0,0,0,.45)';
        fontSize = 12;
        fontWeight = undefined;
        fontFamily = '';
        lineHeight = undefined;
        letterSpacing = '';
        textColor = '#000';
        description = '';
    }

    $: if (origJson) {
        rebind();
    }

    $: variable = componentContext.json.value_variable;
    $: items = componentContext.json.options;
    $: filteredItems = Array.isArray(items) && items.filter(it => typeof it.value === 'string') || [];

    $: valueVariable = variable && componentContext.getVariable(variable, 'string') || createVariable('temp', 'string', '');

    $: jsonPaddings = componentContext.getDerivedFromVars(componentContext.json.paddings);
    $: jsonHintText = componentContext.getDerivedFromVars(componentContext.json.hint_text);
    $: jsonHintColor = componentContext.getDerivedFromVars(componentContext.json.hint_color);
    $: jsonFontSize = componentContext.getDerivedFromVars(componentContext.json.font_size);
    $: jsonFontWeight = componentContext.getDerivedFromVars(componentContext.json.font_weight);
    $: jsonFontWeightValue = componentContext.getDerivedFromVars(componentContext.json.font_weight_value);
    $: jsonFontFamily = componentContext.getDerivedFromVars(componentContext.json.font_family);
    $: jsonLineHeight = componentContext.getDerivedFromVars(componentContext.json.line_height);
    $: jsonLetterSpacing = componentContext.getDerivedFromVars(componentContext.json.letter_spacing);
    $: jsonTextColor = componentContext.getDerivedFromVars(componentContext.json.text_color);
    $: jsonAccessibility = componentContext.getDerivedFromVars(componentContext.json.accessibility);

    $: if (!(Array.isArray(filteredItems) && filteredItems.length)) {
        componentContext.logError(wrapError(new Error('Empty selection "items" in "select"')));
    }

    $: {
        let newHasError = false;

        if (!variable) {
            hasError = true;
            componentContext.logError(wrapError(new Error('Missing "value_variable" in "select"')));
        } else if (actionCtx.hasAction() || $jsonAccessibility?.mode === 'exclude') {
            newHasError = true;
            componentContext.logError(wrapError(new Error('Cannot show "select" inside component with an action or inside accessibility mode=exclude')));
        }

        if (hasError !== newHasError) {
            hasError = newHasError;
        }
    }

    $: {
        const item = filteredItems.find(it => {
            return it.value === $valueVariable;
        });
        if (item) {
            selectText = (typeof item.text === 'string' ? item.text : item.value) || '';
        } else {
            selectText = '';
            if ($valueVariable && prevWarnValue !== $valueVariable) {
                prevWarnValue = $valueVariable;
                componentContext.logError(wrapError(new Error('Value from the variable was not found in the selection items for "select"')));
            }
        }
    }

    $: {
        selfPadding = correctEdgeInsertsObject(($jsonPaddings) ? $jsonPaddings : undefined, selfPadding);
        padding = selfPadding ? edgeInsertsToCss({
            top: (Number(selfPadding.top) || 0) / fontSize * 10,
            right: (Number($direction === 'ltr' ? selfPadding.end : selfPadding.start) || Number(selfPadding.right) || 0) / fontSize * 10,
            bottom: (Number(selfPadding.bottom) || 0) / fontSize * 10,
            left: (Number($direction === 'ltr' ? selfPadding.start : selfPadding.end) || Number(selfPadding.left) || 0) / fontSize * 10
        }, $direction) : '';
    }

    $: {
        hintColor = correctColor($jsonHintColor, 1, hintColor);
    }

    $: {
        fontSize = correctPositiveNumber($jsonFontSize, fontSize);
    }

    $: {
        fontWeight = correctFontWeight($jsonFontWeight, $jsonFontWeightValue, fontWeight);
        if ($jsonFontFamily && typeof $jsonFontFamily === 'string') {
            fontFamily = rootCtx.typefaceProvider($jsonFontFamily, {
                fontWeight: fontWeight || 400
            });
        } else {
            fontFamily = '';
        }
    }

    $: {
        const val = $jsonLineHeight;
        if (isPositiveNumber(val)) {
            lineHeight = val / fontSize;
        }
    }

    $: {
        if (isNumber($jsonLetterSpacing)) {
            letterSpacing = pxToEm($jsonLetterSpacing / fontSize * 10);
        }
    }

    $: {
        textColor = correctColor($jsonTextColor, 1, textColor);
    }

    $: if ($jsonAccessibility?.description) {
        description = composeAccessibilityDescription($jsonAccessibility);
    } else {
        componentContext.logError(wrapError(new Error('Missing accessibility "description" for select'), {
            level: 'warn'
        }));
    }

    $: mods = {
        hint: !selectText
    };
    $: stl = {
        '--divkit-input-hint-color': hintColor,
        'font-weight': fontWeight,
        'font-family': fontFamily,
        color: textColor
    };
    $: innerStl = {
        padding,
        'font-size': pxToEm(fontSize),
        'line-height': lineHeight,
        'letter-spacing': letterSpacing
    };
    $: selectStl = {
        'font-size': pxToEm(fontSize),
        'line-height': lineHeight,
        'letter-spacing': letterSpacing
    };

    $: if (componentContext.json && select) {
        if (prevId) {
            rootCtx.unregisterFocusable(prevId);
            prevId = undefined;
        }

        if (componentContext.id && !componentContext.fakeElement) {
            prevId = componentContext.id;
            rootCtx.registerFocusable(prevId, {
                focus() {
                    if (select) {
                        select.focus();
                    }
                }
            });
        }
    }

    onDestroy(() => {
        if (prevId) {
            rootCtx.unregisterFocusable(prevId);
            prevId = undefined;
        }
    });
</script>

{#if !hasError}
    <Outer
        let:hasCustomFocus
        let:focusHandler
        let:blurHandler
        cls={genClassName('select', css, mods)}
        style={stl}
        customDescription={true}
        customActions={'select'}
        customPaddings={true}
        hasInnerFocusable={true}
        {componentContext}
        {layoutParams}
    >
        <span class={css['select__select-text']} style={makeStyle(innerStl)} aria-hidden="true">
            <!--Space holder should have height even it has no value-->
            {selectText || $jsonHintText || '​'}
        </span>

        <select
            class={genClassName('select__select', css, { 'has-custom-focus': hasCustomFocus })}
            aria-label={description}
            bind:this={select}
            bind:value={$valueVariable}
            style={makeStyle(selectStl)}
            on:focus={focusHandler}
            on:blur={blurHandler}
        >
            {#each filteredItems as item}
                <option class={css.select__option} value={item.value}>{item.text || item.value}</option>
            {/each}
        </select>
    </Outer>
{:else if process.env.DEVTOOL}
    <DevtoolHolder
        {componentContext}
    />
{/if}

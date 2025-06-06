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
 * Applies a new appearance to the content in `div-state'.
 * 
 * Can be created using the method [actionSetState].
 * 
 * Required parameters: `type, state_id`.
 */
@Generated
data class ActionSetState internal constructor(
    @JsonIgnore
    val properties: Properties,
) : ActionTyped {
    @JsonAnyGetter
    internal fun getJsonProperties(): Map<String, Any> = properties.mergeWith(
        mapOf("type" to "set_state")
    )

    operator fun plus(additive: Properties): ActionSetState = ActionSetState(
        Properties(
            stateId = additive.stateId ?: properties.stateId,
            temporary = additive.temporary ?: properties.temporary,
        )
    )

    data class Properties internal constructor(
        /**
         * The path of the state inside `state` that needs to be activated. Set in the format `div_data_state_id/id/state_id'. Can be hierarchical: `div_data_state_id/id_1/state_id_1/../id_n/state_id_n`. Consists of:<li>`div_data_state_id` — the numeric value of the `state_id` of the `state` object in `data`</li><li>'id` — the `id` value of the `state` object</li><li>`state_id` — the `state_id` value of the `state` object in `state`</li>
         */
        val stateId: Property<String>?,
        /**
         * Indicates a state change:<li>`true` — the change is temporary and will switch to the original one (default value) when the element is recreated</li><li>`false` — the change is permanent</li>
         * Default value: `true`.
         */
        val temporary: Property<Boolean>?,
    ) {
        internal fun mergeWith(properties: Map<String, Any>): Map<String, Any> {
            val result = mutableMapOf<String, Any>()
            result.putAll(properties)
            result.tryPutProperty("state_id", stateId)
            result.tryPutProperty("temporary", temporary)
            return result
        }
    }
}

/**
 * @param stateId The path of the state inside `state` that needs to be activated. Set in the format `div_data_state_id/id/state_id'. Can be hierarchical: `div_data_state_id/id_1/state_id_1/../id_n/state_id_n`. Consists of:<li>`div_data_state_id` — the numeric value of the `state_id` of the `state` object in `data`</li><li>'id` — the `id` value of the `state` object</li><li>`state_id` — the `state_id` value of the `state` object in `state`</li>
 * @param temporary Indicates a state change:<li>`true` — the change is temporary and will switch to the original one (default value) when the element is recreated</li><li>`false` — the change is permanent</li>
 */
@Generated
fun DivScope.actionSetState(
    `use named arguments`: Guard = Guard.instance,
    stateId: String? = null,
    temporary: Boolean? = null,
): ActionSetState = ActionSetState(
    ActionSetState.Properties(
        stateId = valueOrNull(stateId),
        temporary = valueOrNull(temporary),
    )
)

/**
 * @param stateId The path of the state inside `state` that needs to be activated. Set in the format `div_data_state_id/id/state_id'. Can be hierarchical: `div_data_state_id/id_1/state_id_1/../id_n/state_id_n`. Consists of:<li>`div_data_state_id` — the numeric value of the `state_id` of the `state` object in `data`</li><li>'id` — the `id` value of the `state` object</li><li>`state_id` — the `state_id` value of the `state` object in `state`</li>
 * @param temporary Indicates a state change:<li>`true` — the change is temporary and will switch to the original one (default value) when the element is recreated</li><li>`false` — the change is permanent</li>
 */
@Generated
fun DivScope.actionSetStateProps(
    `use named arguments`: Guard = Guard.instance,
    stateId: String? = null,
    temporary: Boolean? = null,
) = ActionSetState.Properties(
    stateId = valueOrNull(stateId),
    temporary = valueOrNull(temporary),
)

/**
 * @param stateId The path of the state inside `state` that needs to be activated. Set in the format `div_data_state_id/id/state_id'. Can be hierarchical: `div_data_state_id/id_1/state_id_1/../id_n/state_id_n`. Consists of:<li>`div_data_state_id` — the numeric value of the `state_id` of the `state` object in `data`</li><li>'id` — the `id` value of the `state` object</li><li>`state_id` — the `state_id` value of the `state` object in `state`</li>
 * @param temporary Indicates a state change:<li>`true` — the change is temporary and will switch to the original one (default value) when the element is recreated</li><li>`false` — the change is permanent</li>
 */
@Generated
fun TemplateScope.actionSetStateRefs(
    `use named arguments`: Guard = Guard.instance,
    stateId: ReferenceProperty<String>? = null,
    temporary: ReferenceProperty<Boolean>? = null,
) = ActionSetState.Properties(
    stateId = stateId,
    temporary = temporary,
)

/**
 * @param stateId The path of the state inside `state` that needs to be activated. Set in the format `div_data_state_id/id/state_id'. Can be hierarchical: `div_data_state_id/id_1/state_id_1/../id_n/state_id_n`. Consists of:<li>`div_data_state_id` — the numeric value of the `state_id` of the `state` object in `data`</li><li>'id` — the `id` value of the `state` object</li><li>`state_id` — the `state_id` value of the `state` object in `state`</li>
 * @param temporary Indicates a state change:<li>`true` — the change is temporary and will switch to the original one (default value) when the element is recreated</li><li>`false` — the change is permanent</li>
 */
@Generated
fun ActionSetState.override(
    `use named arguments`: Guard = Guard.instance,
    stateId: String? = null,
    temporary: Boolean? = null,
): ActionSetState = ActionSetState(
    ActionSetState.Properties(
        stateId = valueOrNull(stateId) ?: properties.stateId,
        temporary = valueOrNull(temporary) ?: properties.temporary,
    )
)

/**
 * @param stateId The path of the state inside `state` that needs to be activated. Set in the format `div_data_state_id/id/state_id'. Can be hierarchical: `div_data_state_id/id_1/state_id_1/../id_n/state_id_n`. Consists of:<li>`div_data_state_id` — the numeric value of the `state_id` of the `state` object in `data`</li><li>'id` — the `id` value of the `state` object</li><li>`state_id` — the `state_id` value of the `state` object in `state`</li>
 * @param temporary Indicates a state change:<li>`true` — the change is temporary and will switch to the original one (default value) when the element is recreated</li><li>`false` — the change is permanent</li>
 */
@Generated
fun ActionSetState.defer(
    `use named arguments`: Guard = Guard.instance,
    stateId: ReferenceProperty<String>? = null,
    temporary: ReferenceProperty<Boolean>? = null,
): ActionSetState = ActionSetState(
    ActionSetState.Properties(
        stateId = stateId ?: properties.stateId,
        temporary = temporary ?: properties.temporary,
    )
)

/**
 * @param stateId The path of the state inside `state` that needs to be activated. Set in the format `div_data_state_id/id/state_id'. Can be hierarchical: `div_data_state_id/id_1/state_id_1/../id_n/state_id_n`. Consists of:<li>`div_data_state_id` — the numeric value of the `state_id` of the `state` object in `data`</li><li>'id` — the `id` value of the `state` object</li><li>`state_id` — the `state_id` value of the `state` object in `state`</li>
 * @param temporary Indicates a state change:<li>`true` — the change is temporary and will switch to the original one (default value) when the element is recreated</li><li>`false` — the change is permanent</li>
 */
@Generated
fun ActionSetState.modify(
    `use named arguments`: Guard = Guard.instance,
    stateId: Property<String>? = null,
    temporary: Property<Boolean>? = null,
): ActionSetState = ActionSetState(
    ActionSetState.Properties(
        stateId = stateId ?: properties.stateId,
        temporary = temporary ?: properties.temporary,
    )
)

/**
 * @param stateId The path of the state inside `state` that needs to be activated. Set in the format `div_data_state_id/id/state_id'. Can be hierarchical: `div_data_state_id/id_1/state_id_1/../id_n/state_id_n`. Consists of:<li>`div_data_state_id` — the numeric value of the `state_id` of the `state` object in `data`</li><li>'id` — the `id` value of the `state` object</li><li>`state_id` — the `state_id` value of the `state` object in `state`</li>
 * @param temporary Indicates a state change:<li>`true` — the change is temporary and will switch to the original one (default value) when the element is recreated</li><li>`false` — the change is permanent</li>
 */
@Generated
fun ActionSetState.evaluate(
    `use named arguments`: Guard = Guard.instance,
    stateId: ExpressionProperty<String>? = null,
    temporary: ExpressionProperty<Boolean>? = null,
): ActionSetState = ActionSetState(
    ActionSetState.Properties(
        stateId = stateId ?: properties.stateId,
        temporary = temporary ?: properties.temporary,
    )
)

@Generated
fun ActionSetState.asList() = listOf(this)

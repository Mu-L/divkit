// Generated code. Do not modify.

package com.yandex.div.reference

import android.graphics.Color
import android.net.Uri
import androidx.annotation.ColorInt
import com.yandex.div.data.*
import com.yandex.div.json.*
import com.yandex.div.json.expressions.Expression
import com.yandex.div.json.expressions.ExpressionsList
import com.yandex.div.json.schema.*
import org.json.JSONArray
import org.json.JSONObject

class EntityWithSimpleProperties(
    @JvmField val boolean: Expression<Boolean>? = null,
    @JvmField val booleanInt: Expression<Boolean>? = null,
    @JvmField val color: Expression<Int>? = null,
    @JvmField val double: Expression<Double>? = null,
    @JvmField val id: Long = ID_DEFAULT_VALUE, // default value: 0
    @JvmField val integer: Expression<Long> = INTEGER_DEFAULT_VALUE, // default value: 0
    @JvmField val positiveInteger: Expression<Long>? = null, // constraint: number > 0
    @JvmField val string: Expression<String>? = null,
    @JvmField val url: Expression<Uri>? = null,
) : JSONSerializable, Hashable {

    private var _hash: Int? = null 

    override fun hash(): Int {
        _hash?.let {
            return it
        }
        val hash = 
            this::class.hashCode() +
            (boolean?.hashCode() ?: 0) +
            (booleanInt?.hashCode() ?: 0) +
            (color?.hashCode() ?: 0) +
            (double?.hashCode() ?: 0) +
            id.hashCode() +
            integer.hashCode() +
            (positiveInteger?.hashCode() ?: 0) +
            (string?.hashCode() ?: 0) +
            (url?.hashCode() ?: 0)
        _hash = hash
        return hash
    }

    fun equals(other: EntityWithSimpleProperties?, resolver: ExpressionResolver, otherResolver: ExpressionResolver): Boolean {
        other ?: return false
        return boolean?.evaluate(resolver) == other.boolean?.evaluate(otherResolver) &&
            booleanInt?.evaluate(resolver) == other.booleanInt?.evaluate(otherResolver) &&
            color?.evaluate(resolver) == other.color?.evaluate(otherResolver) &&
            double?.evaluate(resolver) == other.double?.evaluate(otherResolver) &&
            id == other.id &&
            integer.evaluate(resolver) == other.integer.evaluate(otherResolver) &&
            positiveInteger?.evaluate(resolver) == other.positiveInteger?.evaluate(otherResolver) &&
            string?.evaluate(resolver) == other.string?.evaluate(otherResolver) &&
            url?.evaluate(resolver) == other.url?.evaluate(otherResolver)
    }

    fun copy(
        boolean: Expression<Boolean>? = this.boolean,
        booleanInt: Expression<Boolean>? = this.booleanInt,
        color: Expression<Int>? = this.color,
        double: Expression<Double>? = this.double,
        id: Long = this.id,
        integer: Expression<Long> = this.integer,
        positiveInteger: Expression<Long>? = this.positiveInteger,
        string: Expression<String>? = this.string,
        url: Expression<Uri>? = this.url,
    ) = EntityWithSimpleProperties(
        boolean = boolean,
        booleanInt = booleanInt,
        color = color,
        double = double,
        id = id,
        integer = integer,
        positiveInteger = positiveInteger,
        string = string,
        url = url,
    )

    override fun writeToJSON(): JSONObject {
        val json = JSONObject()
        json.writeExpression(key = "boolean", value = boolean)
        json.writeExpression(key = "boolean_int", value = booleanInt)
        json.writeExpression(key = "color", value = color, converter = COLOR_INT_TO_STRING)
        json.writeExpression(key = "double", value = double)
        json.write(key = "id", value = id)
        json.writeExpression(key = "integer", value = integer)
        json.writeExpression(key = "positive_integer", value = positiveInteger)
        json.writeExpression(key = "string", value = string)
        json.write(key = "type", value = TYPE)
        json.writeExpression(key = "url", value = url, converter = URI_TO_STRING)
        return json
    }

    companion object {
        const val TYPE = "entity_with_simple_properties"

        private val ID_DEFAULT_VALUE = 0L
        private val INTEGER_DEFAULT_VALUE = Expression.constant(0L)

        @JvmStatic
        @JvmName("fromJson")
        operator fun invoke(env: ParsingEnvironment, json: JSONObject): EntityWithSimpleProperties {
            val logger = env.logger
            return EntityWithSimpleProperties(
                boolean = JsonParser.readOptionalExpression(json, "boolean", ANY_TO_BOOLEAN, logger, env, TYPE_HELPER_BOOLEAN),
                booleanInt = JsonParser.readOptionalExpression(json, "boolean_int", ANY_TO_BOOLEAN, logger, env, TYPE_HELPER_BOOLEAN),
                color = JsonParser.readOptionalExpression(json, "color", STRING_TO_COLOR_INT, logger, env, TYPE_HELPER_COLOR),
                double = JsonParser.readOptionalExpression(json, "double", NUMBER_TO_DOUBLE, logger, env, TYPE_HELPER_DOUBLE),
                id = JsonParser.readOptional(json, "id", NUMBER_TO_INT, logger, env) ?: ID_DEFAULT_VALUE,
                integer = JsonParser.readOptionalExpression(json, "integer", NUMBER_TO_INT, logger, env, INTEGER_DEFAULT_VALUE, TYPE_HELPER_INT) ?: INTEGER_DEFAULT_VALUE,
                positiveInteger = JsonParser.readOptionalExpression(json, "positive_integer", NUMBER_TO_INT, POSITIVE_INTEGER_VALIDATOR, logger, env, TYPE_HELPER_INT),
                string = JsonParser.readOptionalExpression(json, "string", logger, env, TYPE_HELPER_STRING),
                url = JsonParser.readOptionalExpression(json, "url", ANY_TO_URI, logger, env, TYPE_HELPER_URI)
            )
        }

        private val POSITIVE_INTEGER_VALIDATOR = ValueValidator<Long> { it: Long -> it > 0 }

        val CREATOR = { env: ParsingEnvironment, it: JSONObject -> EntityWithSimpleProperties(env, json = it) }
    }
}

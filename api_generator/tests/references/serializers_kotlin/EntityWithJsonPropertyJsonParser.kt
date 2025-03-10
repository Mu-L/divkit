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

internal class EntityWithJsonPropertyJsonParser(
    private val component: JsonParserComponent
) {

    class EntityParserImpl(
        private val component: JsonParserComponent
    ) : Parser<JSONObject, EntityWithJsonProperty> {

        @Throws(ParsingException::class)
        override fun deserialize(context: ParsingContext, data: JSONObject): EntityWithJsonProperty {
            return EntityWithJsonProperty(
                jsonProperty = JsonPropertyParser.readOptional(context, data, "json_property") ?: JSON_PROPERTY_DEFAULT_VALUE,
            )
        }

        @Throws(ParsingException::class)
        override fun serialize(context: ParsingContext, value: EntityWithJsonProperty): JSONObject {
            val data = JSONObject()
            JsonPropertyParser.write(context, data, "json_property", value.jsonProperty)
            JsonPropertyParser.write(context, data, "type", EntityWithJsonProperty.TYPE)
            return data
        }
    }

    class TemplateParserImpl(
        private val component: JsonParserComponent
    ) : TemplateParser<JSONObject, EntityWithJsonPropertyTemplate> {

        @Throws(ParsingException::class)
        override fun deserialize(context: ParsingContext, parent: EntityWithJsonPropertyTemplate?, data: JSONObject): EntityWithJsonPropertyTemplate {
            val allowOverride = context.allowPropertyOverride
            @Suppress("NAME_SHADOWING") val context = context.restrictPropertyOverride()
            return EntityWithJsonPropertyTemplate(
                jsonProperty = JsonFieldParser.readOptionalField(context, data, "json_property", allowOverride, parent?.jsonProperty),
            )
        }

        @Throws(ParsingException::class)
        override fun serialize(context: ParsingContext, value: EntityWithJsonPropertyTemplate): JSONObject {
            val data = JSONObject()
            JsonFieldParser.writeField(context, data, "json_property", value.jsonProperty)
            JsonPropertyParser.write(context, data, "type", EntityWithJsonProperty.TYPE)
          return data
        }
    }

    class TemplateResolverImpl(
        private val component: JsonParserComponent
    ) : TemplateResolver<JSONObject, EntityWithJsonPropertyTemplate, EntityWithJsonProperty> {

        @Throws(ParsingException::class)
        override fun resolve(context: ParsingContext, template: EntityWithJsonPropertyTemplate, data: JSONObject): EntityWithJsonProperty {
            return EntityWithJsonProperty(
                jsonProperty = JsonFieldResolver.resolveOptional(context, template.jsonProperty, data, "json_property") ?: JSON_PROPERTY_DEFAULT_VALUE,
            )
        }
    }

    private companion object {

        @JvmField val JSON_PROPERTY_DEFAULT_VALUE = JSONObject("""
        {
            "key": "value",
            "items": [
                "value"
            ]
        }
        """)
    }
}

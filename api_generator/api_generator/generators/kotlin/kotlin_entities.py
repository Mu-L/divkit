from __future__ import annotations
from functools import reduce
import dataclasses
from typing import List, Optional, cast

from ..base import declaration_comment
from ...schema.modeling.entities import (
    Declarable,
    Entity,
    EntityEnumeration,
    StringEnumeration,
    Property,
    PropertyType,
    Int,
    Bool,
    BoolInt,
    Double,
    StaticString,
    Object,
    Array,
    Url,
    Color,
    String,
    Dictionary,
    RawObject,
    RawArray,
    ObjectFormat,
    KotlinGeneratorProperties
)
from ...config import Config
from ...config import GenerationMode
from ...config import SERIALIZER_SUFFIX
from ... import utils
from ...schema.modeling.text import Text, EMPTY

EXPRESSION_TYPE_NAME = 'Expression'
EXPRESSION_LIST_TYPE_NAME = 'ExpressionList'
PARSING_ERRORS_PROP_NAME = "parsingErrors"
ENTITY_STATIC_CREATOR = 'CREATOR'
ENTITY_PARSER_NAME = "EntityParserImpl"
TEMPLATE_PARSER_NAME = "TemplateParserImpl"
TEMPLATE_RESOLVER_NAME = "TemplateResolverImpl"


def _number_validator_decl(type: str, constraint: Optional[str]) -> Optional[str]:
    if constraint is None:
        return None
    return f'{{ it: {type} -> {constraint.replace("number", "it")} }}'


def _kotlin_default_value_declaration_comment(p: Property) -> str:
    if isinstance(p.property_type, Object) and not isinstance(p.property_type.object, StringEnumeration):
        property_type = cast(KotlinPropertyType, Object(name='',
                                                        object=p.property_type.object,
                                                        format=ObjectFormat.DEFAULT))
        comment_value = property_type.declaration_by_default_value(p.default_value, True, p.supports_expressions_flag)
    else:
        comment_value = p.default_value
    return comment_value


class KotlinEntity(Entity):
    errors_collector_enabled: bool = False

    def update_bases(self):
        Int.__bases__ = (KotlinPropertyType, PropertyType,)
        Bool.__bases__ = (KotlinPropertyType, PropertyType,)
        BoolInt.__bases__ = (KotlinPropertyType, PropertyType,)
        Double.__bases__ = (KotlinPropertyType, PropertyType,)
        StaticString.__bases__ = (KotlinPropertyType, PropertyType,)
        Object.__bases__ = (KotlinPropertyType, PropertyType,)
        Array.__bases__ = (KotlinPropertyType, PropertyType,)
        Url.__bases__ = (KotlinPropertyType, PropertyType,)
        Color.__bases__ = (KotlinPropertyType, PropertyType,)
        String.__bases__ = (KotlinPropertyType, PropertyType,)
        Dictionary.__bases__ = (KotlinPropertyType, PropertyType,)
        RawObject.__bases__ = (KotlinPropertyType, PropertyType,)
        RawArray.__bases__ = (KotlinPropertyType, PropertyType,)
        for prop in self.properties:
            prop.__class__ = KotlinProperty

    @property
    def properties_kotlin(self) -> List[KotlinProperty]:
        result = []
        for prop in self.properties:
            prop.__class__ = KotlinProperty
            result.append(cast(KotlinProperty, prop))
        return result

    @property
    def instance_properties_kotlin(self) -> List[KotlinProperty]:
        result = []
        for prop in self.instance_properties:
            prop.__class__ = KotlinProperty
            result.append(cast(KotlinProperty, prop))
        return result

    @property
    def plain_type_declaration(self) -> str:
        return (self.entity_declaration_prefix.replace('.', '')
                + utils.capitalize_camel_case(self.name_for(GenerationMode.NORMAL_WITH_TEMPLATES)))

    @property
    def serializer_type_declaration(self) -> str:
        return (self.entity_declaration_prefix.replace('.', '')
                + utils.capitalize_camel_case(self.name_for(GenerationMode.SERIALIZER)))

    @property
    def entity_serializer_type_declaration(self) -> str:
        return f'{self.plain_type_declaration}.{ENTITY_PARSER_NAME}'

    @property
    def entity_serializer_name_declaration(self) -> str:
        return utils.lower_camel_case(self.plain_type_declaration) + 'JsonEntityParser'

    @property
    def template_serializer_type_declaration(self) -> str:
        return f'{self.plain_type_declaration}.{TEMPLATE_PARSER_NAME}'

    @property
    def template_serializer_name_declaration(self) -> str:
        return utils.lower_camel_case(self.plain_type_declaration) + 'JsonTemplateParser'

    @property
    def template_resolver_type_declaration(self) -> str:
        return f'{self.plain_type_declaration}.{TEMPLATE_RESOLVER_NAME}'

    @property
    def template_resolver_name_declaration(self) -> str:
        return utils.lower_camel_case(self.plain_type_declaration) + 'JsonTemplateResolver'

    def eval_errors_collector_enabled(self, errors_collectors: List[str]):
        self.errors_collector_enabled = not self.generation_mode.is_template and self.original_name in errors_collectors

    def constructor_body(self, with_commas: bool, extra_properties: List[str] = None) -> Text:
        if extra_properties is None:
            extra_properties = []
        if not self.instance_properties and not extra_properties:
            return Text()
        expressions = []
        for prop in self.instance_properties_kotlin:
            expressions.append(prop.deserialization_declaration(mode=self.generation_mode))
        expressions.extend(extra_properties)
        result = Text()
        for ind, expr in enumerate(expressions):
            comma = ''
            if with_commas and ind != (len(expressions) - 1):
                comma = ','
            result += f'{expr}{comma}'
        return result

    def value_resolving_declaration(self, generate_serializers: bool) -> Text:
        args = 'env: ParsingEnvironment, data: JSONObject'
        result = Text(f'override fun resolve({args}): {self.resolved_prefixed_declaration} {{')

        if generate_serializers:
            result += f'    return builtInParserComponent.{self.template_resolver_name_declaration}'
            result += '        .value'
            result += '        .resolve(context = env, template = this, data = data)'
        else:
            if not self.instance_properties:
                result += f'    return {self.resolved_prefixed_declaration}()'
            else:
                result += f'    return {self.resolved_prefixed_declaration}('
                prop_decl = Text()
                props = self.instance_properties_kotlin
                for ind, p in enumerate(props):
                    ending = ',' if ind != (len(props) - 1) else ''
                    template_deserialization = p.make_template_deserialization(dict_field=p.dict_field,
                                                                               value_override=None)
                    prop_decl += f'{template_deserialization}{ending}'
                result += prop_decl.indented(indent_width=8)
                result += '    )'
        result += '}'
        return result

    def serialization_declaration(self, generate_serializers: bool) -> Text:
        result = Text('override fun writeToJSON(): JSONObject {')
        if generate_serializers:
            if self.generation_mode.is_template:
                serializer_name = self.template_serializer_name_declaration
            else:
                serializer_name = self.entity_serializer_name_declaration
            result += f'    return builtInParserComponent.{serializer_name}'
            result += '        .value'
            result += '        .serialize(context = builtInParsingContext, value = this)'
        else:
            result += '    val json = JSONObject()'
            for prop in self.properties_kotlin:
                result += prop.serialization_declaration.indented(indent_width=4)
            result += '    return json'
        result += '}'
        return result

    def header_declaration(
            self,
            kotlin_annotations: Config.KotlinAnnotations,
            generate_serialization: bool = True
    ) -> Text:
        result = Text()
        for annotation in kotlin_annotations.classes:
            result += annotation
        prefix = f'class {utils.capitalize_camel_case(self.name)}'

        interfaces = ['JSONSerializable'] if generate_serialization else []
        if not self.generation_mode.is_template:
            interfaces.append('Hashable')
        protocol_plus_super_entities = self.protocol_plus_super_entities()
        if protocol_plus_super_entities is not None:
            interfaces.append(protocol_plus_super_entities)
        interfaces = ', '.join(interfaces)
        suffix = f' : {interfaces}' if interfaces else ''
        suffix += ' {'

        def add_instance_properties(text: Text, is_template: bool) -> Text:
            mixed_properties = self.instance_properties_kotlin
            if self.errors_collector_enabled:
                mixed_properties.append(KotlinProperty(
                    name=PARSING_ERRORS_PROP_NAME,
                    description='',
                    description_translations={},
                    dict_field='',
                    property_type=Object(name='List<Exception>', object=None, format=ObjectFormat.DEFAULT),
                    optional=True,
                    is_deprecated=False,
                    mode=GenerationMode.NORMAL_WITHOUT_TEMPLATES,
                    supports_expressions_flag=False,
                    default_value=None,
                    platforms=None
                ))
            for prop in mixed_properties:
                overridden = False
                if self.implemented_protocol is not None:
                    overridden = any(p.name == prop.name for p in self.implemented_protocol.properties)
                text += prop.declaration(
                    overridden=overridden,
                    in_interface=False,
                    with_comma=not is_template,
                    with_default=not is_template
                ).indented(indent_width=4)
            return text

        if self.generation_mode.is_template:
            result += prefix + suffix
            if self.instance_properties:
                result = add_instance_properties(text=result, is_template=True)
        else:
            constructor_prefix = ''
            if kotlin_annotations.constructors:
                constructor_annotations = ', '.join(kotlin_annotations.constructors)
                constructor_prefix = f' {constructor_annotations} constructor '
            if not self.instance_properties:
                result += f'{prefix}{constructor_prefix}(){suffix}'
            else:
                result += f'{prefix}{constructor_prefix}('
                result = add_instance_properties(text=result, is_template=False)
                result += f'){suffix}'
        return result

    def serializer_declaration(self) -> Text:
        entity_type = self.resolved_prefixed_declaration
        result = Text()
        result += f'internal class {self.serializer_type_declaration}('
        result += '    private val component: JsonParserComponent'
        result += ') {'
        result += EMPTY
        result += self.__entity_serializer_declaration().indented(indent_width=4)
        result += EMPTY
        result += self.__template_serializer_declaration().indented(indent_width=4)
        result += EMPTY
        result += self.__template_resolver_declaration().indented(indent_width=4)
        serializer_static_declaration = self.__serializer_static_declaration(entity_type)
        if serializer_static_declaration.lines:
            result += EMPTY
            result += '    private companion object {'
            result += serializer_static_declaration.indented(4)
            result += '    }'
        result += '}'
        return result

    def __entity_serializer_declaration(self) -> Text:
        entity_type = self.resolved_prefixed_declaration
        result = Text()
        result += f'class {ENTITY_PARSER_NAME}('
        result += '    private val component: JsonParserComponent'
        result += f') : Parser<JSONObject, {entity_type}> {{'
        result += EMPTY
        result += '    @Throws(ParsingException::class)'
        result += f'    override fun deserialize(context: ParsingContext, data: JSONObject): {entity_type} {{'
        if self.instance_properties_kotlin:
            if self.errors_collector_enabled:
                result += '        @Suppress("NAME_SHADOWING") val context = context.collectingErrors()'
            result += f'        return {entity_type}('
            for property in self.instance_properties_kotlin:
                result += self.property_deserialization_declaration(property, mode=GenerationMode.NORMAL_WITH_TEMPLATES).indented(indent_width=12)
            if self.errors_collector_enabled:
                result += f'            {PARSING_ERRORS_PROP_NAME} = context.collectedErrors'
            result += '        )'
        else:
            result += f'        return {entity_type}()'
        result += '    }'
        result += EMPTY
        result += '    @Throws(ParsingException::class)'
        result += f'    override fun serialize(context: ParsingContext, value: {entity_type}): JSONObject {{'
        result += '        val data = JSONObject()'
        for property in self.properties_kotlin:
            result += self.property_serialization_declaration(property, mode=GenerationMode.NORMAL_WITH_TEMPLATES).indented(indent_width=8)
        result += '        return data'
        result += '    }'
        result += '}'
        return result

    def __template_serializer_declaration(self) -> Text:
        template_type = self.prefixed_declaration_for(mode=GenerationMode.TEMPLATE)
        result = Text()
        result += f'class {TEMPLATE_PARSER_NAME}('
        result += '    private val component: JsonParserComponent'
        result += f') : TemplateParser<JSONObject, {template_type}> {{'
        result += EMPTY
        result += '    @Throws(ParsingException::class)'
        result += f'    override fun deserialize(context: ParsingContext, parent: {template_type}?, data: JSONObject): {template_type} {{'
        if self.instance_properties_kotlin:
            result += '        val allowOverride = context.allowPropertyOverride'
            result += '        @Suppress("NAME_SHADOWING") val context = context.restrictPropertyOverride()'
            result += f'        return {template_type}('
            for property in self.instance_properties_kotlin:
                result += self.property_deserialization_declaration(property, mode=GenerationMode.TEMPLATE).indented(indent_width=12)
            result += '        )'
        else:
            result += f'        return {template_type}()'
        result += '    }'
        result += EMPTY
        result += '    @Throws(ParsingException::class)'
        result += f'    override fun serialize(context: ParsingContext, value: {template_type}): JSONObject {{'
        result += '        val data = JSONObject()'
        for property in self.properties_kotlin:
            result += self.property_serialization_declaration(property, mode=GenerationMode.TEMPLATE).indented(indent_width=8)
        result += '      return data'
        result += '    }'
        result += '}'
        return result

    def __template_resolver_declaration(self) -> Text:
        entity_type = self.resolved_prefixed_declaration
        template_type = self.prefixed_declaration_for(mode=GenerationMode.TEMPLATE)
        result = Text()
        result += f'class {TEMPLATE_RESOLVER_NAME}('
        result += '    private val component: JsonParserComponent'
        result += f') : TemplateResolver<JSONObject, {template_type}, {entity_type}> {{'
        result += EMPTY
        result += '    @Throws(ParsingException::class)'
        result += f'    override fun resolve(context: ParsingContext, template: {template_type}, data: JSONObject): {entity_type} {{'
        if self.instance_properties_kotlin:
            result += f'        return {entity_type}('
            for property in self.instance_properties_kotlin:
                result += self.property_resolving_declaration(property, mode=GenerationMode.TEMPLATE).indented(indent_width=12)
            result += '        )'
        else:
            result += f'        return {entity_type}()'
        result += '    }'
        result += '}'
        return result

    def __serializer_static_declaration(self, declaration_name: str) -> Text:
        groups = []

        default_values = self.default_values_static_declaration(is_private=False)
        type_helpers = self.type_helpers_static_declaration(is_private=False)
        validators = set()
        validators.update(self.validators_static_declaration(is_private=False, is_template=False))

        if default_values:
            groups.append(Text(default_values))

        if type_helpers:
            groups.append(Text(type_helpers))

        if validators:
            groups.append(Text(sorted(validators)))

        result = Text()
        for index, group in enumerate(groups):
            result += EMPTY
            result += group
        return result

    def property_serializer_list(self) -> List[str]:
        serializers = map(lambda property: cast(KotlinProperty, property).serializer_type_declaration, self.instance_properties_kotlin)
        object_serializers = filter(lambda serializer_type: serializer_type is not None, serializers)
        return sorted(set(object_serializers))

    def property_deserialization_declaration(self, property: KotlinProperty, mode: GenerationMode) -> Text:
        property_type = cast(KotlinPropertyType, property.property_type)

        if isinstance(property_type, Array):
            if property.supports_expressions and property_type.is_array_of_expressions:
                collection_prefix = EXPRESSION_LIST_TYPE_NAME
            else:
                collection_prefix = 'List'
            expression_prexix = ''
            expression_suffix = ''
        else:
            if property.supports_expressions:
                expression_prexix = EXPRESSION_TYPE_NAME
                expression_suffix = 'WithExpression'
            else:
                expression_prexix = ''
                expression_suffix = ''
            collection_prefix = ''
        optionality_prefix = 'Optional' if property.parsed_value_is_optional else ''

        if mode.is_template:
            method_name = f'read{optionality_prefix}{collection_prefix}Field{expression_suffix}'
            template_args = f'allowOverride, parent?.{property.declaration_name}'
        else:
            method_name = f'read{optionality_prefix}{expression_prexix}{collection_prefix}'
            template_args = ''
        key = f'"{property.dict_field}"'

        if mode.is_template:
            serializer_name = property.template_serializer_name_declaration
        else:
            serializer_name = property.entity_serializer_name_declaration
        if serializer_name is None:
            deserializer = ''
        else:
            deserializer = f'component.{serializer_name}'
        transform = property_type.deserialization_transform(string_enum_prefixed=True)

        type_helper = property_type.type_helper_reference(property) if property.supports_expressions else ''
        validator = property_type.validator_arg(
            property_name=property.name,
            with_template_validators=False
        )
        if validator and isinstance(property_type, Array) and mode.is_template:
            validator = validator + '.cast()'
        arg_list = ['context', 'data', key, type_helper, template_args, deserializer, transform, validator]
        if isinstance(property_type, Array):
            arg_list.append(cast(KotlinPropertyType, property_type.property_type).validator_arg(
                property_name=property.name + '_item',
                with_template_validators=False
            ))
        if property.supports_expressions and not mode.is_template and property.default_value_definition is not None:
            arg_list.append(property.default_value_var_name)
        args = ', '.join(filter(lambda arg: arg, arg_list))

        if mode.is_template:
            receiver = 'JsonFieldParser'
        else:
            receiver = 'JsonExpressionParser' if property.supports_expressions else 'JsonPropertyParser'
        deserialization_expr = f'{receiver}.{method_name}({args})'
        return Text(f'{property.declaration_name} = {deserialization_expr}{property.default_value_coalescing(mode)},')

    def property_serialization_declaration(self, property: KotlinProperty, mode: GenerationMode) -> Text:
        if mode.is_template and not property.is_static:
            receiver = 'JsonFieldParser'
        else:
            receiver = 'JsonExpressionParser' if property.supports_expressions else 'JsonPropertyParser'

        if property.supports_expressions:
            expression_suffix = 'Expression'
        else:
            expression_suffix = ''

        property_type = cast(KotlinPropertyType, property.property_type)
        if isinstance(property_type, Array):
            collection_suffix = 'List'
        else:
            collection_suffix = ''

        field_suffix = 'Field' if mode.is_template and not property.is_static else ''

        if property.is_static:
            value_prefix = self.resolved_prefixed_declaration
        else:
            value_prefix = 'value'

        if mode.is_template:
            serializer_name = property.template_serializer_name_declaration
            if serializer_name is not None:
                serialization_transform = f', component.{serializer_name}'
            else:
                serialization_transform = property_type.serialization_transform(string_enum_prefixed=True, with_arg_name=False)
        else:
            serializer_name = property.entity_serializer_name_declaration
            if serializer_name is not None:
                serialization_transform = f', component.{serializer_name}'
            else:
                serialization_transform = property_type.serialization_transform(string_enum_prefixed=True, with_arg_name=False)

        args = f'context, data, "{property.dict_field}", {value_prefix}.{property.declaration_name}{serialization_transform}'
        return Text(f'{receiver}.write{expression_suffix}{collection_suffix}{field_suffix}({args})')

    def property_resolving_declaration(self, property: KotlinProperty, mode: GenerationMode) -> Text:
        optionality_prefix = 'Optional' if property.parsed_value_is_optional else ''
        property_type = cast(KotlinPropertyType, property.property_type)
        if isinstance(property_type, Array):
            if property.supports_expressions and property_type.is_array_of_expressions:
                collection_prefix = EXPRESSION_LIST_TYPE_NAME
            else:
                collection_prefix = 'List'
            expression_prefix = ''
        else:
            collection_prefix = ''
            expression_prefix = EXPRESSION_TYPE_NAME if property.supports_expressions else ''

        type_helper = property_type.type_helper_reference(property) if property.supports_expressions else ''
        resolver_name = property.template_resolver_name_declaration
        if resolver_name is None:
            resolver = ''
        else:
            resolver = f'component.{resolver_name}'
        deserializer_name = property.entity_serializer_name_declaration
        if deserializer_name is None:
            value_deserializer = ''
        else:
            value_deserializer = f'component.{deserializer_name}'
        transform = property_type.deserialization_transform(string_enum_prefixed=mode.is_template)
        validator = property_type.validator_arg(
            property_name=property.name,
            with_template_validators=False
        )

        arg_list = ['context', f'template.{property.declaration_name}', 'data',
                    f'"{property.dict_field}"', type_helper, resolver, value_deserializer, transform, validator]
        if isinstance(property_type, Array):
            arg_list.append(cast(KotlinPropertyType, property_type.property_type).validator_arg(
                property_name=property.name + '_item',
                with_template_validators=False
            ))
        if property.supports_expressions and property.default_value_definition is not None:
            arg_list.append(property.default_value_var_name)
        args = ', '.join(filter(lambda arg: arg, arg_list))
        default_value = property.default_value_coalescing(GenerationMode.NORMAL_WITH_TEMPLATES)
        return Text(f'{property.declaration_name} = JsonFieldResolver.resolve{optionality_prefix}{expression_prefix}{collection_prefix}({args}){default_value},')

    def static_declarations(self, generate_serialization: bool, generate_serializers: bool) -> Text:
        is_template = self.generation_mode.is_template
        name = utils.capitalize_camel_case(self.name)
        groups = []

        static_properties_declaration = self.static_properties_declaration
        if static_properties_declaration.lines:
            groups.append(static_properties_declaration)

        is_private = True
        if isinstance(self.generator_properties, KotlinGeneratorProperties):
            is_private = not self.generator_properties.public_default_values
        default_values = self.default_values_static_declaration(is_private)
        if default_values:
            groups.append(Text(default_values))

        if generate_serialization and not generate_serializers:
            type_helpers = self.type_helpers_static_declaration(is_private=True)
            if type_helpers and generate_serialization:
                groups.append(Text(type_helpers))

        if not is_template and generate_serialization:
            constructor = Text()
            constructor += '@JvmStatic'
            constructor += '@JvmName("fromJson")'
            constructor += f'operator fun invoke(env: ParsingEnvironment, json: JSONObject): {name} {{'
            if generate_serializers:
                constructor += f'    return builtInParserComponent.{self.entity_serializer_name_declaration}'
                constructor += '        .value'
                constructor += '        .deserialize(context = env, data = json)'
            else:
                if self.errors_collector_enabled:
                    constructor += '    val env = env.withErrorsCollector()'

                constructor += '    val logger = env.logger'
                constructor += f'    return {name}('
                extra_properties = []
                if self.errors_collector_enabled:
                    extra_properties.append(f'{PARSING_ERRORS_PROP_NAME} = env.collectErrors()')

                constructor_body = self.constructor_body(with_commas=True,
                                                         extra_properties=extra_properties).indented(indent_width=8)
                if constructor_body.lines:
                    constructor += constructor_body
                constructor += utils.indented(')', indent_width=4)
            constructor += '}'
            groups.append(constructor)

        if generate_serialization and not generate_serializers:
            validators = self.validators_static_declaration(is_private=True, is_template=is_template)

            if validators:
                groups.append(Text(validators))

            if is_template:
                readers = Text()
                for p in self.properties_kotlin:
                    readers += p.static_reader_deserialization_expression
                groups.append(readers)

        if generate_serialization:
            static_creator_lambda = f'env: ParsingEnvironment, it: JSONObject -> {name}(env, json = it)'
            groups.append(Text(f'val {ENTITY_STATIC_CREATOR} = {{ {static_creator_lambda} }}'))

        result = Text()
        for ind, group in enumerate(groups):
            result += group
            if ind != (len(groups) - 1):
                result += EMPTY
        return result

    def default_values_static_declaration(self, is_private: bool = True) -> List[str]:
        default_values = []
        instance_properties_kotlin = self.instance_properties_kotlin
        for p in instance_properties_kotlin:
            decl = p.default_value_declaration(is_private)
            if decl is not None:
                default_values.append(decl)
        return default_values

    def type_helpers_static_declaration(self, is_private: bool) -> List[str]:
        type_helpers = []
        for p in self.instance_properties_kotlin:
            if p.supports_expressions:
                property_type = p.property_type
                if isinstance(property_type, Array):
                    property_type = property_type.property_type
                property_type = cast(KotlinPropertyType, property_type)
                if property_type.is_enum_of_expressions:
                    type_helpers.append(property_type.type_helper_declaration(p, is_private))
        return type_helpers

    def validators_static_declaration(self, is_private: bool, is_template: bool) -> List[str]:
        validators = []
        for p in self.properties_kotlin:
            validators_or_empty = p.property_type.static_validator_expression(
                property_name=p.name,
                supports_expressions=p.supports_expressions,
                with_template_validators=is_template,
                is_private=is_private
            )
            if validators_or_empty:
                validators.extend(validators_or_empty)

            if p.property_type.is_array:
                validators_or_empty = cast(KotlinPropertyType, p.property_type.property_type) \
                    .static_validator_expression(
                    property_name=p.name + '_item',
                    supports_expressions=p.supports_expressions,
                    with_template_validators=is_template,
                    is_private=is_private
                )
                if validators_or_empty:
                    validators.append(validators_or_empty)
        return validators

    @property
    def static_properties_declaration(self) -> Text:
        static_properties = list(filter(lambda p: isinstance(p.property_type, StaticString), self.properties_kotlin))
        static_decl = Text()
        if not static_properties:
            return static_decl
        for static in static_properties:
            static_decl += static.declaration(overridden=False,
                                              in_interface=False,
                                              with_comma=False,
                                              with_default=False)
        return static_decl

    @property
    def copy_declaration(self) -> Text:
        result = EMPTY
        decl = '    fun copy('

        method_params: List[str] = []
        constructor_params: List[str] = []

        for p in sorted(filter(lambda prop: not isinstance(prop.property_type, StaticString),
                               self.properties_kotlin), key=lambda prop: prop.name):
            property_name = p.declaration_name
            property_class = p.type_declaration
            method_params.append(f'\n        {property_name}: {property_class} = this.{property_name},')
            constructor_params.append(f'\n        {property_name} = {property_name},')

        decl += ''.join(method_params)
        if len(method_params) > 0:
            decl += '\n    '
        decl += f') = {utils.capitalize_camel_case(self.name)}('
        decl += ''.join(constructor_params)
        if len(constructor_params) > 0:
            decl += '\n    '
        decl += ')'
        result += decl
        return result

    @property
    def equality_declaration(self) -> Text:
        result = EMPTY
        if self.instance_properties:
            result += '    override fun equals(other: Any?): Boolean {'
            result += '        if (this === other) { return true }'
            class_name = utils.capitalize_camel_case(self.name)
            result += f'        if (other !is {class_name} || (this.isHashCalculated() && other.isHashCalculated()'
            result += '                    && this.hash() != other.hash())) {'
            result += '            return false'
            result += '        }'
            result += EMPTY
            for prop in self.instance_properties_kotlin:
                prefix = "return " if prop == self.instance_properties_kotlin[0] else "    "
                postfix = "&&" if prop != self.instance_properties_kotlin[-1] else ""
                result += f'         {prefix}this.{prop.declaration_name} == other.{prop.declaration_name} {postfix}'
            result += '    }'
            result += EMPTY
            result += '    override fun hashCode() = hash()'
        else:
            result += self.__manual_equals_hash_code_declaration.indented(indent_width=4)
        return result

    @property
    def __manual_equals_hash_code_declaration(self) -> Text:
        result = Text('override fun equals(other: Any?) = javaClass == other?.javaClass')
        result += EMPTY
        result += 'override fun hashCode() = this::class.hashCode()'
        return result

    def hash_declaration(self, with_calculation_flag: bool = False) -> Text:
        prop_names = map(lambda prop_name: prop_name.declaration_name, self.instance_properties_kotlin)

        prop_filter = ['items']
        is_div_state = utils.capitalize_camel_case(self.name) == 'DivState'

        if is_div_state:
            prop_filter.append('states')

        generate_properties = True if len(set(prop_filter).intersection(prop_names)) > 0 else False

        result = EMPTY
        if generate_properties:
            result += '    private var _propertiesHash: Int? = null '
        result += '    private var _hash: Int? = null '
        if generate_properties:
            result += EMPTY
            result += '    override fun propertiesHash(): Int {'
            result += '        _propertiesHash?.let {'
            result += '            return it'
            result += '        }'
            if len(self.instance_properties_kotlin) > 1:
                result += '        val propertiesHash = '
                result += '            this::class.hashCode() +'
                result += self.__generate_hash(list(filter(lambda it: it.declaration_name not in prop_filter,
                                                           self.instance_properties_kotlin))).indented(indent_width=12)
            else:
                result += '        val propertiesHash = this::class.hashCode()'
            result += '        _propertiesHash = propertiesHash'
            result += '        return propertiesHash'
            result += '    }'
        result += EMPTY
        result += '    override fun hash(): Int {'
        result += '        _hash?.let {'
        result += '            return it'
        result += '        }'

        if self.instance_properties_kotlin:
            result += '        val hash = '
            if generate_properties:
                hash_text = Text()
                hash_text += 'propertiesHash() +'
                hash_text += self.__generate_hash(
                    list(filter(lambda it: it.declaration_name in prop_filter, self.instance_properties_kotlin)))

                result += hash_text.indented(indent_width=12)
            else:
                result += '            this::class.hashCode() +'
                result += self.__generate_hash(list(filter(
                    lambda it: it.declaration_name not in prop_filter,
                    self.instance_properties_kotlin))
                ).indented(indent_width=12)
        else:
            result += '        val hash = this::class.hashCode()'

        result += '        _hash = hash'
        result += '        return hash'
        result += '    }'
        if with_calculation_flag:
            result += EMPTY
            result += '    fun isHashCalculated() = _hash != null'
        return result

    @staticmethod
    def __generate_hash(props: List[KotlinProperty]) -> Text:
        hash_text = Text()
        for prop in props:
            hash_type = 'hash()' if prop.use_custom_hash else 'hashCode()'

            prop_hash = ''
            if prop.should_be_optional:
                prop_hash += '('
            prop_hash += prop.declaration_name
            if prop.should_be_optional:
                prop_hash += '?'
            prop_hash += '.'
            if isinstance(prop.property_type, Array) and prop.use_custom_hash:
                prop_hash += f'sumOf {{ it.{hash_type} }}'
            else:
                prop_hash += hash_type
            if prop.should_be_optional:
                prop_hash += ' ?: 0)'
            prop_hash += " +" if prop != props[-1] else ""
            hash_text += prop_hash
        return hash_text

    def equals_resolved_declaration(self) -> Text:
        prop_filter = ['items']
        is_div_state = utils.capitalize_camel_case(self.name) == 'DivState'

        if is_div_state:
            prop_filter.append('states')

        result = EMPTY
        result += f'    fun equals(other: {utils.capitalize_camel_case(self.name)}?, ' +\
                  'resolver: ExpressionResolver, otherResolver: ExpressionResolver): Boolean {'

        if self.instance_properties_kotlin:
            result += '        other ?: return false'
            result += self.__generate_equals_resolved().indented(4)
        else:
            result += '        return other != null'

        result += '    }'
        return result

    def __generate_equals_resolved(self) -> Text:
        result = Text()
        props = self.instance_properties_kotlin
        for prop in props:
            name = prop.declaration_name
            prop_type = prop.property_type
            opt = '?' if prop.should_be_optional else ''
            or_null = f' ?: (other.{name} == null))' if opt else ''
            nullable_beginning = ('(' if opt else '') + f'{name}{opt}.'
            prop_equality = 'return ' if prop == props[0] else '    '
            if not isinstance(prop_type, Array) and prop.supports_expressions:
                prop_equality += f'{name}{opt}.evaluate(resolver) == other.{name}{opt}.evaluate(otherResolver)'
            elif isinstance(prop_type, Object) and not isinstance(prop_type.object, StringEnumeration):
                prop_equality += nullable_beginning + f'equals(other.{name}, resolver, otherResolver)' + or_null
            elif isinstance(prop_type, Array):
                prop_equality += nullable_beginning
                compare_with = f'compareWith(other.{name}'
                return_false = ' ?: return false' if opt else ''
                if prop.supports_expressions:
                    prop_equality += f'evaluate(resolver){opt}.{compare_with}{opt}.evaluate(otherResolver)' +\
                                     return_false + ') { a, b -> a == b }'
                elif isinstance(prop_type.property_type, Object) and \
                        not isinstance(prop_type.property_type.object, StringEnumeration):
                    prop_equality += compare_with + return_false + ') { a, b -> a.equals(b, resolver, otherResolver) }'
                else:
                    prop_equality += compare_with + return_false + ') { a, b -> a == b }'
                prop_equality += or_null
            else:
                prop_equality += f'{name} == other.{name}'
            prop_equality += ' &&' if prop != props[-1] else ''
            result += prop_equality
        return result


class KotlinProperty(Property):
    @property
    def is_static(self) -> bool:
        return isinstance(self.property_type, StaticString)

    @property
    def declaration_name(self) -> str:
        if isinstance(self.property_type, StaticString):
            name = utils.constant_upper_case(self.name)
        else:
            name = utils.lower_camel_case(self.name)
        return utils.fixing_first_digit(name)

    @property
    def default_value_var_name(self) -> str:
        return f'{utils.constant_upper_case(self.declaration_name)}_DEFAULT_VALUE'

    def default_value_declaration(self, is_private: bool = True) -> Optional[str]:
        default_value_definition = self.default_value_definition
        if default_value_definition is not None:
            prefix = 'private ' if is_private else '@JvmField '
            return f'{prefix}val {self.default_value_var_name} = {default_value_definition}'
        return None

    @property
    def default_value_definition(self) -> Optional[str]:
        if self.default_value is not None:
            declaration = cast(KotlinPropertyType, self.property_type).declaration_by_default_value(
                default_value=self.default_value,
                string_enum_prefixed=self.mode.is_template or self.mode.is_serializer,
                supports_expressions_flag=self.supports_expressions_flag
            )
            if declaration is not None:
                return declaration
        return None

    @property
    def should_be_optional(self) -> bool:
        if self.mode.is_template:
            return False
        return self.optional and (self.default_value is None)

    @property
    def parsed_value_is_optional(self) -> bool:
        return self.optional or self.default_value is not None

    @property
    def use_expression_type(self) -> bool:
        if not self.supports_expressions:
            return False
        prop = cast(KotlinPropertyType, self.property_type)
        if prop.is_array_of_expressions:
            return False
        return prop.supports_expressions

    @property
    def type_declaration(self) -> str:
        if self.mode.is_template:
            if self.use_expression_type:
                prefix = f'Field<{EXPRESSION_TYPE_NAME}<'
                suffix = '>>'
            else:
                prefix = 'Field<'
                suffix = '>'
        else:
            if self.use_expression_type:
                prefix = f'{EXPRESSION_TYPE_NAME}<'
                suffix = '>'
            else:
                prefix = ''
                suffix = ''
        type_decl = cast(KotlinPropertyType, self.property_type).declaration(
            self.mode,
            self.supports_expressions
        )
        return f'{prefix}{type_decl}{suffix}{"?" if self.should_be_optional else ""}'

    @property
    def plain_type_declaration(self) -> str:
        property_type = cast(KotlinPropertyType, self.property_type)
        return property_type.plain_type_declaration

    @property
    def serializer_type_declaration(self) -> str:
        prefix = self.plain_type_declaration
        return None if prefix is None else prefix + utils.capitalize_camel_case(SERIALIZER_SUFFIX)

    @property
    def entity_serializer_type_declaration(self) -> str:
        prefix = self.plain_type_declaration
        return None if prefix is None else f'{prefix}.{ENTITY_PARSER_NAME}'

    @property
    def entity_serializer_name_declaration(self) -> str:
        prefix = self.plain_type_declaration
        return None if prefix is None else utils.lower_camel_case(prefix) + 'JsonEntityParser'

    @property
    def template_serializer_type_declaration(self) -> str:
        prefix = self.plain_type_declaration
        return None if prefix is None else f'{prefix}.{TEMPLATE_PARSER_NAME}'

    @property
    def template_serializer_name_declaration(self) -> str:
        prefix = self.plain_type_declaration
        return None if prefix is None else utils.lower_camel_case(prefix) + 'JsonTemplateParser'

    @property
    def template_resolver_type_declaration(self) -> str:
        prefix = self.plain_type_declaration
        return None if prefix is None else f'{prefix}.{TEMPLATE_RESOLVER_NAME}'

    @property
    def template_resolver_name_declaration(self) -> str:
        prefix = self.plain_type_declaration
        return None if prefix is None else utils.lower_camel_case(prefix) + 'JsonTemplateResolver'

    @property
    def use_custom_hash(self) -> bool:
        return self.property_type.use_custom_hash

    def declaration(self, overridden: bool, in_interface: bool, with_comma: bool, with_default: bool) -> Text:
        if isinstance(self.property_type, StaticString):
            assert not overridden and not with_comma
            return Text(f'const val {self.declaration_name} = "{self.property_type.value}"')
        if overridden:
            prefix = 'override '
            assert not in_interface
        elif not in_interface:
            prefix = '@JvmField '
        else:
            prefix = ''
        comma = ',' if with_comma else ''
        default_assignment = ''
        if with_default:
            if self.should_be_optional:
                default_assignment = ' = null'
            elif self.default_value_declaration() is not None:
                default_assignment = f' = {self.default_value_var_name}'
        comment = declaration_comment(self, _kotlin_default_value_declaration_comment) if with_default else ''
        return Text(f'{prefix}val {self.declaration_name}: {self.type_declaration}{default_assignment}{comma}{comment}')

    def argument_declaration(self, with_default: bool) -> Text:
        if isinstance(self.property_type, StaticString):
            return None
        default_assignment = ''
        if with_default:
            if self.should_be_optional:
                default_assignment = ' = null'
            elif self.default_value_declaration() is not None:
                default_assignment = f' = {self.default_value_var_name}'
        return Text(f'{self.declaration_name}: {self.type_declaration}{default_assignment},')

    def deserialization_declaration(self, mode: GenerationMode) -> str:
        deserialization_expr = self.deserialization_expression(mode=mode, reuse_logger_instance=True)
        return f'{self.declaration_name} = {deserialization_expr}{self.default_value_coalescing(mode)}'

    def deserialization_expression(self, mode: GenerationMode, reuse_logger_instance: bool) -> str:
        if isinstance(self.property_type, Array):
            if self.supports_expressions and cast(KotlinPropertyType, self.property_type).is_array_of_expressions:
                list_or_empty = EXPRESSION_LIST_TYPE_NAME
            else:
                list_or_empty = 'List'
            expression_or_empty = ''
            expression_suffix_or_empty = ''
        else:
            if self.supports_expressions:
                expression_or_empty = EXPRESSION_TYPE_NAME
                expression_suffix_or_empty = 'WithExpression'
            else:
                expression_or_empty = ''
                expression_suffix_or_empty = ''
            list_or_empty = ''
        optionality = 'Optional' if self.parsed_value_is_optional else ''
        if reuse_logger_instance:
            logger_arg = 'logger'
        else:
            logger_arg = 'env.logger'
        if mode.is_template:
            method_name = f'read{optionality}{list_or_empty}Field{expression_suffix_or_empty}'
            key_value = f'"{self.dict_field}"'
            template_args = f'topLevel, parent?.{self.declaration_name}'
        else:
            method_name = f'read{optionality}{expression_or_empty}{list_or_empty}'
            key_value = 'key' if self.mode.is_template else f'"{self.dict_field}"'
            template_args = ''
        creator = self.creator_declaration(mode=mode) or ''
        kotlin_type = cast(KotlinPropertyType, self.property_type)
        transform = kotlin_type.deserialization_transform(
            string_enum_prefixed=self.mode.is_template
        )
        arg_list = ['json', key_value, template_args, creator, transform, kotlin_type.validator_arg(
            property_name=self.name,
            with_template_validators=mode.is_template
        )]

        if isinstance(kotlin_type, Array):
            arg_list.append(cast(KotlinPropertyType, kotlin_type.property_type).validator_arg(
                property_name=self.name + '_item',
                with_template_validators=mode.is_template
            ))

        arg_list.extend([logger_arg, 'env'])

        if self.supports_expressions and not mode.is_template and self.default_value_definition is not None:
            arg_list.append(self.default_value_var_name)

        if self.supports_expressions:
            arg_list.append(kotlin_type.type_helper_reference(self))

        args = ', '.join(filter(lambda s: s, arg_list))
        receiver = 'JsonTemplateParser.' if mode.is_template else 'JsonParser.'
        return f'{receiver}{method_name}({args})'

    def creator_declaration(self, mode: GenerationMode) -> Optional[str]:
        creator_type = None
        if isinstance(self.property_type, Object) and not isinstance(self.property_type.object, StringEnumeration):
            creator_type = self.property_type
        elif isinstance(self.property_type, Array):
            item = self.property_type.property_type
            if isinstance(item, Object) and not isinstance(item.object, StringEnumeration):
                creator_type = item

        if creator_type is None:
            return None

        type_decl = cast(KotlinPropertyType, creator_type).declaration_by_prefixed(
            prefixed=self.mode.is_template and self.mode != mode,
            mode=mode,
            supports_expressions=self.supports_expressions
        )

        return f'{type_decl}.{ENTITY_STATIC_CREATOR}'

    def default_value_coalescing(self, mode: GenerationMode) -> str:
        if not mode.is_template and self.default_value_definition is not None:
            return f' ?: {self.default_value_var_name}'
        return ''

    def make_template_deserialization(self, dict_field: str, value_override: Optional[str]) -> str:
        optionality = 'Optional' if self.optional else ''
        plain_or_empty = 'Template' if self.property_type.can_be_templated else ''
        if value_override is None:
            value_override = ''
        else:
            value_override = f', valueOverride = {value_override}'
        reader = self.reader_declaration_name
        if isinstance(self.property_type, Array):
            kotlin_prop = cast(KotlinPropertyType, self.property_type)
            if self.supports_expressions and kotlin_prop.is_array_of_expressions:
                list_or_empty = 'ExpressionList'
                validator = ''
                expression_or_empty = ''
            else:
                list_or_empty = 'List'
                validator = cast(KotlinPropertyType, self.property_type).validator_arg(
                    property_name=self.name,
                    with_template_validators=False
                )
                if validator != '':
                    validator = f', {validator}'
                expression_or_empty = ''
                if self.supports_expressions and not kotlin_prop.is_enum_of_expressions:
                    expression_or_empty = EXPRESSION_TYPE_NAME
        else:
            list_or_empty = ''
            validator = ''
            expression_or_empty = ''
        method_name = f'.resolve{optionality}{plain_or_empty}{expression_or_empty}{list_or_empty}'
        method_args = f'env = env, key = "{dict_field}", data = data{validator}{value_override}, reader = {reader}'
        def_val = self.default_value_coalescing(mode=GenerationMode.NORMAL_WITHOUT_TEMPLATES)
        return f'{self.declaration_name} = this.{self.declaration_name}{method_name}({method_args}){def_val}'

    @property
    def reader_declaration_name(self) -> str:
        return f'{utils.constant_upper_case(self.name)}_READER'

    @property
    def serialization_declaration(self) -> Text:
        is_field = self.mode.is_template and not isinstance(self.property_type, StaticString)
        suffix = 'Field' if is_field else ''
        value_arg = 'field' if is_field else 'value'
        if self.use_expression_type:
            if is_field:
                expression_prefix_or_empty = ''
                expression_suffix_or_empty = 'WithExpression'
            else:
                expression_prefix_or_empty = EXPRESSION_TYPE_NAME
                expression_suffix_or_empty = ''
        else:
            expression_prefix_or_empty = ''
            if self.supports_expressions and \
                    cast(KotlinPropertyType, self.property_type).is_array_of_expressions:
                expression_prefix_or_empty = EXPRESSION_LIST_TYPE_NAME
            expression_suffix_or_empty = ''
        serialization_transform = cast(KotlinPropertyType, self.property_type) \
            .serialization_transform(string_enum_prefixed=self.mode.is_template)
        args = f'key = "{self.dict_field}", {value_arg} = {self.declaration_name}{serialization_transform}'
        return Text(f'json.write{expression_prefix_or_empty}{suffix}{expression_suffix_or_empty}({args})')

    @property
    def static_reader_deserialization_expression(self) -> str:
        def_val = self.default_value_coalescing(mode=GenerationMode.NORMAL_WITHOUT_TEMPLATES)
        optional = ''
        if self.parsed_value_is_optional and def_val == '':
            optional = '?'
        deserialize_expression = self.deserialization_expression(mode=GenerationMode.NORMAL_WITH_TEMPLATES,
                                                                 reuse_logger_instance=False)
        lambda_val = f'key, json, env -> {deserialize_expression}{def_val}'
        reader_type = cast(KotlinPropertyType, self.property_type).declaration_by_prefixed(
            prefixed=True,
            mode=GenerationMode.NORMAL_WITH_TEMPLATES,
            supports_expressions=self.supports_expressions
        )
        if self.use_expression_type:
            reader_type = f'{EXPRESSION_TYPE_NAME}<{reader_type}>'
        return f'val {self.reader_declaration_name}: Reader<{reader_type}{optional}> = {{ {lambda_val} }}'


class KotlinPropertyType(PropertyType):
    @property
    def is_array(self) -> bool:
        return isinstance(self, Array)

    @property
    def is_array_of_expressions(self) -> bool:
        if isinstance(self, Array):
            return self.property_type.supports_expressions and \
                not cast(KotlinPropertyType, self.property_type).is_enum_of_expressions
        return False

    @property
    def is_enum_of_expressions(self) -> bool:
        return isinstance(self, Object) and isinstance(self.object, StringEnumeration)

    def declaration_by_default_value(self,
                                     default_value: str,
                                     string_enum_prefixed: bool,
                                     supports_expressions_flag: bool) -> Optional[str]:
        def wrap(value: str) -> str:
            if self.supports_expressions and supports_expressions_flag and \
                    not self.is_array_of_expressions:
                return f'{EXPRESSION_TYPE_NAME}.constant({value})'
            return value

        if isinstance(self, (Bool, BoolInt)):
            return wrap(default_value)
        elif isinstance(self, Int):
            return wrap(str(default_value) if 'L' in str(default_value) else f'{default_value}L')
        elif isinstance(self, Double):
            return wrap(str(default_value) if '.' in str(default_value) else f'{default_value}.0')
        elif isinstance(self, Color):
            return wrap(f'{default_value.replace("#", "0x")}.toInt()')
        elif isinstance(self, String):
            escaping = default_value.replace("\"", "\\\"")
            return wrap(f'"{escaping}"')
        elif isinstance(self, Url):
            return wrap(f'Uri.parse("{default_value}")')
        elif isinstance(self, Array):
            without_whitespaces = default_value.replace(' ', '').replace('\n', '')
            if not without_whitespaces.startswith('[') or not without_whitespaces.endswith(']'):
                return None
            if without_whitespaces == '[]':
                values = []
            else:
                values = without_whitespaces[1:-1].split(',')
            item_type = cast(KotlinPropertyType, self.property_type)
            declarations = list(filter(None, map(
                lambda value: item_type.declaration_by_default_value(
                    value, string_enum_prefixed, supports_expressions_flag),
                values)))
            if len(values) != len(declarations):
                return None
            joined = ', '.join(declarations)
            return 'listOf<' +\
                item_type.declaration(GenerationMode.NORMAL_WITHOUT_TEMPLATES, supports_expressions_flag) +\
                f'>({joined})'
        elif isinstance(self, Object):
            if self.object is None:
                return None

            if isinstance(self.object, StringEnumeration):
                if string_enum_prefixed:
                    name = self.object.resolved_prefixed_declaration
                else:
                    name = utils.capitalize_camel_case(self.object.name)
                def_val = utils.fixing_first_digit(utils.constant_upper_case(default_value))
                return wrap(f'{name}.{def_val}')

            default_value_dict = utils.json_dict(default_value)
            if isinstance(self.object, EntityEnumeration):
                type_val = default_value_dict.get('type')
                enum_case = None
                for case in self.object.entities:
                    ent = case[1]
                    if isinstance(ent, Entity) and ent.static_type == type_val:
                        enum_case = case
                if enum_case is None:
                    raise ValueError(type_val)
                obj = cast(KotlinPropertyType, Object(name='', object=enum_case[1], format=ObjectFormat.DEFAULT))
                case_constructor: Optional[str] = obj.declaration_by_default_value(
                    default_value, True, supports_expressions_flag)
                if case_constructor is None:
                    return None
                obj_name = self.object.resolved_prefixed_declaration
                self.object.__class__ = KotlinEntityEnumeration
                case_name = cast(KotlinEntityEnumeration, self.object).format_case_naming(
                    utils.capitalize_camel_case(enum_case[0])
                )
                return wrap(f'{obj_name}.{case_name}({case_constructor})')

            entity: KotlinEntity = cast(KotlinEntity, self.object)
            entity.__class__ = KotlinEntity
            args = []
            for prop in entity.instance_properties_kotlin:
                str_type = default_value_dict.get(prop.dict_field)
                if str_type is None:
                    continue
                declaration = cast(KotlinPropertyType, prop.property_type).declaration_by_default_value(
                    str_type, True, supports_expressions_flag)
                args.append(f'{prop.declaration_name} = {declaration}')
            args = ', '.join(args)
            return wrap(f'{entity.resolved_prefixed_declaration}({args})')
        elif isinstance(self, (Dictionary, RawObject)):
            return wrap(f'JSONObject("""\n{default_value}\n""")')
        else:
            return None

    def declaration(self, mode: GenerationMode, supports_expressions: bool) -> str:
        return self.declaration_by_prefixed(
            prefixed=False,
            mode=mode,
            supports_expressions=supports_expressions
        )

    def prefixed_declaration(self, mode: GenerationMode, supports_expressions: bool) -> str:
        return self.declaration_by_prefixed(
            prefixed=True,
            mode=mode,
            supports_expressions=supports_expressions
        )

    @property
    def is_object(self) -> bool:
        return isinstance(self, Object) and not isinstance(self.object, StringEnumeration)

    @property
    def is_array_of_objects(self) -> bool:
        if isinstance(self, Array):
            item_type = cast(KotlinPropertyType, self.property_type)
            return item_type.is_object
        else:
            return False

    @property
    def plain_type_declaration(self) -> Optional[str]:
        if self.is_object and self.object is not None:
            return self.object.resolved_prefixed_declaration.replace('.', '')
        elif self.is_array_of_objects:
            item_type = cast(KotlinPropertyType, self.property_type)
            return item_type.plain_type_declaration
        else:
            return None

    def declaration_by_prefixed(self,
                                prefixed: bool,
                                mode: GenerationMode,
                                supports_expressions: bool) -> str:
        if isinstance(self, Color):
            return 'Int'
        if isinstance(self, Int):
            return 'Long'
        elif isinstance(self, Double):
            return 'Double'
        elif isinstance(self, (Bool, BoolInt)):
            return 'Boolean'
        elif isinstance(self, String):
            return 'CharSequence' if self.formatted else 'String'
        elif isinstance(self, (Dictionary, RawObject)):
            return 'JSONObject'
        elif isinstance(self, RawArray):
            return 'JSONArray'
        elif isinstance(self, StaticString):
            return 'String'
        elif isinstance(self, Url):
            return 'Uri'
        elif isinstance(self, Array):
            item_type = cast(KotlinPropertyType, self.property_type)
            item_decl = item_type.declaration_by_prefixed(prefixed, mode, supports_expressions)
            if supports_expressions and cast(KotlinPropertyType, self).is_array_of_expressions:
                return f'{EXPRESSION_LIST_TYPE_NAME}<{item_decl}>'
            return f'List<{item_decl}>'
        elif isinstance(self, Object):
            if self.name.startswith('$predefined_'):
                return self.name.replace('$predefined_', '')
            obj_name = None
            if mode.is_template:
                if isinstance(self.object, StringEnumeration):
                    string_enum: StringEnumeration = self.object
                    return string_enum.resolved_prefixed_declaration
                else:
                    obj_name = utils.capitalize_camel_case(self.object.resolved_name + mode.name_suffix)
            elif self.object is not None:
                obj_name = utils.capitalize_camel_case(self.object.resolved_name)
            prefix = ''
            if prefixed:
                prefix = self.object.declaration_prefix_for(mode)
            return f'{prefix}{obj_name or utils.capitalize_camel_case(self.name)}'

    def deserialization_transform(self, string_enum_prefixed: bool) -> str:
        if isinstance(self, Url):
            return 'ANY_TO_URI'
        elif isinstance(self, Color):
            return 'STRING_TO_COLOR_INT'
        elif isinstance(self, (Bool, BoolInt)):
            return 'ANY_TO_BOOLEAN'
        elif isinstance(self, Object) and isinstance(self.object, StringEnumeration):
            if string_enum_prefixed:
                typename = self.object.resolved_prefixed_declaration
            else:
                typename = utils.capitalize_camel_case(self.object.name)
            return f'{typename}.FROM_STRING'
        elif isinstance(self, Double):
            return 'NUMBER_TO_DOUBLE'
        elif isinstance(self, Int):
            return 'NUMBER_TO_INT'
        elif isinstance(self, String):
            return '::HtmlString' if self.formatted else ''
        elif isinstance(self, Array):
            return cast(KotlinPropertyType, self.property_type).deserialization_transform(string_enum_prefixed)
        else:
            return ''

    def static_validator_expression(self,
                                    property_name: str,
                                    supports_expressions: bool,
                                    with_template_validators: bool,
                                    is_private: bool) -> List[str]:
        result = []
        definition = self.validator_definition() or ''
        if not definition:
            return result

        prefix = 'private ' if is_private else '@JvmField '
        if isinstance(self, Array) and self.min_items > 0:
            item_type = cast(KotlinPropertyType, self.property_type)
            list_type = item_type.prefixed_declaration(
                GenerationMode.NORMAL_WITHOUT_TEMPLATES,
                supports_expressions
            )

            kotlin_type = cast(KotlinPropertyType, self)
            validator_instance_name = kotlin_type.validator_instance_name(
                property_name=property_name,
                with_templates=False
            )
            result.append(f'{prefix}val {validator_instance_name} = ListValidator<{list_type}> {definition}')

            if with_template_validators:

                templated_list_type = item_type.prefixed_declaration(
                    GenerationMode.TEMPLATE,
                    supports_expressions
                )
                validator_instance_name = kotlin_type.validator_instance_name(
                    property_name=property_name,
                    with_templates=True
                )
                result.append(f'{prefix}val {validator_instance_name} = ListValidator<{templated_list_type}> {definition}')
        else:
            validator_type = self.prefixed_declaration(
                GenerationMode.NORMAL_WITH_TEMPLATES,
                supports_expressions
            )
            validator_instance_name_without = self.validator_instance_name(
                property_name=property_name,
                with_templates=False
            )

            if with_template_validators:
                validator_instance_name_with = self.validator_instance_name(
                    property_name=property_name,
                    with_templates=True
                )
                result.append(f'{prefix}val {validator_instance_name_with} = ValueValidator<{validator_type}> {definition}')

            result.append(f'{prefix}val {validator_instance_name_without} = ValueValidator<{validator_type}> {definition}')

        return result

    def validator_arg(self,
                      property_name: str,
                      with_template_validators: bool,
                      inline_definition: bool = False) -> str:
        validator_definition = self.validator_definition()
        if validator_definition is None:
            return ''
        if inline_definition:
            return validator_definition
        return self.validator_instance_name(property_name, with_template_validators)

    @staticmethod
    def validator_instance_name(property_name: str, with_templates: bool) -> str:
        name = utils.constant_upper_case(property_name)
        return f'{name}_TEMPLATE_VALIDATOR' if with_templates else f'{name}_VALIDATOR'

    def validator_definition(self) -> Optional[str]:
        if isinstance(self, Array) and self.min_items > 0:
            return f'{{ it: List<*> -> it.size >= {self.min_items} }}'
        elif isinstance(self, RawArray) and self.min_items > 0:
            return f'{{ it: JSONArray -> it.length() >= {self.min_items} }}'
        elif isinstance(self, String) and (self.min_length > 0 or self.regex is not None):
            expressions = []
            length_field = 'rawLength' if self.formatted else 'length'
            if self.min_length > 0:
                expressions.append(f'it.{length_field} >= {self.min_length}')
            regex = self.regex
            if regex is not None:
                escaped_pattern = regex.pattern.replace('\\', '\\\\')
                expressions.append(f'it.doesMatch("{escaped_pattern}")')
            if not expressions:
                return ''
            return f'{{ it: String -> {" && ".join(expressions)} }}'
        elif isinstance(self, Url) and self.schemes is not None:
            scheme_list = ', '.join(map(lambda s: f'"{s}"', self.schemes))
            return f'{{ it.hasScheme(listOf({scheme_list})) }}'
        elif isinstance(self, Int):
            return _number_validator_decl('Long', self.constraint)
        elif isinstance(self, Double):
            return _number_validator_decl('Double', self.constraint)
        else:
            return None

    def type_helper_declaration(self, p: KotlinProperty, is_private: bool) -> str:
        type_decl = self.prefixed_declaration(
            mode=GenerationMode.NORMAL_WITHOUT_TEMPLATES,
            supports_expressions=p.supports_expressions
        )
        if p.default_value is None:
            default_value = f'{type_decl}.values().first()'
        else:
            default_value = cast(KotlinPropertyType, p.property_type).declaration_by_default_value(
                default_value=p.default_value,
                string_enum_prefixed=True,
                supports_expressions_flag=False
            )
        definition = f'TypeHelper.from(default = {default_value}) {{ it is {type_decl} }}'
        prefix = 'private ' if is_private else '@JvmField '
        return f'{prefix}val {self.type_helper_reference(p)} = {definition}'

    def type_helper_reference(self, p: KotlinProperty) -> str:
        prefix = 'TYPE_HELPER_'
        if isinstance(self, (Bool, BoolInt)):
            return f'{prefix}BOOLEAN'
        elif isinstance(self, String):
            return f'{prefix}STRING'
        elif isinstance(self, Url):
            return f'{prefix}URI'
        elif isinstance(self, Color):
            return f'{prefix}COLOR'
        elif isinstance(self, Int):
            return f'{prefix}INT'
        elif isinstance(self, Double):
            return f'{prefix}DOUBLE'
        elif isinstance(self, Dictionary):
            return f'{prefix}DICT'
        elif isinstance(self, RawArray):
            return f'{prefix}JSON_ARRAY'
        elif isinstance(self, Array):
            if self.property_type.supports_expressions:
                return cast(KotlinPropertyType, self.property_type).type_helper_reference(p)
            return ''
        elif isinstance(self, Object):
            return f'{prefix}{utils.constant_upper_case(p.name)}'
        else:
            return ''

    def serialization_transform(self, string_enum_prefixed: bool, with_arg_name: bool = True) -> str:
        arg_name_decl = 'converter = ' if with_arg_name else ''
        prefix = f', {arg_name_decl}'
        if isinstance(self, String):
            return f'{prefix}SPANNED_TO_HTML' if self.formatted else ''
        elif isinstance(self, Url):
            return f'{prefix}URI_TO_STRING'
        elif isinstance(self, Object) and isinstance(self.object, StringEnumeration):
            if string_enum_prefixed:
                typename = self.object.resolved_prefixed_declaration
            else:
                typename = utils.capitalize_camel_case(self.object.name)
            return f'{prefix}{typename}.TO_STRING'
        elif isinstance(self, Color):
            return f'{prefix}COLOR_INT_TO_STRING'
        elif isinstance(self, Array):
            return cast(KotlinPropertyType, self.property_type).serialization_transform(string_enum_prefixed, with_arg_name)
        else:
            return ''


class CaseNaming:
    def format_case_name(self, name: str) -> str:
        if isinstance(self, Suffix):
            return name + 'Case'
        elif isinstance(self, RemoveCommonPart):
            reduced_name = name[len(self.prefix):len(name) - len(self.suffix)]
            # Add `Case` suffix in templates to match the naming of corresponding enumeration inner classes.
            if self.prefix == '' and self.suffix in ['Template', 'JsonParser']:
                return reduced_name + 'Case'
            return reduced_name


class Suffix(CaseNaming):
    pass


@dataclasses.dataclass
class RemoveCommonPart(CaseNaming):
    prefix: str
    suffix: str


class KotlinEntityEnumeration(EntityEnumeration):

    @property
    def entities_kotlin(self) -> List[KotlinEntity]:
        return list(map(lambda x: self.__cast_entity_kotlin(x[1]), self.entities))

    def __cast_entity_kotlin(self, obj: Declarable) -> KotlinEntity:
        entity: KotlinEntity = cast(KotlinEntity, obj)
        entity.__class__ = KotlinEntity
        return entity

    @property
    def plain_type_declaration(self) -> str:
        return (self.entity_declaration_prefix.replace('.', '')
                + utils.capitalize_camel_case(self.name_for(GenerationMode.NORMAL_WITH_TEMPLATES)))

    @property
    def serializer_type_declaration(self) -> str:
        return (self.entity_declaration_prefix.replace('.', '')
                + utils.capitalize_camel_case(self.name_for(GenerationMode.SERIALIZER)))

    @property
    def entity_serializer_type_declaration(self) -> str:
        return f'{self.plain_type_declaration}.{ENTITY_PARSER_NAME}'

    @property
    def entity_serializer_name_declaration(self) -> str:
        return utils.lower_camel_case(self.plain_type_declaration) + 'JsonEntityParser'

    @property
    def template_serializer_type_declaration(self) -> str:
        return f'{self.plain_type_declaration}.{TEMPLATE_PARSER_NAME}'

    @property
    def template_serializer_name_declaration(self) -> str:
        return utils.lower_camel_case(self.plain_type_declaration) + 'JsonTemplateParser'

    @property
    def template_resolver_type_declaration(self) -> str:
        return f'{self.plain_type_declaration}.{TEMPLATE_RESOLVER_NAME}'

    @property
    def template_resolver_name_declaration(self) -> str:
        return utils.lower_camel_case(self.plain_type_declaration) + 'JsonTemplateResolver'

    def format_case_naming(self, entity_name: str) -> str:
        return self.__case_naming(entity_name).format_case_name(entity_name)

    def __case_naming(self, entity_name: str) -> CaseNaming:
        def find_common_prefix(a: List[str], b: List[str]) -> List[str]:
            common = []
            for a_el, b_el in zip(a, b):
                if a_el.lower() == b_el.lower():
                    common.append(a_el)
                else:
                    break
            return common

        names = [utils.name_components(self.name), utils.name_components(entity_name)]
        common_prefix = reduce(find_common_prefix, names)
        for element in names:
            element.reverse()
        common_suffix = reduce(find_common_prefix, names)
        if common_prefix or common_suffix:
            return RemoveCommonPart(
                prefix=utils.capitalize_camel_case('_'.join(reversed(common_prefix))),
                suffix=utils.capitalize_camel_case('_'.join(reversed(common_suffix))))
        else:
            return Suffix()

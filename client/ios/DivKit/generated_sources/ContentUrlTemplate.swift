// Generated code. Do not modify.

import Foundation
import Serialization
import VGSL

public final class ContentUrlTemplate: TemplateValue, Sendable {
  public static let type: String = "url"
  public let parent: String?
  public let value: Field<Expression<URL>>?

  public convenience init(dictionary: [String: Any], templateToType: [TemplateName: String]) throws {
    self.init(
      parent: dictionary["type"] as? String,
      value: dictionary.getOptionalExpressionField("value", transform: URL.makeFromNonEncodedString)
    )
  }

  init(
    parent: String?,
    value: Field<Expression<URL>>? = nil
  ) {
    self.parent = parent
    self.value = value
  }

  private static func resolveOnlyLinks(context: TemplatesContext, parent: ContentUrlTemplate?) -> DeserializationResult<ContentUrl> {
    let valueValue = { parent?.value?.resolveValue(context: context, transform: URL.makeFromNonEncodedString) ?? .noValue }()
    var errors = mergeErrors(
      valueValue.errorsOrWarnings?.map { .nestedObjectError(field: "value", error: $0) }
    )
    if case .noValue = valueValue {
      errors.append(.requiredFieldIsMissing(field: "value"))
    }
    guard
      let valueNonNil = valueValue.value
    else {
      return .failure(NonEmptyArray(errors)!)
    }
    let result = ContentUrl(
      value: { valueNonNil }()
    )
    return errors.isEmpty ? .success(result) : .partialSuccess(result, warnings: NonEmptyArray(errors)!)
  }

  public static func resolveValue(context: TemplatesContext, parent: ContentUrlTemplate?, useOnlyLinks: Bool) -> DeserializationResult<ContentUrl> {
    if useOnlyLinks {
      return resolveOnlyLinks(context: context, parent: parent)
    }
    var valueValue: DeserializationResult<Expression<URL>> = { parent?.value?.value() ?? .noValue }()
    _ = {
      // Each field is parsed in its own lambda to keep the stack size managable
      // Otherwise the compiler will allocate stack for each intermediate variable
      // upfront even when we don't actually visit a relevant branch
      for (key, __dictValue) in context.templateData {
        _ = {
          if key == "value" {
           valueValue = deserialize(__dictValue, transform: URL.makeFromNonEncodedString).merged(with: valueValue)
          }
        }()
        _ = {
         if key == parent?.value?.link {
           valueValue = valueValue.merged(with: { deserialize(__dictValue, transform: URL.makeFromNonEncodedString) })
          }
        }()
      }
    }()
    var errors = mergeErrors(
      valueValue.errorsOrWarnings?.map { .nestedObjectError(field: "value", error: $0) }
    )
    if case .noValue = valueValue {
      errors.append(.requiredFieldIsMissing(field: "value"))
    }
    guard
      let valueNonNil = valueValue.value
    else {
      return .failure(NonEmptyArray(errors)!)
    }
    let result = ContentUrl(
      value: { valueNonNil }()
    )
    return errors.isEmpty ? .success(result) : .partialSuccess(result, warnings: NonEmptyArray(errors)!)
  }

  private func mergedWithParent(templates: [TemplateName: Any]) throws -> ContentUrlTemplate {
    guard let parent = parent, parent != Self.type else { return self }
    guard let parentTemplate = templates[parent] as? ContentUrlTemplate else {
      throw DeserializationError.unknownType(type: parent)
    }
    let mergedParent = try parentTemplate.mergedWithParent(templates: templates)

    return ContentUrlTemplate(
      parent: nil,
      value: value ?? mergedParent.value
    )
  }

  public func resolveParent(templates: [TemplateName: Any]) throws -> ContentUrlTemplate {
    return try mergedWithParent(templates: templates)
  }
}

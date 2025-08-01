import VGSL

extension DivAccessibility {
  func resolve(
    _ expressionResolver: ExpressionResolver,
    id: String?,
    customParams: CustomAccessibilityParams
  ) -> AccessibilityElement {
    if resolveMode(expressionResolver) == .exclude {
      return AccessibilityElement(
        traits: .none,
        strings: AccessibilityElement.Strings(label: nil),
        hideElementWithChildren: true
      )
    }

    var label: String?
    if let customDescriptionProvider = customParams.descriptionProvider {
      label = customDescriptionProvider()
    } else if let description = resolveDescription(expressionResolver) {
      label = description
    }
    if label == nil, type != .auto {
      label = ""
    }

    return AccessibilityElement(
      traits: traits ?? customParams.defaultTraits,
      strings: AccessibilityElement.Strings(
        label: label,
        hint: resolveHint(expressionResolver),
        value: resolveValue(expressionResolver),
        identifier: id
      ),
      startsMediaSession: resolveMuteAfterAction(expressionResolver),
      isContainer: type == .list
    )
  }

  private var traits: AccessibilityElement.Traits? {
    switch type {
    case .button:
      return .button
    case .header:
      return .header
    case .image:
      return .image
    case .text:
      return .staticText
    case .checkbox:
      return .switchButton
    case .editText, .radio, .select, .tabBar:
      DivKitLogger.warning("Unsupported accessibility type")
      return AccessibilityElement.Traits.none
    case .none, .list:
      return AccessibilityElement.Traits.none
    case .auto:
      return nil
    }
  }

  private func resolveValue(_ resolver: ExpressionResolver) -> String? {
    switch type {
    case .checkbox:
      // voice over sounds this as "checked" / "unchecked" for switchButton trait
      resolveIsChecked(resolver) == true ? "1" : "0"
    default:
      resolveStateDescription(resolver)
    }
  }
}

struct CustomAccessibilityParams {
  static let `default` = CustomAccessibilityParams()

  let defaultTraits: AccessibilityElement.Traits
  let descriptionProvider: (() -> String?)?

  init(
    defaultTraits: AccessibilityElement.Traits = .none,
    descriptionProvider: (() -> String?)? = nil
  ) {
    self.defaultTraits = defaultTraits
    self.descriptionProvider = descriptionProvider
  }
}

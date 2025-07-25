import CoreGraphics
import Foundation
import VGSL

extension ContainerBlock.LayoutDirection: CustomDebugStringConvertible {
  public var debugDescription: String {
    switch self {
    case .horizontal:
      "Horizontal"
    case .vertical:
      "Vertical"
    }
  }
}

extension BlockAlignment2D: CustomDebugStringConvertible {
  public var debugDescription: String {
    if horizontal != vertical {
      "V: \(vertical) H: \(horizontal)"
    } else {
      horizontal.debugDescription
    }
  }
}

extension TextBlock: CustomDebugStringConvertible {
  public var debugDescription: String {
    """
    Text \(widthTrait) x \(heightTrait) {
    \(text.prettyDebugDescription.indented())
    }
    """
  }
}

extension ImageBlock: CustomDebugStringConvertible {
  public var debugDescription: String {
    """
    Image \(widthTrait) x \(height) {
      Content mode: \(contentMode)
      Holder: \(imageHolder)
    }
    """
  }
}

extension AnimatableImageBlock: CustomDebugStringConvertible {
  public var debugDescription: String {
    """
    Image \(widthTrait) x \(height) {
      Content mode: \(contentMode)
      Holder: \(imageHolder)
    }
    """
  }
}

extension SeparatorBlock: CustomDebugStringConvertible {
  public var debugDescription: String {
    "Separator \(widthTrait) x \(heightTrait) \(color)"
  }
}

extension SwipeContainerBlock: CustomDebugStringConvertible {
  public var debugDescription: String {
    """
    SwipeContainerBlock
    Actions:
    \(swipeOutActions.debugDescription)
    Content:
    \(child.debugDescription)
    """
  }
}

extension ContainerBlock: CustomDebugStringConvertible {
  public var debugDescription: String {
    var description = """
    \(layoutDirection) Container \(widthTrait) x \(heightTrait) {
      Axial alignment: \(axialAlignment)
      Cross alignment: \(crossAlignment)
    """
    if let animation = contentAnimation {
      description += "\n  Animation: \(animation)"
    }
    if anchorPoint != ContainerBlock.defaultAnchorPoint {
      description += "\n  Anchor:\(anchorPoint)"
    }

    description += "\n  Children: ["
    let lastGap = gaps.last!
    for (offset, tuple) in zip(children, gaps).enumerated() {
      let gap = tuple.1
      if !gap.isApproximatelyEqualTo(0) {
        description += "\n    Gap: \(gap),"
      }
      let child = tuple.0
      description += "\n    Cross alignment: \(child.crossAlignment)\n"
      description += child.content.debugDescription.indented(level: 2)
      if offset != children.count - 1 || !lastGap.isApproximatelyEqualTo(0) {
        description += ","
      }
    }
    if !lastGap.isApproximatelyEqualTo(0) {
      description += "\n    Gap: \(lastGap)"
    }
    description += "\n  ]"

    description += "\n}"
    return description
  }
}

extension ShadedBlock: CustomDebugStringConvertible {
  public var debugDescription: String {
    """
    Shaded {
      Shadow: \(shadow)
      Block: \(block)
    }
    """
  }
}

extension SwitchBlock: CustomDebugStringConvertible {
  public var debugDescription: String {
    var result = """
    Switch {
      On: \(on)
      Enabled: \(enabled)
    """

    if let action {
      result += "\n  URL: \(dbgStr(action.url))"
      result += "\n  Path: \(dbgStr(action.path))"
    }

    result += "\n}"
    return result
  }
}

extension PageControlBlock: CustomDebugStringConvertible {
  public var debugDescription: String {
    """
    PageControl {
      Pager ID: \(dbgStr(pagerPath?.pagerId))
      Card ID: \(dbgStr(pagerPath?.cardId))
      Number of pages: \(state.numberOfPages)
      Current page: \(state.currentPage)
    }
    """
  }
}

extension SwitchableContainerBlock: CustomDebugStringConvertible {
  public var debugDescription: String {
    """
    Switchable {
      Selector background color: \(backgroundColor)
      Selected item color:       \(selectedBackgroundColor)
      Title gaps:                \(titleGaps)
      Title to content gap:      \(titleContentGap)
      Left {
        Title: \(items.0.title)
    \(items.0.content.debugDescription.indented(level: 2))
      }
      Right {
        Title: \(items.1.title)
    \(items.1.content.debugDescription.indented(level: 2))
      }
    }
    """
  }
}

extension LayoutTrait: CustomDebugStringConvertible {
  public var debugDescription: String {
    switch self {
    case .intrinsic:
      "I"
    case let .weighted(value):
      "R(\(value.rawValue))"
    case let .fixed(value):
      "\(value)"
    }
  }
}

extension AnimationChanges: CustomDebugStringConvertible {
  public var debugDescription: String {
    switch self {
    case let .transform(values):
      "transform\(values)"
    case let .opacity(values):
      "opacity\(values)"
    }
  }
}

extension BlockAnimation: CustomDebugStringConvertible {
  public var debugDescription: String {
    "{ Animation changes:\(changes), KeyTimes: \(keyTimes.map(\.value)), Duration: \(duration) }"
  }
}

extension BlockShadow: CustomDebugStringConvertible {
  public var debugDescription: String {
    "{ Corners: \(cornerRadii), Blur: \(blurRadius), Offset: \(offset), Opacity: \(opacity) }"
  }
}

extension GalleryViewModel.Item: CustomDebugStringConvertible {
  public var debugDescription: String {
    "[\(crossAlignment)] \(content)"
  }
}

extension GalleryBlock: CustomDebugStringConvertible {
  public var debugDescription: String {
    var description = """
    Gallery \(widthTrait) x \(heightTrait) {
    """

    for tuple in zip(model.metrics.spacings, model.items) {
      let item = tuple.1
      description += "\n" + item.debugDescription.indented() + ","
      let spacing = tuple.0
      if !spacing.isApproximatelyEqualTo(0) {
        description += "\n  Spacing \(spacing),"
      }
    }
    description += "\n" + model.items.last!.debugDescription.indented()
    description += "\n}"
    return description
  }
}

extension PagerBlock: CustomDebugStringConvertible {
  public var debugDescription: String {
    """
    PagerBlock {
      Pager ID: \(dbgStr(pagerPath?.pagerId))
      Card ID: \(dbgStr(pagerPath?.cardId))
      Number of pages: \(state.numberOfPages)
      Current page: \(state.currentPage)
    }
    """
  }
}

extension BackgroundBlock: CustomDebugStringConvertible {
  public var debugDescription: String {
    """
    Background {
    \(background.debugDescription.indented())
      Content:
    \(child.debugDescription.indented())
    }
    """
  }
}

extension BlockBorder: CustomDebugStringConvertible {
  public var debugDescription: String {
    "{ color: \(color), width: \(width) }"
  }
}

extension TabsBlock: CustomDebugStringConvertible {
  public var debugDescription: String {
    var result = """
    Tabs \(widthTrait) x \(heightTrait) {
      Selected: \(state.selectedPageIndex)
      Title style: \(model.listModel.titleStyle.debugDescription.indented().dropFirst(2))
    """

    if let separator = model.separatorStyle {
      result += "\n  Separator: color \(separator.color), insets \(separator.insets)"
    }

    result += "\n  Tabs:"
    result += "\n  ["
    for item in zip(model.listModel.tabTitles, model.contentsModel.pages) {
      let title = item.0
      result += "\n    Title: \(title.text), path: \(title.path)"
      if let url = title.url {
        result += "\n    Title url: \(url)"
      }

      let page = item.1
      result += "\n" + page.debugDescription.indented(level: 2)
    }
    result += "\n  ]"
    result += "\n}"

    return result
  }
}

extension LayeredBlock.Child {
  fileprivate var description: String {
    "[\(alignment)]\(content)"
  }
}

extension LayeredBlock: CustomDebugStringConvertible {
  public var debugDescription: String {
    var result = "Layers \(widthTrait) x \(heightTrait) {"
    for child in children {
      result += "\n  Alignment: \(child.alignment)"
      result += "\n" + child.content.debugDescription.indented()
    }
    result += "\n}"
    return result
  }
}

extension GridBlock.Span: CustomDebugStringConvertible {
  public var debugDescription: String {
    "(\(rows)x\(columns))"
  }
}

extension GridBlock.Item: CustomDebugStringConvertible {
  public var debugDescription: String {
    "\(span)\(contents)"
  }
}

extension GridBlock: CustomDebugStringConvertible {
  public var debugDescription: String {
    """
    Grid \(columnCount) Columns, \(widthTrait.debugDescription) x \(heightTrait.debugDescription) {
      Items map:
    \(formatTable(grid.itemsIndices).indented())
      Items:
    \(items.map(\.debugDescription).joined(separator: ",\n").indented())
    }
    """
  }
}

extension DecoratingBlock: CustomDebugStringConvertible {
  var debugDescription: String {
    var result = "Decorated {"
    var decorations: [String] = []

    if !backgroundColor.alpha.isApproximatelyEqualTo(0) {
      decorations.append("Background color: \(backgroundColor)")
    }

    if let actions {
      var actionDescription = "Actions:\n"
      for action in actions {
        actionDescription += "   UIAction:\n"
        actionDescription += "      path: \(action.path)\n"
        actionDescription +=
          "      payload: \(action.payload.debugDescription.indented())\n"
      }
      decorations.append(actionDescription)
    }

    if let actionAnimation {
      var description = "Action animation:\n"
      for animation in actionAnimation.touchDown {
        description += "   TouchDown animation:\n"
        description += "      name: \(animation.kind.rawValue)\n"
      }
      for animation in actionAnimation.touchUp {
        description += "   TouchUp animation:\n"
        description += "      name: \(animation.kind.rawValue)\n"
      }
      if actionAnimation.touchDown.isEmpty, actionAnimation.touchUp.isEmpty {
        description += "   empty\n"
      }
      decorations.append(description)
    }

    if let actions = longTapActions {
      decorations.append(actions.debugDescription)
    }

    if boundary != DecoratingBlock.defaultBoundary {
      decorations.append("Boundary: \(boundary)")
    }

    if let border {
      decorations.append("Border: \(border)")
    }

    if !childAlpha.isApproximatelyEqualTo(1) {
      decorations.append("Alpha: \(childAlpha)")
    }

    if let visibilityParams {
      var visibilityActionDescription = "Visibility actions:\n"
      for action in visibilityParams.actions {
        let uiAction = action.uiAction
        visibilityActionDescription += "   UIAction:\n"
        visibilityActionDescription += "      path: \(uiAction.path)\n"
        visibilityActionDescription +=
          "      payload: \(uiAction.payload.debugDescription.indented())\n"
        visibilityActionDescription +=
          "   Required visibility duration: \(action.requiredDuration)\n"
        visibilityActionDescription += "   Target percentage: \(action.targetPercentage)\n"
        visibilityActionDescription += "   Action type: \(action.actionType)\n"
      }
      decorations.append(visibilityActionDescription)
    }

    if !tooltips.isEmpty {
      var tooltipsDescription = "Tooltips:\n"
      for tooltip in tooltips {
        tooltipsDescription += "   \(tooltip.debugDescription.indented())\n"
      }
      decorations.append(tooltipsDescription)
    }

    if paddings != .zero {
      var paddingsDescription = "Padded:"
      func append(_ what: String, _ value: CGFloat) {
        if !value.isApproximatelyEqualTo(0) {
          paddingsDescription += " \(what) \(value)"
        }
      }
      append("top", paddings.top)
      append("left", paddings.left)
      append("bottom", paddings.bottom)
      append("right", paddings.right)

      decorations.append(paddingsDescription)
    }

    result += decorations.map { "\n  " + $0 }.joined()
    result += "\n" + child.debugDescription.indented()
    result += "\n}"

    return result
  }
}

@_spi(Internal)
extension AccessibilityBlock: CustomDebugStringConvertible {
  var debugDescription: String {
    """
    Accessibility {
      accessibilityID: \(accessibilityID)
      child: \(child.debugDescription.indented())
    }
    """
  }
}

extension TransitioningBlock: CustomDebugStringConvertible {
  public var debugDescription: String {
    """
    Transitioning {
      from: \(dbgStr(from?.debugDescription.indented()))
      to: \(to.debugDescription.indented())
    }
    """
  }
}

extension BlockTooltip: CustomDebugStringConvertible {
  public var debugDescription: String {
    """
    BlockTooltip {
      id: \(id)
      duration: \(params.duration)
      offset: \(offset.x) x \(offset.y)
      position: \(position.rawValue)
      block: \(block.debugDescription.indented())
    }
    """
  }
}

extension MaskedBlock: CustomDebugStringConvertible {
  public var debugDescription: String {
    """
    MaskedBlock {
      maskBlock: \(maskBlock.debugDescription.indented())
      maskedBlock: \(maskedBlock.debugDescription.indented())
      allowsUserInteraction: \(allowsUserInteraction)
    }
    """
  }
}

extension EmptyBlock: CustomDebugStringConvertible {
  public var debugDescription: String {
    "Empty \(widthTrait) x \(heightTrait)"
  }
}

private func formatTable(_ rawTable: [[some Any]]) -> String {
  let table = rawTable.map { $0.map(String.init(describing:)) }
  guard !table.isEmpty else { return "Empty table" }
  let columnCounts = table.map(\.count)
  guard columnCounts.min() == columnCounts.max() else {
    return "Malformed table"
  }
  let columnCount = columnCounts.min()!
  guard columnCount != 0 else {
    return "Empty table"
  }
  let columnWidths = (0..<columnCount).map { column in
    table.map { $0[column].count }.max()!
  }
  return table.map {
    $0.enumerated().map { offset, element in
      String(repeating: " ", times: try! UInt(value: columnWidths[offset] - element.count)) +
        element
    }.joined(separator: " ")
  }.joined(separator: "\n")
}

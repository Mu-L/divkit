import CoreGraphics
import Foundation
import VGSL

#if canImport(UIKit)
import UIKit
#else
import AppKit
#endif

public final class ContainerBlock: BlockWithLayout {
  typealias Layout = ContainerBlockLayout
  public static let defaultAnchorPoint = AnchorPoint(
    x: .relative(value: 50),
    y: .relative(value: 50)
  )

  /// Determines direction in which child blocks are laid out in a container
  @frozen
  public enum LayoutDirection: CaseIterable {
    /// Child blocks are laid out horizontally one after another
    case horizontal
    /// Child blocks are laid out vertically one after another
    case vertical

    /// Returns opposite direction
    var opposite: LayoutDirection {
      self == .horizontal ? .vertical : .horizontal
    }
  }

  public enum LayoutMode {
    case wrap
    case noWrap
  }

  public struct Child: Equatable {
    static let empty = Child(content: EmptyBlock.zeroSized)

    public var content: Block
    /// Alignment for dimension crossing container direction
    public let crossAlignment: CrossAlignment

    public init(content: Block, crossAlignment: CrossAlignment = .leading) {
      self.content = content
      self.crossAlignment = crossAlignment
    }
  }

  public enum CrossAlignment {
    case leading
    case center
    case trailing
    case baseline
  }

  public enum AxialAlignment {
    case leading
    case center
    case trailing
    case spaceBetween
    case spaceAround
    case spaceEvenly
  }

  public struct Separator: Equatable {
    public let style: Child
    public let showAtEnd: Bool
    public let showAtStart: Bool
    public let showBetween: Bool

    public init(
      style: Child,
      showAtEnd: Bool = false,
      showAtStart: Bool = false,
      showBetween: Bool = false
    ) {
      self.style = style
      self.showAtEnd = showAtEnd
      self.showAtStart = showAtStart
      self.showBetween = showBetween
    }
  }

  private struct CachedSizes {
    var intrinsicWidth: CGFloat?
    var intrinsicHeight: (width: CGFloat, height: CGFloat)?
    var nonResizableSize: (width: CGFloat, height: CGFloat?)?
  }

  public let blockLayoutDirection: UserInterfaceLayoutDirection
  public let layoutDirection: LayoutDirection
  public let layoutMode: LayoutMode
  public let widthTrait: LayoutTrait
  public let heightTrait: LayoutTrait
  public let axialAlignment: AxialAlignment
  public let crossAlignment: CrossAlignment
  public let gaps: [CGFloat]
  public let children: [Child]
  public let separator: Separator?
  public let lineSeparator: Separator?
  public let contentAnimation: BlockAnimation?
  public let anchorPoint: AnchorPoint
  public let childrenTransform: CGAffineTransform
  public let clipContent: Bool
  public let accessibilityElement: AccessibilityElement?
  public let path: UIElementPath?

  private var cached = CachedSizes()

  public init(
    blockLayoutDirection: UserInterfaceLayoutDirection = .leftToRight,
    layoutDirection: LayoutDirection,
    layoutMode: LayoutMode = .noWrap,
    widthTrait: LayoutTrait = .resizable,
    heightTrait: LayoutTrait = .intrinsic,
    axialAlignment: AxialAlignment = .leading,
    crossAlignment: CrossAlignment = .leading,
    gaps: [CGFloat]? = nil,
    children: [Child],
    separator: Separator? = nil,
    lineSeparator: Separator? = nil,
    contentAnimation: BlockAnimation? = nil,
    anchorPoint: AnchorPoint = ContainerBlock.defaultAnchorPoint,
    childrenTransform: CGAffineTransform = .identity,
    clipContent: Bool = true,
    accessibilityElement: AccessibilityElement? = nil,
    path: UIElementPath? = nil
  ) throws {
    let gaps = gaps ?? Array(repeating: 0, times: try! UInt(value: children.count + 1))

    if gaps.count != children.count + 1 {
      throw BlockError(
        "Container block error: gaps count is not equal to children count plus 1 " +
          "(\(gaps.count) != \(children.count) + 1)"
      )
    }

    self.blockLayoutDirection = blockLayoutDirection
    self.layoutDirection = layoutDirection
    self.layoutMode = layoutMode
    self.widthTrait = widthTrait
    self.heightTrait = heightTrait
    self.axialAlignment = axialAlignment
    self.crossAlignment = crossAlignment
    self.gaps = makeGapsWithSeparators(
      gaps: gaps,
      separator: separator,
      layoutMode: layoutMode
    )
    self.children = makeChildrenWithSeparators(
      children: children,
      separator: separator,
      layoutMode: layoutMode
    )
    self.separator = separator
    self.lineSeparator = lineSeparator
    self.contentAnimation = contentAnimation
    self.anchorPoint = anchorPoint
    self.childrenTransform = childrenTransform
    self.clipContent = clipContent
    self.accessibilityElement = accessibilityElement
    self.path = path

    if !children.isEmpty {
      try validateLayoutTraits()
    }
  }

  public func ascent(forWidth width: CGFloat) -> CGFloat? {
    guard layoutDirection == .horizontal else {
      return nil
    }
    let layout = ContainerBlockLayout(
      children: children,
      separator: separator,
      lineSeparator: lineSeparator,
      gaps: gaps,
      blockLayoutDirection: blockLayoutDirection,
      layoutDirection: layoutDirection,
      layoutMode: layoutMode,
      axialAlignment: axialAlignment,
      crossAlignment: crossAlignment,
      size: CGSize(width: width, height: .zero)
    )
    return layout.ascent
  }

  private func validateLayoutTraits() throws {
    if layoutMode == .wrap {
      switch layoutDirection {
      case .horizontal:
        guard children.map(\.content).allVerticallyNonResizable else {
          throw BlockError(
            "Container block error: horizontal wrap container has children with resizable height"
          )
        }
      case .vertical:
        guard children.map(\.content).allHorizontallyNonResizable else {
          throw BlockError(
            "Container block error: vertical wrap container has children with resizable width"
          )
        }
      }
    }

    if case .intrinsic = widthTrait {
      switch layoutDirection {
      case .horizontal:
        guard children.map(\.content).allHorizontallyNonResizable else {
          throw BlockError(
            "Container block error: horizontal intrinsic-width container has children with resizable width"
          )
        }
      case .vertical:
        guard children.map(\.content).hasHorizontallyNonResizable else {
          throw BlockError(
            "Container block error: in vertical intrinsic-width container all children have resizable width"
          )
        }
      }
    }

    if case .intrinsic = heightTrait {
      switch layoutDirection {
      case .horizontal:
        break // this is currently a valid case, see `.max() ?? 0` on line 163
      case .vertical:
        guard children.map(\.content).allVerticallyNonResizable else {
          throw BlockError(
            "Container block error: vertical intrinsic-height container has children with resizable height"
          )
        }
      }
    }
  }

  public var isVerticallyResizable: Bool { heightTrait.isResizable }
  public var isHorizontallyResizable: Bool { widthTrait.isResizable }

  public var calculateWidthFirst: Bool {
    switch widthTrait {
    case .fixed, .weighted:
      true
    case .intrinsic:
      !(layoutDirection == .vertical && layoutMode == .wrap)
    }
  }

  public var isVerticallyConstrained: Bool { heightTrait.isConstrained }
  public var isHorizontallyConstrained: Bool { widthTrait.isConstrained }

  public var intrinsicContentWidth: CGFloat {
    if case let .fixed(width) = widthTrait {
      return width
    }

    if let cached = cached.intrinsicWidth {
      return cached
    }

    var result: CGFloat = switch layoutDirection {
    case .horizontal:
      (children.map(\.content.intrinsicContentWidth) + gaps).reduce(0, +)
    case .vertical:
      children.map(\.content.intrinsicContentWidth).max() ?? 0
    }

    if case let .intrinsic(_, minSize, maxSize) = widthTrait {
      result = clamp(result, min: minSize, max: maxSize)
    }

    cached.intrinsicWidth = result
    return result
  }

  public func intrinsicContentHeight(forWidth width: CGFloat) -> CGFloat {
    if case let .fixed(height) = heightTrait {
      return height
    }

    if let cached = cached.intrinsicHeight,
       cached.width.isApproximatelyEqualTo(width) {
      return cached.height
    }

    var result: CGFloat
    switch layoutDirection {
    case .horizontal:
      let preparedChildren = children.map {
        ContainerBlock.Child(
          content: $0.content,
          crossAlignment: $0.crossAlignmentForCalculatingHeight
        )
      }
      let layout = ContainerBlockLayout(
        children: preparedChildren,
        separator: separator,
        lineSeparator: lineSeparator,
        gaps: gaps,
        blockLayoutDirection: blockLayoutDirection,
        layoutDirection: layoutDirection,
        layoutMode: layoutMode,
        axialAlignment: axialAlignment,
        crossAlignment: crossAlignment,
        size: CGSize(width: width, height: .zero),
        needCompressConstrainedBlocks: false
      )
      result = layout.blockFrames.map(\.maxY).max() ?? 0
    case .vertical:
      let childrenHeights = children.map(\.content).intrinsicHeights(forWidth: width)
      result = (childrenHeights + gaps).reduce(0, +)
    }

    if case let .intrinsic(_, minSize, maxSize) = heightTrait {
      result = clamp(result, min: minSize, max: maxSize)
    }

    cached.intrinsicHeight = (width: width, height: result)
    return result
  }

  public var widthOfHorizontallyNonResizableBlock: CGFloat {
    if case let .fixed(value) = widthTrait {
      return value
    }

    guard case .intrinsic = widthTrait else {
      assertionFailure("cannot get widthOfHorizontallyNonResizableBlock for resizable block")
      return 0
    }

    if let cached = cached.nonResizableSize, cached.height == nil {
      return cached.width
    }

    let result: CGFloat = switch layoutDirection {
    case .horizontal:
      (children.map(\.content.widthOfHorizontallyNonResizableBlock) + gaps)
        .reduce(0, +)
    case .vertical:
      // MOBYANDEXIOS-1092: Only non-resizable children can influence the width of a container
      // because the widths of resizable children depend on the width of container itself
      children.filter { !$0.content.isHorizontallyResizable }
        .map(\.content.widthOfHorizontallyNonResizableBlock).max() ?? 0
    }

    cached.nonResizableSize = (width: result, height: nil)
    return result
  }

  public var heightOfVerticallyNonResizableBlock: CGFloat {
    assert(
      layoutMode == .wrap && layoutDirection == .vertical,
      "First height calculation should only be used for vertical container with wrap layout mode"
    )
    return heightOfVerticallyNonResizableBlock(forWidth: .zero)
  }

  public func widthOfHorizontallyNonResizableBlock(forHeight height: CGFloat) -> CGFloat {
    assert(
      layoutMode == .wrap && layoutDirection == .vertical,
      "First height calculation should only be used for vertical container with wrap layout mode"
    )
    if case let .fixed(value) = widthTrait {
      return value
    }

    guard case .intrinsic = widthTrait else {
      assertionFailure("cannot get widthOfHorizontallyNonResizableBlock for resizable block")
      return 0
    }

    if let cached = cached.nonResizableSize, let cachedHeight = cached.height,
       cachedHeight.isApproximatelyEqualTo(height) {
      return cached.width
    }

    let result: CGFloat
    let layout = ContainerBlockLayout(
      children: children,
      separator: separator,
      lineSeparator: lineSeparator,
      gaps: gaps,
      blockLayoutDirection: blockLayoutDirection,
      layoutDirection: layoutDirection,
      layoutMode: layoutMode,
      axialAlignment: axialAlignment,
      crossAlignment: crossAlignment,
      size: CGSize(width: .zero, height: height)
    )
    result = layout.blockFrames.map(\.maxX).max() ?? 0
    cached.nonResizableSize = (width: result, height: height)
    return result
  }

  public func heightOfVerticallyNonResizableBlock(forWidth width: CGFloat) -> CGFloat {
    switch heightTrait {
    case let .fixed(value):
      return value
    case .intrinsic:
      return intrinsicContentHeight(forWidth: width)
    case .weighted:
      assertionFailure("cannot get heightOfVerticallyNonResizableBlock for resizable block")
      return 0
    }
  }

  public var weightOfVerticallyResizableBlock: LayoutTrait.Weight {
    guard case let .weighted(value) = heightTrait else {
      assertionFailure("try to get weight for non resizable block")
      return LayoutTrait.Weight.default
    }
    return value
  }

  public var weightOfHorizontallyResizableBlock: LayoutTrait.Weight {
    guard case let .weighted(value) = widthTrait else {
      assertionFailure("try to get weight for non resizable block")
      return LayoutTrait.Weight.default
    }
    return value
  }

  public func equals(_ other: Block) -> Bool {
    guard let other = other as? ContainerBlock else {
      return false
    }

    return self == other
  }

  func laidOutHierarchy(for size: CGSize) -> (ContainerBlock, Layout) {
    let layout = ContainerBlockLayout(
      children: children,
      separator: separator,
      lineSeparator: lineSeparator,
      gaps: gaps,
      blockLayoutDirection: blockLayoutDirection,
      layoutDirection: layoutDirection,
      layoutMode: layoutMode,
      axialAlignment: axialAlignment,
      crossAlignment: crossAlignment,
      size: size
    )
    let newChildren = zip(self.children, layout.blockFrames).map { child, frame -> Child in
      modified(child) { $0.content = $0.content.laidOut(for: frame.size) }
    }
    let block = try! modifying(children: newChildren)

    return (block, layout)
  }
}

private func makeGapsWithSeparators(
  gaps: [CGFloat],
  separator: ContainerBlock.Separator?,
  layoutMode: ContainerBlock.LayoutMode
) -> [CGFloat] {
  guard layoutMode == .noWrap, let separator else {
    return gaps
  }
  return Array<CGFloat>.build {
    for (index, gap) in gaps.enumerated() {
      switch index {
      case 0:
        gap
        if separator.showAtStart {
          0
        }
      case gaps.count - 1:
        if separator.showAtEnd {
          0
        }
        gap
      default:
        if separator.showBetween {
          gap / 2
          gap / 2
        } else {
          gap
        }
      }
    }
  }
}

private func makeChildrenWithSeparators(
  children: [ContainerBlock.Child],
  separator: ContainerBlock.Separator?,
  layoutMode: ContainerBlock.LayoutMode
) -> [ContainerBlock.Child] {
  guard layoutMode == .noWrap, let separator else {
    return children
  }

  var childrenWithSeparators: [ContainerBlock.Child] = []
  var hasVisilbeItems = false
  for (index, child) in children.enumerated() {
    let isCurrentItemVisible = !child.content.isEmpty
    if separator.showBetween, index > 0 {
      if isCurrentItemVisible, hasVisilbeItems {
        childrenWithSeparators.append(separator.style)
      } else {
        childrenWithSeparators.append(.empty)
      }
    }
    childrenWithSeparators.append(child)
    hasVisilbeItems = hasVisilbeItems || isCurrentItemVisible
  }

  if separator.showAtStart {
    childrenWithSeparators.insert(hasVisilbeItems ? separator.style : .empty, at: 0)
  }

  if separator.showAtEnd {
    childrenWithSeparators.append(hasVisilbeItems ? separator.style : .empty)
  }

  return childrenWithSeparators
}

extension ContainerBlock: Equatable {
  public static func ==(lhs: ContainerBlock, rhs: ContainerBlock) -> Bool {
    lhs.layoutDirection == rhs.layoutDirection &&
      lhs.widthTrait == rhs.widthTrait &&
      lhs.heightTrait == rhs.heightTrait &&
      lhs.axialAlignment == rhs.axialAlignment &&
      lhs.crossAlignment == rhs.crossAlignment &&
      lhs.gaps == rhs.gaps &&
      lhs.children == rhs.children &&
      lhs.contentAnimation == rhs.contentAnimation &&
      lhs.anchorPoint == rhs.anchorPoint &&
      lhs.childrenTransform == rhs.childrenTransform &&
      lhs.separator == rhs.separator &&
      lhs.lineSeparator == rhs.lineSeparator &&
      lhs.accessibilityElement == rhs.accessibilityElement &&
      lhs.path == rhs.path
  }
}

extension ContainerBlock: ImageContaining {
  public func getImageHolders() -> [ImageHolder] {
    children.flatMap { $0.content.getImageHolders() }
  }
}

extension ContainerBlock: ElementStateUpdating {
  public func updated(withStates states: BlocksState) throws -> ContainerBlock {
    let newChildren = try children.map {
      try ContainerBlock.Child(
        content: $0.content.updated(withStates: states),
        crossAlignment: $0.crossAlignment
      )
    }
    let childrenChanged = zip(children, newChildren).contains { $1.content !== $0.content }

    return childrenChanged ? try modifying(children: newChildren) : self
  }
}

extension ContainerBlock: ElementFocusUpdating {
  public func updated(path: UIElementPath, isFocused: Bool) throws -> ContainerBlock {
    let newChildren = try children.map {
      try ContainerBlock.Child(
        content: $0.content.updated(path: path, isFocused: isFocused),
        crossAlignment: $0.crossAlignment
      )
    }
    let childrenChanged = zip(children, newChildren).contains { $1.content !== $0.content }

    return childrenChanged ? try modifying(children: newChildren) : self
  }
}

extension ContainerBlock.Child {
  public static func ==(_ lhs: ContainerBlock.Child, _ rhs: ContainerBlock.Child) -> Bool {
    lhs.content == rhs.content && lhs.crossAlignment == rhs.crossAlignment
  }
}

extension ContainerBlock.CrossAlignment {
  public func offset(
    forAvailableSpace availableSpace: CGFloat,
    contentSize: CGFloat = 0
  ) -> CGFloat {
    switch self {
    case .leading, .baseline:
      0
    case .center:
      ((availableSpace - contentSize) * 0.5).roundedToScreenScale
    case .trailing:
      availableSpace - contentSize
    }
  }
}

extension Sequence<ContainerBlock.Child> {
  fileprivate func applyingContents(_ contents: some Sequence<Block>) -> [ContainerBlock.Child] {
    zip(self, contents).map { child, newContent in
      modified(child) { $0.content = newContent }
    }
  }
}

extension ContainerBlock.Child {
  fileprivate var crossAlignmentForCalculatingHeight: ContainerBlock.CrossAlignment {
    guard self.crossAlignment != .center,
          self.crossAlignment != .trailing else {
      return .leading
    }
    return self.crossAlignment
  }
}

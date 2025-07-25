#if os(iOS)
import UIKit
import VGSL

extension StateBlock {
  public static func makeBlockView() -> BlockView {
    StateBlockView()
  }

  public func canConfigureBlockView(_ view: BlockView) -> Bool {
    view is StateBlockView
  }

  public func configureBlockView(
    _ view: BlockView,
    observer: ElementStateObserver?,
    overscrollDelegate: ScrollDelegate?,
    renderingDelegate: RenderingDelegate?
  ) {
    (view as! StateBlockView).configure(
      child: child,
      ids: Set(ids.map { BlockViewID(rawValue: $0) }),
      observer: observer,
      overscrollDelegate: overscrollDelegate,
      renderingDelegate: renderingDelegate,
      parentBlock: self
    )
  }
}

private final class SubviewStorage: RenderingDelegate {
  private typealias ViewWithID = (id: BlockViewID, view: DetachableAnimationBlockView)
  typealias FrameWithID = (id: BlockViewID, frame: CGRect)

  private let wrappedRenderingDelegate: RenderingDelegate?
  private let ids: Set<BlockViewID>
  // views are ordered, so can't use Dictionary here
  private var views: [ViewWithID] = []

  init(
    wrappedRenderingDelegate: RenderingDelegate?,
    ids: Set<BlockViewID>
  ) {
    if let subviewStorage = wrappedRenderingDelegate as? SubviewStorage {
      self.wrappedRenderingDelegate = subviewStorage.wrappedRenderingDelegate
    } else {
      self.wrappedRenderingDelegate = wrappedRenderingDelegate
    }
    self.ids = ids
  }

  func mapView(_ view: BlockView, to id: BlockViewID) {
    if let view = view as? DetachableAnimationBlockView {
      if getView(id) == nil {
        views.append((id: id, view: view))
      } else {
        views.removeAll { $0.id == id }
        views.append((id: id, view: view))
      }
    }
    wrappedRenderingDelegate?.mapView(view, to: id)
  }

  func tooltipAnchorViewAdded(anchorView: TooltipAnchorView) {
    wrappedRenderingDelegate?.tooltipAnchorViewAdded(anchorView: anchorView)
  }

  func tooltipAnchorViewRemoved(anchorView: TooltipAnchorView) {
    wrappedRenderingDelegate?.tooltipAnchorViewRemoved(anchorView: anchorView)
  }

  func reportRenderingError(message: String, isWarning: Bool, path: UIElementPath) {
    wrappedRenderingDelegate?.reportRenderingError(
      message: message,
      isWarning: isWarning,
      path: path
    )
  }

  func reportViewWasCreated() {
    wrappedRenderingDelegate?.reportViewWasCreated()
  }

  func reportBlockDidConfigure(path: UIElementPath) {
    wrappedRenderingDelegate?.reportBlockDidConfigure(path: path)
  }

  func reportBlockWillConfigure(path: UIElementPath) {
    wrappedRenderingDelegate?.reportBlockWillConfigure(path: path)
  }

  func reportViewDidLayout(path: UIElementPath) {
    wrappedRenderingDelegate?.reportViewDidLayout(path: path)
  }

  func reportViewWillLayout(path: UIElementPath) {
    wrappedRenderingDelegate?.reportViewWillLayout(path: path)
  }

  func getView(_ id: BlockViewID) -> DetachableAnimationBlockView? {
    views.first { $0.id == id }?.view
  }

  func getViewsToRemove(
    newIds: Set<BlockViewID>
  ) -> [DetachableAnimationBlockView] {
    let idsToRemove = ids.subtracting(newIds)
    return idsToRemove.compactMap {
      getView($0)
    }
  }

  func getViewsToAdd() -> [DetachableAnimationBlockView] {
    views
      .filter(\.view.hasAnimationIn)
      .map(\.view)
  }

  func getViewsToTransition(
    newIds: Set<BlockViewID>,
    container: UIView
  ) -> [FrameWithID] {
    let idsToTransition = ids.intersection(newIds)
    if idsToTransition.isEmpty {
      return []
    }

    return views.compactMap { id, view in
      idsToTransition.contains(id)
        ? (id: id, frame: view.convertFrame(to: container))
        : nil
    }
  }
}

private final class StateBlockView: BlockView {
  private var subviewStorage = SubviewStorage(wrappedRenderingDelegate: nil, ids: [])
  private var childView: BlockView?
  private var stateId: String?

  private var parentBlock: StateBlock?
  private weak var observer: ElementStateObserver?
  private weak var overscrollDelegate: ScrollDelegate?
  private weak var renderingDelegate: RenderingDelegate?

  var effectiveBackgroundColor: UIColor? { childView?.effectiveBackgroundColor }

  func configure(
    child: Block,
    ids: Set<BlockViewID>,
    observer: ElementStateObserver?,
    overscrollDelegate: ScrollDelegate?,
    renderingDelegate: RenderingDelegate?,
    parentBlock: StateBlock
  ) {
    defer {
      self.parentBlock = parentBlock
      self.observer = observer
      self.overscrollDelegate = overscrollDelegate
      self.renderingDelegate = renderingDelegate
    }

    if let oldParentChild = self.parentBlock?.child,
       child.self.equals(oldParentChild),
       self.observer === observer,
       self.overscrollDelegate === overscrollDelegate,
       self.renderingDelegate === renderingDelegate {
      return // The child block hasn't changed, stop configuring
    }

    // remove views with unfinished animations
    for subview in subviews {
      if subview !== childView {
        subview.removeFromSuperview()
      }
    }

    let viewsToTransition = subviewStorage.getViewsToTransition(
      newIds: ids,
      container: self
    )

    for view in subviewStorage.getViewsToRemove(newIds: ids) {
      view.cancelAnimations()
      view.removeWithAnimation(in: self)
    }

    subviewStorage = SubviewStorage(
      wrappedRenderingDelegate: renderingDelegate,
      ids: ids
    )

    childView = child.reuse(
      childView,
      observer: observer,
      overscrollDelegate: overscrollDelegate,
      renderingDelegate: subviewStorage,
      superview: self
    )

    let viewsToAdd = subviewStorage.getViewsToAdd()

    if viewsToAdd.isEmpty, viewsToTransition.isEmpty {
      setNeedsLayout()
    } else {
      forceLayout()

      changeBoundsWithAnimation(viewsToTransition)
      addWithAnimations(viewsToAdd)
    }
  }

  private func addWithAnimations(_ views: [DetachableAnimationBlockView]) {
    for view in views {
      view.addWithAnimation()
    }
  }

  private func changeBoundsWithAnimation(_ views: [SubviewStorage.FrameWithID]) {
    for (id, frame) in views {
      if let view = subviewStorage.getView(id) {
        view.changeBoundsWithAnimation(in: self, startFrame: frame)
      }
    }
  }

  override func layoutSubviews() {
    super.layoutSubviews()

    childView?.setNonTransformedFrame(bounds)
  }

  override func hitTest(_ point: CGPoint, with event: UIEvent?) -> UIView? {
    let result = super.hitTest(point, with: event)
    return result === self ? nil : result
  }
}

extension StateBlockView: VisibleBoundsTrackingContainer {
  var visibleBoundsTrackingSubviews: [VisibleBoundsTrackingView] {
    childView.asArray()
  }
}
#endif

#if os(iOS)
import Foundation
import UIKit
import VGSL

extension MaskedBlock: UIViewRenderable {
  public static func makeBlockView() -> BlockView { MaskedBlockView() }

  public func configureBlockView(
    _ view: BlockView,
    observer: ElementStateObserver?,
    overscrollDelegate: ScrollDelegate?,
    renderingDelegate: RenderingDelegate?
  ) {
    let model = MaskedBlockView.Model(
      maskBlock: maskBlock,
      maskedBlock: maskedBlock,
      allowsUserInteraction: allowsUserInteraction
    )
    (view as! MaskedBlockView).configure(
      model: model,
      observer: observer,
      overscrollDelegate: overscrollDelegate,
      renderingDelegate: renderingDelegate
    )
  }

  public func canConfigureBlockView(_ view: BlockView) -> Bool {
    view is MaskedBlockView
  }
}

private final class MaskedBlockView: BlockView, VisibleBoundsTrackingContainer {
  struct Model: Equatable {
    let maskBlock: Block
    let maskedBlock: Block
    let allowsUserInteraction: Bool

    static func ==(lhs: Model, rhs: Model) -> Bool {
      lhs.maskBlock == rhs.maskBlock &&
        lhs.maskedBlock == rhs.maskedBlock &&
        lhs.allowsUserInteraction == rhs.allowsUserInteraction
    }
  }

  private var maskBlockView: BlockView?
  private var maskedBlockView: BlockView?

  private var model: Model!

  var visibleBoundsTrackingSubviews: [VisibleBoundsTrackingView] {
    [maskedBlockView, maskBlockView].compactMap { $0 }
  }

  var effectiveBackgroundColor: UIColor? { maskedBlockView?.effectiveBackgroundColor }

  func configure(
    model: Model,
    observer: ElementStateObserver?,
    overscrollDelegate: ScrollDelegate?,
    renderingDelegate: RenderingDelegate?
  ) {
    let blockViews = [maskBlockView, maskedBlockView].compactMap { $0 }
      .reused(
        with: [model.maskBlock, model.maskedBlock],
        attachTo: self,
        observer: observer,
        overscrollDelegate: overscrollDelegate,
        renderingDelegate: renderingDelegate
      )

    self.maskBlockView = blockViews[0]
    self.maskedBlockView = blockViews[1]
    isUserInteractionEnabled = model.allowsUserInteraction

    setNeedsLayout()
  }

  override func layoutSubviews() {
    super.layoutSubviews()

    maskedBlockView?.frame = bounds
    maskBlockView?.frame = bounds

    maskedBlockView?.mask = maskBlockView
  }
}
#endif

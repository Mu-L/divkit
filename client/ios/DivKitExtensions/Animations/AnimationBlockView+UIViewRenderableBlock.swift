#if os(iOS)
import AVFoundation
import DivKit
import LayoutKit
import UIKit
import VGSL

extension LottieAnimationBlock {
  static func makeBlockView() -> BlockView {
    AnimationBlockView()
  }

  func canConfigureBlockView(_ view: BlockView) -> Bool {
    view is AnimationBlockView
  }

  func configureBlockView(
    _ view: BlockView,
    observer _: ElementStateObserver?,
    overscrollDelegate _: ScrollDelegate?,
    renderingDelegate _: RenderingDelegate?
  ) {
    let lottieView = view as! AnimationBlockView
    if lottieView.animationHolder != animationHolder {
      lottieView.animatableView = animatableView.value
      lottieView.animationContentMode = scale.contentMode
      lottieView.animationHolder = animationHolder
    }
    lottieView.isPlaying = isPlaying
  }
}

extension DivImageScale {
  fileprivate var contentMode: UIView.ContentMode {
    switch self {
    case .fit: .scaleAspectFit
    case .fill: .scaleAspectFill
    case .noScale: .center
    case .stretch: .scaleToFill
    }
  }
}
#endif

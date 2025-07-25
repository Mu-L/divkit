#if os(iOS)
import LayoutKit
import UIKit
import VGSL

final class AnimationBlockView: BlockView {
  var animatableView: AsyncSourceAnimatableView? {
    didSet {
      if let animatablView = animatableView {
        oldValue?.removeFrom(self)
        addSubview(animatablView)
      }
    }
  }

  var animationContentMode: UIView.ContentMode = .scaleAspectFit {
    didSet {
      animatableView?.contentMode = animationContentMode
    }
  }

  private var animationRequest: Cancellable?

  var isPlaying: Bool = true {
    didSet {
      guard oldValue != isPlaying else { return }
      if isPlaying {
        animatableView?.play()
      } else {
        animatableView?.pause()
      }
    }
  }

  var animationHolder: AnimationHolder? {
    didSet {
      animationRequest?.cancel()

      let newValue = animationHolder
      animationRequest = animationHolder?
        .requestAnimationWithCompletion { [weak self] animationSource in
          guard let self,
                newValue === self.animationHolder,
                let animationSource,
                let view = self.animatableView else {
            return
          }

          view.contentMode = animationContentMode
          Task { @MainActor in
            await view.setSourceAsync(animationSource)
            if self.isPlaying {
              view.play()
            }
          }
        }
    }
  }

  init() {
    super.init(frame: .zero)
  }

  override func layoutSubviews() {
    super.layoutSubviews()
    animatableView?.frame = bounds
  }

  @available(*, unavailable)
  required init?(coder _: NSCoder) {
    fatalError("init(coder:) has not been implemented")
  }

  let effectiveBackgroundColor: UIColor? = nil
}

extension AnimationBlockView: VisibleBoundsTrackingLeaf {}
#endif

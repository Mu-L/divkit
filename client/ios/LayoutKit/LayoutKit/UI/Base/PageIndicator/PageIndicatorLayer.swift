#if os(iOS)
import UIKit
import VGSL

typealias Scale = (x: CGFloat, y: CGFloat)

final class ScrollPageIndicatorLayer: CALayer {
  @NSManaged var currentIndexPosition: CGFloat
  var numberOfPages = 0
  var configuration = PageIndicatorConfiguration(
    highlightedColor: .clear,
    normalColor: .clear,
    highlightedBorder: BlockBorder(color: .clear, width: 0),
    normalBorder: BlockBorder(color: .clear, width: 0),
    highlightedHeightScale: 0,
    highlightedWidthScale: 0,
    disappearingHeightScale: 0,
    disappearingWidthScale: 0,
    pageSize: .zero,
    highlightedPageCornerRadius: 0,
    pageCornerRadius: 0,
    animation: .scale,
    itemPlacement: .fixed(spaceBetweenCenters: 0)
  ) {
    didSet {
      _cachedParams = nil
    }
  }

  private var _cachedParams: PageIndicatorLayerParams?
  private var params: PageIndicatorLayerParams {
    if let value = _cachedParams { return value }
    let result = PageIndicatorLayerParams(
      numberOfPages: numberOfPages,
      itemPlacement: configuration.itemPlacement,
      position: currentIndexPosition,
      boundsSize: bounds.size
    )
    _cachedParams = result
    return result
  }

  override init() {
    super.init()
  }

  override init(layer: Any) {
    super.init(layer: layer)

    guard let pageIndicatorLayer = layer as? ScrollPageIndicatorLayer else {
      assertionFailure("unknown layer inside init(layer: Any)")
      return
    }

    numberOfPages = pageIndicatorLayer.numberOfPages
    currentIndexPosition = pageIndicatorLayer.currentIndexPosition
    configuration = pageIndicatorLayer.configuration
  }

  @available(*, unavailable)
  required init?(coder _: NSCoder) {
    fatalError("init(coder:) has not been implemented")
  }

  override func draw(in ctx: CGContext) {
    ctx.clip(to: params.visibleRect)

    guard numberOfPages > 1 else {
      return
    }

    let animator = IndicatorStateAnimator(
      configuration: configuration,
      boundsWidth: bounds.size.width,
      numberOfPages: numberOfPages
    )

    for index in params.renderRange {
      let state = IndicatorState(
        index: index,
        currentPosition: currentIndexPosition,
        params: params,
        numberOfPages: numberOfPages
      )
      makeInactiveIndicator(for: state, animator: animator).render(in: ctx)
    }
    makeActiveIndicator(animator: animator)?.render(in: ctx)
  }

  override func action(forKey key: String) -> CAAction? {
    guard Self.isAnimationKeySupported(key),
          animation(forKey: key) == nil,
          let presentation = presentation(),
          let fromValue = presentation.value(forKey: key) else {
      return super.action(forKey: key)
    }

    return ScrollPageIndicatorTransitionAction(
      fromValue: fromValue,
      numberOfPages: numberOfPages
    )
  }

  override class func needsDisplay(forKey key: String) -> Bool {
    guard isAnimationKeySupported(key) else {
      return super.needsDisplay(forKey: key)
    }

    return true
  }

  private class func isAnimationKeySupported(_ key: String) -> Bool {
    key == #keyPath(currentIndexPosition)
  }
}

extension ScrollPageIndicatorLayer {
  fileprivate func makeInactiveIndicator(
    for state: IndicatorState,
    animator: IndicatorStateAnimator
  ) -> Indicator {
    let rect = indicatorRect(at: state.index)
    let border = animator.indicatorBorder(for: state)
    let scale = indicatorScale(
      for: state,
      animator: animator,
      borderScale: (
        (rect.width - border.width) / rect.width,
        (rect.height - border.width) / rect.height
      )
    )
    let indicator = Indicator(
      rect: rect.withScaledSize(x: scale.x, y: scale.y),
      cornerRadius: configuration
        .highlightedPageCornerRadius ?? (configuration.pageCornerRadius * scale.y),
      color: animator.indicatorColor(for: state).cgColor,
      borderColor: animator.indicatorBorder(for: state).color.cgColor,
      borderWidth: animator.indicatorBorder(for: state).width
    )

    return indicator
  }

  fileprivate func makeActiveIndicator(animator: IndicatorStateAnimator) -> Indicator? {
    let roundedIndex = currentIndexPosition.rounded(.down)
    let progress = currentIndexPosition - roundedIndex

    guard let offsets = animator.activeIndicatorOffsets(progress: progress) else {
      return nil
    }

    var rect = indicatorRect(at: Int(roundedIndex))
    rect.size.width += offsets.widthOffset
    rect.center.x += offsets.xOffset
    let indicator = Indicator(
      rect: rect,
      cornerRadius: configuration.pageCornerRadius,
      color: configuration.highlightedColor.cgColor,
      borderColor: configuration.highlightedBorder.color.cgColor,
      borderWidth: configuration.highlightedBorder.width
    )

    return indicator
  }

  fileprivate func indicatorRect(at index: Int) -> CGRect {
    let xPosition = CGFloat(index - params.head) + 0.5
    let x = xPosition * params.itemWidth + params.offsetX
    let y = params.visibleRect.center.y
    let center = CGPoint(x: x, y: y)
    let width: CGFloat = if case let .stretch(spacing, _) = configuration.itemPlacement {
      params.itemWidth - spacing
    } else {
      configuration.pageSize.width
    }
    return CGRect(center: center, size: CGSize(width: width, height: configuration.pageSize.height))
  }

  fileprivate func indicatorScale(
    for state: IndicatorState,
    animator: IndicatorStateAnimator,
    borderScale: Scale
  ) -> Scale {
    switch state.kind {
    case .normal:
      (
        configuration.disappearingWidthScale
          .interpolated(to: 1, progress: state.progress) * borderScale.x,
        configuration.disappearingHeightScale
          .interpolated(to: 1, progress: state.progress) * borderScale.y
      )
    case .highlighted:
      animator.highlightedIndicatorScale(
        for: state,
        borderScale: (borderScale.x, borderScale.y)
      )
    }
  }
}

private struct Indicator {
  let rect: CGRect
  let cornerRadius: CGFloat
  let color: CGColor
  let borderColor: CGColor
  let borderWidth: CGFloat

  func render(in ctx: CGContext) {
    let path = CGPath(
      roundedRect: rect,
      cornerWidth: min(cornerRadius, rect.width / 2),
      cornerHeight: min(cornerRadius, rect.height / 2),
      transform: nil
    )
    ctx.addPath(path)
    ctx.setLineWidth(borderWidth)
    ctx.setStrokeColor(borderColor)
    ctx.setFillColor(color)
    ctx.closePath()
    ctx.drawPath(using: .fillStroke)
  }
}

extension ScrollPageIndicatorLayer {
  private class ScrollPageIndicatorTransitionAction: CAAction {
    let fromValue: Any
    let numberOfPages: Int

    init(fromValue: Any, numberOfPages: Int) {
      self.fromValue = fromValue
      self.numberOfPages = numberOfPages
    }

    func run(forKey event: String, object anObject: Any, arguments _: [AnyHashable: Any]?) {
      guard let layer = anObject as? CALayer,
            let fromValue = self.fromValue as? Double,
            let toValue = layer.value(forKey: event) as? Int,
            toValue != numberOfPages else {
        return
      }

      let lastPage = numberOfPages - 1
      let correctedFromValue = if fromValue.isApproximatelyLessOrEqualThan(0), toValue == lastPage {
        Double(numberOfPages)
      } else if fromValue.isApproximatelyGreaterOrEqualThan(Double(lastPage)), toValue == 0 {
        Double(-1)
      } else {
        fromValue
      }

      let animation = CABasicAnimation(keyPath: event)
      animation.fromValue = correctedFromValue
      animation.toValue = toValue

      layer.add(animation, forKey: event)
    }
  }
}
#endif

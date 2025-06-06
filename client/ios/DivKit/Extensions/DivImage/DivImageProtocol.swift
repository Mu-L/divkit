import Foundation
import LayoutKit
import VGSL

protocol DivImageProtocol: DivBase, DivImageContentMode {
  var aspect: DivAspect? { get }

  func resolvePreview(_ expressionResolver: ExpressionResolver) -> String?
  func resolvePlaceholderColor(_ expressionResolver: ExpressionResolver) -> Color
}

extension DivImageProtocol {
  func resolveHeight(_ context: DivBlockModelingContext) -> ImageBlockHeight {
    if let aspect {
      return .ratio(aspect.resolveRatio(context.expressionResolver) ?? 0)
    }
    return .trait(resolveContentHeightTrait(context))
  }

  func resolvePlaceholder(
    _ expressionResolver: ExpressionResolver,
    highPriority: Bool = false
  ) -> ImagePlaceholder {
    if let base64 = resolvePreview(expressionResolver) {
      .imageData(ImageData(base64: base64, highPriority: highPriority))
    } else {
      .color(resolvePlaceholderColor(expressionResolver))
    }
  }

  private var typeName: String {
    guard let typeName = String(describing: self).split(separator: ".").last else {
      return "DivImage"
    }
    return String(typeName)
  }
}

@testable import DivKit
import DivKitTestsSupport
@testable import LayoutKit
import XCTest

final class DivDataExtensionsTests: XCTestCase {
  func test_WhenStateIsNil_TakesFirstState() throws {
    let block = try data
      .makeBlock(context: .default)
      .withoutStateBlock()
    let expectedBlock = try data.states[0].div.value
      .makeBlock(context: .default.modifying(pathSuffix: "0"))
    XCTAssertTrue(block == expectedBlock)
  }

  func test_WhenStateIsPresent_TakesCorrespondingState() throws {
    let context = DivBlockModelingContext()
    context.stateManager.setStateWithHistory(
      path: DivData.rootPath,
      stateID: DivStateID(rawValue: "1")
    )
    let block = try data
      .makeBlock(context: context)
      .withoutStateBlock()
    let expectedBlock = try data.states[1].div.value
      .makeBlock(context: .default.modifying(pathSuffix: "1"))
    XCTAssertTrue(block == expectedBlock)
  }

  func test_WhenStateIsNotFound_TakesFirstState() throws {
    let context = DivBlockModelingContext()
    context.stateManager.setStateWithHistory(
      path: DivData.rootPath,
      stateID: DivStateID(rawValue: "100500")
    )
    let block = try data
      .makeBlock(context: context)
      .withoutStateBlock()
    let expectedBlock = try data.states[0].div.value
      .makeBlock(context: .default.modifying(pathSuffix: "0"))
    XCTAssertTrue(block == expectedBlock)
  }

  func test_ActionPathContainsCardLogId() throws {
    let block = try divData(
      divText(
        actions: [divAction(logId: "action_log_id")],
        text: "0"
      ),
      logId: "card_log_id"
    )
    .makeBlock(context: .default)
    .withoutStateBlock()

    XCTAssertEqual(block.actions?.first.path, UIElementPath("card_log_id") + "action_log_id")
  }

  func test_GalleryPathContainsRoot() throws {
    let block = try divData(
      divGallery(
        items: [
          divText(
            text: "0",
            width: fixedSize(10)
          ),
        ]
      )
    )
    .makeBlock(context: .default)
    .withoutStateBlock()
    let galleryBlock = block.child as! GalleryBlock

    XCTAssertEqual(galleryBlock.model.path, UIElementPath.root + 0 + "gallery")
  }

  func test_WhenStateChanges_ReportsVisibilityForNewState() throws {
    let timerScheduler = TestTimerScheduler()
    let context = DivBlockModelingContext(scheduler: timerScheduler)

    for rootStateId in [nil, "1", "0", "1"] {
      if let rootStateId {
        context.stateManager.setStateWithHistory(
          path: DivData.rootPath,
          stateID: DivStateID(rawValue: rootStateId)
        )
      }

      let block = try makeBlock(fromFile: "root_states_visibility", context: context)

      let rect = CGRect(
        origin: .zero,
        size: CGSize(width: 100, height: block.intrinsicContentHeight(forWidth: 100))
      )
      let view = block.makeBlockView()
      XCTAssertEqual(
        getViewVisibilityCallCount(view: view, rect: rect, timerScheduler: timerScheduler),
        1
      )
    }
  }
}

private let data = divData(
  states: [
    .init(div: divText(text: "0"), stateId: 0),
    .init(div: divText(text: "1"), stateId: 1),
    .init(div: divText(text: "2"), stateId: 2),
  ]
)

extension Block {
  fileprivate func withoutStateBlock() throws -> DecoratingBlock {
    let stateBlock = try XCTUnwrap(self as? StateBlock)
    return try XCTUnwrap(stateBlock.child as? DecoratingBlock)
  }
}

private func makeBlock(
  fromFile filename: String,
  context: DivBlockModelingContext = .default
) throws -> Block {
  try DivDataTemplate.make(
    fromFile: filename,
    subdirectory: "div-data",
    context: context
  )
}

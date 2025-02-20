import Foundation
import VGSL

final class DivTimerController {
  typealias RunActions = ([DivAction]) -> Void
  typealias UpdateCard = () -> Void

  enum State {
    case started
    case stopped
    case paused
  }

  private let cardId: DivCardID
  private let divTimer: DivTimer
  private let timerScheduler: Scheduling
  private let timeMeasuring: TimeMeasuring
  private let runActions: RunActions
  private let updateCard: UpdateCard
  private let variablesStorage: DivVariablesStorage
  private let functionsStorage: DivFunctionsStorage
  private let persistentValuesStorage: DivPersistentValuesStorage
  private let reporter: DivReporter

  private(set) var state: State = .stopped

  private var savedDuration: TimeInterval?
  private var savedInterval: TimeInterval?
  private var tickTimer: TimerType?
  private var endTimer: TimerType?
  private var expectedTickCount: Int = 0
  private var actualTickCount: Int = 0
  private var tickBeforeEndIsNeeded = false

  init(
    cardId: DivCardID,
    divTimer: DivTimer,
    timerScheduler: Scheduling,
    timeMeasuring: TimeMeasuring,
    runActions: @escaping RunActions,
    updateCard: @escaping UpdateCard,
    variablesStorage: DivVariablesStorage,
    functionsStorage: DivFunctionsStorage,
    persistentValuesStorage: DivPersistentValuesStorage,
    reporter: DivReporter
  ) {
    self.cardId = cardId
    self.divTimer = divTimer
    self.timerScheduler = timerScheduler
    self.timeMeasuring = timeMeasuring
    self.runActions = runActions
    self.updateCard = updateCard
    self.variablesStorage = variablesStorage
    self.functionsStorage = functionsStorage
    self.persistentValuesStorage = persistentValuesStorage
    self.reporter = reporter
  }

  deinit {
    cancel()
  }

  func start() {
    if state != .stopped {
      DivKitLogger.error("Timer '\(divTimer.id)' can't start because it has state '\(state)'.")
      return
    }
    let expressionResolver = ExpressionResolver(
      path: cardId.path,
      variablesStorage: variablesStorage,
      functionsStorage: functionsStorage,
      persistentValuesStorage: persistentValuesStorage,
      reporter: reporter
    )
    guard divTimer.parametersAreValid(expressionResolver) else {
      DivKitLogger.error("Timer '\(divTimer.id)' is not valid.")
      return
    }
    state = .started
    if let duration = divTimer.getDuration(expressionResolver),
       let tickInterval = divTimer.getTickInterval(expressionResolver) {
      expectedTickCount = duration / tickInterval
      tickBeforeEndIsNeeded = duration % tickInterval == 0
    } else {
      expectedTickCount = 0
      tickBeforeEndIsNeeded = false
    }
    savedDuration = divTimer.getDuration(expressionResolver)?.toTimeInterval
    savedInterval = divTimer.getTickInterval(expressionResolver)?.toTimeInterval
    actualTickCount = 0
    setVariable(0)
    timeMeasuring.start()
    makeTickTimer()
    makeEndTimer()
  }

  func stop() {
    if state == .stopped {
      DivKitLogger.error("Timer '\(divTimer.id)' already stopped.")
      return
    }
    state = .stopped
    invalidateTimers()
    if let endActions = divTimer.endActions {
      runActions(endActions)
    }
    updateCard()
  }

  func pause() {
    if state != .started {
      DivKitLogger.error("Timer '\(divTimer.id)' can't pause because it has state '\(state)'.")
      return
    }
    state = .paused
    timeMeasuring.pause()
    invalidateTimers()
  }

  func resume() {
    if state != .paused {
      DivKitLogger.error("Timer '\(divTimer.id)' can't resume because it has state '\(state)'.")
      return
    }
    state = .started
    onTick()
    makeRemainderTickTimer()
    makeEndTimer()
    timeMeasuring.resume()
  }

  func cancel() {
    state = .stopped
    invalidateTimers()
  }

  func reset() {
    cancel()
    start()
  }

  private func makeEndTimer() {
    guard let duration = savedDuration else {
      return
    }
    let delay = duration - timeMeasuring.passedInterval()
    guard delay > 0 else {
      return
    }
    endTimer = timerScheduler.makeTimer(
      delay: delay,
      handler: { [weak self] in
        self?.onEnd(duration: duration)
      }
    )
  }

  private func makeRemainderTickTimer() {
    guard let tickInterval = savedInterval, tickInterval > 0 else {
      return
    }
    let remainder = timeMeasuring.passedInterval().truncatingRemainder(dividingBy: tickInterval)
    guard remainder > ACCURACY else {
      makeTickTimer()
      return
    }
    tickTimer = timerScheduler.makeTimer(
      delay: tickInterval - remainder,
      handler: { [weak self] in
        self?.makeTickTimer()
        self?.onTick()
      }
    )
  }

  private func makeTickTimer() {
    guard let tickInterval = savedInterval, tickInterval > 0 else {
      return
    }
    tickTimer = timerScheduler.makeRepeatingTimer(
      interval: tickInterval,
      handler: { [weak self] in
        self?.onTick()
      }
    )
  }

  private func onTick() {
    guard state == .started else {
      return
    }
    let passedInterval = timeMeasuring.passedInterval()
    if let duration = savedDuration, passedInterval >= duration {
      return
    }
    actualTickCount += 1
    setVariable(passedInterval)
    if let tickActions = divTimer.tickActions {
      runActions(tickActions)
    }
    updateCard()
  }

  private func onEnd(duration: TimeInterval) {
    guard state == .started else {
      return
    }
    setVariable(duration)
    if tickBeforeEndIsNeeded, actualTickCount < expectedTickCount {
      if let tickActions = divTimer.tickActions {
        runActions(tickActions)
      }
    }
    stop()
  }

  private func setVariable(_ value: TimeInterval) {
    if let variableName = divTimer.valueVariable {
      variablesStorage.update(
        cardId: cardId,
        name: DivVariableName(rawValue: variableName),
        value: String(format: "%.0f", value * 1000)
      )
    }
  }

  private func invalidateTimers() {
    tickTimer?.invalidate()
    endTimer?.invalidate()
    tickTimer = nil
    endTimer = nil
  }
}

extension DivTimer {
  fileprivate func getDuration(_ expressionResolver: ExpressionResolver) -> Int? {
    let duration = resolveDuration(expressionResolver)
    guard duration > 0 else {
      return nil
    }
    return duration
  }

  fileprivate func getTickInterval(_ expressionResolver: ExpressionResolver) -> Int? {
    guard let tickInterval = resolveTickInterval(expressionResolver),
          tickInterval > 0 else {
      return nil
    }
    return tickInterval
  }

  fileprivate func parametersAreValid(_ expressionResolver: ExpressionResolver) -> Bool {
    let duration = resolveDuration(expressionResolver)
    if duration > 0 {
      return true
    }
    guard let tickInterval = resolveTickInterval(expressionResolver), tickInterval > 0 else {
      DivKitLogger.error("Timer '\(self.id)' parameters is not valid: tick_interval not set")
      return false
    }
    if tickActions == nil, valueVariable == nil {
      DivKitLogger.error(
        "Timer '\(self.id)' parameters is not valid: set tickActions or valueVariable"
      )
      return false
    }
    return true
  }
}

extension Int {
  var toTimeInterval: TimeInterval {
    TimeInterval(Double(self) / 1000)
  }
}

private let ACCURACY = 0.01

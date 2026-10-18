package elevator.dispatch

import elevator.components.Direction
import elevator.observer.ElevatorObserver

class ElevatorDispatchController : ElevatorObserver {
    override fun update(floor: Int, direction: Direction) {
        // Logic to handle floor request
    }
}

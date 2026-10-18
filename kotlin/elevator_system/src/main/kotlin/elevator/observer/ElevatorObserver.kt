package elevator.observer

import elevator.components.Direction

interface ElevatorObserver {
    fun update(floor: Int, direction: Direction)
}

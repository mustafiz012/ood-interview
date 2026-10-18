package elevator.components

import java.util.LinkedList
import java.util.Queue

class ElevatorCar(startingFloor: Int) {
    var status: ElevatorStatus = ElevatorStatus(startingFloor, Direction.IDLE)
        private set
    val targetFloors: Queue<Int> = LinkedList()

    fun addFloorRequest(floor: Int) {
        if (!targetFloors.contains(floor)) {
            targetFloors.offer(floor)
            updateDirection(floor)
        }
    }

    val currentFloor: Int
        get() = status.currentFloor

    val currentDirection: Direction
        get() = status.currentDirection

    fun isIdle(): Boolean = targetFloors.isEmpty()

    private fun updateDirection(targetFloor: Int) {
        if (status.currentFloor < targetFloor) {
            status = ElevatorStatus(status.currentFloor, Direction.UP)
        } else if (status.currentFloor > targetFloor) {
            status = ElevatorStatus(status.currentFloor, Direction.DOWN)
        }
    }

    fun moveOneStep() {
        if (targetFloors.isEmpty()) return

        val nextFloor = targetFloors.peek() ?: return
        if (status.currentFloor < nextFloor) {
            status = ElevatorStatus(status.currentFloor + 1, Direction.UP)
        } else if (status.currentFloor > nextFloor) {
            status = ElevatorStatus(status.currentFloor - 1, Direction.DOWN)
        } else {
            targetFloors.poll()
            if (targetFloors.isEmpty()) {
                status = ElevatorStatus(status.currentFloor, Direction.IDLE)
            }
        }
    }

    fun isAtDestination(): Boolean {
        return !targetFloors.isEmpty() && status.currentFloor == targetFloors.peek()
    }

    fun nextDestination() {
        if (isAtDestination()) {
            targetFloors.poll()
        }
    }

    fun moveUntilNextFloor() {
        if (!targetFloors.isEmpty()) {
            val nextFloor = targetFloors.peek()
            while (status.currentFloor != nextFloor) {
                moveOneStep()
            }
        }
    }
}

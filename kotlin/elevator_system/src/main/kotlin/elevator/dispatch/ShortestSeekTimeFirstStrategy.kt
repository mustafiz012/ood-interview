package elevator.dispatch

import elevator.components.Direction
import elevator.components.ElevatorCar
import kotlin.math.abs

class ShortestSeekTimeFirstStrategy : DispatchingStrategy {
    override fun selectElevator(elevators: List<ElevatorCar>, floor: Int, direction: Direction): ElevatorCar? {
        var bestElevator: ElevatorCar? = null
        var shortestDistance = Int.MAX_VALUE

        for (elevator in elevators) {
            val distance = abs(elevator.currentFloor - floor)
            if ((elevator.isIdle() || elevator.currentDirection == direction) && distance < shortestDistance) {
                bestElevator = elevator
                shortestDistance = distance
            }
        }

        return bestElevator
    }
}

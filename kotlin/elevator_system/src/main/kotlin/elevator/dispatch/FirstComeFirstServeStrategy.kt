package elevator.dispatch

import elevator.components.Direction
import elevator.components.ElevatorCar
import java.util.Random

class FirstComeFirstServeStrategy : DispatchingStrategy {
    override fun selectElevator(elevators: List<ElevatorCar>, floor: Int, direction: Direction): ElevatorCar? {
        for (elevator in elevators) {
            if (elevator.isIdle() || elevator.currentDirection == direction) {
                return elevator
            }
        }
        return if (elevators.isNotEmpty()) elevators[Random().nextInt(elevators.size)] else null
    }
}

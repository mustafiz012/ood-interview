package elevator.dispatch

import elevator.components.Direction
import elevator.components.ElevatorCar

interface DispatchingStrategy {
    fun selectElevator(elevators: List<ElevatorCar>, floor: Int, direction: Direction): ElevatorCar?
}

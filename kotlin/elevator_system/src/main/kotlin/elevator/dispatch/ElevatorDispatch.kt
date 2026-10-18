package elevator.dispatch

import elevator.components.Direction
import elevator.components.ElevatorCar

class ElevatorDispatch(private val strategy: DispatchingStrategy) {
    fun dispatchElevatorCar(floor: Int, direction: Direction, elevators: List<ElevatorCar>) {
        val selectedElevator = strategy.selectElevator(elevators, floor, direction)
        selectedElevator?.addFloorRequest(floor)
    }
}

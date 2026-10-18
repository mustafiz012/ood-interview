package elevator

import elevator.components.Direction
import elevator.components.ElevatorCar
import elevator.components.ElevatorStatus
import elevator.dispatch.DispatchingStrategy
import elevator.dispatch.ElevatorDispatch

class ElevatorSystem(
    val elevators: List<ElevatorCar>,
    strategy: DispatchingStrategy
) {
    private val dispatchController: ElevatorDispatch = ElevatorDispatch(strategy)

    fun getAllElevatorStatuses(): List<ElevatorStatus> {
        return elevators.map { it.status }
    }

    fun requestElevator(currentFloor: Int, direction: Direction) {
        dispatchController.dispatchElevatorCar(currentFloor, direction, elevators)
    }

    fun selectFloor(car: ElevatorCar, destinationFloor: Int) {
        car.addFloorRequest(destinationFloor)
    }
}

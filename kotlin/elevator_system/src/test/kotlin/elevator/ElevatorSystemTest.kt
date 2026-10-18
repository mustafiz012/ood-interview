package elevator

import elevator.components.Direction
import elevator.components.ElevatorCar
import elevator.components.ElevatorStatus
import elevator.dispatch.FirstComeFirstServeStrategy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ElevatorSystemTest {

    @Test
    fun testElevatorFeature() {
        println("\n=== Testing Elevator System Features ===")

        val car = ElevatorCar(1)
        val elevatorSystem = ElevatorSystem(listOf(car), FirstComeFirstServeStrategy())
        println("✓ Elevator system initialized with 1 elevator car starting at floor 1")
        println("✓ Using First-Come-First-Serve dispatching strategy")

        println("\n--- Initial State Check ---")
        var elevators: List<ElevatorStatus> = elevatorSystem.getAllElevatorStatuses()
        assertEquals(1, elevators[0].currentFloor)
        assertEquals(Direction.IDLE, elevators[0].currentDirection)
        println("✓ Elevator is at floor 1 and in IDLE state (ready for requests)")

        println("\n--- Requesting Elevator from Floor 3 (Going UP) ---")
        elevatorSystem.requestElevator(3, Direction.UP)
        println("✓ Elevator requested from floor 3 going UP")

        elevators = elevatorSystem.getAllElevatorStatuses()
        assertEquals(1, elevators[0].currentFloor)
        assertEquals(Direction.UP, elevators[0].currentDirection)
        assertFalse(car.isAtDestination())
        println("✓ Elevator direction changed to UP (preparing to move)")

        println("\n--- Elevator Movement: Step 1 ---")
        car.moveOneStep()
        elevators = elevatorSystem.getAllElevatorStatuses()
        assertEquals(2, elevators[0].currentFloor)
        assertEquals(Direction.UP, elevators[0].currentDirection)
        assertFalse(car.isAtDestination())
        println("✓ Elevator moved from floor 1 → floor 2")
        println("✓ Still moving UP toward destination (floor 3)")

        println("\n--- Elevator Movement: Step 2 ---")
        car.moveOneStep()
        elevators = elevatorSystem.getAllElevatorStatuses()
        assertEquals(3, elevators[0].currentFloor)
        assertEquals(Direction.UP, elevators[0].currentDirection)
        assertTrue(car.isAtDestination())
        println("✓ Elevator moved from floor 2 → floor 3")
        println("✓ Reached destination floor 3")

        println("\n--- Arriving at Destination ---")
        car.moveOneStep()
        elevators = elevatorSystem.getAllElevatorStatuses()
        assertEquals(3, elevators[0].currentFloor)
        assertEquals(Direction.IDLE, elevators[0].currentDirection)
        assertFalse(car.isAtDestination())
        println("✓ Elevator stopped at floor 3")
        println("✓ Direction changed back to IDLE (ready for next request)")

        println("\n--- Multiple Floor Requests ---")
        elevatorSystem.requestElevator(8, Direction.DOWN)
        elevatorSystem.requestElevator(6, Direction.DOWN)
        println("✓ New requests: Floor 8 (DOWN) and Floor 6 (DOWN)")
        println("✓ Elevator will handle requests in order (First-Come-First-Serve)")

        println("\n--- Moving to Floor 8 (First Request) ---")
        car.moveUntilNextFloor()
        elevators = elevatorSystem.getAllElevatorStatuses()
        assertEquals(8, elevators[0].currentFloor)
        assertEquals(Direction.UP, elevators[0].currentDirection)
        assertTrue(car.isAtDestination())
        println("✓ Elevator moved directly to floor 8 (first request)")
        println("✓ Direction is UP (moving upward to reach floor 8)")

        println("\n--- Moving to Floor 6 (Second Request) ---")
        car.nextDestination()
        car.moveUntilNextFloor()
        elevators = elevatorSystem.getAllElevatorStatuses()
        assertEquals(6, elevators[0].currentFloor)
        assertEquals(Direction.DOWN, elevators[0].currentDirection)
        assertTrue(car.isAtDestination())
        println("✓ Elevator moved from floor 8 → floor 6 (second request)")
        println("✓ Direction is DOWN (moving downward from floor 8 to floor 6)")
        println("✓ All requests completed successfully")
        println("=== Elevator System Test Completed Successfully ===\n")
    }
}

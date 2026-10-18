package parkinglot

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import parkinglot.fare.BaseFareStrategy
import parkinglot.fare.FareCalculator
import parkinglot.fare.FareStrategy
import parkinglot.fare.PeakHoursFareStrategy
import parkinglot.spot.ParkingManager
import parkinglot.spot.ParkingSpot
import parkinglot.spot.RegularSpot
import parkinglot.vehicle.Car
import parkinglot.vehicle.Vehicle
import parkinglot.vehicle.VehicleSize

class ParkingLotTest {
    @Test
    fun testVehicleJourney() {
        println("\n=== Testing Parking Lot System: Complete Vehicle Journey ===")

        println("\n--- Setting Up Parking Spots ---")
        val availableSpots = mutableMapOf<VehicleSize, MutableList<ParkingSpot>>()
        availableSpots[VehicleSize.MEDIUM] = mutableListOf(RegularSpot(1), RegularSpot(2))
        println("✓ Created 2 regular parking spots for medium-sized vehicles")
        println("  - Spot 1: Regular parking spot")
        println("  - Spot 2: Regular parking spot")

        println("\n--- Initializing Parking Manager ---")
        val parkingManager = ParkingManager(availableSpots)
        println("✓ Parking manager initialized with available spots")

        println("\n--- Setting Up Fare Calculation System ---")
        val strategies: List<FareStrategy> = listOf(BaseFareStrategy(), PeakHoursFareStrategy())
        val fareCalculator = FareCalculator(strategies)
        println("✓ Fare calculator initialized with multiple strategies:")
        println("  - Base fare strategy")
        println("  - Peak hours fare strategy")

        val parkingLot = ParkingLot(parkingManager, fareCalculator)

        println("\n--- Creating Test Vehicle ---")
        val car: Vehicle = Car("ABC123")
        println("✓ Created car with license plate: ABC123")
        println("  - Vehicle type: Car (MEDIUM size)")

        println("\n--- Vehicle Entering Parking Lot ---")
        val ticket = parkingLot.enterVehicle(car)
        println("✓ Ticket generated for vehicle ABC123")
        println("✓ Parking spot assigned: " + ticket?.parkingSpot?.spotNumber)
        assertNotNull(ticket, "Ticket should not be null")
        assertEquals(car, ticket?.vehicle, "Vehicle should match the one that entered")
        assertNotNull(ticket?.parkingSpot, "Parking spot should not be null")
        println("✓ Ticket validation passed:")
        println("  - Ticket is not null")
        println("  - Vehicle matches the one that entered")
        println("  - Parking spot assigned successfully")

        // Find the vehicle in the parking lot
        val foundSpot = parkingManager.findVehicleSpot(car)
        assertNotNull(foundSpot, "Vehicle should be found in the parking lot")
        assertEquals(ticket?.parkingSpot, foundSpot, "Found spot should match the ticket's spot")

        println("\n--- Vehicle Leaving Parking Lot ---")
        parkingLot.leaveVehicle(ticket)
        assertNotNull(ticket?.exitTime, "Exit time should be set")
        assertTrue(foundSpot?.isAvailable == true, "Parking spot should be available after vehicle leaves")
        println("✓ Vehicle exit verification passed:")
        println("  - Exit time recorded on ticket")
        println("  - Parking spot is now available for other vehicles")
        println("=== Parking Lot Vehicle Journey Test Completed Successfully ===\n")
    }
}

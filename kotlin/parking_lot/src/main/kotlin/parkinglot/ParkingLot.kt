package parkinglot

import parkinglot.fare.FareCalculator
import parkinglot.fare.Ticket
import parkinglot.spot.ParkingManager
import parkinglot.vehicle.Vehicle
import java.time.LocalDateTime

class ParkingLot(
    private val parkingManager: ParkingManager,
    private val fareCalculator: FareCalculator
) {
    fun enterVehicle(vehicle: Vehicle): Ticket? {
        val spot = parkingManager.parkVehicle(vehicle) ?: return null
        return Ticket(generateTicketId(), vehicle, spot, LocalDateTime.now())
    }

    fun leaveVehicle(ticket: Ticket?) {
        if (ticket != null && ticket.exitTime == null) {
            ticket.exitTime = LocalDateTime.now()
            parkingManager.unparkVehicle(ticket.vehicle)
            fareCalculator.calculateFare(ticket)
        }
    }

    private fun generateTicketId(): String {
        return "TICKET-" + System.currentTimeMillis()
    }
}

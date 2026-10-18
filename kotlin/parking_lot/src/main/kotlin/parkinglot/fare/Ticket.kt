package parkinglot.fare

import parkinglot.spot.ParkingSpot
import parkinglot.vehicle.Vehicle
import java.math.BigDecimal
import java.time.Duration
import java.time.LocalDateTime

class Ticket(
    val ticketId: String,
    val vehicle: Vehicle,
    val parkingSpot: ParkingSpot,
    val entryTime: LocalDateTime,
    var exitTime: LocalDateTime? = null
) {
    fun calculateParkingDuration(): BigDecimal {
        val end = exitTime ?: LocalDateTime.now()
        return BigDecimal(Duration.between(entryTime, end).toMinutes())
    }
}

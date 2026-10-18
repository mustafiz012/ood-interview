package parkinglot.fare

import parkinglot.vehicle.VehicleSize
import java.math.BigDecimal

class BaseFareStrategy : FareStrategy {
    override fun calculateFare(ticket: Ticket, inputFare: BigDecimal): BigDecimal {
        val rate = when (ticket.vehicle.size) {
            VehicleSize.MEDIUM -> MEDIUM_VEHICLE_RATE
            VehicleSize.LARGE -> LARGE_VEHICLE_RATE
            VehicleSize.SMALL -> SMALL_VEHICLE_RATE
        }
        return inputFare.add(rate.multiply(ticket.calculateParkingDuration()))
    }

    companion object {
        private val SMALL_VEHICLE_RATE = BigDecimal("1.0")
        private val MEDIUM_VEHICLE_RATE = BigDecimal("2.0")
        private val LARGE_VEHICLE_RATE = BigDecimal("3.0")
    }
}

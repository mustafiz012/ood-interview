package parkinglot.fare

import java.math.BigDecimal
import java.time.LocalDateTime

class PeakHoursFareStrategy : FareStrategy {
    override fun calculateFare(ticket: Ticket, inputFare: BigDecimal): BigDecimal {
        var fare = inputFare
        if (isPeakHours(ticket.entryTime)) {
            fare = fare.multiply(PEAK_HOURS_MULTIPLIER)
        }
        return fare
    }

    private fun isPeakHours(time: LocalDateTime): Boolean {
        val hour = time.hour
        return (hour in 7..10) || (hour in 16..19)
    }

    companion object {
        private val PEAK_HOURS_MULTIPLIER = BigDecimal("1.5")
    }
}

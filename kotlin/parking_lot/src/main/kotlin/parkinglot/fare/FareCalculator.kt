package parkinglot.fare

import java.math.BigDecimal

class FareCalculator(private val fareStrategies: List<FareStrategy>) {
    fun calculateFare(ticket: Ticket): BigDecimal {
        var fare = BigDecimal.ZERO
        for (strategy in fareStrategies) {
            fare = strategy.calculateFare(ticket, fare)
        }
        return fare
    }
}

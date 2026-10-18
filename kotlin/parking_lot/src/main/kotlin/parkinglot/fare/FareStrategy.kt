package parkinglot.fare

import java.math.BigDecimal

interface FareStrategy {
    fun calculateFare(ticket: Ticket, inputFare: BigDecimal): BigDecimal
}

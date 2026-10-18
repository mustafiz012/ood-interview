package movie_ticket.rate

import java.math.BigDecimal

interface PricingStrategy {
    val price: BigDecimal
}

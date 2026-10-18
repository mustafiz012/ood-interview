package movie_ticket.rate

import java.math.BigDecimal

class NormalRate(override val price: BigDecimal) : PricingStrategy

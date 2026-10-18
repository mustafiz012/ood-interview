package movie_ticket.rate

import java.math.BigDecimal

class PremiumRate(override val price: BigDecimal) : PricingStrategy

package movie_ticket.rate

import java.math.BigDecimal

class VIPRate(override val price: BigDecimal) : PricingStrategy

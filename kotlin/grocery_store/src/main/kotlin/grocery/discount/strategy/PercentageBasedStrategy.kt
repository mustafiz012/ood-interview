package grocery.discount.strategy

import java.math.BigDecimal

class PercentageBasedStrategy(private val percentage: BigDecimal) : DiscountCalculationStrategy {
    override fun calculateDiscountedPrice(originalPrice: BigDecimal): BigDecimal {
        val multiplier = BigDecimal.ONE.subtract(percentage.divide(BigDecimal.valueOf(100)))
        return originalPrice.multiply(multiplier)
    }
}

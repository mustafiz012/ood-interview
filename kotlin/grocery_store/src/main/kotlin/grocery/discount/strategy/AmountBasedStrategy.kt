package grocery.discount.strategy

import java.math.BigDecimal

class AmountBasedStrategy(private val discountAmount: BigDecimal) : DiscountCalculationStrategy {
    override fun calculateDiscountedPrice(originalPrice: BigDecimal): BigDecimal {
        return originalPrice.subtract(discountAmount).max(BigDecimal.ZERO)
    }
}

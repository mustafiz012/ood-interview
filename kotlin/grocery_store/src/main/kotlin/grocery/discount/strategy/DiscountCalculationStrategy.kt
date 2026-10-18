package grocery.discount.strategy

import java.math.BigDecimal

fun interface DiscountCalculationStrategy {
    fun calculateDiscountedPrice(originalPrice: BigDecimal): BigDecimal
}

package grocery.discount

import grocery.Item
import grocery.OrderItem
import grocery.discount.criteria.DiscountCriteria
import grocery.discount.strategy.DiscountCalculationStrategy
import java.math.BigDecimal

class DiscountCampaign(
    val discountId: String,
    val name: String,
    val criteria: DiscountCriteria,
    val calculationStrategy: DiscountCalculationStrategy
) {
    fun isApplicable(item: Item): Boolean = criteria.isApplicable(item)

    fun calculateDiscount(item: OrderItem): BigDecimal {
        return calculationStrategy.calculateDiscountedPrice(item.calculatePrice())
    }
}

package grocery

import grocery.discount.DiscountCampaign
import java.math.BigDecimal

class OrderItem(
    val item: Item,
    val quantity: Int
) {
    fun calculatePrice(): BigDecimal {
        return item.price.multiply(BigDecimal.valueOf(quantity.toLong()))
    }

    fun calculatePriceWithDiscount(newDiscount: DiscountCampaign): BigDecimal {
        return newDiscount.calculateDiscount(this)
    }
}

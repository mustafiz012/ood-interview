package grocery

import grocery.discount.DiscountCampaign
import java.math.BigDecimal
import java.util.Collections
import java.util.UUID

class Order {
    val orderId: String = UUID.randomUUID().toString()
    private val items: MutableList<OrderItem> = mutableListOf()
    val appliedDiscounts: MutableMap<OrderItem, DiscountCampaign> = mutableMapOf()
    var paymentAmount: BigDecimal = BigDecimal.ZERO

    fun addItem(item: OrderItem) {
        items.add(item)
    }

    fun calculateSubtotal(): BigDecimal {
        return items.map { it.calculatePrice() }.fold(BigDecimal.ZERO, BigDecimal::add)
    }

    fun calculateTotal(): BigDecimal {
        return items.map { item ->
            val discount = appliedDiscounts[item]
            if (discount != null) item.calculatePriceWithDiscount(discount) else item.calculatePrice()
        }.fold(BigDecimal.ZERO, BigDecimal::add)
    }

    fun applyDiscount(item: OrderItem, discount: DiscountCampaign) {
        appliedDiscounts[item] = discount
    }

    fun getItems(): List<OrderItem> = Collections.unmodifiableList(items)

    fun setPayment(paymentAmount: BigDecimal) {
        this.paymentAmount = paymentAmount
    }

    fun calculateChange(): BigDecimal {
        return paymentAmount.subtract(calculateTotal())
    }
}

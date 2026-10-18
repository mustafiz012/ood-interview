package grocery

import grocery.discount.DiscountCampaign
import java.math.BigDecimal

class Checkout(private val activeDiscounts: List<DiscountCampaign>) {
    private var currentOrder: Order = Order()

    init {
        startNewOrder()
    }

    fun startNewOrder() {
        this.currentOrder = Order()
    }

    fun processPayment(paymentAmount: BigDecimal): BigDecimal {
        currentOrder.setPayment(paymentAmount)
        return currentOrder.calculateChange()
    }

    fun addItemToOrder(item: Item?, quantity: Int) {
        if (item == null) return
        val orderItem = OrderItem(item, quantity)
        currentOrder.addItem(orderItem)

        for (newDiscount in activeDiscounts) {
            if (newDiscount.isApplicable(item)) {
                val existingDiscount = currentOrder.appliedDiscounts[orderItem]
                if (existingDiscount != null) {
                    if (orderItem.calculatePriceWithDiscount(newDiscount) >
                        orderItem.calculatePriceWithDiscount(existingDiscount)
                    ) {
                        currentOrder.applyDiscount(orderItem, newDiscount)
                    }
                } else {
                    currentOrder.applyDiscount(orderItem, newDiscount)
                }
            }
        }
    }

    fun getReceipt(): Receipt = Receipt(currentOrder)

    fun getOrderTotal(): BigDecimal = currentOrder.calculateTotal()
}

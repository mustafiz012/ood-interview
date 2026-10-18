package grocery

import java.util.Date

class Receipt(val order: Order) {
    val receiptId: String = order.orderId
    val issueDate: Date = Date()

    fun printReceipt(): String {
        val receipt = StringBuilder("grocery.Receipt ID: $receiptId\n")
        receipt.append("Date: ").append(issueDate).append("\n")
        receipt.append("Items:\n")

        for (item in order.getItems()) {
            receipt.append("- ").append(item.item.name)
                .append(" x ").append(item.quantity)
                .append(" @ ").append(item.item.price)
            val discount = order.appliedDiscounts[item]
            if (discount == null) {
                receipt.append(" = ").append(item.calculatePrice()).append("\n")
            } else {
                receipt.append(" (").append(discount.name).append(")")
                receipt.append(" = ").append(item.calculatePriceWithDiscount(discount)).append("\n")
            }
        }

        receipt.append("Subtotal: ").append(order.calculateSubtotal()).append("\n")
        receipt.append("Total: ").append(order.calculateTotal()).append("\n")

        return receipt.toString()
    }
}

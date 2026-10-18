package vendingmachine

import java.math.BigDecimal

class PaymentProcessor {
    var currentBalance: BigDecimal = BigDecimal.ZERO
        private set

    fun addBalance(amount: BigDecimal) {
        currentBalance = currentBalance.add(amount)
    }

    fun charge(amount: BigDecimal) {
        currentBalance = currentBalance.subtract(amount)
    }

    fun returnChange(): BigDecimal {
        val change = currentBalance
        currentBalance = BigDecimal.ZERO
        return change
    }
}

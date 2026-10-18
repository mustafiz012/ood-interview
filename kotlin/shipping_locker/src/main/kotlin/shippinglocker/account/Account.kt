package shippinglocker.account

import java.math.BigDecimal

class Account(
    val accountId: String,
    val ownerName: String,
    val lockerPolicy: AccountLockerPolicy
) {
    var usageCharges: BigDecimal = BigDecimal("0.00")
        private set

    fun addUsageCharge(amount: BigDecimal) {
        usageCharges = usageCharges.add(amount)
    }
}

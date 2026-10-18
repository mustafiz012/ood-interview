package shippinglocker.account

data class AccountLockerPolicy(
    val freePeriodDays: Int,
    val maximumPeriodDays: Int
)

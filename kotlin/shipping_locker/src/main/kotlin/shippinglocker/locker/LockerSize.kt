package shippinglocker.locker

import java.math.BigDecimal

enum class LockerSize(
    val sizeName: String,
    val dailyCharge: BigDecimal,
    val width: BigDecimal,
    val height: BigDecimal,
    val depth: BigDecimal
) {
    SMALL("Small", BigDecimal("5.00"), BigDecimal("10.00"), BigDecimal("10.00"), BigDecimal("10.00")),
    MEDIUM("Medium", BigDecimal("10.00"), BigDecimal("20.00"), BigDecimal("20.00"), BigDecimal("20.00")),
    LARGE("Large", BigDecimal("15.00"), BigDecimal("30.00"), BigDecimal("30.00"), BigDecimal("30.00"))
}

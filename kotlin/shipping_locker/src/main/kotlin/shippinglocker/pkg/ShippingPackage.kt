package shippinglocker.pkg

import shippinglocker.account.Account
import shippinglocker.locker.LockerSize
import java.math.BigDecimal

interface ShippingPackage {
    val orderId: String
    val user: Account
    val width: BigDecimal
    val height: BigDecimal
    val depth: BigDecimal
    var status: ShippingStatus

    fun updateShippingStatus(status: ShippingStatus)
    fun getLockerSize(): LockerSize
}

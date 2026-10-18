package shippinglocker.pkg

import shippinglocker.account.Account
import shippinglocker.locker.LockerSize
import java.math.BigDecimal

class BasicShippingPackage(
    override val orderId: String,
    override val user: Account,
    override val width: BigDecimal,
    override val height: BigDecimal,
    override val depth: BigDecimal
) : ShippingPackage {
    override var status: ShippingStatus = ShippingStatus.CREATED

    override fun updateShippingStatus(status: ShippingStatus) {
        this.status = status
    }

    override fun getLockerSize(): LockerSize {
        for (size in LockerSize.values()) {
            if (size.width >= width && size.height >= height && size.depth >= depth) {
                return size
            }
        }
        throw PackageIncompatibleException("No locker size available for the package")
    }
}

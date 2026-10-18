package shippinglocker.locker

import shippinglocker.NotificationInterface
import shippinglocker.Site
import shippinglocker.account.Account
import shippinglocker.pkg.MaximumStoragePeriodExceededException
import shippinglocker.pkg.ShippingPackage
import shippinglocker.pkg.ShippingStatus
import java.math.BigDecimal
import java.util.Date

class LockerManager(
    private val site: Site,
    private val accounts: Map<String, Account>,
    private val notificationService: NotificationInterface
) {
    private val accessCodeMap: MutableMap<String, Locker> = mutableMapOf()

    fun assignPackage(pkg: ShippingPackage, date: Date): Locker? {
        val locker = site.placePackage(pkg, date)
        val code = locker.accessCode
        if (code != null) {
            accessCodeMap[code] = locker
            notificationService.sendNotification("Package assigned to locker$code", pkg.user)
        }
        return locker
    }

    fun pickUpPackage(accessCode: String): Locker? {
        val locker = accessCodeMap[accessCode]
        if (locker != null && locker.checkAccessCode(accessCode)) {
            try {
                val charge = locker.calculateStorageCharges()
                val pkg = locker.getPackage()
                locker.releaseLocker()
                pkg?.user?.addUsageCharge(charge)
                pkg?.updateShippingStatus(ShippingStatus.RETRIEVED)
                return locker
            } catch (e: MaximumStoragePeriodExceededException) {
                locker.releaseLocker()
                return locker
            }
        }
        return null
    }

    fun getAccount(testAccount: String): Account? {
        return accounts[testAccount]
    }
}

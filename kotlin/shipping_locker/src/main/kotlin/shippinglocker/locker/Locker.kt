package shippinglocker.locker

import shippinglocker.account.AccountLockerPolicy
import shippinglocker.pkg.MaximumStoragePeriodExceededException
import shippinglocker.pkg.ShippingPackage
import shippinglocker.pkg.ShippingStatus
import java.math.BigDecimal
import java.util.Date
import java.util.Random

class Locker(val size: LockerSize) {
    var packageStored: ShippingPackage? = null
    var assignmentDate: Date? = null
    var accessCode: String? = null
        private set

    fun assignPackage(pkg: ShippingPackage, date: Date) {
        this.packageStored = pkg
        this.assignmentDate = date
        this.accessCode = generateAccessCode()
    }

    fun releaseLocker() {
        this.packageStored = null
        this.assignmentDate = null
        this.accessCode = null
    }

    fun calculateStorageCharges(): BigDecimal {
        val currentPackage = packageStored ?: return BigDecimal.ZERO
        val assignDate = assignmentDate ?: return BigDecimal.ZERO

        val policy: AccountLockerPolicy = currentPackage.user.lockerPolicy
        val totalDaysUsed = (Date().time - assignDate.time) / (1000 * 60 * 60 * 24)

        if (totalDaysUsed > policy.maximumPeriodDays) {
            currentPackage.updateShippingStatus(ShippingStatus.EXPIRED)
            throw MaximumStoragePeriodExceededException(
                "Package has exceeded maximum allowed storage period of ${policy.maximumPeriodDays} days"
            )
        }

        val chargeableDays = maxOf(0L, totalDaysUsed - policy.freePeriodDays)
        return size.dailyCharge.multiply(BigDecimal(chargeableDays))
    }

    val isAvailable: Boolean
        get() = packageStored == null

    fun checkAccessCode(code: String): Boolean {
        return this.accessCode != null && accessCode == code
    }

    private fun generateAccessCode(): String {
        val random = Random()
        val code = 100000 + random.nextInt(900000)
        return code.toString()
    }

    fun getPackage(): ShippingPackage? = packageStored
}

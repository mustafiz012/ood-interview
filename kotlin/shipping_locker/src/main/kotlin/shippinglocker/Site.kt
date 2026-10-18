package shippinglocker

import shippinglocker.locker.Locker
import shippinglocker.locker.LockerSize
import shippinglocker.pkg.NoLockerAvailableException
import shippinglocker.pkg.ShippingPackage
import shippinglocker.pkg.ShippingStatus
import java.util.Date

class Site(lockersConfig: Map<LockerSize, Int>) {
    val lockers: MutableMap<LockerSize, MutableSet<Locker>> = mutableMapOf()

    init {
        for ((size, count) in lockersConfig) {
            val lockerSet = mutableSetOf<Locker>()
            for (i in 0 until count) {
                lockerSet.add(Locker(size))
            }
            lockers[size] = lockerSet
        }
    }

    fun findAvailableLocker(size: LockerSize): Locker? {
        val set = lockers[size] ?: return null
        for (locker in set) {
            if (locker.isAvailable) {
                return locker
            }
        }
        return null
    }

    fun placePackage(pkg: ShippingPackage, date: Date): Locker {
        val size = pkg.getLockerSize()
        val locker = findAvailableLocker(size)
        if (locker != null) {
            locker.assignPackage(pkg, date)
            pkg.updateShippingStatus(ShippingStatus.IN_LOCKER)
            return locker
        }
        throw NoLockerAvailableException("No locker of size $size is currently available")
    }
}

package shippinglocker

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import shippinglocker.account.Account
import shippinglocker.account.AccountLockerPolicy
import shippinglocker.locker.Locker
import shippinglocker.locker.LockerManager
import shippinglocker.locker.LockerSize
import shippinglocker.locker.LockerSize.LARGE
import shippinglocker.locker.LockerSize.MEDIUM
import shippinglocker.locker.LockerSize.SMALL
import shippinglocker.pkg.BasicShippingPackage
import shippinglocker.pkg.ShippingPackage
import shippinglocker.pkg.ShippingStatus
import java.math.BigDecimal
import java.util.Date
import java.util.EnumMap

class ShippingLockerTest {
    @Test
    fun testLockerManager() {
        println("\n=== Testing Shipping Locker System ===")

        println("\n--- Initializing Locker Manager ---")
        val lockerManager = createLockerManager()
        println("✓ Locker manager initialized with test site and accounts")

        println("\n--- Retrieving Test Account ---")
        val testAccount = lockerManager.getAccount("testAccount")!!
        println("✓ Retrieved account: 'testAccount' (Owner: " + testAccount.ownerName + ")")

        println("\n--- Creating Shipping Package ---")
        val pkg: ShippingPackage = BasicShippingPackage(
            "orderId", testAccount,
            BigDecimal("10.00"), BigDecimal("10.00"), BigDecimal("10.00")
        )
        println("✓ Created shipping package:")
        println("  - Order ID: orderId")
        println("  - Dimensions: 10.00 × 10.00 × 10.00 inches")
        println("  - Account: testAccount")

        println("\n--- Assigning Package to Locker ---")
        val today = Date()
        val twoDaysAgo = Date(today.time - 2 * 24 * 60 * 60 * 1000)
        println("✓ Package delivery date: $twoDaysAgo")
        val assignedLocker = lockerManager.assignPackage(pkg, twoDaysAgo)!!
        println("✓ Package assigned to locker successfully")
        assertEquals(SMALL, assignedLocker.size)
        assertEquals(ShippingStatus.IN_LOCKER, pkg.status)
        assertFalse(assignedLocker.isAvailable)
        println("✓ Package assignment verified:")
        println("  - Assigned locker size: SMALL")
        println("  - Package status: IN_LOCKER")
        println("  - Locker is now occupied")

        println("\n--- Generating Access Code ---")
        val access = assignedLocker.accessCode!!
        println("✓ Access code generated for package pickup")

        println("\n--- Processing Package Pickup ---")
        val foundLocker = lockerManager.pickUpPackage(access)!!
        println("✓ Package picked up using access code")
        assertTrue(foundLocker.isAvailable)
        assertEquals(foundLocker, assignedLocker)
        assertEquals(ShippingStatus.RETRIEVED, pkg.status)
        println("✓ Package pickup verified:")
        println("  - Locker is now available")
        println("  - Package status: RETRIEVED")
        println("  - Correct locker accessed")

        println("\n--- Verifying Account Charges ---")
        assertTrue(BigDecimal.TEN.compareTo(testAccount.usageCharges) == 0)
        println("✓ Account charges verified: $" + testAccount.usageCharges)
        println("=== Shipping Locker Test Completed Successfully ===\n")
    }

    private fun createLockerManager(): LockerManager {
        println("  - Creating locker site with 10 lockers of each size (SMALL, MEDIUM, LARGE)")
        val lockerSizeToCountMap: MutableMap<LockerSize, Int> = EnumMap(
            mapOf(
                SMALL to 10,
                MEDIUM to 10,
                LARGE to 10
            )
        )
        val testSite = Site(lockerSizeToCountMap)

        println("  - Creating test account with locker policy (0-10 lockers allowed)")
        val policy = AccountLockerPolicy(0, 10)
        val testAccount = Account("testAccount", "testOwnerName", policy)

        println("  - Setting up account management system")
        val accountMap = mutableMapOf("testAccount" to testAccount)

        return LockerManager(
            testSite,
            accountMap
        ) { _, _ -> }
    }
}

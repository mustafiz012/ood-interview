package vendingmachine

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class VendingMachineTest {
    @Test
    fun testEndToEndVending() {
        println("\n=== Testing Vending Machine System: End-to-End Operations ===")

        println("\n--- Initializing Vending Machine ---")
        val machine = VendingMachine()
        println("✓ Vending machine initialized")

        println("\n--- Setting Up Products and Inventory ---")
        val itemA = Product("a", "Product A", BigDecimal("1.0"))
        val itemB = Product("b", "Product B", BigDecimal("1.5"))
        val itemC = Product("c", "Product C", BigDecimal("1.25"))
        machine.setRack(
            mapOf(
                "A1" to Rack("A1", itemA, 10),
                "A2" to Rack("A2", itemB, 5),
                "A3" to Rack("A3", itemC, 15)
            )
        )
        println("✓ Products loaded into vending machine:")
        println("  - A1: Product A ($1.00) - 10 units available")
        println("  - A2: Product B ($1.50) - 5 units available")
        println("  - A3: Product C ($1.25) - 15 units available")

        println("\n--- First Purchase Transaction ---")
        machine.insertMoney(BigDecimal("20.00"))
        println("✓ Inserted $20.00 into machine")
        machine.chooseProduct("A2")
        println("✓ Selected Product B (A2) - Price: $1.50")
        var transaction = machine.confirmTransaction()
        assertEquals(itemB, transaction.product)
        assertEquals(BigDecimal("18.50"), transaction.totalAmount)
        println("✓ Transaction completed successfully:")
        println("  - Product dispensed: Product B")
        println("  - Change returned: $18.50")

        println("\n--- Second Purchase Transaction ---")
        machine.insertMoney(BigDecimal("6.00"))
        println("✓ Inserted $6.00 into machine")
        machine.chooseProduct("A2")
        println("✓ Selected Product B (A2) - Price: $1.50")
        transaction = machine.confirmTransaction()
        assertEquals(itemB, transaction.product)
        assertEquals(BigDecimal("4.50"), transaction.totalAmount)
        println("✓ Transaction completed successfully:")
        println("  - Product dispensed: Product B")
        println("  - Change returned: $4.50")

        println("\n--- Third Purchase Attempt (Should Fail) ---")
        machine.insertMoney(BigDecimal("1.00"))
        println("✓ Inserted $1.00 into machine")
        machine.chooseProduct("A2")
        println("✓ Selected Product B (A2) - Price: $1.50")
        println("✓ Attempting to purchase with insufficient funds...")
        val exception = assertThrows(InvalidTransactionException::class.java) {
            machine.confirmTransaction()
        }
        assertEquals("Insufficient fund", exception.message)
        println("✓ Transaction correctly rejected:")
        println("  - Error: Insufficient fund")
        println("  - Expected behavior: Cannot purchase $1.50 item with $1.00")

        println("\n--- Canceling Failed Transaction ---")
        machine.cancelTransaction()
        println("✓ Transaction canceled, money returned to customer")

        println("\n--- Verifying Transaction History ---")
        val history = machine.getTransactionHistory()
        assertEquals(2, history.size)
        assertEquals(itemB, history[0].product)
        assertEquals(itemB, history[1].product)
        println("✓ Transaction history verified:")
        println("  - Total successful transactions: " + history.size)
        println("  - Both transactions were for Product B")

        println("\n--- Verifying Inventory Levels ---")
        val inventory = machine.inventoryManager
        assertEquals(3, inventory.getRack("A2")?.productCount)
        println("✓ Inventory levels verified:")
        println("  - Product B (A2) remaining: " + inventory.getRack("A2")?.productCount + " units")
        println("  - Started with 5 units, sold 2 units, remaining 3 units")
        println("=== Vending Machine Test Completed Successfully ===\n")
    }
}

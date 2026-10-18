package grocery

import grocery.discount.DiscountCampaign
import grocery.discount.criteria.CategoryBasedCriteria
import grocery.discount.strategy.PercentageBasedStrategy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class GroceryStoreSystemTest {

    @Test
    fun testEndToEnd() {
        println("\n=== Testing Grocery Store System End-to-End ===")

        println("\n--- Initializing Grocery Store System ---")
        val groceryStoreSystem = GroceryStoreSystem()
        println("✓ Grocery store system initialized")

        println("\n--- Setting Up Product Catalog ---")
        groceryStoreSystem.addOrUpdateItem(Item("Apple", "123", "Fruit", BigDecimal("0.5")))
        groceryStoreSystem.addOrUpdateItem(Item("Banana", "124", "Fruit", BigDecimal("1.0")))
        groceryStoreSystem.addOrUpdateItem(Item("Gum", "125", "Candy", BigDecimal("4.0")))
        println("✓ Added Apple (barcode: 123, category: Fruit, price: $0.50)")
        println("✓ Added Banana (barcode: 124, category: Fruit, price: $1.00)")
        println("✓ Added Gum (barcode: 125, category: Candy, price: $4.00)")

        println("\n--- Setting Up Inventory ---")
        groceryStoreSystem.updateInventory("123", 100)
        groceryStoreSystem.updateInventory("124", 100)
        groceryStoreSystem.updateInventory("125", 100)
        println("✓ Set inventory: 100 Apples, 100 Bananas, 100 Gum packs")

        println("\n--- Creating Discount Campaign ---")
        groceryStoreSystem.addDiscountCampaign(
            DiscountCampaign(
                "1", "Fruit Half Price",
                CategoryBasedCriteria("Fruit"),
                PercentageBasedStrategy(BigDecimal("50"))
            )
        )
        println("✓ Created discount campaign: 'Fruit Half Price'")
        println("  - Applies to all items in 'Fruit' category")
        println("  - 50% discount on all fruit items")

        println("\n--- Starting Checkout Session ---")
        val checkOutSession = groceryStoreSystem.checkout
        println("✓ New checkout session created")

        println("\n--- Adding Items to Order ---")
        checkOutSession.addItemToOrder(groceryStoreSystem.getItemByBarcode("123"), 20)
        checkOutSession.addItemToOrder(groceryStoreSystem.getItemByBarcode("124"), 10)
        checkOutSession.addItemToOrder(groceryStoreSystem.getItemByBarcode("125"), 5)
        println("✓ Added 20 Apples to order (original price: $10.00, with 50% discount: $5.00)")
        println("✓ Added 10 Bananas to order (original price: $10.00, with 50% discount: $5.00)")
        println("✓ Added 5 Gum packs to order (no discount: $20.00)")

        println("\n--- Calculating Order Total ---")
        val total = checkOutSession.getOrderTotal()
        println("✓ Order total calculated: $$total")
        println("  - Fruit items (with 50% discount): $10.00")
        println("  - Candy items (no discount): $20.00")
        println("  - Total: $30.00")
        assertEquals(BigDecimal("30.0").stripTrailingZeros(), total.stripTrailingZeros())

        println("\n--- Processing Payment ---")
        val change = checkOutSession.processPayment(BigDecimal.valueOf(100))
        println("✓ Customer paid: $100.00")
        println("✓ Change calculated: $$change")
        println("  - Amount paid: $100.00")
        println("  - Order total: $30.00")
        println("  - Change due: $70.00")
        assertEquals(BigDecimal("70.0").stripTrailingZeros(), change.stripTrailingZeros())

        println("\n--- Generating Receipt ---")
        val receipt = checkOutSession.getReceipt()
        println("✓ Receipt generated successfully")
        println("=== Receipt Details ===")
        println(receipt.printReceipt())
        println("=== End-to-End Test Completed Successfully ===\n")
    }

    @Test
    fun testCatalogueManagement() {
        println("\n=== Testing Catalog Management System ===")

        println("\n--- Initializing Grocery Store System ---")
        val groceryStoreSystem = GroceryStoreSystem()
        println("✓ Grocery store system initialized")

        println("\n--- Adding Items to Catalog ---")
        groceryStoreSystem.addOrUpdateItem(Item("Apple", "123", "Fruit", BigDecimal("0.5")))
        groceryStoreSystem.addOrUpdateItem(Item("Banana", "124", "Fruit", BigDecimal("1.0")))
        groceryStoreSystem.addOrUpdateItem(Item("Gum", "125", "Candy", BigDecimal("4.0")))
        println("✓ Added Apple (barcode: 123, category: Fruit, price: $0.50)")
        println("✓ Added Banana (barcode: 124, category: Fruit, price: $1.00)")
        println("✓ Added Gum (barcode: 125, category: Candy, price: $4.00)")

        println("\n--- Verifying Item Retrieval by Barcode ---")
        assertEquals("Apple", groceryStoreSystem.getItemByBarcode("123")?.name)
        assertEquals("Banana", groceryStoreSystem.getItemByBarcode("124")?.name)
        assertEquals("Gum", groceryStoreSystem.getItemByBarcode("125")?.name)
        println("✓ Successfully retrieved Apple by barcode 123")
        println("✓ Successfully retrieved Banana by barcode 124")
        println("✓ Successfully retrieved Gum by barcode 125")

        println("\n--- Testing Item Update (Price and Category Change) ---")
        groceryStoreSystem.addOrUpdateItem(Item("Bread", "123", "Pantry", BigDecimal("0.6")))
        println("✓ Updated item with barcode 123:")
        println("  - Name changed from 'Apple' to 'Bread'")
        println("  - Category changed from 'Fruit' to 'Pantry'")
        println("  - Price changed from $0.50 to $0.60")
        assertEquals(BigDecimal("0.6"), groceryStoreSystem.getItemByBarcode("123")?.price)
        assertEquals("Pantry", groceryStoreSystem.getItemByBarcode("123")?.category)
        println("✓ Item update verified successfully")

        println("\n--- Testing Item Removal ---")
        groceryStoreSystem.removeItem("123")
        println("✓ Removed item with barcode 123 from catalog")
        assertNull(groceryStoreSystem.getItemByBarcode("123"))
        println("✓ Item removal verified: barcode 123 no longer exists in catalog")
        System.out.println("=== Catalog Management Test Completed Successfully ===\n")
    }
}

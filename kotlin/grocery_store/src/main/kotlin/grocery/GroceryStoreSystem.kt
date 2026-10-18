package grocery

import grocery.discount.DiscountCampaign

class GroceryStoreSystem {
    val catalog: Catalog
    val inventory: Inventory
    private val activeDiscounts: MutableList<DiscountCampaign>
    val checkout: Checkout

    constructor() {
        this.catalog = Catalog()
        this.inventory = Inventory()
        this.activeDiscounts = mutableListOf()
        this.checkout = Checkout(activeDiscounts)
    }

    constructor(catalog: Catalog, inventory: Inventory, activeDiscounts: MutableList<DiscountCampaign>) {
        this.catalog = catalog
        this.inventory = inventory
        this.activeDiscounts = activeDiscounts
        this.checkout = Checkout(activeDiscounts)
    }

    fun addOrUpdateItem(item: Item) {
        catalog.updateItem(item)
    }

    fun updateInventory(barcode: String, count: Int) {
        inventory.addStock(barcode, count)
    }

    fun addDiscountCampaign(discount: DiscountCampaign) {
        activeDiscounts.add(discount)
    }

    fun getItemByBarcode(barcode: String): Item? = catalog.getItem(barcode)

    fun removeItem(barcode: String) {
        catalog.removeItem(barcode)
    }
}

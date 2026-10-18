package vendingmachine

class InventoryManager {
    private var racks: Map<String, Rack> = mutableMapOf()

    fun getProductInRack(rackCode: String): Product? {
        return racks[rackCode]?.product
    }

    fun dispenseProductFromRack(rack: Rack) {
        rack.productCount = rack.productCount - 1
    }

    fun updateRack(racks: Map<String, Rack>) {
        this.racks = racks
    }

    fun getRack(name: String): Rack? {
        return racks[name]
    }

    override fun toString(): String {
        return "InventoryManager(racks=$racks)"
    }
}

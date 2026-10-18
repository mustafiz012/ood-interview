package grocery

class Inventory {
    private val stock: MutableMap<String, Int> = mutableMapOf()

    fun addStock(barcode: String, count: Int) {
        stock[barcode] = (stock[barcode] ?: 0) + count
    }

    fun reduceStock(barcode: String, count: Int) {
        stock[barcode] = (stock[barcode] ?: 0) - count
    }

    fun getStock(barcode: String): Int = stock[barcode] ?: 0
}

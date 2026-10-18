package grocery

class Catalog {
    private val items: MutableMap<String, Item> = mutableMapOf()

    fun updateItem(item: Item) {
        items[item.barcode] = item
    }

    fun removeItem(barcode: String) {
        items.remove(barcode)
    }

    fun getItem(barcode: String): Item? = items[barcode]
}

package vendingmachine

class Rack(
    val rackCode: String,
    val product: Product,
    var productCount: Int
) {
    var count: Int
        get() = productCount
        set(value) {
            productCount = value
        }
}

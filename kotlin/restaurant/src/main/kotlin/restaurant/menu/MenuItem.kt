package restaurant.menu

import java.math.BigDecimal

data class MenuItem(
    val name: String,
    val description: String,
    val price: BigDecimal,
    val category: Category
) {
    enum class Category {
        MAIN,
        APPETIZER,
        DESSERT
    }
}

package grocery

import java.math.BigDecimal

data class Item(
    val name: String,
    val barcode: String,
    val category: String,
    val price: BigDecimal
)

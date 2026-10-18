package vendingmachine

import java.math.BigDecimal

data class Product(
    val productCode: String,
    val description: String,
    val unitPrice: BigDecimal
)

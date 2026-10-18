package vendingmachine

import java.math.BigDecimal

data class Transaction(
    var rack: Rack? = null,
    var product: Product? = null,
    var totalAmount: BigDecimal = BigDecimal.ZERO
)

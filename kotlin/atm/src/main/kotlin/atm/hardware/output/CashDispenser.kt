package atm.hardware.output

import java.math.BigDecimal

interface CashDispenser {
    fun dispenseCash(amount: BigDecimal)
}

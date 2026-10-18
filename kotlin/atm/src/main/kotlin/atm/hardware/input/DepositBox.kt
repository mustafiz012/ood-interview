package atm.hardware.input

import atm.ATMMachine
import java.math.BigDecimal

interface DepositBox {
    fun acceptDeposit(amount: BigDecimal, machine: ATMMachine)
}

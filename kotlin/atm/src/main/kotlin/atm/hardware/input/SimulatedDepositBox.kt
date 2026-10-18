package atm.hardware.input

import atm.ATMMachine
import java.math.BigDecimal

class SimulatedDepositBox : DepositBox {
    override fun acceptDeposit(amount: BigDecimal, machine: ATMMachine) {
        machine.collectDeposit(amount)
    }
}

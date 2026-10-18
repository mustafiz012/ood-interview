package atm.hardware.input

import atm.ATMMachine
import atm.bank.enums.TransactionType
import java.math.BigDecimal

class SimulatedKeyPad : Keypad {
    override fun handlePinEntry(pin: String, machine: ATMMachine) {
        machine.enterPin(pin)
    }

    override fun handleAmountEntry(amount: BigDecimal, machine: ATMMachine) {
        machine.enterAmount(amount)
    }

    override fun handleSelectTransaction(transactionType: TransactionType, atmMachine: ATMMachine) {
        when (transactionType) {
            TransactionType.WITHDRAW -> atmMachine.withdrawRequest()
            TransactionType.DEPOSIT -> atmMachine.depositRequest()
        }
    }
}

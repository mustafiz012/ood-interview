package atm.hardware.input

import atm.ATMMachine
import atm.bank.enums.TransactionType
import java.math.BigDecimal

interface Keypad {
    fun handlePinEntry(pin: String, machine: ATMMachine)
    fun handleAmountEntry(amount: BigDecimal, machine: ATMMachine)
    fun handleSelectTransaction(transactionType: TransactionType, atmMachine: ATMMachine)
}

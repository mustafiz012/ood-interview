package atm

import atm.bank.Bank
import atm.bank.BankInterface
import atm.hardware.input.CardProcessor
import atm.hardware.input.DepositBox
import atm.hardware.input.Keypad
import atm.hardware.output.CashDispenser
import atm.hardware.output.Display
import atm.states.ATMState
import atm.states.IdleState
import java.math.BigDecimal

class ATMMachine(
    val bank: Bank,
    val cardProcessor: CardProcessor,
    val depositBox: DepositBox,
    val cashDispenser: CashDispenser,
    val keypad: Keypad,
    val display: Display
) {
    var currentState: ATMState = IdleState()
        private set

    val bankInterface: BankInterface
        get() = bank

    fun insertCard(cardNumber: String) {
        currentState.processCardInsertion(this, cardNumber)
    }

    fun ejectCard() {
        currentState.processCardEjection(this)
    }

    fun enterPin(pin: String) {
        currentState.processPinEntry(this, pin)
    }

    fun withdrawRequest() {
        currentState.processWithdrawalRequest(this)
    }

    fun depositRequest() {
        currentState.processDepositRequest(this)
    }

    fun enterAmount(amount: BigDecimal) {
        currentState.processAmountEntry(this, amount)
    }

    fun collectDeposit(amount: BigDecimal) {
        currentState.processDepositCollection(this, amount)
    }

    fun transitionToState(nextState: ATMState) {
        this.currentState = nextState
    }
}

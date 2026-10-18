package atm.states

import atm.ATMMachine
import java.math.BigDecimal

class WithdrawAmountEntryState : ATMState {
    override fun processCardEjection(atmMachine: ATMMachine) {
        atmMachine.display.showMessage("Transaction cancelled, card ejected")
        atmMachine.transitionToState(IdleState())
    }

    override fun processAmountEntry(atmMachine: ATMMachine, amount: BigDecimal) {
        val cardNumber = atmMachine.cardProcessor.cardNumber
        val account = cardNumber?.let { atmMachine.bankInterface.getAccountByCard(it) }
        val isSuccess = account != null && atmMachine.bankInterface.withdrawFunds(account, amount)

        if (isSuccess) {
            atmMachine.cashDispenser.dispenseCash(amount)
            atmMachine.display.showMessage("Please take your cash.")
        } else {
            atmMachine.display.showMessage("Insufficient funds, please try again.")
        }
        atmMachine.transitionToState(TransactionSelectionState())
    }
}

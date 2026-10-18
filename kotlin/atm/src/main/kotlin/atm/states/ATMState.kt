package atm.states

import atm.ATMMachine
import java.math.BigDecimal

interface ATMState {
    fun processCardInsertion(atmMachine: ATMMachine, cardNumber: String) {
        renderDefaultAction(atmMachine)
    }

    fun processCardEjection(atmMachine: ATMMachine) {
        renderDefaultAction(atmMachine)
    }

    fun processPinEntry(atmMachine: ATMMachine, pin: String) {
        renderDefaultAction(atmMachine)
    }

    fun processWithdrawalRequest(atmMachine: ATMMachine) {
        renderDefaultAction(atmMachine)
    }

    fun processDepositRequest(atmMachine: ATMMachine) {
        renderDefaultAction(atmMachine)
    }

    fun processAmountEntry(atmMachine: ATMMachine, amount: BigDecimal) {
        renderDefaultAction(atmMachine)
    }

    fun processDepositCollection(atmMachine: ATMMachine, amount: BigDecimal) {
        renderDefaultAction(atmMachine)
    }

    private fun renderDefaultAction(atmMachine: ATMMachine) {
        atmMachine.display.showMessage("Invalid action, please try again.")
    }
}

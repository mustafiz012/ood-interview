package atm.states

import atm.ATMMachine

class TransactionSelectionState : ATMState {
    override fun processCardEjection(atmMachine: ATMMachine) {
        atmMachine.display.showMessage("Card ejected, transaction cancelled.")
        atmMachine.transitionToState(IdleState())
    }

    override fun processWithdrawalRequest(atmMachine: ATMMachine) {
        atmMachine.display.showMessage("Enter amount to withdraw:")
        atmMachine.transitionToState(WithdrawAmountEntryState())
    }

    override fun processDepositRequest(atmMachine: ATMMachine) {
        atmMachine.display.showMessage("Please deposit cash into the deposit box.")
        atmMachine.transitionToState(DepositCollectionState())
    }
}

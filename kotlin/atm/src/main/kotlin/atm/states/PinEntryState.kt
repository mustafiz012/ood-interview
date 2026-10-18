package atm.states

import atm.ATMMachine

class PinEntryState : ATMState {
    override fun processCardEjection(atmMachine: ATMMachine) {
        atmMachine.display.showMessage("Card ejected")
        atmMachine.transitionToState(IdleState())
    }

    override fun processPinEntry(atmMachine: ATMMachine, pin: String) {
        val cardNumber = atmMachine.cardProcessor.cardNumber
        val isPinCorrect = cardNumber != null && atmMachine.bankInterface.checkPin(cardNumber, pin)

        if (isPinCorrect) {
            atmMachine.display.showMessage("PIN correct, select transaction type")
            atmMachine.transitionToState(TransactionSelectionState())
        } else {
            atmMachine.display.showMessage("Invalid PIN. Please try again")
        }
    }
}

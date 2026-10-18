package atm.states

import atm.ATMMachine

class IdleState : ATMState {
    override fun processCardInsertion(atmMachine: ATMMachine, cardNumber: String) {
        if (atmMachine.bankInterface.validateCard(cardNumber)) {
            atmMachine.display.showMessage("Please enter your PIN")
            atmMachine.transitionToState(PinEntryState())
        } else {
            atmMachine.display.showMessage("Invalid card. Please try again.")
        }
    }
}

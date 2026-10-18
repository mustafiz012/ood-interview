package atm.hardware.input

import atm.ATMMachine

class SimulatedCardProcessor : CardProcessor {
    override var cardNumber: String? = null
        private set

    override fun handleCardInsertion(cardNumber: String, atmMachine: ATMMachine) {
        this.cardNumber = cardNumber
        atmMachine.insertCard(cardNumber)
    }

    override fun handleCardEjection(atmMachine: ATMMachine) {
        this.cardNumber = null
        atmMachine.ejectCard()
    }
}

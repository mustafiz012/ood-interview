package atm.hardware.input

import atm.ATMMachine

interface CardProcessor {
    fun handleCardInsertion(cardNumber: String, atmMachine: ATMMachine)
    fun handleCardEjection(atmMachine: ATMMachine)
    val cardNumber: String?
}

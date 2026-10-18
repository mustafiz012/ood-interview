package atm.states

import atm.ATMMachine
import atm.bank.DepositTransaction
import java.math.BigDecimal

class DepositCollectionState : ATMState {
    override fun processCardEjection(atmMachine: ATMMachine) {
        atmMachine.display.showMessage("Transaction cancelled, card ejected")
        atmMachine.transitionToState(IdleState())
    }

    override fun processDepositCollection(atmMachine: ATMMachine, amount: BigDecimal) {
        val cardNumber = atmMachine.cardProcessor.cardNumber
        val account = cardNumber?.let { atmMachine.bankInterface.getAccountByCard(it) }
        if (account != null) {
            val transaction = DepositTransaction(account, amount)
            transaction.executeTransaction()
            atmMachine.display.showMessage(
                "Deposit successful. Deposited amount: $amount to account: ${account.accountNumber}"
            )
        }
        atmMachine.transitionToState(TransactionSelectionState())
    }
}

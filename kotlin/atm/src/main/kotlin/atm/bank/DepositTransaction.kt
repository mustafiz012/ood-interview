package atm.bank

import atm.bank.enums.TransactionType
import java.math.BigDecimal

class DepositTransaction(
    val account: Account,
    val amount: BigDecimal
) : Transaction {
    override val type: TransactionType
        get() = TransactionType.DEPOSIT

    override fun validateTransaction(): Boolean = true

    override fun executeTransaction() {
        account.updateBalanceWithTransaction(amount)
    }
}

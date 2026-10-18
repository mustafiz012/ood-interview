package atm.bank

import atm.bank.enums.TransactionType
import java.math.BigDecimal

class WithdrawTransaction(
    val account: Account,
    val amount: BigDecimal
) : Transaction {
    override val type: TransactionType
        get() = TransactionType.WITHDRAW

    override fun validateTransaction(): Boolean = account.balance > amount

    init {
        check(validateTransaction()) { "Cannot complete withdrawal: Insufficient funds in account" }
    }

    override fun executeTransaction() {
        account.updateBalanceWithTransaction(amount.negate())
    }
}

package atm.bank

import atm.bank.enums.TransactionType

interface Transaction {
    val type: TransactionType
    fun validateTransaction(): Boolean
    fun executeTransaction()
}

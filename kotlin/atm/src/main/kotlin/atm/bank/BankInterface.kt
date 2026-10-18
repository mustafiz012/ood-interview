package atm.bank

import atm.bank.enums.AccountType
import java.math.BigDecimal

interface BankInterface {
    fun addAccount(accountNumber: String, type: AccountType, cardNumber: String, pin: String)
    fun validateCard(cardNumber: String): Boolean
    fun checkPin(cardNumber: String, pinNumber: String): Boolean
    fun getAccountByAccountNumber(accountNumber: String): Account?
    fun getAccountByCard(cardNumber: String): Account?
    fun withdrawFunds(account: Account, amount: BigDecimal): Boolean
}

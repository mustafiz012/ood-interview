package atm.bank

import atm.bank.enums.AccountType
import java.math.BigDecimal

class Bank : BankInterface {
    private val accounts = mutableMapOf<String, Account>()
    private val accountByCard = mutableMapOf<String, Account>()

    override fun addAccount(
        accountNumber: String,
        type: AccountType,
        cardNumber: String,
        pin: String
    ) {
        val newAccount = Account(accountNumber, type, cardNumber, pin)
        accounts[newAccount.accountNumber] = newAccount
        accountByCard[newAccount.cardNumber] = newAccount
    }

    override fun validateCard(cardNumber: String): Boolean {
        return getAccountByCard(cardNumber) != null
    }

    override fun checkPin(cardNumber: String, pinNumber: String): Boolean {
        val account = getAccountByCard(cardNumber) ?: return false
        return account.validatePin(pinNumber)
    }

    override fun getAccountByAccountNumber(accountNumber: String): Account? {
        return accounts[accountNumber]
    }

    override fun getAccountByCard(cardNumber: String): Account? {
        return accountByCard[cardNumber]
    }

    override fun withdrawFunds(account: Account, amount: BigDecimal): Boolean {
        if (account.balance >= amount) {
            account.updateBalanceWithTransaction(amount.negate())
            return true
        }
        return false
    }
}

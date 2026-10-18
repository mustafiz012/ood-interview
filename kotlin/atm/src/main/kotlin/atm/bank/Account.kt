package atm.bank

import atm.bank.enums.AccountType
import java.math.BigDecimal
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class Account(
    val accountNumber: String,
    val accountType: AccountType,
    val cardNumber: String,
    pin: String
) {
    var balance: BigDecimal = BigDecimal.ZERO
        private set

    private val cardPinHash: ByteArray = calculateMd5(pin)

    fun validatePin(pinNumber: String): Boolean {
        val entryPinHash = calculateMd5(pinNumber)
        return cardPinHash.contentEquals(entryPinHash)
    }

    fun updateBalanceWithTransaction(balanceChange: BigDecimal) {
        balance = balance.add(balanceChange)
    }

    companion object {
        private fun calculateMd5(pinNumber: String): ByteArray {
            return try {
                MessageDigest.getInstance("MD5").digest(pinNumber.toByteArray())
            } catch (e: NoSuchAlgorithmException) {
                throw CryptographicException("MD5 algorithm not available in the current environment", e)
            }
        }
    }
}

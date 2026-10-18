package vendingmachine

import java.math.BigDecimal
import java.util.Collections

class VendingMachine {
    private val transactionHistory: MutableList<Transaction> = mutableListOf()
    val inventoryManager: InventoryManager = InventoryManager()
    val paymentProcessor: PaymentProcessor = PaymentProcessor()
    private var currentTransaction: Transaction = Transaction()

    fun setRack(rack: Map<String, Rack>) {
        inventoryManager.updateRack(rack)
    }

    fun insertMoney(amount: BigDecimal) {
        paymentProcessor.addBalance(amount)
    }

    fun chooseProduct(rackId: String) {
        val product = inventoryManager.getProductInRack(rackId)
        currentTransaction.rack = inventoryManager.getRack(rackId)
        currentTransaction.product = product
    }

    @Throws(InvalidTransactionException::class)
    fun confirmTransaction(): Transaction {
        // Step 1: Validate the transaction before processing
        validateTransaction()

        val product = currentTransaction.product!!
        val rack = currentTransaction.rack!!

        // Step 2: Charge the customer for the product
        paymentProcessor.charge(product.unitPrice)

        // Step 3: Dispense the product from the rack
        inventoryManager.dispenseProductFromRack(rack)

        // Step 4: Return the change to the customer
        currentTransaction.totalAmount = paymentProcessor.returnChange()

        // Step 5: Add the completed transaction to the history
        transactionHistory.add(currentTransaction)
        val completedTransaction = currentTransaction

        // Reset the current transaction for the next purchase
        currentTransaction = Transaction()
        return completedTransaction
    }

    @Throws(InvalidTransactionException::class)
    private fun validateTransaction() {
        val product = currentTransaction.product
        val rack = currentTransaction.rack
        if (product == null || rack == null) {
            throw InvalidTransactionException("Invalid product selection")
        } else if (rack.productCount == 0) {
            throw InvalidTransactionException("Insufficient inventory for product.")
        } else if (paymentProcessor.currentBalance < product.unitPrice) {
            throw InvalidTransactionException("Insufficient fund")
        }
    }

    fun getTransactionHistory(): List<Transaction> = Collections.unmodifiableList(transactionHistory)

    fun cancelTransaction() {
        paymentProcessor.returnChange()
        currentTransaction = Transaction()
    }
}

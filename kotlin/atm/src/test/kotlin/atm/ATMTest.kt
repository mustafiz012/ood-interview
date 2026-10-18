package atm

import atm.bank.Bank
import atm.bank.enums.AccountType
import atm.bank.enums.TransactionType
import atm.hardware.input.SimulatedCardProcessor
import atm.hardware.input.SimulatedDepositBox
import atm.hardware.input.SimulatedKeyPad
import atm.hardware.output.ConsoleDisplay
import atm.hardware.output.SimulatedCashDispenser
import atm.states.IdleState
import atm.states.PinEntryState
import atm.states.TransactionSelectionState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ATMTest {

    @Test
    fun testATMFullFlow() {
        println("\n=== Starting ATM Full Flow Test ===")

        // Initialize bank and account
        val bank = Bank()
        bank.addAccount("123456", AccountType.SAVING, "1111-2222-3333-4444", "1234")
        bank.getAccountByAccountNumber("123456")?.updateBalanceWithTransaction(BigDecimal("1000"))
        println("✓ Bank initialized with account 123456 (PIN: 1234)")
        println("✓ Account balance: $1000")

        // Initialize simulated ATM components
        val cardProcessor = SimulatedCardProcessor()
        val depositBox = SimulatedDepositBox()
        val display = ConsoleDisplay()
        val keypad = SimulatedKeyPad()
        val cashDispenser = SimulatedCashDispenser()

        val atmMachine = ATMMachine(bank, cardProcessor, depositBox, cashDispenser, keypad, display)
        println("✓ ATM machine initialized with all components")

        // Insert card
        println("\n--- Step 1: Card Insertion ---")
        cardProcessor.handleCardInsertion("1111-2222-3333-4444", atmMachine)
        assertEquals("Please enter your PIN", display.displayedMessage)
        println("✓ Card inserted: 1111-2222-3333-4444")
        println("✓ Display shows: '${display.displayedMessage}'")

        // Enter correct PIN
        println("\n--- Step 2: PIN Verification ---")
        keypad.handlePinEntry("1234", atmMachine)
        assertEquals("PIN correct, select transaction type", display.displayedMessage)
        println("✓ Correct PIN entered: 1234")
        println("✓ Display shows: '${display.displayedMessage}'")

        // Select withdraw transaction
        println("\n--- Step 3: Transaction Selection ---")
        keypad.handleSelectTransaction(TransactionType.WITHDRAW, atmMachine)
        assertEquals("Enter amount to withdraw:", display.displayedMessage)
        println("✓ Selected transaction: WITHDRAW")
        println("✓ Display shows: '${display.displayedMessage}'")

        // Enter amount to withdraw
        println("\n--- Step 4: Amount Entry & Cash Dispensing ---")
        keypad.handleAmountEntry(BigDecimal("100"), atmMachine)
        assertEquals("Please take your cash.", display.displayedMessage)
        println("✓ Withdrawal amount entered: $100")
        println("✓ Display shows: '${display.displayedMessage}'")
        println("✓ Cash dispensed: $100")

        // Verify balance after withdrawal
        val account = bank.getAccountByAccountNumber("123456")
        assertEquals(BigDecimal("900"), account?.balance)
        println("✓ Updated balance verified: $900")

        // Eject card to finish
        println("\n--- Step 5: Session Termination ---")
        cardProcessor.handleCardEjection(atmMachine)
        assertEquals("Card ejected, transaction cancelled.", display.displayedMessage)
        println("✓ Card ejected successfully")
        println("✓ Display shows: '${display.displayedMessage}'")
        println("=== ATM Full Flow Test Completed Successfully ===\n")
    }

    @Test
    fun testDepositFlow() {
        println("\n=== Starting Deposit Flow Test ===")

        // Initialize bank and account
        val bank = Bank()
        bank.addAccount("123456", AccountType.SAVING, "1111-2222-3333-4444", "1234")
        println("✓ Bank initialized with account 123456 (PIN: 1234)")
        println("✓ Initial balance: $0")

        // Initialize simulated ATM components
        val cardProcessor = SimulatedCardProcessor()
        val depositBox = SimulatedDepositBox()
        val display = ConsoleDisplay()
        val keypad = SimulatedKeyPad()
        val cashDispenser = SimulatedCashDispenser()

        val atmMachine = ATMMachine(bank, cardProcessor, depositBox, cashDispenser, keypad, display)
        println("✓ ATM machine initialized")

        // Insert card
        println("\n--- Starting Deposit Transaction ---")
        cardProcessor.handleCardInsertion("1111-2222-3333-4444", atmMachine)
        assertEquals("Please enter your PIN", display.displayedMessage)
        println("✓ Card inserted, PIN requested")

        // Enter PIN
        keypad.handlePinEntry("1234", atmMachine)
        assertEquals("PIN correct, select transaction type", display.displayedMessage)
        println("✓ PIN verified, transaction menu displayed")

        // Select deposit transaction
        keypad.handleSelectTransaction(TransactionType.DEPOSIT, atmMachine)
        assertEquals("Please deposit cash into the deposit box.", display.displayedMessage)
        println("✓ Deposit selected, deposit box opened")

        // Deposit cash
        println("\n--- Depositing Cash ---")
        depositBox.acceptDeposit(BigDecimal("500"), atmMachine)
        assertEquals(
            "Deposit successful. Deposited amount: 500 to account: 123456",
            display.displayedMessage
        )
        println("✓ Deposited: $500")
        println("✓ Display shows: '${display.displayedMessage}'")

        // Verify updated balance
        val account = bank.getAccountByAccountNumber("123456")
        assertEquals(BigDecimal("500"), account?.balance)
        println("✓ Updated balance verified: $500")

        // Eject card
        cardProcessor.handleCardEjection(atmMachine)
        println("✓ Card ejected, session ended")
        println("=== Deposit Flow Test Completed Successfully ===\n")
    }

    @Test
    fun testInvalidCard() {
        println("\n=== Testing Invalid Card Handling ===")

        val bank = Bank()
        val cardProcessor = SimulatedCardProcessor()
        val depositBox = SimulatedDepositBox()
        val display = ConsoleDisplay()
        val keypad = SimulatedKeyPad()
        val cashDispenser = SimulatedCashDispenser()

        val atmMachine = ATMMachine(bank, cardProcessor, depositBox, cashDispenser, keypad, display)
        println("✓ ATM machine initialized with empty bank database")

        // Insert unregistered card
        println("\n--- Inserting Unregistered Card ---")
        cardProcessor.handleCardInsertion("0000-0000-0000-0000", atmMachine)
        assertEquals("Invalid card. Please try again.", display.displayedMessage)
        println("✓ Card inserted: 0000-0000-0000-0000 (not in system)")
        println("✓ ATM correctly rejected: 'Invalid card. Please try again.'")

        // Verify state remains IdleState
        assertEquals(IdleState::class.java, atmMachine.currentState.javaClass)
        println("✓ ATM remains in IDLE state (ready for next user)")
        println("=== Invalid Card Test Completed Successfully ===\n")
    }

    @Test
    fun testInvalidPin() {
        println("\n=== Testing Invalid PIN Handling ===")

        val bank = Bank()
        bank.addAccount("123456", AccountType.SAVING, "1111-2222-3333-4444", "1234")
        println("✓ Bank initialized with account 123456 (PIN: 1234)")

        val cardProcessor = SimulatedCardProcessor()
        val depositBox = SimulatedDepositBox()
        val display = ConsoleDisplay()
        val keypad = SimulatedKeyPad()
        val cashDispenser = SimulatedCashDispenser()

        val atmMachine = ATMMachine(bank, cardProcessor, depositBox, cashDispenser, keypad, display)
        println("✓ ATM machine initialized")

        // Insert valid card
        println("\n--- Inserting Valid Card ---")
        cardProcessor.handleCardInsertion("1111-2222-3333-4444", atmMachine)
        assertEquals("Please enter your PIN", display.displayedMessage)
        println("✓ Valid card inserted: 1111-2222-3333-4444")
        println("✓ ATM requests PIN entry")

        // Enter invalid PIN
        println("\n--- Entering Invalid PIN ---")
        keypad.handlePinEntry("0000", atmMachine)
        assertEquals("Invalid PIN. Please try again", display.displayedMessage)
        println("✓ Invalid PIN entered: 0000 (correct PIN is 1234)")
        println("✓ ATM correctly rejected: 'Invalid PIN. Please try again'")

        // Verify state remains PinEntryState
        assertEquals(PinEntryState::class.java, atmMachine.currentState.javaClass)
        println("✓ ATM remains in PIN entry state (allows retry)")
        println("=== Invalid PIN Test Completed Successfully ===\n")
    }

    @Test
    fun testWithdrawWithoutSufficientFunds() {
        println("\n=== Testing Withdrawal Without Sufficient Funds ===")

        // Initialize bank
        val bank = Bank()
        bank.addAccount("123456", AccountType.SAVING, "1111-2222-3333-4444", "1234")
        bank.getAccountByAccountNumber("123456")?.updateBalanceWithTransaction(BigDecimal("100"))
        println("✓ Bank initialized with account 123456 (PIN: 1234)")
        println("✓ Account balance: $100")

        // Initialize simulated ATM components
        val cardProcessor = SimulatedCardProcessor()
        val depositBox = SimulatedDepositBox()
        val display = ConsoleDisplay()
        val keypad = SimulatedKeyPad()
        val cashDispenser = SimulatedCashDispenser()

        val atmMachine = ATMMachine(bank, cardProcessor, depositBox, cashDispenser, keypad, display)
        println("✓ ATM machine initialized")

        // Insert valid card
        println("\n--- Starting Withdrawal Process ---")
        cardProcessor.handleCardInsertion("1111-2222-3333-4444", atmMachine)
        assertEquals("Please enter your PIN", display.displayedMessage)
        println("✓ Card inserted, PIN requested")

        // Enter valid PIN
        keypad.handlePinEntry("1234", atmMachine)
        assertEquals("PIN correct, select transaction type", display.displayedMessage)
        println("✓ PIN verified, transaction menu displayed")

        // Select withdraw
        keypad.handleSelectTransaction(TransactionType.WITHDRAW, atmMachine)
        assertEquals("Enter amount to withdraw:", display.displayedMessage)
        println("✓ Withdrawal selected, amount requested")

        // Attempt to withdraw more than balance
        println("\n--- Attempting Excessive Withdrawal ---")
        keypad.handleAmountEntry(BigDecimal("200"), atmMachine)
        assertEquals("Insufficient funds, please try again.", display.displayedMessage)
        println("✓ Attempted to withdraw $200 (exceeds balance of $100)")
        println("✓ ATM correctly rejected: 'Insufficient funds, please try again.'")

        // Verify state remains TransactionSelectionState
        assertEquals(TransactionSelectionState::class.java, atmMachine.currentState.javaClass)
        println("✓ ATM returned to transaction selection state (allows other transactions)")
        println("=== Insufficient Funds Test Completed Successfully ===\n")
    }

    @Test
    fun testEjectCardDuringTransaction() {
        println("\n=== Testing Card Ejection During Transaction ===")

        // Initialize bank
        val bank = Bank()
        bank.addAccount("123456", AccountType.SAVING, "1111-2222-3333-4444", "1234")
        println("✓ Bank initialized with account 123456 (PIN: 1234)")

        // Initialize simulated ATM components
        val cardProcessor = SimulatedCardProcessor()
        val depositBox = SimulatedDepositBox()
        val display = ConsoleDisplay()
        val keypad = SimulatedKeyPad()
        val cashDispenser = SimulatedCashDispenser()

        val atmMachine = ATMMachine(bank, cardProcessor, depositBox, cashDispenser, keypad, display)
        println("✓ ATM machine initialized")

        // Insert valid card
        println("\n--- Starting Transaction Process ---")
        cardProcessor.handleCardInsertion("1111-2222-3333-4444", atmMachine)
        assertEquals("Please enter your PIN", display.displayedMessage)
        println("✓ Card inserted, PIN requested")

        // Enter valid PIN
        keypad.handlePinEntry("1234", atmMachine)
        assertEquals("PIN correct, select transaction type", display.displayedMessage)
        println("✓ PIN verified, transaction menu displayed")

        // Select deposit
        keypad.handleSelectTransaction(TransactionType.DEPOSIT, atmMachine)
        assertEquals("Please deposit cash into the deposit box.", display.displayedMessage)
        println("✓ Deposit selected, waiting for cash")

        // Eject card during transaction
        println("\n--- Ejecting Card During Transaction ---")
        cardProcessor.handleCardEjection(atmMachine)
        assertEquals("Transaction cancelled, card ejected", display.displayedMessage)
        println("✓ Card ejected during active transaction")
        println("✓ ATM displays: 'Transaction cancelled, card ejected'")

        // Verify state transitions back to IdleState
        assertEquals(IdleState::class.java, atmMachine.currentState.javaClass)
        println("✓ ATM returned to IDLE state (ready for new card)")
        println("=== Card Ejection Test Completed Successfully ===\n")
    }
}

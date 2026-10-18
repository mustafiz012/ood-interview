package atm.hardware.output

class ConsoleDisplay : Display {
    override var displayedMessage: String? = null
        private set

    override fun showMessage(message: String) {
        this.displayedMessage = message
    }
}

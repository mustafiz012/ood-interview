package atm.hardware.output

interface Display {
    fun showMessage(message: String)
    val displayedMessage: String?
}

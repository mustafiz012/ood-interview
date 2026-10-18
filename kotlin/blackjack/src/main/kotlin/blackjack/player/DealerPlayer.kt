package blackjack.player

class DealerPlayer : Player {
    private val dealerName: String = "Dealer"
    private val dealerHand: Hand = Hand()

    override fun bet(bet: Int) {}
    override fun loseBet() {}
    override fun returnBet() {}
    override fun payout() {}

    override fun isBust(): Boolean = dealerHand.isBust()

    override fun getHand(): Hand = dealerHand

    override fun getBalance(): Int = 0

    override fun getName(): String = dealerName

    override fun getBet(): Int = 0
}

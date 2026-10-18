package blackjack.player

class RealPlayer(
    private val playerName: String,
    startBalance: Int
) : Player {
    private val playerHand: Hand = Hand()
    private var betAmount: Int = 0
    private var playerBalance: Int = startBalance

    override fun bet(bet: Int) {
        if (bet > playerBalance) {
            throw IllegalArgumentException("Bet is greater than balance")
        }
        this.betAmount = bet
        this.playerBalance -= bet
    }

    override fun loseBet() {
        this.betAmount = 0
    }

    override fun returnBet() {
        this.playerBalance += betAmount
        this.betAmount = 0
    }

    override fun payout() {
        this.playerBalance += betAmount * 2
        this.betAmount = 0
    }

    override fun isBust(): Boolean = playerHand.isBust()

    override fun getHand(): Hand = playerHand

    override fun getBalance(): Int = playerBalance

    override fun getName(): String = playerName

    override fun getBet(): Int = betAmount
}

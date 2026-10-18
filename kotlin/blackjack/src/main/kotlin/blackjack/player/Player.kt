package blackjack.player

interface Player {
    fun bet(bet: Int)
    fun loseBet()
    fun returnBet()
    fun payout()
    fun isBust(): Boolean
    fun getHand(): Hand
    fun getBalance(): Int
    fun getName(): String
    fun getBet(): Int
}

package blackjack.deck

data class Card(val rank: Rank, val suit: Suit) {
    fun getRankValues(): IntArray = rank.rankValues

    override fun toString(): String = "Card{rank=$rank, suit=$suit}"
}

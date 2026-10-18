package blackjack.deck

import java.util.Random

class Deck {
    @JvmField
    var nextCardIndex: Int = 0
    @JvmField
    var cards: MutableList<Card> = mutableListOf()

    init {
        initializeDeck()
    }

    private fun initializeDeck() {
        cards = mutableListOf()
        for (suit in Suit.values()) {
            for (rank in Rank.values()) {
                cards.add(Card(rank, suit))
            }
        }
        nextCardIndex = 0
    }

    fun shuffle() {
        cards.shuffle(Random(System.currentTimeMillis()))
    }

    fun shuffle(seed: Long) {
        cards.shuffle(Random(seed))
    }

    fun draw(): Card? {
        if (isEmpty()) return null
        val drawCard = cards[nextCardIndex]
        nextCardIndex++
        return drawCard
    }

    val remainingCardCount: Int
        get() = cards.size - nextCardIndex

    fun isEmpty(): Boolean = remainingCardCount == 0

    fun reset() {
        nextCardIndex = 0
    }
}

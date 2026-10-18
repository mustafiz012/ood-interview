package blackjack.player

import blackjack.deck.Card
import java.util.Collections
import java.util.SortedSet
import java.util.TreeSet

class Hand {
    @JvmField
    val handCards: MutableList<Card> = mutableListOf()
    @JvmField
    val possibleValues: SortedSet<Int> = TreeSet()

    fun addCard(card: Card?) {
        requireNotNull(card) { "Cannot add null card to hand" }
        handCards.add(card)

        if (possibleValues.isEmpty()) {
            for (value in card.getRankValues()) {
                possibleValues.add(value)
            }
        } else {
            val newPossibleValue = TreeSet<Int>()
            for (value in possibleValues) {
                for (cardValue in card.getRankValues()) {
                    newPossibleValue.add(value + cardValue)
                }
            }
            possibleValues.clear()
            possibleValues.addAll(newPossibleValue)
        }
    }

    fun getCards(): List<Card> = Collections.unmodifiableList(handCards)

    fun getPossibleValues(): SortedSet<Int> = Collections.unmodifiableSortedSet(possibleValues)

    fun clear() {
        handCards.clear()
        possibleValues.clear()
    }

    fun isBust(): Boolean {
        return if (possibleValues.isEmpty()) false else possibleValues.first() > 21
    }

    override fun toString(): String {
        return "Hand{handCards=$handCards, possibleValue=$possibleValues}"
    }
}

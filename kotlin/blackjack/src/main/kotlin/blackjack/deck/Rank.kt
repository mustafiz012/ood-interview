package blackjack.deck

enum class Rank(val rankValues: IntArray) {
    ACE(intArrayOf(1, 11)),
    TWO(intArrayOf(2)),
    THREE(intArrayOf(3)),
    FOUR(intArrayOf(4)),
    FIVE(intArrayOf(5)),
    SIX(intArrayOf(6)),
    SEVEN(intArrayOf(7)),
    EIGHT(intArrayOf(8)),
    NINE(intArrayOf(9)),
    TEN(intArrayOf(10)),
    JACK(intArrayOf(10)),
    QUEEN(intArrayOf(10)),
    KING(intArrayOf(10))
}

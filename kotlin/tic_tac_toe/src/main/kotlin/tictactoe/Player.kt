package tictactoe

data class Player(
    val name: String,
    val symbol: Char
) {
    override fun toString(): String = name
}

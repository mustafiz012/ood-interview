package tictactoe

data class Move(
    val colIndex: Int,
    val rowIndex: Int,
    val player: Player
)

package tictactoe

import java.util.Arrays
import java.util.Optional

class Board {
    private val grid: Array<Array<Player?>> = Array(3) { arrayOfNulls<Player>(3) }

    fun updateBoard(colIndex: Int, rowIndex: Int, player: Player) {
        if (grid[colIndex][rowIndex] == null) {
            grid[colIndex][rowIndex] = player
        }
    }

    fun getWinner(): Optional<Player> {
        // Check rows
        for (i in 0..2) {
            val first = grid[i][0]
            if (first != null && grid[i].all { it == first }) {
                return Optional.of(first)
            }
        }

        // Check columns
        for (j in 0..2) {
            val first = grid[0][j]
            if (first != null && (0..2).all { grid[it][j] == first }) {
                return Optional.of(first)
            }
        }

        // Main diagonal
        val topLeft = grid[0][0]
        if (topLeft != null && (0..2).all { grid[it][it] == topLeft }) {
            return Optional.of(topLeft)
        }

        // Anti diagonal
        val topRight = grid[0][2]
        if (topRight != null && (0..2).all { grid[it][2 - it] == topRight }) {
            return Optional.of(topRight)
        }

        return Optional.empty()
    }

    fun isFull(): Boolean {
        return grid.all { row -> row.all { it != null } }
    }

    fun reset() {
        for (row in grid) {
            Arrays.fill(row, null)
        }
    }

    fun printBoard(): String = Arrays.deepToString(grid)

    fun getPlayerAt(colIndex: Int, rowIndex: Int): Player? = grid[colIndex][rowIndex]
}

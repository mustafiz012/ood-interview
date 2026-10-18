package tictactoe

import java.util.Arrays

class Game(playerX: Player, playerY: Player) {
    private val board: Board = Board()
    private val scoreTracker: ScoreTracker = ScoreTracker()
    private var players: Array<Player> = arrayOf(playerX, playerY)
    private var currentPlayerIndex: Int = 0

    init {
        startNewGame(playerX, playerY)
    }

    fun startNewGame(playerX: Player, playerY: Player) {
        board.reset()
        players = arrayOf(playerX, playerY)
        currentPlayerIndex = 0
    }

    fun makeMove(colIndex: Int, rowIndex: Int, player: Player) {
        if (getGameStatus() == GameCondition.ENDED) {
            throw IllegalStateException("game ended")
        }
        if (players[currentPlayerIndex] != player) {
            throw IllegalArgumentException("not the current player")
        }
        if (board.getPlayerAt(colIndex, rowIndex) != null) {
            throw IllegalArgumentException("board position is taken")
        }
        board.updateBoard(colIndex, rowIndex, player)
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size
        if (getGameStatus() == GameCondition.ENDED) {
            scoreTracker.reportGameResult(players[0], players[1], board.getWinner())
        }
    }

    fun getGameStatus(): GameCondition {
        val winner = board.getWinner()
        if (winner.isPresent) {
            return GameCondition.ENDED
        }
        return if (board.isFull()) GameCondition.ENDED else GameCondition.IN_PROGRESS
    }

    fun getStateString(): String {
        return "Between players " + Arrays.deepToString(players) + ", state: " + getGameStatus()
    }

    fun getCurrentPlayer(): Player = players[currentPlayerIndex]

    fun printGameState() {
        // Printing game state
    }

    fun getScoreTracker(): ScoreTracker = scoreTracker
}

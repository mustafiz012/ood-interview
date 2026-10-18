package tictactoe

import java.util.Optional

class ScoreTracker {
    private val playerRatings: HashMap<Player, Int> = HashMap()

    fun reportGameResult(player1: Player, player2: Player, winningPlayer: Optional<Player>) {
        if (winningPlayer.isPresent) {
            val winner = winningPlayer.get()
            val loser = if (player1 == winner) player2 else player1
            playerRatings[winner] = (playerRatings[winner] ?: 0) + 1
            playerRatings[loser] = (playerRatings[loser] ?: 0) - 1
        }
    }

    fun getTopPlayers(): List<Player> {
        return playerRatings.entries
            .sortedByDescending { it.value }
            .map { it.key }
    }

    fun getRank(player: Player): Int {
        val sortedPlayers = getTopPlayers()
        return sortedPlayers.indexOf(player) + 1
    }

    fun getPlayerRatings(): HashMap<Player, Int> = playerRatings
}

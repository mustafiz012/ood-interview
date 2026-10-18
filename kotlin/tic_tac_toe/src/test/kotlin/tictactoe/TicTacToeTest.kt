package tictactoe

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

class TicTacToeTest {
    @Test
    fun testPlay() {
        println("\n=== Testing Tic-Tac-Toe Game System: Complete Game Play ===")

        println("\n--- Creating Players ---")
        val playerX = Player("X", 'X')
        val playerY = Player("Y", 'Y')
        println("✓ Created Player X (symbol: X)")
        println("✓ Created Player Y (symbol: Y)")

        println("\n--- Initializing Game ---")
        val game = Game(playerX, playerY)
        println("✓ New tic-tac-toe game started")
        assertEquals(GameCondition.IN_PROGRESS, game.getGameStatus())
        assertEquals(playerX, game.getCurrentPlayer())
        println("✓ Game status: IN_PROGRESS")
        println("✓ Current player: Player X (first to move)")

        println("\n--- Making Game Moves ---")
        println("✓ Player X moves at position (0,0) - Top-left corner")
        game.makeMove(0, 0, game.getCurrentPlayer())
        assertEquals(playerY, game.getCurrentPlayer())
        println("✓ Turn switched to Player Y")

        println("✓ Player Y moves at position (1,0) - Middle-left")
        game.makeMove(1, 0, game.getCurrentPlayer())
        println("✓ Player X moves at position (0,1) - Top-center")
        game.makeMove(0, 1, game.getCurrentPlayer())
        println("✓ Player Y moves at position (1,1) - Center")
        game.makeMove(1, 1, game.getCurrentPlayer())
        println("✓ Player X moves at position (0,2) - Top-right corner")
        game.makeMove(0, 2, game.getCurrentPlayer())

        println("\n--- Game Completion ---")
        assertEquals(GameCondition.ENDED, game.getGameStatus())
        println("✓ Game status: ENDED")
        println("✓ Player X wins with a horizontal line in the top row!")
        println("✓ Final game board:")
        game.printGameState()

        println("\n--- Score Tracking ---")
        val scoreTracker = game.getScoreTracker()
        assertEquals(1, scoreTracker.getPlayerRatings()[playerX])
        println("✓ Player X rating: " + scoreTracker.getPlayerRatings()[playerX] + " point")
        println("✓ Player Y rating: " + scoreTracker.getPlayerRatings()[playerY] + " points")

        assertEquals(1, scoreTracker.getRank(playerX))
        assertEquals(2, scoreTracker.getRank(playerY))
        assertEquals(2, scoreTracker.getTopPlayers().size)
        println("✓ Player rankings:")
        println("  - Player X rank: " + scoreTracker.getRank(playerX) + " (Winner)")
        println("  - Player Y rank: " + scoreTracker.getRank(playerY) + " (Runner-up)")
        println("  - Total players tracked: " + scoreTracker.getTopPlayers().size)
        println("=== Tic-Tac-Toe Game Test Completed Successfully ===\n")
    }

    @Test
    fun testDuplicatePlay() {
        println("\n=== Testing Tic-Tac-Toe: Duplicate Move Validation ===")

        println("\n--- Setting Up Game ---")
        val playerX = Player("X", 'X')
        val playerY = Player("Y", 'Y')
        val game = Game(playerX, playerY)
        println("✓ New game started with Player X and Player Y")

        println("\n--- Making First Move ---")
        println("✓ Player X moves at position (1,1) - Center")
        game.makeMove(1, 1, game.getCurrentPlayer())
        println("✓ Move successful - center position occupied")

        println("\n--- Attempting Duplicate Move ---")
        println("✓ Player Y attempts to move at position (1,1) - Already occupied")
        try {
            game.makeMove(1, 1, game.getCurrentPlayer())
            fail<Unit>("didn't catch IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
            println("✓ Duplicate move correctly rejected:")
            println("  - Expected behavior: Cannot place move on occupied position")
        }
        println("=== Duplicate Move Test Completed Successfully ===\n")
    }
}

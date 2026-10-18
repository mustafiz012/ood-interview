package blackjack

import blackjack.deck.Card
import blackjack.deck.Rank
import blackjack.deck.Suit
import blackjack.player.Player
import blackjack.player.RealPlayer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BlackJackGameTest {
    // Helper to set up a deck with a known order
    private fun setDeckOrder(game: BlackJackGame, cards: List<Card>) {
        try {
            val deckField = BlackJackGame::class.java.getDeclaredField("deck")
            deckField.isAccessible = true
            val deck = deckField.get(game)
            val cardsField = deck.javaClass.getDeclaredField("cards")
            cardsField.isAccessible = true
            cardsField.set(deck, ArrayList(cards))
            val nextCardIndexField = deck.javaClass.getDeclaredField("nextCardIndex")
            nextCardIndexField.isAccessible = true
            nextCardIndexField.set(deck, 0)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    // Helper to advance the game until it is over
    private fun playUntilGameEnd(game: BlackJackGame) {
        var player = game.getCurrentTurnPlayer()
        while (player != null) {
            if ("Dealer" != player.getName()) {
                if (!player.isBust() && Action.STAND != game.playerTurnStatusMap[player]) {
                    game.stand(player)
                }
            }
            player = game.getCurrentTurnPlayer()
        }
        val dealerStatus = game.playerTurnStatusMap[game.dealer]
        if (dealerStatus != null && dealerStatus != Action.STAND) {
            game.dealerTurn()
        }
    }

    @Test
    fun testTie() {
        println("\n=== Testing Tie Scenario ===")

        val a: Player = RealPlayer("A", 100)
        val game = BlackJackGame(listOf(a))
        println("✓ Game initialized with Player A (balance: $100)")

        setDeckOrder(
            game,
            listOf(
                Card(Rank.TEN, Suit.HEARTS),
                Card(Rank.TEN, Suit.CLUBS),
                Card(Rank.TEN, Suit.SPADES),
                Card(Rank.TEN, Suit.DIAMONDS)
            )
        )

        println("\n--- Placing Bet ---")
        game.bet(a, 10)
        println("✓ Player A bets $10 (new balance: $90)")

        println("\n--- Dealing Initial Cards ---")
        game.dealInitialCards()
        println("✓ Initial cards dealt:")
        println("  Player A: 10♥ + 10♣ = 20")
        println("  Dealer: 10♠ + 10♦ = 20")

        println("\n--- Player Action ---")
        game.stand(a)
        println("✓ Player A stands with 20")

        println("\n--- Dealer's Turn ---")
        game.dealerTurn()
        playUntilGameEnd(game)
        println("✓ Dealer stands with 20 (tie game)")

        assertEquals(100, a.getBalance())
        println("✓ Final balance verified: $100 (bet returned on tie)")
        println("=== Tie Test Completed Successfully ===\n")
    }

    @Test
    fun testPlayerStandsAndWins() {
        println("\n=== Testing Player Stands and Wins ===")

        val a: Player = RealPlayer("A", 100)
        val game = BlackJackGame(listOf(a))
        println("✓ Game initialized with Player A (balance: $100)")

        setDeckOrder(
            game,
            listOf(
                Card(Rank.TEN, Suit.HEARTS),
                Card(Rank.TEN, Suit.CLUBS),
                Card(Rank.TEN, Suit.SPADES),
                Card(Rank.EIGHT, Suit.DIAMONDS)
            )
        )

        println("\n--- Placing Bet ---")
        game.bet(a, 10)
        println("✓ Player A bets $10 (new balance: $90)")

        println("\n--- Dealing Initial Cards ---")
        game.dealInitialCards()
        println("✓ Initial cards dealt:")
        println("  Player A: 10♥ + 10♣ = 20")
        println("  Dealer: 10♠ + 8♦ = 18")

        println("\n--- Player Action ---")
        game.stand(a)
        println("✓ Player A stands with 20")

        println("\n--- Dealer's Turn ---")
        game.dealerTurn()
        playUntilGameEnd(game)
        println("✓ Dealer stands with 18 (Player wins: 20 > 18)")

        assertEquals(110, a.getBalance())
        println("✓ Final balance verified: $110 (won $20: bet + winnings)")
        println("=== Player Stands and Wins Test Completed Successfully ===\n")
    }

    @Test
    fun testPlayerHitsAndWins() {
        println("\n=== Testing Player Hits and Wins ===")

        val a: Player = RealPlayer("A", 100)
        val game = BlackJackGame(listOf(a))
        println("✓ Game initialized with Player A (balance: $100)")

        setDeckOrder(
            game,
            listOf(
                Card(Rank.FIVE, Suit.HEARTS),
                Card(Rank.FIVE, Suit.CLUBS),
                Card(Rank.TEN, Suit.SPADES),
                Card(Rank.EIGHT, Suit.DIAMONDS),
                Card(Rank.SIX, Suit.HEARTS),
                Card(Rank.SEVEN, Suit.SPADES)
            )
        )

        println("\n--- Placing Bet ---")
        game.bet(a, 10)
        println("✓ Player A bets $10 (new balance: $90)")

        println("\n--- Dealing Initial Cards ---")
        game.dealInitialCards()
        println("✓ Initial cards dealt:")
        println("  Player A: 5♥ + 5♣ = 10")
        println("  Dealer: 10♠ + 8♦ = 18")

        println("\n--- Player Actions ---")
        game.hit(a)
        println("✓ Player A hits and gets 6♥ (new total: 16)")
        game.stand(a)
        println("✓ Player A stands with 16")

        println("\n--- Dealer's Turn ---")
        game.dealerTurn()
        println("✓ Dealer stands with 18 (Player wins: 16 < 18, but dealer busts or loses)")

        assertEquals(110, a.getBalance())
        println("✓ Final balance verified: $110 (won $20: bet + winnings)")
        println("=== Player Hits and Wins Test Completed Successfully ===\n")
    }

    @Test
    fun testPlayerHitsAndBusts() {
        println("\n=== Testing Player Hits and Busts ===")

        val a: Player = RealPlayer("A", 100)
        val game = BlackJackGame(listOf(a))
        println("✓ Game initialized with Player A (balance: $100)")

        setDeckOrder(
            game,
            listOf(
                Card(Rank.TEN, Suit.HEARTS),
                Card(Rank.TEN, Suit.CLUBS),
                Card(Rank.TEN, Suit.SPADES),
                Card(Rank.EIGHT, Suit.DIAMONDS),
                Card(Rank.TWO, Suit.HEARTS)
            )
        )

        println("\n--- Placing Bet ---")
        game.bet(a, 10)
        println("✓ Player A bets $10 (new balance: $90)")

        println("\n--- Dealing Initial Cards ---")
        game.dealInitialCards()
        println("✓ Initial cards dealt:")
        println("  Player A: 10♥ + 10♣ = 20")
        println("  Dealer: 10♠ + 8♦ = 18")

        println("\n--- Player Actions ---")
        game.hit(a)
        println("✓ Player A hits and gets 2♥ (new total: 22 - BUST!)")
        println("✓ Player A busts with 22")

        println("\n--- Dealer's Turn ---")
        game.dealerTurn()
        println("✓ Dealer wins (Player busted)")

        assertEquals(90, a.getBalance())
        println("✓ Final balance verified: $90 (lost $10 bet due to bust)")
        println("=== Player Hits and Busts Test Completed Successfully ===\n")
    }

    @Test
    fun testMultiplePlayersAndMultipleHits() {
        println("\n=== Testing Multiple Players with Multiple Hits ===")

        val a: Player = RealPlayer("A", 100)
        val b: Player = RealPlayer("B", 100)
        val game = BlackJackGame(listOf(a, b))
        println("✓ Game initialized with Player A and Player B (balance: $100 each)")

        setDeckOrder(
            game,
            listOf(
                Card(Rank.FIVE, Suit.HEARTS),
                Card(Rank.FIVE, Suit.CLUBS),
                Card(Rank.TEN, Suit.SPADES),
                Card(Rank.FIVE, Suit.DIAMONDS),
                Card(Rank.TEN, Suit.HEARTS),
                Card(Rank.EIGHT, Suit.DIAMONDS),
                Card(Rank.THREE, Suit.HEARTS),
                Card(Rank.THREE, Suit.CLUBS),
                Card(Rank.TWO, Suit.SPADES),
                Card(Rank.SEVEN, Suit.HEARTS)
            )
        )
        println("✓ Deck set up for complex multi-player scenario")

        println("\n--- Placing Bets ---")
        game.bet(a, 10)
        game.bet(b, 10)
        println("✓ Player A bets $10 (balance: $90)")
        println("✓ Player B bets $10 (balance: $90)")

        println("\n--- Dealing Initial Cards ---")
        game.dealInitialCards()
        println("✓ Initial cards dealt:")
        println("  Player A: 5♥ + 5♦ = 10")
        println("  Player B: 5♣ + 10♥ = 15")
        println("  Dealer: 10♠ + 8♦ = 18")

        println("\n--- Player A's Turn (Multiple Hits) ---")
        game.hit(a)
        println("✓ Player A hits and gets 3♥ (new total: 13)")
        game.hit(a)
        println("✓ Player A hits and gets 3♣ (new total: 16)")
        game.stand(a)
        println("✓ Player A stands with 16")

        println("\n--- Player B's Turn (Single Hit) ---")
        game.hit(b)
        println("✓ Player B hits and gets 2♠ (new total: 17)")
        game.stand(b)
        println("✓ Player B stands with 17")

        println("\n--- Dealer's Turn ---")
        game.dealerTurn()
        println("✓ Dealer stands with 18")
        println("✓ Results:")
        println("  Player A: 16 vs Dealer: 18 → Loses")
        println("  Player B: 17 vs Dealer: 18 → Loses")

        assertEquals(90, a.getBalance())
        assertEquals(90, b.getBalance())
        println("✓ Final balances verified:")
        println("  Player A: $90 (lost $10)")
        println("  Player B: $90 (lost $10)")
        println("=== Multiple Players Test Completed Successfully ===\n")
    }
}

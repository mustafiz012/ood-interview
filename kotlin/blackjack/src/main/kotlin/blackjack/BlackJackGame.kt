package blackjack

import blackjack.deck.Card
import blackjack.deck.Deck
import blackjack.player.DealerPlayer
import blackjack.player.Player

class BlackJackGame(playersList: List<Player>) {
    @JvmField
    val deck: Deck = Deck()
    private val players: MutableList<Player> = mutableListOf()
    @JvmField
    val dealer: Player = DealerPlayer()
    private var currentPlayer: Player? = null

    @JvmField
    val playerTurnStatusMap: MutableMap<Player, Action?> = mutableMapOf()
    var currentPhase: GamePhase = GamePhase.STARTED

    init {
        for (player in playersList) {
            players.add(player)
            playerTurnStatusMap[player] = null
        }
        playerTurnStatusMap[dealer] = null
        deck.shuffle()
    }

    fun getNextEligiblePlayer(): Player? {
        if (currentPlayer != null &&
            playerTurnStatusMap[currentPlayer] != Action.STAND &&
            currentPlayer?.isBust() == false
        ) {
            return currentPlayer
        }

        if (currentPlayer == null) {
            for (player in players) {
                if (playerTurnStatusMap[player] != Action.STAND && !player.isBust()) {
                    currentPlayer = player
                    return currentPlayer
                }
            }
        }

        val currentPlayerIndex = players.indexOf(currentPlayer)
        for (i in currentPlayerIndex + 1 until players.size) {
            val player = players[i]
            if (playerTurnStatusMap[player] != Action.STAND && !player.isBust()) {
                if (currentPlayer == dealer) {
                    if (playerTurnStatusMap[dealer] != Action.STAND) {
                        dealerTurn()
                    }
                    return currentPlayer
                }
                currentPlayer = player
                return currentPlayer
            }
        }

        return null
    }

    fun dealerTurn() {
        while ((dealer.getHand().getPossibleValues().lastOrNull() ?: 0) < 17) {
            val newDraw = deck.draw()
            dealer.getHand().addCard(newDraw)
        }
        playerTurnStatusMap[dealer] = Action.STAND
        checkGameEndCondition()
    }

    fun startNewRound() {
        deck.reset()
        for (player in playerTurnStatusMap.keys) {
            player.getHand().clear()
        }
        dealer.getHand().clear()
        for (key in playerTurnStatusMap.keys) {
            playerTurnStatusMap[key] = null
        }
        currentPlayer = null
        currentPhase = GamePhase.STARTED
    }

    fun dealInitialCards() {
        if (currentPhase != GamePhase.BET_PLACED) {
            throw IllegalStateException("All players must bet before dealing")
        }
        for (player in players) {
            player.getHand().addCard(deck.draw())
        }
        dealer.getHand().addCard(deck.draw())
        for (player in players) {
            player.getHand().addCard(deck.draw())
        }
        dealer.getHand().addCard(deck.draw())
        currentPhase = GamePhase.INITIAL_CARD_DRAWN
    }

    fun bet(player: Player, bet: Int) {
        if (currentPhase != GamePhase.STARTED) {
            throw IllegalStateException("Bets must be placed at the start of the round")
        }
        player.bet(bet)
        if (players.filter { it !is DealerPlayer }.all { it.getBet() > 0 }) {
            currentPhase = GamePhase.BET_PLACED
        }
    }

    fun hit(player: Player) {
        if (playerTurnStatusMap[player] == Action.STAND) {
            throw IllegalStateException("Player has already stood")
        }
        if (player.isBust()) {
            throw IllegalStateException("Player is already bust")
        }
        val drawnCard = deck.draw()
        player.getHand().addCard(drawnCard)
        playerTurnStatusMap[player] = Action.HIT
    }

    fun stand(player: Player) {
        if (playerTurnStatusMap[player] == Action.STAND) {
            throw IllegalStateException("Player has already stood")
        }
        if (player.isBust()) {
            throw IllegalStateException("Player is already bust")
        }
        playerTurnStatusMap[player] = Action.STAND
    }

    private fun checkGameEndCondition() {
        val allPlayersDone = players.all { playerTurnStatusMap[it] == Action.STAND || it.isBust() }
        if (!allPlayersDone) {
            return
        }

        val dealerValue = dealer.getHand().getPossibleValues().lastOrNull() ?: 0
        val dealerBusts = dealer.isBust()

        for (player in players) {
            if (player.isBust()) {
                player.loseBet()
            } else {
                val playerValue = player.getHand().getPossibleValues().lastOrNull() ?: 0
                if (dealerBusts || playerValue > dealerValue) {
                    player.payout()
                } else if (playerValue == dealerValue) {
                    player.returnBet()
                } else {
                    player.loseBet()
                }
            }
        }
        currentPhase = GamePhase.END
    }

    fun getStateString(): String {
        val stringBuilder = StringBuilder()
        for (player in playerTurnStatusMap.keys) {
            stringBuilder
                .append(player.getName()).append(": ")
                .append(player.getHand().toString()).append(", ")
                .append(playerTurnStatusMap[player])
                .append("\n")
        }
        return stringBuilder.toString()
    }

    fun getDeck(): Deck = deck

    fun getCurrentTurnPlayer(): Player? = currentPlayer
}

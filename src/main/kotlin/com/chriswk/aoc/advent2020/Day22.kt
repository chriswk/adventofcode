package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import org.apache.logging.log4j.LogManager
import java.lang.IllegalStateException

class Day22 : AdventDay(2020, 22) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day22()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }

        val logger = LogManager.getLogger(Day22::class.java)
    }


    fun decideRoundWinner(p1: Int, p2: Int): RoundResult {
        return when {
            p1 > p2 -> {
                RoundResult.Player1Win
            }
            p2 > p1 -> {
                RoundResult.Player2Win
            }
            else -> throw IllegalStateException("Can not handle ties")
        }
    }

    fun playRound(game: Game): Game {
        val (p1, p2) = game
        val p1Card = p1.first()
        val p2Card = p2.first()
        return when (decideRoundWinner(p1Card, p2Card)) {
            RoundResult.Player1Win -> p1.drop(1) + listOf(p1Card, p2Card) to p2.drop(1)
            RoundResult.Player2Win -> p1.drop(1) to p2.drop(1) + listOf(p2Card, p1Card)
        }
    }

    tailrec fun playGamePart1(game: Game, round: Int = 1): Deck {
        return when {
            game.first.isEmpty() -> {
                logger.info("Player 2 won game")
                game.second
            }
            game.second.isEmpty() -> {
                logger.info("Player 1 won game")
                game.first
            }
            else -> {
                playGamePart1(playRound(game), round + 1)
            }
        }
    }

    fun playRoundPart2(game: Game, recursive: Boolean = false): Game {
        val (p1, p2) = game
        val p1Card = p1.first()
        val p2Card = p2.first()
        val p1Remainder = p1.drop(1)
        val p2Remainder = p2.drop(1)
        return if (p1Card <= p1Remainder.size && p2Card <= p2Remainder.size) {
            val recursiveGame = p1Remainder.take(p1Card) to p2Remainder.take(p2Card)
            when (playGamePart2(game = recursiveGame, recursive = true).second) {
                GameState.Player1Win -> {
                    p1Remainder + listOf(p1Card, p2Card) to p2Remainder
                }
                GameState.Player2Win -> {
                    p1Remainder to p2Remainder + listOf(p2Card, p1Card)
                }
            }
        } else {
            when (decideRoundWinner(p1Card, p2Card)) {
                RoundResult.Player1Win -> p1Remainder + listOf(p1Card, p2Card) to p2Remainder
                RoundResult.Player2Win -> p1Remainder to p2Remainder + listOf(p2Card, p1Card)
            }

        }
    }

    tailrec fun playGamePart2(
        game: Game,
        previousP1Decks: Set<List<Int>> = emptySet(),
        previousP2Decks: Set<List<Int>> = emptySet(),
        round: Int = 1,
        recursive: Boolean = false
    ): Pair<Deck, GameState> {
        return when {
            game.first.isEmpty() -> {
                game.second to GameState.Player2Win
            }
            game.second.isEmpty() -> {
                game.first to GameState.Player1Win
            }
            previousP1Decks.contains(game.first) || previousP2Decks.contains(game.second) -> {
                game.first to GameState.Player1Win
            }
            else -> {
                val previousP1: Set<Deck> = previousP1Decks + setOf(game.first)
                val previousP2: Set<Deck> = previousP2Decks + setOf(game.second)
                playGamePart2(
                    game = playRoundPart2(game, recursive),
                    previousP1Decks = previousP1,
                    previousP2Decks = previousP2,
                    recursive = recursive,
                    round = round + 1
                )
            }
        }
    }

    fun buildDecks(input: String): Game {
        val (player1, player2) = input.split("Player 2:\n")
        val p1List = player1.substringAfter("Player 1:\n").trim().lines().map { it.toInt() }
        val p2List = player2.trim().lines().map { it.toInt() }
        return p1List to p2List
    }

    fun scoreCards(winningDeck: Deck): Int {
        return winningDeck.fold((0 to winningDeck.size)) { (acc, multiplier), next ->
            val f = acc + (multiplier * next)
            f to multiplier - 1
        }.first
    }

    fun part1(): Int {
        val game = buildDecks(inputAsString)
        val deck = playGamePart1(game)
        return scoreCards(deck)
    }

    fun part2(): Int {
        val game = buildDecks(inputAsString)
        val (winningDeck, _) = playGamePart2(game)
        return scoreCards(winningDeck)
    }

}

enum class RoundResult {
    Player1Win,
    Player2Win
}

enum class GameState {
    Player1Win,
    Player2Win
}
typealias Deck = List<Int>
typealias Game = Pair<Deck, Deck>
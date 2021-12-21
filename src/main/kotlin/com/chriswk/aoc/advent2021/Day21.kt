package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day21: AdventDay(2021, 21) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day21()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun parseInput(input: List<String>): Game {
        val players = input.map { Player(it) }
        return Game(players[0], players[1], 0)
    }

    fun playTurn(game: Game): Sequence<Game>  {
        return generateSequence(game) { g ->
            val move = listOf(g.rolls + 1, g.rolls + 2, g.rolls + 3)
            if (g.player1) {
                val d = (g.p1.position + move.sum()) % 10
                if (d == 0) {
                    val newPlayer = g.p1.copy(position = 10, score = g.p1.score + 10)
                    g.copy(rolls = g.rolls + 3, p1 = newPlayer, player1 = false)
                } else {
                    val newPlayer = g.p1.copy(position = d, score = g.p1.score + d)
                    g.copy(rolls = g.rolls + 3, p1 = newPlayer, player1 = false)
                }
            } else {
                val d = (g.p2.position + move.sum()) % 10
                if (d == 0) {
                    val newPlayer = g.p2.copy(position = 10, score = g.p2.score + 10)
                    g.copy(rolls = g.rolls + 3, p2 = newPlayer, player1 = true)
                } else {
                    val newPlayer = g.p2.copy(position = d, score = g.p2.score + d)
                    g.copy(rolls = g.rolls + 3, p2 = newPlayer, player1 = true)
                }
            }
        }
    }

    fun Int.gauss(): Long {
        return (this * this + 1) / 2L
    }

    data class Player(val startingPosition: Int, val position: Int = startingPosition, val score: Int = 0) {
        constructor(s: String): this(startingPosition = s.split(":")[1].trim().toInt())
    }
    data class Game(val p1: Player, val p2: Player, val rolls: Int, val player1: Boolean = true)



    fun part1(): Long {
        val game = parseInput(inputAsLines)
        val g = playTurn(game).first { it.p1.score >= 1000 || it.p2.score >= 1000 }
        return if (g.p1.score >= 1000) {
            g.p2.score * g.rolls.toLong()
        } else {
            g.p1.score * g.rolls.toLong()
        }
    }

    fun part2(): Long {
        val players = inputAsLines.map { Player(it) }
        return Score(players[0].startingPosition, players[1].startingPosition, 0, 0).let { p -> maxOf(p.first, p.second) }
    }

    data class LongPair(val first: Long, val second: Long) {
        override fun toString(): String = "($first, $second)"
    }

    infix fun Long.to(other: Long): LongPair = LongPair(this, other)

    private object Score {
        private val dice = sequence {
            for (i in 1..3) for (j in 1..3) for (k in 1..3) yield(i + j + k)
        }.groupingBy { it }.eachCount()
        private val x = LongArray(44100)
        private val y = LongArray(44100)

        operator fun invoke(player1: Int, player2: Int, score1: Int, score2: Int): LongPair {
            val i = player1 + 10 * player2 + 100 * score1 + 2100 * score2 - 11
            if (x[i] == 0L && y[i] == 0L) {
                var x1 = 0L
                var y1 = 0L
                for ((d, n) in dice) {
                    val play = (player1 + d - 1) % 10 + 1
                    if (score1 + play < 21) {
                        val (x2, y2) = this(player2, play, score2, score1 + play)
                        x1 += n * y2
                        y1 += n * x2
                    } else {
                        x1 += n
                    }
                }
                x[i] = x1
                y[i] = y1
            }
            return LongPair(x[i], y[i])
        }
    }

}

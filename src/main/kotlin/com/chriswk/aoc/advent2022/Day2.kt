package com.chriswk.aoc.advent2022

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day2 : AdventDay(2022, 2) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>): Unit {
            val day = Day2()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    enum class Play(val score: Int) {
        Rock(1),
        Paper(2),
        Scissors(3);

        fun win(): Play {
            return when(this) {
                Rock -> Scissors
                Paper -> Rock
                Scissors -> Paper
            }
        }

        fun lose(): Play {
            return when(this) {
                Rock -> Paper
                Paper -> Scissors
                Scissors -> Rock
            }
        }

    }

    data class Round(val opponent: Play, val player: Play) {
        private val outcomeValue: Int = if (opponent == player) { 3 } else if (player.win() == opponent) { 6 } else { 0 }
        val score: Int = player.score + outcomeValue

        companion object {
            fun fromLine(line: String): Round {
                val (opponentChoice, playerChoice) = line.split(" ")
                val opponent = when (opponentChoice) {
                    "A" -> Play.Rock
                    "B" -> Play.Paper
                    "C" -> Play.Scissors
                    else -> throw IllegalStateException("Don't know how to interpret opponent state")
                }
                val player = when (playerChoice) {
                    "Y" -> Play.Paper
                    "X" -> Play.Rock
                    "Z" -> Play.Scissors
                    else -> throw IllegalStateException("Don't know how to interpret player state")
                }
                return Round(opponent, player)
            }

            fun fromLinePart2(line: String): Round {
                val (opponentChoice, playerChoice) = line.split(" ")
                val opponent = when (opponentChoice) {
                    "A" -> Play.Rock
                    "B" -> Play.Paper
                    "C" -> Play.Scissors
                    else -> throw IllegalStateException("Don't know how to interpret opponent state")
                }
                val player = when (playerChoice) {
                    "Y" -> opponent
                    "X" -> opponent.win()
                    "Z" -> opponent.lose()
                    else -> throw IllegalStateException("Don't know how to interpret player state")
                }
                return Round(opponent, player)
            }
        }
    }

    fun parseInput(input: List<String>): List<Round> {
        return input.map(Round::fromLine)
    }

    fun parseInputPart2(input: List<String>): List<Round> {
        return input.map(Round::fromLinePart2)
    }

    fun part1(): Int {
        return parseInput(inputAsLines).sumOf { it.score }
    }

    fun part2(): Int {
        return parseInputPart2(inputAsLines).sumOf { it.score }
    }
}

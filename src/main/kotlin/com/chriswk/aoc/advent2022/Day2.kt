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
        Rock(1) {
            override fun win(): Play {
                return Paper
            }

            override fun lose(): Play {
                return Scissors
            }

            override fun draw(): Play {
                return Rock
            }
        },
        Paper(2) {
            override fun win(): Play {
                return Scissors
            }

            override fun lose(): Play {
                return Rock
            }

            override fun draw(): Play {
                return Paper
            }
        },
        Scissors(3) {
            override fun win(): Play {
                return Rock
            }

            override fun lose(): Play {
                return Paper
            }

            override fun draw(): Play {
                return Scissors
            }
        };
        abstract fun win(): Play
        abstract fun lose(): Play
        abstract fun draw(): Play
    }

    data class Round(val opponent: Play, val player: Play) {
        fun score(): Int {
            return player.score + when (opponent) {
                Play.Rock -> when (player) {
                    Play.Rock -> 3
                    Play.Paper -> 6
                    Play.Scissors -> 0
                }

                Play.Scissors -> when (player) {
                    Play.Rock -> 6
                    Play.Scissors -> 3
                    Play.Paper -> 0
                }

                Play.Paper -> when (player) {
                    Play.Rock -> 0
                    Play.Scissors -> 6
                    Play.Paper -> 3
                }
            }
        }

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
                    "Y" -> opponent.draw()
                    "X" -> opponent.lose()
                    "Z" -> opponent.win()
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
        return parseInput(inputAsLines).sumOf { it.score() }
    }

    fun part2(): Int {
        return parseInputPart2(inputAsLines).sumOf { it.score() }
    }
}

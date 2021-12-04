package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Day4 : AdventDay(2021, 4) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day4()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }

        val logger: Logger = LogManager.getLogger(Day4::class.java)
    }

    fun part1(): Int {
        val (draws, boards) = parseInput(inputAsLines)
        val (hadToDraw, winningBoard) = playGame(draws, boards)
        return winningBoard.score(hadToDraw)
    }

    fun part2(): Int {
        val (draws, boards) = parseInput(inputAsLines)
        val (hadToDraw, winningBoard) = playGameLast(draws, boards)
        return winningBoard.score(hadToDraw)
    }

    fun playGame(draws: List<Int>, boards: List<Bingo>): Pair<List<Int>, Bingo> {
        val minimumDraws = draws.take(5)
        val hasAWinner = draws.drop(5).asSequence().runningFold(minimumDraws) { drawn, nextNumber ->
            logger.info("Drawing $nextNumber. So far: $drawn")
            drawn + nextNumber
        }.first { drawn ->
            val winner = boards.find { it.hasBingo(drawn) }
            logger.info(winner)
            winner != null
        }
        return hasAWinner to boards.first { it.hasBingo(hasAWinner) }
    }

    fun playGameLast(draws: List<Int>, boards: List<Bingo>): Pair<List<Int>, Bingo> {
        val minimumDraws = draws.take(5)
        val lastWinner = draws.drop(5).asSequence().runningFold(minimumDraws) { drawn, nextNumber ->
            drawn + nextNumber
        }.first { drawn ->
            boards.all { it.hasBingo(drawn) }
        }
        return lastWinner to boards.filterNot { it.hasBingo(lastWinner.dropLast(1)) }.first()
    }

    fun parseInput(input: List<String>): Pair<List<Int>, List<Bingo>> {
        val draws = input.first().split(",").map { it.trim().toInt() }
        val boards = input.drop(2).chunked(6).map { board ->
            val parsedBoard = board.take(5).flatMap { row ->
                val split = row.trim().split(" ").filter { it.isNotBlank() }
                split.map { it.toInt() }
            }
            Bingo(parsedBoard)
        }
        return draws to boards
    }

    data class Bingo(val board: List<Int>) {
        val rows = board.chunked(5)
        val columns: List<List<Int>> = (0.until(5)).map { col ->
            (0.until(5)).map { row ->
                board[col + (row * 5)]
            }
        }

        fun hasBingo(marked: List<Int>): Boolean {
            return rows.any { marked.containsAll(it) } || columns.any { marked.containsAll(it) }
        }

        fun matches(marked: List<Int>): List<Int> {
            return board.filter { marked.contains(it) }
        }

        fun score(drawn: List<Int>): Int {
            return board.filterNot { drawn.contains(it) }.sum() * drawn.last()
        }
    }
}

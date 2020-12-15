package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day15 : AdventDay(2020, 15) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day15()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun findNthNumber(numbers: String, nth: Int): Int {
        val numberList = parseToNumberList(numbers)
        return if (nth - 1 < numberList.size) {
            numberList[nth - 1]
        } else {
            readNumbers(numberList).first { it.second == nth }.first
        }
    }

    fun parseToNumberList(numbers: String): List<Int> {
        return numbers.split(",").map { it.toInt() }
    }

    fun readNumbers(numbers: List<Int>): Sequence<Pair<Int, Int>> {
        val numbersSeen = numbers.dropLast(1).mapIndexed { idx, n -> n to idx + 1 }.toMap().toMutableMap()
        return generateSequence(numbers.last() to numbers.size) { (previous, previousTurn) ->
            val thisTurn = previousTurn + 1
            if (numbersSeen.containsKey(previous)) {
                val diff = previousTurn - numbersSeen.getValue(previous)
                numbersSeen[previous] = previousTurn
                diff to thisTurn
            } else {
                numbersSeen[previous] = previousTurn
                0 to thisTurn
            }
        }
    }

    fun part1(): Int {
        return findNthNumber(inputAsString, 2020)
    }

    fun part2(): Int {
        return findNthNumber(inputAsString, 30000000)
    }

}
package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day9: AdventDay(2020, 9) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day9()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun availableSums(numbers: List<Long>): Set<Long> {
        return numbers.flatMapIndexed { i, x ->
            if (i < numbers.size - 1) {
                numbers.drop(i).map { x + it }
            } else {
                listOf(0)
            }
        }.toSet()
    }

    fun findFirstNumberWhichIsNotASum(preambleSize: Int, numbers: Sequence<List<Long>>): Long? {
        return findWeakness(preambleSize, numbers)?.last()
    }
    fun findWeakness(preambleSize: Int, numbers: Sequence<List<Long>>): List<Long>? {
        return numbers.firstOrNull {
            !availableSums(it.take(preambleSize)).contains(it.last())
        }
    }

    fun part1(): Long {
        val numberSequence = inputAsLines.asSequence().map { it.toLong() }.windowed(26)
        val ans = findFirstNumberWhichIsNotASum(25, numberSequence)
        if (ans == null) {
            throw IllegalStateException("Could not find a valid number")
        } else {
            return ans
        }
    }

    fun findContinguousSumForWeakness(target: Long, numbers: List<Long>): List<Long> {
        return (3 until numbers.size).asSequence().mapNotNull { numbersToSum ->
            numbers.windowed(numbersToSum).firstOrNull { it.sum() == target }
        }.first()
    }
    fun findMinMax(numbers: List<Long>): Pair<Long, Long> {
        return numbers.minOrNull()!! to numbers.maxOrNull()!!
    }

    fun part2(): Long {
        val allNumbers = inputAsLines.map { it.toLong() }
        val numberSequence = inputAsLines.asSequence().map { it.toLong() }.windowed(26)
        val numbers = findWeakness(25, numberSequence)!!
        val sumToFind = numbers.last()
        val sumFound = findContinguousSumForWeakness(sumToFind, allNumbers)
        val (min, max) = findMinMax(sumFound)
        return min + max
    }

}
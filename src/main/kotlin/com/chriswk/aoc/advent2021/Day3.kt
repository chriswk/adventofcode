package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day3 : AdventDay(2021, 3) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day3()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }


    fun epsilon(gamma: String): String {
        return gamma.map {
            when (it) {
                '0' -> '1'
                '1' -> '0'
                else -> ""
            }
        }.joinToString(separator = "")
    }

    fun epsilonRate(epsilon: String): Int {
        return epsilon.toInt(2)
    }

    fun gamma(input: List<String>): String {
        return (0.until(input[0].length)).map { idx ->
            mostUsedBit(input, idx)
        }.joinToString(separator = "")
    }

    fun gammaRate(gamma: String): Int {
        return gamma.toInt(2)
    }

    fun mostUsedBit(input: List<String>, idx: Int): Char {
        val (zeros, ones) = input.map { it[idx] }.partition { it == '0' }
        return if (zeros.size >= ones.size) '0' else '1'
    }

    fun leastUsedBit(input: List<String>, idx: Int): Char {
        val (zeros, ones) = input.map { it[idx] }.partition { it == '0' }
        return if (zeros.size <= ones.size) '0' else '1'
    }

    fun mostUsedBitOnes(input: List<String>, idx: Int): Char {
        val (zeros, ones) = input.map { it[idx] }.partition { it == '0' }
        return if (ones.size >= zeros.size) '1' else '0'
    }

    fun oxygen(input: List<String>): String {
        return (0 until input[0].length).fold(input) { remaining, idx ->
            if (remaining.size == 1) {
                remaining
            } else {
                val toKeep = mostUsedBitOnes(remaining, idx)
                remaining.filter { it[idx] == toKeep }
            }
        }.first()
    }

    fun co2(input: List<String>): String {
        return (0 until input[0].length).fold(input) { remaining, idx ->
            if (remaining.size == 1) {
                remaining
            } else {
                val toKeep = leastUsedBit(remaining, idx)
                remaining.filter { it[idx] == toKeep }
            }
        }.first()

    }

    fun Char.flip() = when (this) {
        '0' -> '1'
        '1' -> '0'
        else -> this
    }


    fun part1(): Int {
        val gamma = gamma(inputAsLines)
        val epsilon = epsilon(gamma)
        return gammaRate(gamma) * epsilonRate(epsilon)
    }

    fun part2(): Int {
        val oxygen = oxygen(inputAsLines)
        val co2 = co2(inputAsLines)
        return oxygen.toInt(2) * co2.toInt(2)
    }

}

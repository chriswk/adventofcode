package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import kotlin.math.pow

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


    fun epsilon(gamma: Int, mask: Int): Int {
        return gamma xor mask
    }

    fun gamma(input: List<String>): String {
        return (0.until(input[0].length)).map { idx ->
            mostUsedBit(input, idx)
        }.joinToString(separator = "")
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
                return remaining.first()
            } else {
                val toKeep = leastUsedBit(remaining, idx)
                remaining.filter { it[idx] == toKeep }
            }
        }.first()

    }

    fun part1(): Int {
        val bitCount = inputAsLines[0].length
        val mask = 2.0.pow(bitCount) - 1
        val gamma = gamma(inputAsLines).toInt(2)
        val epsilon = epsilon(gamma, mask.toInt())
        return gamma * epsilon
    }

    fun part2(): Int {
        val oxygen = oxygen(inputAsLines)
        val co2 = co2(inputAsLines)
        return oxygen.toInt(2) * co2.toInt(2)
    }

}

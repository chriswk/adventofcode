package com.chriswk.aoc.advent2015

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day5 : AdventDay(2015, 5) {
    val twoPairs = "(..).*\\1".toRegex()
    val repeatWithLetterBetween = "(.).\\1".toRegex()
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day5()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun isNicePart1(input: String): Boolean {
        return !hasANaughtyCombo(input)
                && twiceInARow(input)
                && threeVowels(input)
    }

    fun isNicePart2(input: String): Boolean {
        return twicePair(input) && repeatWithLetterBetween(input)
    }

    fun twicePair(input: String): Boolean {
        return input.contains(twoPairs)
    }

    fun repeatWithLetterBetween(input: String): Boolean {
        return input.contains(repeatWithLetterBetween)
    }

    fun threeVowels(input: String): Boolean {
        return input.count { it in "aeiou" } >= 3
    }

    fun twiceInARow(input: String): Boolean {
        return input.windowed(2).any { s -> s[0] == s[1] }
    }

    fun hasANaughtyCombo(input: String): Boolean {
        return input.contains("ab") || input.contains("cd") || input.contains("pq") || input.contains("xy")
    }

    fun part1(): Int {
        return inputAsLines.count { isNicePart1(it) }
    }

    fun part2(): Int {
        return inputAsLines.count {
            isNicePart2(it)
        }
    }

}
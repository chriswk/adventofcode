package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.combinations
import com.chriswk.aoc.util.dayInputAsLines
import com.chriswk.aoc.util.report

class Day1 : AdventDay(2020, 1) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>): Unit {
            val day = Day1()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun findPair(goal: Int, options: Set<Int>): Pair<Int, Int>? {
        return options.mapNotNull { a ->
            if (options.contains(goal - a)) {
                a to (goal - a)
            } else {
                null
            }
        }.firstOrNull()
    }

    fun findTriplet(goal: Int, options: Set<Int>): Triple<Int, Int, Int>? {
        return options.asSequence().mapNotNull { a ->
            val pair = findPair(goal - a, options - a)
            if (pair != null) {
                Triple(a, pair.first, pair.second)
            } else {
                null
            }
        }.firstOrNull()
    }

    fun part1(): Int {
        val (a, b) = findPair(2020, inputAsLines.map { it.toInt() }.toSortedSet())!!
        return a * b
    }

    fun part2(): Int {
        return findTriplet(2020, inputAsLines.map { it.toInt() }.toSortedSet())?.let { (a, b, c) ->
            a * b * c
        } ?: 0

    }

}
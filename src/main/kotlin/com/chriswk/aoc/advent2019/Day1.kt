package com.chriswk.aoc.advent2019

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day1 : AdventDay(2019,1) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val d1 = Day1()
            report { d1.part1() }
            report { d1.part2() }
        }

    }

    fun part1(): Int {
        return inputAsLines
            .map { it.toInt() }
            .map { findFuel(it) }
            .sum()
    }

    fun part2(): Int {
        return inputAsLines
            .map { it.toInt() }
            .map { findFuel2(it) }
            .sum()
    }

    fun findFuel2(weight: Int): Int {
        return generateSequence(findFuel(weight)) { w ->
            findFuel(w)
        }.takeWhile { it > 0 }.sum()
    }

    fun findFuel(weight: Int): Int {
        return Math.floorDiv(weight, 3) - 2
    }
}


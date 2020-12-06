package com.chriswk.aoc.advent2015

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.md5
import com.chriswk.aoc.util.report

class Day4: AdventDay(2015, 4) {
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
    }
    fun hash(integer: Int): String {
        return "$inputAsString$integer".md5()
    }
    fun part1(): Int {
        return generateSequence((0 to hash(0))) { (number, _) ->
            (number+1 to hash(number+1))
        }.first { (_, hash) -> hash.startsWith("00000") }.first
    }

    fun part2(): Int {
        return generateSequence((0 to hash(0))) { (number, _) ->
            (number+1 to hash(number+1))
        }.first { (_, hash) -> hash.startsWith("000000") }.first
    }

}
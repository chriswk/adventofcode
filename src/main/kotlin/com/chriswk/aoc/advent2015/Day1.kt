package com.chriswk.aoc.advent2015

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import java.lang.IllegalArgumentException

class Day1: AdventDay(2015, 1) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day1()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun endFloor(input: String): Int {
        return input.fold(0) { a, c ->
            a + c.direction()
        }
    }
    fun firstEntry(input: String, desiredFloor: Int): Int {
        val seq: Sequence<Pair<Int, Int>> = generateSequence(Pair(0,0)) { (floor, idx) ->
            floor + input[idx].direction() to idx + 1
        }
        val (_, idx) = seq.dropWhile { (floor, idx) -> floor != desiredFloor }.first()
        return idx
    }
    fun part1(): Int {
        return endFloor(inputAsString)
    }

    fun part2(): Int {
        return firstEntry(inputAsString, -1)
    }

    fun Char.direction(): Int {
        return when(this) {
            '(' -> 1
            ')' -> -1
            else -> throw IllegalArgumentException("Don't know which way to go for $this")
        }
    }
}
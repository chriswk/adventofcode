package com.chriswk.aoc.advent2015

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.dayInputAsString
import com.chriswk.aoc.util.report
import kotlin.math.pow

class Day10 : AdventDay(2015, 10) {
    val repetitionRegex = """(\d)\1*""".toRegex();

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day10()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun next(number: String) = sequence {
        var lastChar = 'a'
        var count = 0
        number.forEach {
            if (it == lastChar) {
                count++
            } else {
                if (count > 0) {
                    yield(count to lastChar)
                }
                count = 1
                lastChar = it
            }
        }
        yield(count to lastChar)
    }
        .map { it.first.toString() + it.second }
        .joinToString("")


    fun part1(): Int {
        return (1..40).fold(inputAsString) { acc, _ -> next(acc) }.length

    }

    fun part2(): Int {
        return (1..50).fold(inputAsString) { acc, _ -> next(acc) }.length //return counter(inputAsString.toLong()).drop(50).first().length
    }

}
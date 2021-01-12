package com.chriswk.aoc.advent2015

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import com.chriswk.aoc.util.reportableString

class Day11 : AdventDay(2015, 11) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day11()
            reportableString {
                day.part1()
            }
            reportableString {
                day.part2()
            }
        }
    }


    fun part1(): String {
        return passwords(inputAsString).drop(1).first { it.isValidPassword() }
    }

    fun part2(): String {
        return passwords(inputAsString).filter { it.isValidPassword() }.drop(1).first()
    }

    fun passwords(initialPassword: String): Sequence<String> {
        return generateSequence(initialPassword) {  p ->
            p.next()
        }
    }

    val x = 'x'
    val invalidChars = setOf('i', 'o', 'l')
    fun String.next(): String {
        val incr = this[length - 1].next()
        return if (incr != 'a') {
            this.substring(0, length - 1) + incr
        } else {
            this.substring(0, length - 1).next() + 'a'
        }
    }
    fun Char.next(): Char = if (this == 'z') 'a' else {
        this + 1
    }

    private fun String.isValidPassword() = this.allValidChars() && this.increasing() && this.pairs()
    private fun String.allValidChars() = all { it !in setOf('i', 'o', 'l') }
    private fun String.increasing() = windowed(3).any { it[0] <= x && it[2] == it[1].next() && it[1] == it[0].next() }
    private fun String.pairs(): Boolean {
        val w = windowed(2).mapIndexed { i, s -> i to s }.filter { (_, d) -> d[0] == d[1] }
        return if (w.size == 2) {
            w[1].first - w[0].first > 1 // Not a triple
        } else {
            w.size > 2
        }
    }
}
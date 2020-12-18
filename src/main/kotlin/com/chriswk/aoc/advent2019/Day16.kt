package com.chriswk.aoc.advent2019

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import kotlin.math.absoluteValue

class Day16: AdventDay(2019, 16) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day16()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
        val base = intArrayOf(0, 1, 0, -1)
    }

    val signal = inputAsString.map { Character.getNumericValue(it) }.toIntArray()

    fun part1(): Long {
        return (1..100).fold(signal) { p, _ -> phase(p) }.joinToString("").take(8).toLong()
    }

    fun part2(): Int {
        return 0
    }

    fun phase(input: IntArray): IntArray {
        return (1..input.size).map { element ->
            val cycle: IntArray = base.cycle(element, input.size)
            input.mapIndexed { index, inputElement -> (inputElement * cycle[index]) }.sum().lastDigit()
        }.toIntArray()
    }
}

fun IntArray.cycle(perCycle: Int, length: Int): IntArray {
    return (0..length / perCycle).flatMap { idx -> List(perCycle) { this[idx % this.size] } }.drop(1).toIntArray()
}
fun Int.lastDigit() = (this % 10).absoluteValue
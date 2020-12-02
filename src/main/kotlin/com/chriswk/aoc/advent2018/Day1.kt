package com.chriswk.aoc.advent2018

import com.chriswk.aoc.util.infiniteCycle

object Day1 {

    fun partOne(input: List<String>): Int {
        return input.asSequence().sumBy { it.toInt() }
    }

    fun partTwo(input: List<String>): Int {
        val data = input.asSequence().map { it.toInt() }.infiniteCycle()
        val seen = mutableSetOf<Int>()
        var frequency = 0
        data.first { change ->
            frequency += change
            !seen.add(frequency)
        }
        return frequency
    }
}
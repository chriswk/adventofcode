package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day1: AdventDay(2021, 1) {
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

    val inputAsInts = inputAsLines.map { it.toInt() }


    fun part1(): Int {
        return increasing(inputAsInts)
    }

    fun increasing(l: List<Int>): Int {
        return l.fold((0 to 0), increases()).first
    }

    fun increasingWindows(l: List<Int>, windowSize: Int = 3): Int {
        return l.windowed(windowSize).map { it.sum() }.fold((0 to 0), increases()).first
    }

    private fun increases() = { (increases, previous): Pair<Int, Int>, cur: Int ->
        if (previous < cur) {
            increases + 1 to cur
        } else {
            increases to cur
        }
    }


    fun part2(): Int {
        return increasingWindows(inputAsInts)
    }
}

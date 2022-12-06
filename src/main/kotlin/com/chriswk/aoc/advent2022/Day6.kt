package com.chriswk.aoc.advent2022

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day6: AdventDay(2022, 6) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day6()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun part1(): Int {
        return startOfPacket(inputAsString, 4)
    }

    fun part2(): Int {
        return startOfPacket(inputAsString, 14)
    }

    fun startOfPacket(message: String, uniqueChars: Int): Int {
        return message.windowedSequence(uniqueChars).indexOfFirst { message -> message.toSet().size == uniqueChars } + uniqueChars
    }

}

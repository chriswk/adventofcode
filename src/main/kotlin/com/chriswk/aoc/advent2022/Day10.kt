package com.chriswk.aoc.advent2022

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.getCharForTyping
import com.chriswk.aoc.util.report

class Day10: AdventDay(2022, 10) {
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

    fun parseInput(input: List<String>): List<Int> {
        return input.flatMap { command ->
            when(command) {
                "noop" -> listOf(0)
                else -> listOf(0, command.split(" ")[1].toInt())
            }
        }
    }
    fun signalStrength(commands: List<Int>): Int {
        val signals = mutableListOf<Int>()
        val register = commands.foldIndexed(1) { idx, register, add ->
            val cycle = idx+1
            if (cycle % 40 == 20) {
                val signal = cycle * register
                signals.add(signal)
            }
            register + add
        }
        return signals.sum()
    }

    fun pixie(commands: List<Int>): String {
        return buildString {
            commands.foldIndexed(1) { cycle, register, add ->
                val column = cycle % 40
                append(getCharForTyping { register in (column - 1..column+1) })
                if (column == 0) {
                    appendLine()
                }
                register + add
            }
        }
    }

    fun part1(): Int {
        return signalStrength(parseInput(inputAsLines))
    }

    fun part2(): Int {
        println(pixie(parseInput(inputAsLines)))
        return 0
    }

}

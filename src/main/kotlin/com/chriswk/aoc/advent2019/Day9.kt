package com.chriswk.aoc.advent2019

import com.chriswk.aoc.util.*
import kotlinx.coroutines.runBlocking

class Day9 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day9 = Day9()
            val input = dayInputAsString(2019, 9)
            report { day9.part1(input) }
            report { day9.part2(input) }
        }
    }

    fun part1(input: String): Long = runBlocking {
        val program = parseInstructions(input).toMutableMap()
        IntCodeComputer(program).run {
            this.input.send(1)
            runSuspending()
            output.receive()
        }

    }

    fun part2(input: String): Long = runBlocking {
        val program = parseInstructions(input).toMutableMap()
        IntCodeComputer(program).run {
            this.input.send(2)
            runSuspending()
            output.receive()
        }
    }
}
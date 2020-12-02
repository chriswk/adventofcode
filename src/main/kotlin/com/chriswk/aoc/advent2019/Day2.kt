package com.chriswk.aoc.advent2019

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.dayInputAsString
import com.chriswk.aoc.util.fileToString
import com.chriswk.aoc.util.parseInstructions
import com.chriswk.aoc.util.report
import java.lang.IllegalStateException

class Day2 : AdventDay(2019, 2) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day2()
            report { day.part1() }
            report { day.part2() }
        }
    }

    fun part1(): Long {
        val instructions = parseInstructions(inputAsString)
        instructions[1] = 12
        instructions[2] = 2
        val computer = IntCodeComputer(instructions)
        computer.run()
        return computer.program[0] ?: throw IllegalStateException("No memory in position 0")
    }

    fun part2(): Int {
        val instructions = parseInstructions(inputAsString)
        (1..100).forEach { noun ->
            (1..100).forEach { verb ->
                val copy = instructions.copyOf()
                copy[1] = noun
                copy[2] = verb
                val p = IntCodeComputer(copy)
                p.run()
                if (p.program[0] == 19690720L) {
                    return 100 * noun + verb
                }
            }
        }
        return -1
    }
}
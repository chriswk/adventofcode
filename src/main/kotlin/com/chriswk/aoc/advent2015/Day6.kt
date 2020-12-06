package com.chriswk.aoc.advent2015

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.Pos
import com.chriswk.aoc.util.report
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import kotlin.math.max

class Day6 : AdventDay(2015, 6) {
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
        val grid: Array<Int> = Array(1000 * 1000) { 0 }
        inputAsLines.map {
            Instruction.fromInstructionString(it)
        }.forEach {
            it.actPart1(grid)
        }
        return grid.sum()
    }

    fun part2(): Int {
        val grid: Array<Int> = Array(1000 * 1000) { 0 }
        inputAsLines.map {
            Instruction.fromInstructionString(it)
        }.forEach {
            it.actPart2(grid)
        }
        return grid.sum()
    }

}

data class Instruction(val operation: Operation, val minX: Int, val minY: Int, val maxX: Int, val maxY: Int) {
    val points: List<Pos> = (minY..maxY).flatMap { y -> (minX..maxX).map { x -> Pos(x, y) } }
    fun actPart1(grid: Array<Int>) {
        points.forEach { p: Pos ->
            val idx = p.toIndex(1000)
            when (operation) {
                Operation.ON -> grid[idx] = 1
                Operation.OFF -> grid[idx] = 0
                Operation.TOGGLE -> if (grid[idx] == 0) {
                    grid[idx] = 1
                } else {
                    grid[idx] = 0
                }
            }

        }
    }

    fun actPart2(grid: Array<Int>) {
        points.forEach {
            val idx = it.toIndex(1000)
            when (operation) {
                Operation.ON -> grid[idx]++
                Operation.OFF -> grid[idx] = max(0, grid[idx] - 1)
                Operation.TOGGLE -> grid[idx] += 2
            }
        }
    }

    companion object {
        val numberInstruction = """(\d+),(\d+) through (\d+),(\d+)""".toRegex()
        fun fromInstructionString(input: String): Instruction {
            val coords = numberInstruction.find(input)
            coords?.let { res ->
                return Instruction(
                    Operation.fromString(input),
                    toInt(res.groups[1]),
                    toInt(res.groups[2]),
                    toInt(res.groups[3]),
                    toInt(res.groups[4])
                )
            } ?: throw IllegalStateException("Couldn't find coordinates in $input")
        }
    }
}

fun toInt(group: MatchGroup?): Int {
    return group?.value?.toInt() ?: throw IllegalStateException("Couldn't parse to value")
}

enum class Operation {
    OFF,
    ON,
    TOGGLE;

    companion object {
        fun fromString(input: String): Operation {
            return when {
                input.startsWith("turn on") -> {
                    ON
                }
                input.startsWith("turn off") -> {
                    OFF
                }
                input.startsWith("toggle") -> {
                    TOGGLE
                }
                else -> {
                    throw IllegalArgumentException("Don't know how to perform $input")
                }
            }
        }
    }
}
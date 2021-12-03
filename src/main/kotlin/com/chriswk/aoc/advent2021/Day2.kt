package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day2: AdventDay(2021, 2) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day2()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }


    fun parseInstructionsPart1(line: String): Submarine {
        val (direction, amount) = line.split(" ")
        return when(direction) {
            "forward" -> Submarine(x = amount.toInt(), depth = 0)
            "up" -> Submarine(x = 0, depth = -amount.toInt())
            "down" -> Submarine(x = 0, depth = amount.toInt())
            else -> Submarine(0, 0)
        }
    }

    fun parseInstructionPart2(line: String): Instruction {
        val (direction, amount) = line.split(" ")
        return when(direction) {
            "forward" -> Instruction(Direction.FORWARD, amount.toInt())
            "up" -> Instruction(Direction.UP, amount.toInt())
            "down" -> Instruction(Direction.DOWN, amount.toInt())
            else -> Instruction(Direction.FORWARD, 0)
        }
    }

    fun parseInstructions(lines: List<String>): List<Submarine> {
        return lines.map { parseInstructionsPart1(it) }
    }

    fun parseInstructionsPart2(lines: List<String>): List<Instruction> {
        return lines.map { parseInstructionPart2(it) }
    }

    fun drive(startPos: Submarine, instructions: List<Submarine>): Submarine {
        return instructions.fold(startPos) { sub, next ->
            sub + next
        }
    }

    fun drivePart2(startPos: Submarine, instructions: List<Instruction>): Submarine {
        return instructions.fold(startPos) { sub, next ->
            when(next.direction) {
                Direction.UP -> sub.copy(aim = sub.aim - next.amount)
                Direction.DOWN -> sub.copy(aim = sub.aim + next.amount)
                Direction.FORWARD -> sub.copy(x = sub.x + next.amount, depth = sub.depth + (next.amount * sub.aim))
            }
        }
    }

    val instructions: List<Submarine> = parseInstructions(inputAsLines)

    fun part1(): Int {
        return drive(Submarine(0, 0), instructions).product
    }

    fun part2(): Int {
        return drivePart2(Submarine(0, 0), parseInstructionsPart2(inputAsLines)).product
    }
    enum class Direction {
        UP,
        DOWN,
        FORWARD
    }
    data class Instruction(val direction: Direction, val amount: Int)


    data class Submarine(val x: Int, val depth: Int, val aim: Int = 0) {
        val product = x*depth
    }
    operator fun Submarine.plus(other: Submarine) = Submarine(x = x + other.x, depth = depth + other.depth)

}


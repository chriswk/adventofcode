package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day8: AdventDay(2020, 8) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day8()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun parseInstruction(line: String): Instruction {
        val (op, value) = line.split(" ")
        return Instruction(Op.valueOf(op.uppercase()), value.replace("+", "").toInt())
    }

    fun buildInstructions(input: List<String>): Array<Instruction> {
        return input.map { parseInstruction(it) }.toTypedArray()
    }

    fun runProgram(program: Array<Instruction>): Pair<Int, Boolean> {
        val seen = mutableSetOf<Int>()
        var ip = 0
        var acc = 0
        val size = program.size
        var exceptionThrown = false
        while(!seen.contains(ip) && ip < size && !exceptionThrown) {
            exceptionThrown = false
            seen.add(ip)
            if (ip < 0 || ip > size) {
                exceptionThrown = true
            } else {
                val ins = program[ip]
                when (ins.op) {
                    Op.NOP -> ip += 1
                    Op.ACC -> {
                        acc += ins.value
                        ip += 1
                    }
                    Op.JMP -> ip += ins.value
                }
            }
        }
        return acc to (!exceptionThrown && ip >= size)
    }

    fun part1(): Int {
        return runProgram(buildInstructions(inputAsLines)).first
    }

    fun toggle(instruction: Instruction): Instruction {
        return when(instruction.op) {
            Op.JMP -> instruction.copy(op = Op.NOP)
            Op.NOP -> instruction.copy(op = Op.JMP)
            Op.ACC -> instruction.copy(op = Op.ACC)
        }
    }

    fun toggleOps(program: Array<Instruction>): Int {
        return program.asSequence().mapIndexed { idx, instruction ->
            val toggle = when (instruction.op) {
                Op.NOP -> instruction.value != 0
                Op.JMP -> true
                Op.ACC -> false
            }
            if (toggle) {
                val c = program.copyOf()
                c[idx] = toggle(instruction)
                runProgram(c)
            } else {
                (0 to false)
            }
        }.first { it.second }.first
    }

    fun part2(): Int {
        val program = buildInstructions(inputAsLines)
        return toggleOps(program)
    }

    data class Instruction(val op: Op, val value: Int)
    enum class Op {
        NOP,
        ACC,
        JMP
    }

}

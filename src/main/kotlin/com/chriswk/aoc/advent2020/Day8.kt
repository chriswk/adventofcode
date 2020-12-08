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
        return Instruction(Op.valueOf(op.toUpperCase()), value.replace("+", "").toInt())
    }

    fun buildInstructions(input: List<String>): Array<Instruction> {
        return input.map { parseInstruction(it) }.toTypedArray()
    }

    fun runProgram(program: Array<Instruction>): Int {
        val seen = mutableSetOf<Int>()
        var ip = 0
        var acc = 0
        while(!seen.contains(ip)) {
            seen.add(ip)
            val ins = program[ip]
            when(ins.op) {
                Op.NOP -> ip += 1
                Op.ACC -> {
                    acc += ins.value
                    ip += 1
                }
                Op.JMP -> ip += ins.value
            }
        }
        return acc
    }

    fun part1(): Int {
        return runProgram(buildInstructions(inputAsLines))
    }

    fun part2(): Int {
        return 0
    }

    data class Instruction(val op: Op, val value: Int)
    enum class Op {
        NOP,
        ACC,
        JMP
    }

}
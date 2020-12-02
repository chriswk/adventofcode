package com.chriswk.aoc.advent2018

object Day19 {

    fun partOne(input: List<String>): Int {
        var register = IntArray(6) { 0 }
        return commonProcess(input, register)
    }

    fun partTwo(input: List<String>): Int {
        var register = IntArray(6) { 0 }
        register[0] = 1
        //For my solution look at register 5 and see stabilised value, then ask WolframAlpha for sum of factors
        //which is what this is trying to calculate, although in a quadratic way, so since my r5 = 10551339, we'll be here a while
        //111330754692921 operations at least
        return commonProcess(input, register)
    }

    private fun commonProcess(input: List<String>, register: IntArray): Int {
        var register1 = register
        var instructionPointer = 0
        val instructionPointerIdx = input.first().substringAfterLast(' ').toInt()
        val instructions = input.drop(1).map { parseInstruction(it) }
        while (instructionPointer < instructions.size) {
            print(register1.joinToString())
            register1[instructionPointerIdx] = instructionPointer
            val instruction = instructions[instructionPointer]
            register1 = instruction.run(register1)
            println(" -> ${register1.joinToString(separator = ", ")}")
            instructionPointer = register1[instructionPointerIdx]
            instructionPointer++
        }
        return register1[0]
    }



    fun parseInstruction(instruction: String): Instruction {
        val (opCode, a, b, c) = instruction.split(" ")
        return Instruction(opCode, a.toInt(), b.toInt(), c.toInt())
    }

    data class Instruction(val opCode: String, val A: Int, val B: Int, val C: Int) {
        fun run(register: IntArray): IntArray {
            when (opCode) {
                "addr" -> {
                    register[C] = register[A] + register[B]
                }
                "addi" -> {
                    register[C] = register[A] + B
                }
                "mulr" -> {
                    register[C] = register[A] * register[B]
                }
                "muli" -> {
                    register[C] = register[A] * B
                }
                "banr" -> {
                    register[C] = register[A] and register[B]
                }
                "bani" -> {
                    register[C] = register[A] and B
                }
                "borr" -> {
                    register[C] = register[A] or register[B]
                }
                "bori" -> {
                    register[C] = register[A] or B
                }
                "setr" -> {
                    register[C] = register[A]
                }
                "seti" -> {
                    register[C] = A
                }
                "gtir" -> {
                    if (A > register[B]) {
                        register[C] = 1
                    } else {
                        register[C] = 0
                    }
                }
                "gtri" -> {
                    if (register[A] > B) {
                        register[C] = 1
                    } else {
                        register[C] = 0
                    }
                }
                "gtrr" -> {
                    if (register[A] > register[B]) {
                        register[C] = 1
                    } else {
                        register[C] = 0
                    }
                }
                "eqir" -> {
                    if (A == register[B]) {
                        register[C] = 1
                    } else {
                        register[C] = 0
                    }
                }
                "eqri" -> {
                    if (register[A] == B) {
                        register[C] = 1
                    } else {
                        register[C] = 0
                    }
                }
                "eqrr" -> {
                    if (register[A] == register[B]) {
                        register[C] = 1
                    } else {
                        register[C] = 0
                    }
                }
                else -> throw IllegalArgumentException("No such opcode $opCode")
            }
            return register
        }

    }
}
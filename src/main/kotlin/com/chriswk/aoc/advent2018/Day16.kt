package com.chriswk.aoc.advent2018

object Day16 {
    val instructionPattern = """.*?(\d+),? (\d+),? (\d+),? (\d+).*""".toRegex()
    fun partOne(input: List<String>, matchingOpCodeCount: Int): Int {
        val sampleRuns = parseInput(input)
        return sampleRuns.count { sample ->
            findOpCodesWithMatchingOutput(sample) >= matchingOpCodeCount
        }
    }

    fun partTwo(input: List<String>): Register {
        val program = input.map { parseInstruction(it) }
        return program.fold(Register(0, 0, 0, 0)) { currentRegister, instruction ->
            OpCode.get(instruction.opCode).run(instruction, currentRegister)
        }
    }

    fun parseInstruction(instruction: String): Instruction {
        val (opCode, a, b, c) = instructionPattern.find(instruction)!!.groupValues.drop(1).map { it.toInt() }
        return Instruction(opCode, a, b, c)
    }

    fun findOpCodesWithMatchingOutput(sampleRun: SampleRun): Int {
        return OpCode.values().count { it.run(sampleRun.instruction, sampleRun.before) == sampleRun.after }
    }

    fun findOpCode(sampleRun: SampleRun) {
        val codesWithMatch = OpCode.values().filter { it.opCode == -1 }.mapNotNull { opCode ->
            if (opCode.run(sampleRun.instruction, sampleRun.before) == sampleRun.after) {
                opCode
            } else {
                null
            }
        }
        if(codesWithMatch.size == 1) {
            println(codesWithMatch.first() to sampleRun.instruction.opCode)
        }
    }

    data class SampleRun(val instruction: Instruction, val before: Register, val after: Register)

    fun parseInput(input: List<String>) : List<SampleRun> {
        return input.chunked(4).map { parseSingleSample(it) }
    }

    fun parseSingleSample(input: List<String>): SampleRun {
        val (r0, r1, r2, r3) = instructionPattern.find(input[0])!!.groupValues.drop(1).map { it.toInt() }
        val (opCode, a, b, c) = instructionPattern.find(input[1])!!.groupValues.drop(1).map { it.toInt() }
        val (r_0, r_1, r_2, r_3) = instructionPattern.find(input[2])!!.groupValues.drop(1).map { it.toInt() }
        return SampleRun(before = Register(r0, r1, r2, r3), instruction = Instruction(opCode, a, b, c), after = Register(r_0, r_1, r_2, r_3))
    }

    data class Instruction(val opCode: Int, val A: Int, val B: Int, val C: Int)
    data class Register(val r0: Int, val r1: Int, val r2: Int, val r3: Int) {
        operator fun get(idx: Int): Int = when(idx) {
                0 -> r0
                1 -> r1
                2 -> r2
                3 -> r3
                else -> throw IllegalArgumentException("Non existent registry accessed")
            }
        operator fun set(idx: Int, value: Int): Register = when(idx) {
            0 -> copy(r0 = value)
            1 -> copy(r1 = value)
            2 -> copy(r2 = value)
            3 -> copy(r3 = value)
            else -> throw java.lang.IllegalArgumentException("Non existent registry accessed")
        }
        fun copy(idx: Int, value: Int): Register {
            return set(idx, value)
        }
    }
    enum class OpCode(val opCode: Int) {
        ADDR(11), ADDI(4), MULR(13), MULI(1), BANR(0),
        BANI(10), BORR(8), BORI(2), SETR(3), SETI(14),
        GTIR(7), GTRI(6), GTRR(15),
        EQIR(12), EQRI(9), EQRR(5);

        fun run(instruction: Instruction, register: Register): Register {
            return when (this) {
                ADDR -> {
                    val newValue = register[instruction.A] + register[instruction.B]
                    register.copy(instruction.C, newValue)
                }
                ADDI -> {
                    val newValue = register[instruction.A] + instruction.B
                    register.copy(instruction.C, newValue)
                }
                MULR -> {
                    val newValue = register[instruction.A] * register[instruction.B]
                    register.copy(instruction.C, newValue)
                }
                MULI -> {
                    val newValue = register[instruction.A] * instruction.B
                    register.copy(instruction.C, newValue)
                }
                BANR -> {
                    val newValue = register[instruction.A] and register[instruction.B]
                    register.copy(instruction.C, newValue)
                }
                BANI -> {
                    val newValue = register[instruction.A] and instruction.B
                    register.copy(instruction.C, newValue)
                }
                BORR -> {
                    val newValue = register[instruction.A] or register[instruction.B]
                    register.copy(instruction.C, newValue)
                }
                BORI -> {
                    val newValue = register[instruction.A] or instruction.B
                    register.copy(instruction.C, newValue)
                }
                SETR -> {
                    register.copy(instruction.C, register[instruction.A])
                }
                SETI -> {
                    register.copy(instruction.C, instruction.A)
                }
                GTIR -> {
                    if(instruction.A > register[instruction.B]) {
                        register.copy(instruction.C, 1)
                    } else {
                        register.copy(instruction.C, 0)
                    }
                }
                GTRI -> {
                    if (register[instruction.A] > instruction.B) {
                        register.copy(instruction.C, 1)
                    } else {
                        register.copy(instruction.C, 0)
                    }
                }
                GTRR -> {
                    if (register[instruction.A] > register[instruction.B]) {
                        register.copy(instruction.C, 1)
                    } else {
                        register.copy(instruction.C, 0)
                    }
                }
                EQIR -> {
                    if (instruction.A == register[instruction.B]) {
                        register.copy(instruction.C, 1)
                    } else {
                        register.copy(instruction.C, 0)
                    }
                }
                EQRI -> {
                    if (register[instruction.A] == instruction.B) {
                        register.copy(instruction.C, 1)
                    } else {
                        register.copy(instruction.C, 0)
                    }
                }
                EQRR -> {
                    if (register[instruction.A] == register[instruction.B]) {
                        register.copy(instruction.C, 1)
                    } else {
                        register.copy(instruction.C, 0)
                    }
                }
            }
        }
        companion object {
            private val opCodes: Map<Int, OpCode> = OpCode.values().groupBy { it.opCode }.mapValues { it.value.first() }
            fun get(opCode: Int): OpCode = opCodes[opCode]!!
        }
    }
}
package com.chriswk.aoc.advent2015

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.isNumber
import com.chriswk.aoc.util.report
import org.apache.logging.log4j.LogManager

@ExperimentalStdlibApi
@ExperimentalUnsignedTypes
class Day7 : AdventDay(2015, 7) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day7()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }

        val logger = LogManager.getLogger(Day7::class.java)
    }

    fun parseInstructions(instructions: List<String>): List<Instruction> {
        return instructions.map { instruction ->
            val (signal, target) = instruction.split("->").map { it.trim() }
            if (signal.contains("RSHIFT")) {
                val (register, shiftWidth) = signal.split("RSHIFT").map { it.trim() }
                Instruction(
                    signal = shiftWidth.trim().toInt(),
                    OpCode.RSHIFT,
                    inRegister = listOf(register.trim()),
                    outRegister = target
                )
            } else if (signal.contains("LSHIFT")) {
                val (register, shiftWidth) = signal.split("LSHIFT")
                Instruction(
                    signal = shiftWidth.trim().toInt(),
                    OpCode.LSHIFT,
                    inRegister = listOf(register.trim()),
                    outRegister = target
                )
            } else if (signal.contains("OR")) {

                Instruction(signal = null, OpCode.OR, inRegister = signal.split(" OR "), outRegister = target)
            } else if (signal.contains("AND")) {
                Instruction(signal = null, OpCode.AND, inRegister = signal.split(" AND "), outRegister = target)
            } else if (signal.contains("NOT")) {
                Instruction(
                    signal = null,
                    OpCode.NOT,
                    inRegister = listOf(signal.substringAfter("NOT ").trim()),
                    outRegister = target
                )
            } else {
                val si = signal.toIntOrNull()
                if (si == null) {
                    Instruction(
                        signal = null,
                        opCode = OpCode.SET_FROM_WIRE,
                        inRegister = listOf(signal),
                        outRegister = target
                    )
                } else {
                    Instruction(signal = si, opCode = OpCode.SET, inRegister = emptyList(), outRegister = target)
                }
            }
        }
    }

    fun buildDependencyGraph(instructions: List<Instruction>): Map<String, Instruction> {
        return instructions.groupBy { it.outRegister }.mapValues { it.value.first() }
    }

    fun walkGraph(
        registry: MutableMap<String, Int> = mutableMapOf(),
        graph: Map<String, Instruction>,
        register: String
    ): Map<String, Int> {
        val targetWire = graph[register]!!
        if (targetWire.inRegister.isEmpty()) {
            runInstruction(registry, targetWire)
        } else {
            targetWire.inRegister.filterNot { it.isNumber(10) }.map {
                if (!registry.containsKey(it)) {
                    walkGraph(registry, graph, it)
                }
            }
            runInstruction(registry, targetWire)
        }
        return registry
    }

    fun runInstruction(registers: MutableMap<String, Int>, instruction: Instruction) {
        when (instruction.opCode) {
            OpCode.SET -> registers[instruction.outRegister] = instruction.signal!!
            OpCode.SET_FROM_WIRE -> registers[instruction.outRegister] = registers[instruction.inRegister.first()]!!.shorten()
            OpCode.RSHIFT -> registers[instruction.outRegister] =
                registryOrActualValue(registers, instruction.inRegister.first()).rotateRight(instruction.signal!!.toInt()).shorten()
            OpCode.LSHIFT -> registers[instruction.outRegister] =
                registryOrActualValue(registers, instruction.inRegister.first()).rotateLeft(instruction.signal!!.toInt()).shorten()
            OpCode.AND -> registers[instruction.outRegister] = registryOrActualValue(registers, instruction.inRegister[0]).and(registryOrActualValue(registers, instruction.inRegister[1])).shorten()
            OpCode.OR -> registers[instruction.outRegister] =
                registryOrActualValue(registers, instruction.inRegister[0]).or(registryOrActualValue(registers, instruction.inRegister[1])).shorten()
            OpCode.NOT -> registers[instruction.outRegister] = inverse(registryOrActualValue(registers, instruction.inRegister[0]))
        }
    }

    fun registryOrActualValue(registers: MutableMap<String, Int>, key: String): Int {
        return if (key.isNumber(10)) {
            key.toInt()
        } else {
            registers[key]!!
        }
    }

    fun inverse(x: Int): Int {
        return x.toUShort().inv().toInt()
    }
    fun Int.shorten(): Int {
        return this.toUShort().toInt()
    }
    fun runPureOps(registry: MutableMap<String, Int>, graph: Map<String, Instruction>) {
        graph.values.filter { it.opCode == OpCode.SET }.forEach { runInstruction(registry, it) }
    }

    fun part1(): Int {
        val program = parseInstructions(inputAsLines)
        val graph = buildDependencyGraph(program)
        val registry = mutableMapOf<String, Int>()
        val register = walkGraph(registry, graph, "a")
        return register["a"]!!.toInt()
    }


    fun part2(): Int {
        val fromPart1 = 46065
        val program = parseInstructions(inputAsLines)
        val graph = overrideInput(buildDependencyGraph(program), "b", fromPart1)
        val registry = mutableMapOf<String, Int>()
        walkGraph(registry, graph, "a")
        return registry["a"]!!.toInt()
    }

    private fun overrideInput(
        graph: Map<String, Instruction>,
        s: String,
        fromPart1: Int
    ): Map<String, Instruction> {
        val original = graph[s]
        return graph + (s to original!!.copy(signal = fromPart1))
    }

    data class Instruction(
        val signal: Int?,
        val opCode: OpCode,
        val inRegister: List<String>,
        val outRegister: String
    )

    enum class OpCode {
        AND,
        LSHIFT,
        RSHIFT,
        OR,
        NOT,
        SET,
        SET_FROM_WIRE

    }
}
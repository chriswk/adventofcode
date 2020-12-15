package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import java.lang.IllegalArgumentException
import java.lang.Math.pow
import kotlin.math.pow

class Day14 : AdventDay(2020, 14) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day14()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }


    fun bitmaskInstructions(input: List<String>): Pair<Masker, List<String>> {
        val mask = input[0].split("=").map { it.trim() }[1]
        return Masker(mask) to input.drop(1)
    }

    fun executeInstructions(masker: Masker, instructions: List<String>): Map<Long, Long> {
        return instructions.fold(mutableMapOf<Long, Long>() to masker) { (memory, masker), instr ->
            if (instr.startsWith("mask =")) {
                memory to Masker(instr.split("=").map { it.trim() }[1])
            } else {
                val (addressStr, value) = instr.split("=").map { it.trim() }
                val address = addressStr.substringAfter("mem[").substringBefore("]").toLong()
                val maskedValue = masker.mask(value.toLong())
                memory[address] = maskedValue
                memory to masker
            }
        }.first
    }

    fun executeInstructionsPart2(masker: Masker, instructions: List<String>): Map<Long, Long> {
        return instructions.fold(mutableMapOf<Long, Long>() to masker) { (memory, masker), instr ->
            if (instr.startsWith("mask =")) {
                memory to Masker(instr.split("=").map { it.trim() }[1])
            } else {
                val (addressStr, value) = instr.split("=").map { it.trim() }
                val address = addressStr.replace("mem[", "").replace("]", "").toLong()
                val addresses = masker.addressMasks(address)
                addresses.forEach { memory[it] = value.toLong() }
                memory to masker
            }
        }.first
    }

    fun sumOfMemory(memory: Map<Long, Long>): Long {
        return memory.values.sum()
    }

    fun part1(): Long {
        val (masker, instructions) = bitmaskInstructions(inputAsLines)
        val memory = executeInstructions(masker, instructions)
        return sumOfMemory(memory)
    }

    fun part2(): Long {
        val (masker, instructions) = bitmaskInstructions(inputAsLines)
        val memory = executeInstructionsPart2(masker, instructions)
        return sumOfMemory(memory)
    }

    data class Masker(val originalMask: String, val andMask: Long, val orMask: Long) {
        constructor(mask: String) : this(
            originalMask = mask.padStart(36, '0'), andMask = mask.replace('X', '1').toLong(2),
            orMask = mask.replace('X', '0').toLong(2)
        )

        fun mask(input: Long): Long {
            return (input and andMask) or orMask
        }

        fun addressMasks(address: Long): List<Long> {
            val addresses = mutableListOf(address.bitToN(36).toCharArray())
            originalMask.forEachIndexed { idx, bit ->
                when (bit) {
                    '1' -> addresses.forEach { it[idx] = '1' }
                    'X' -> {
                        addresses.forEach { it[idx] = '1' }
                        addresses.addAll(addresses.map { it.copyOf().apply { this[idx] = '0' } })
                    }
                }
            }
            return addresses.map { it.joinToString("").toLong(2) }
        }

    }
}

fun Long.bitToN(bits: Int): String {
    return this.toString(2).padStart(bits, '0')
}
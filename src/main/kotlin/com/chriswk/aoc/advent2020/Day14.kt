package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import java.lang.Math.pow
import kotlin.math.pow

class Day14: AdventDay(2020, 14) {
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
                val address = addressStr.replace("mem[", "").replace("]", "").toLong()
                val maskedValue = masker.mask(value.toLong())
                memory[address] = maskedValue
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

    fun part2(): Int {
        return 0
    }

    data class Masker(val originalMask: String, val andMask: Long, val orMask: Long) {
        constructor(mask: String) : this(originalMask = mask, andMask = mask.replace('X', '1').toLong(2),
            orMask = mask.replace('X', '0').toLong(2))
        fun mask(input: Long): Long {
            return (input and andMask) or orMask
        }
    }
}
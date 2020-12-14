package com.chriswk.aoc.advent2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day14Test {
    val smallInput = """
        mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
        mem[8] = 11
        mem[7] = 101
        mem[8] = 0
    """.trimIndent().lines()

    @Test
    fun maskerWorks() {
        val masker = Day14.Masker("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X")
        assertThat(masker.mask(11)).isEqualTo(73)
    }

    @Test
    fun smallInputPart1() {
        val day = Day14()
        val (masker, instructions) = day.bitmaskInstructions(smallInput)
        val memory = day.executeInstructions(masker, instructions)
        assertThat(day.sumOfMemory(memory)).isEqualTo(165)
    }

    @Test
    fun part1() {
        assertThat(Day14().part1()).isEqualTo(9879607673316L)
    }

    @Test
    fun part2MaskerWorks() {
        val masker = Day14.Masker("X1001X".padStart(36, '0'))
        assertThat(masker.masks()).hasSize(4)
        assertThat(masker.masks()).contains(18L.bitToN(36), 19L.bitToN(36), 50L.bitToN(36), 51L.bitToN(36))
    }

    @Test
    fun appliesAllMasks() {
        val masker = Day14.Masker("X1001X".padStart(36, '0'))
        assertThat(masker.part2Mask(42)).containsAll(listOf(26L,27L,58L,59L))
    }

    @Test
    fun part2SimpleInput() {
        val instructionSet = """
            mask = 000000000000000000000000000000X1001X
            mem[42] = 100
            mask = 00000000000000000000000000000000X0XX
            mem[26] = 1
        """.trimIndent().lines()
        val day = Day14()
        val (mask, instructions) = day.bitmaskInstructions(instructionSet)
        val memory = day.executeInstructionsPart2(mask, instructions)
        assertThat(memory.values.sum()).isEqualTo(208)
    }

    @Test
    fun part2() {
        assertThat(Day14().part2()).isEqualTo(3435342392262)
    }
}
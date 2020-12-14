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
}
package com.chriswk.aoc.advent2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day8Test {

    @Test
    fun testProgramFindsLoop() {
        val input = """
            nop +0
            acc +1
            jmp +4
            acc +3
            jmp -3
            acc -99
            acc +1
            jmp -4
            acc +6
        """.trimIndent().lines()
        val day = Day8()
        val program = day.buildInstructions(input)
        assertThat(day.runProgram(program)).isEqualTo(5)
    }

    @Test
    fun part1() {
        assertThat(Day8().part1()).isEqualTo(1941)
    }
}
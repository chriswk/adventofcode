package com.chriswk.aoc.advent2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day8Test {
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
    @Test
    fun testProgramFindsLoop() {

        val day = Day8()
        val program = day.buildInstructions(input)
        assertThat(day.runProgram(program).first).isEqualTo(5)
    }

    @Test
    fun part1() {
        assertThat(Day8().part1()).isEqualTo(1941)
    }

    @Test
    fun testProgramManagesToUnwrapLoop() {
        val day = Day8()
        val program = day.buildInstructions(input)
        assertThat(day.toggleOps(program)).isEqualTo(8)
    }

    @Test
    fun part2() {
        assertThat(Day8().part2()).isEqualTo(2096)
    }
}
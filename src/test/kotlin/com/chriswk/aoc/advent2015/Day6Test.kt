package com.chriswk.aoc.advent2015

import com.chriswk.aoc.advent.Slow
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class Day6Test {
    val day = Day6()
    @Test
    fun testInput() {
        val instr = Instruction.fromInstructionString("turn on 0,0 through 999,999")
        assertThat(instr.operation).isEqualTo(Operation.ON)
        assertThat(instr.minX).isEqualTo(0)
        assertThat(instr.minY).isEqualTo(0)
        assertThat(instr.maxX).isEqualTo(999)
        assertThat(instr.maxY).isEqualTo(999)
    }

    @Test
    fun part1() {
        assertThat(day.part1()).isEqualTo(400410)
    }

    @Test
    fun part2() {
        assertThat(day.part2()).isEqualTo(15343601)
    }
}
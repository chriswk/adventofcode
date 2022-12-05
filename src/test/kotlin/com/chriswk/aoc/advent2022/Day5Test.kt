package com.chriswk.aoc.advent2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day5Test {
    val day = Day5()
    val testInput = """
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
    """.trimIndent().lines()

    @Test
    fun can_parse_input_format() {
        val (stacks, operations) = day.parseInput(testInput)
        assertThat(stacks).hasSize(3)
        assertThat(stacks[0]).hasSize(2)
        assertThat(stacks[1]).hasSize(3)
        assertThat(stacks[2]).hasSize(1)
        assertThat(operations).hasSize(4)
        assertThat(operations[0].cratesToMove).isEqualTo(1)
        assertThat(operations[0].fromStack).isEqualTo(1)
        assertThat(operations[0].toStack).isEqualTo(0)
    }

    @Test
    fun can_perform_operations() {
        val (stacks, operations) = day.parseInput(testInput)
        val updated = day.applyOps(stacks, operations)
        assertThat(updated[0]).hasSize(1)
        assertThat(updated[1]).hasSize(1)
        assertThat(updated[2]).hasSize(4)
        assertThat(updated[2].first).isEqualTo('Z')
    }

    @Test
    fun can_parse_actual_input() {
        val (stacks, operations) = day.getStacksAndOps()
        assertThat(stacks).hasSize(9)
        assertThat(stacks[0]).hasSize(8)
        assertThat(stacks[1]).hasSize(8)
        assertThat(stacks[2]).hasSize(5)
        assertThat(stacks[3]).hasSize(8)
        assertThat(stacks[4]).hasSize(7)
        assertThat(stacks[5]).hasSize(6)
        assertThat(stacks[6]).hasSize(7)
        assertThat(stacks[7]).hasSize(3)
        assertThat(stacks[8]).hasSize(4)
    }

    @Test
    fun part_1() {
        assertThat(day.part1()).isEqualTo("TBVFVDZPN")
    }

    @Test
    fun crate_mover_9001() {
        val (stacks, ops) = day.parseInput(testInput)
        val movedStacks = day.crateMover9001(stacks, ops)
        assertThat(movedStacks[0].first).isEqualTo('M')
        assertThat(movedStacks[1].first).isEqualTo('C')
        assertThat(movedStacks[2].first).isEqualTo('D')
    }

    @Test
    fun part_2() {
        assertThat(day.part2()).isEqualTo("VLCWHTDSZ")
    }
}

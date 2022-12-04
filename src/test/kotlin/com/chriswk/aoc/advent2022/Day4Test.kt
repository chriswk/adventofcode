package com.chriswk.aoc.advent2022

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
class Day4Test {
    val day = Day4()
    val testInput = """
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
    """.trimIndent().lines()
    @Test
    fun can_parse_single_elf() {
        val elf = "5-97"
        val range = day.parseElf(elf)
        assertThat(range.first).isEqualTo(5)
        assertThat(range.last).isEqualTo(97)
    }

    @Test
    fun can_find_overlaps() {
        val assignments = day.parseInput(testInput)
        assertThat(day.findOverlaps(assignments)).isEqualTo(2)
    }

    @Test
    fun verifies_expected_overlaps() {
        val input = """
            1-44,3-43
            97-97,10-98
            7-85,8-85
            12-32,12-52
            1-86,86-86
            """.trimIndent().lines()
        val elves = day.parseInput(input)
        assertThat(elves.all { day.overlaps(it) }).isTrue
    }

    @Test
    fun part_1() {
        assertThat(day.part1()).isEqualTo(595)
    }

    @Test
    fun part_2() {
        assertThat(day.part2()).isEqualTo(952)
    }
}

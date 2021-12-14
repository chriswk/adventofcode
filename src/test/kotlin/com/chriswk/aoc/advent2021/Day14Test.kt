package com.chriswk.aoc.advent2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day14Test {
    val testInput = """
        NNCB

        CH -> B
        HH -> N
        CB -> H
        NH -> C
        HB -> C
        HC -> B
        HN -> C
        NN -> C
        BH -> H
        NC -> B
        NB -> B
        BN -> B
        BB -> N
        BC -> B
        CC -> N
        CN -> C
    """.trimIndent().lines()
    val day = Day14()

    @Test
    fun `Can parse initial template`() {
        val (template, translations) = day.parseInput(testInput)
        assertThat(template).isEqualTo("NNCB")
        assertThat(translations).hasSize(16)
    }

    @Test
    fun `Part 1`() {
        assertThat(day.part1()).isEqualTo(2509)
    }

    @Test
    fun `Part 2`() {
        assertThat(day.part2()).isEqualTo(2827627697643)
    }
}

package com.chriswk.aoc.advent2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day3Test {

    val testInput = """
            00100
            11110
            10110
            10111
            10101
            01111
            00111
            11100
            10000
            11001
            00010
            01010
        """.trimIndent().lines()
    val day = Day3()

    @Test
    fun `Can calculate gamma rate`() {
        assertThat(day.gamma(testInput)).isEqualTo("10110")
        assertThat(day.gammaRate(day.gamma(testInput))).isEqualTo(22)
    }

    @Test
    fun `Can calculate epsilon rate`() {
        assertThat(day.epsilon("10110")).isEqualTo("01001")
    }

    @Test
    fun `Part 1`() {
        assertThat(day.part1()).isEqualTo(3009600)
    }

    @Test
    fun `Can find oxygen rating`() {
        assertThat(day.oxygen(testInput)).isEqualTo("10111")
    }

    @Test
    fun `Can find CO2 scrubber rating`() {
        assertThat(day.co2(testInput)).isEqualTo("01010")
    }

    @Test
    fun `Part 2`() {
        assertThat(day.part2()).isEqualTo(6940518)
    }
}

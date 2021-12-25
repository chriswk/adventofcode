package com.chriswk.aoc.advent2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day24Test {

    val day = Day24()
    @Test
    fun `Part 1`() {
        assertThat(day.part1()).isEqualTo(92969593497992L)
    }

    @Test
    fun `Part 2`() {
        assertThat(day.part2()).isEqualTo(81514171161381L)
    }
}

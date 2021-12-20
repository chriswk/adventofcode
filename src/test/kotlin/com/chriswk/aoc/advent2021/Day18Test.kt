package com.chriswk.aoc.advent2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day18Test {
    val day = Day18()
    @Test
    fun `Part 1`() {
        assertThat(day.part1()).isEqualTo(3987)
    }
}

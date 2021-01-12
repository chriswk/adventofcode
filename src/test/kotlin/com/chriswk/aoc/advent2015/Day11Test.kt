package com.chriswk.aoc.advent2015

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day11Test {

    @Test
    fun `can loop around`() {
        val password = "az"
        val day = Day11()
        assertThat(day.passwords(password).drop(1).first()).isEqualTo("ba")
    }

    @Test
    fun `part 1`() {
        assertThat(Day11().part1()).isEqualTo("hxbxxyzz")
    }

    @Test
    fun `part 2`() {
        assertThat(Day11().part2()).isEqualTo("hxcaabcc")
    }
}
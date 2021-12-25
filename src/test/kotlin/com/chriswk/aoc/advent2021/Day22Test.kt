package com.chriswk.aoc.advent2021

import com.chriswk.aoc.util.Point3D
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day22Test {

    val day = Day22()

    @Test
    fun `Part 1`() {
        assertThat(day.part1()).isEqualTo(647062)
    }

    @Test
    fun `Part 2`() {
        assertThat(day.part2()).isEqualTo(1319618626668022)
    }
}

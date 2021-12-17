package com.chriswk.aoc.advent2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day17Test {

    val day = Day17()

    @Test
    fun `Can build target area`() {
        val input = "target area: x=20..30, y=-10..-5"
        val target = day.target(input)
        assertThat(target.minX).isEqualTo(20)
        assertThat(target.maxX).isEqualTo(30)
        assertThat(target.minY).isEqualTo(-10)
        assertThat(target.maxY).isEqualTo(-5)
    }

    @Test
    fun `Can see if drone is witin target area`() {
        val input = "target area: x=20..30, y=-10..-5"
        val target = day.target(input)

        assertThat(Pair(25, -7) in target).isTrue
        assertThat(Pair(25, 7) in target).isFalse
    }

    @Test
    fun `Part 1`() {
        assertThat(day.part1()).isEqualTo(7875)
    }

    @Test
    fun `Part 2`() {
        assertThat(day.part2()).isEqualTo(2321)
    }
}

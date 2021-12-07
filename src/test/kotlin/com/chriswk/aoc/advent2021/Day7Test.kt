package com.chriswk.aoc.advent2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day7Test {

    val testInput = "16,1,2,0,4,2,7,1,2,14"

    val day = Day7()

    @Test
    fun `Can find minimum position for test input`() {
        val positionCosts = day.positionCosts(day.crabs(testInput))
        val pos = positionCosts.minByOrNull { it.value }!!
        assertThat(pos.key).isEqualTo(2)
        assertThat(pos.value).isEqualTo(37)
    }

    @Test
    fun `Part 1`() {
        assertThat(day.part1()).isEqualTo(356992)
    }

    @Test
    fun `Can find new minimum position for new cost`() {
        val positionCosts = day.positionCosts(day.crabs(testInput), true)
        val pos = positionCosts.minByOrNull { it.value }!!
        assertThat(pos.key).isEqualTo(5)
        assertThat(pos.value).isEqualTo(168)
    }

    @Test
    fun `Part 2`() {
        assertThat(day.part2()).isEqualTo(101268110)
    }

}

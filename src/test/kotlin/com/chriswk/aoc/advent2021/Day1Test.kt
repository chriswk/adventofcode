package com.chriswk.aoc.advent2021

import org.assertj.core.api.Assertions.assertThat

class Day1Test {
    val day1 = Day1()
    val testInput = listOf(
        199, 200, 208, 210, 200, 207, 240, 269, 260, 263
    )
    fun `Can find increasing depths from test input`() {

        assertThat(day1.increasing(testInput)).isEqualTo(7)
    }

    fun `Can find increasing windows from test input`() {
        assertThat(day1.increasingWindows(testInput)).isEqualTo(5)
    }

    fun `Answers part1`() {
        assertThat(day1.part1()).isEqualTo(1452)
    }

    fun `Answers part2`() {
        assertThat(day1.part2()).isEqualTo(1396)
    }
}

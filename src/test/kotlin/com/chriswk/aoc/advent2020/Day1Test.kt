package com.chriswk.aoc.advent2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day1Test {
    val testInput = """
        1721
        979
        366
        299
        675
        1456
    """.trimIndent().lines().map { it.toInt() }
    @Test
    fun part1_example_succeeds() {
        val day1 = Day1()
        val (a, b) = day1.findPair(2020, testInput.toSet())!!
        assertThat(a*b).isEqualTo(514579)
    }

    @Test
    fun part1() {
        val day1 = Day1()
        assertThat(day1.part1()).isEqualTo(982464)
    }

    @Test
    fun part2_example_succeeds() {
        val day1 = Day1()
        val (a, b, c) = day1.findTriplet(2020, testInput.toSet())!!
        assertThat(a*b*c).isEqualTo(241861950)
    }

    @Test
    fun part2() {
        val day1 = Day1()
        assertThat(day1.part2()).isEqualTo(162292410)
    }
}
package com.chriswk.aoc.advent2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day13Test {

    val smallInput = """
        939
        7,13,x,x,59,x,31,19
    """.trimIndent().lines()
    @Test
    fun part1SmallInput() {
        val day = Day13()
        val (earliest, buses) = day.findEarliestDepartureAndBuses(smallInput)
        assertThat(earliest).isEqualTo(939)
        assertThat(buses).hasSize(5)
        assertThat(day.findClosestBus(earliest, buses)).isEqualTo(Day13.BusDeparture(59, 5))
    }
    @Test
    fun part1() {
        assertThat(Day13().part1()).isEqualTo(5257)
    }

    @Test
    fun part2SmallInput() {
        val day = Day13()

        assertThat(day.solvePart2(smallInput[1])).isEqualTo(1068781L)
    }

    @Test
    fun part2() {
        assertThat(Day13().part2()).isEqualTo(538703333547789L)
    }
}
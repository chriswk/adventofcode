package com.chriswk.aoc.advent2015

import com.chriswk.aoc.util.permutations
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day9Test {

    @Test
    fun parsesDistances() {
        val input = """
            London to Dublin = 464
            London to Belfast = 518
            Dublin to Belfast = 141
        """.trimIndent().lines()
        val day = Day9()
        val distances = day.distances(input)
        assertThat(distances).hasSize(6)
    }

    @Test
    fun findsRoutes() {
        val input = """
            London to Dublin = 464
            London to Belfast = 518
            Dublin to Belfast = 141
        """.trimIndent().lines()
        val day = Day9()
        val distances = day.distances(input)
        val locations = day.locations(distances)
        val distance = day.shortestDistance(distances, locations)
        assertThat(distance).isEqualTo(605)
    }

    @Test
    fun part1() {
        assertThat(Day9().part1()).isEqualTo(117)
    }

    @Test
    fun part2() {
        assertThat(Day9().part2()).isEqualTo(909)
    }
}
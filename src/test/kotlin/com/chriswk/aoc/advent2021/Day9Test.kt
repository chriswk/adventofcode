package com.chriswk.aoc.advent2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day9Test {
    val testInput = """
        2199943210
        3987894921
        9856789892
        8767896789
        9899965678
    """.trimIndent().lines()
    val day = Day9()
    @Test
    fun `Can find low points`() {
        val i = day.parseInput(testInput)
        val points = day.findMinima(i)
        assertThat(points).hasSize(4)
        assertThat(points.sumOf { it.risk }).isEqualTo(15)
    }

    @Test
    fun `Part 1`() {
        assertThat(day.part1()).isEqualTo(588)
    }

    @Test
    fun `Finds basins of test input`() {
        val grid = day.parseInput(testInput)
        val minima = day.findMinima(grid)
        val basins = day.computeBasins(minima, grid)
        assertThat(basins).hasSize(minima.size)
        assertThat(basins.map { it.size }.sortedDescending()).isEqualTo(listOf(14, 9, 9, 3))
    }

    @Test
    fun `Part 2`() {
        assertThat(day.part2()).isEqualTo(964712)
    }
}

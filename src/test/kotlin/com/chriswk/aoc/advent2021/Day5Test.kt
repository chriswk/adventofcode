package com.chriswk.aoc.advent2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day5Test {

    val testInput = """
        0,9 -> 5,9
        8,0 -> 0,8
        9,4 -> 3,4
        2,2 -> 2,1
        7,0 -> 7,4
        6,4 -> 2,0
        0,9 -> 2,9
        3,4 -> 1,4
        0,0 -> 8,8
        5,5 -> 8,2
    """.trimIndent().lines()

    val day = Day5()
    @Test
    fun `can parse a line`() {
        val line = Day5.Line(testInput.first())
        assertThat(line.xStart).isEqualTo(0)
        assertThat(line.yStart).isEqualTo(9)
        assertThat(line.xEnd).isEqualTo(5)
        assertThat(line.yEnd).isEqualTo(9)
    }

    @Test
    fun `can check if point is covered by line`() {
        val point = Day5.Point(4, 9)
        val line = Day5.Line(testInput.first())
        assertThat(line.covers(point)).isTrue
        assertThat(line.covers(Day5.Point(6, 9))).isFalse
        assertThat(line.covers(Day5.Point(5, 9))).isTrue
    }

    @Test
    fun `finds correct number of points covered by lines for test output`() {
        val lines = day.parseInput(testInput).filter { it.isVertical || it.isHorizontal }
        val points = day.findPoints(lines)
        assertThat(points.values.count { it > 1 }).isEqualTo(5)
    }

    fun `Part 1`() {
        assertThat(day.part1()).isEqualTo(8111)
    }

    @Test
    fun `Points on diagonal line is also found`() {
        val line = Day5.Line(testInput.get(1))
        assertThat(line.covers(Day5.Point(5, 3)))
    }

    @Test
    fun `New cover with diagonals on testInput`() {
        val lines = day.parseInput(testInput)
        val points = day.findPoints(lines)
        assertThat(points.values.count { it > 1 }).isEqualTo(12)
    }

    @Test
    fun `Part 2`() {
        assertThat(day.part2()).isEqualTo(22088)
    }
}

package com.chriswk.aoc.advent2021

import com.chriswk.aoc.util.Pos
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day11Test {
    val testInput = """
        5483143223
        2745854711
        5264556173
        6141336146
        6357385478
        4167524645
        2176841721
        6882881134
        4846848554
        5283751526
    """.trimIndent().lines()
    val day = Day11()


    @Test
    fun `Can parse test input to grid`() {
        val grid = day.parseInput(testInput)
        assertThat(grid).hasSize(100)
    }

    @Test
    fun `Can step single day`() {
        val octopi = day.parseInput(testInput)
        val dayOne = day.steps(Day11.Grid(octopi, 10, 10), 1)
        assertThat(dayOne.flashCount).isEqualTo(0)
    }


    @Test
    fun `counts all flashes`() {
        val example = """
            999
            909
            999
        """.trimIndent().lines()
        val octopi = day.parseInput(example)
        val nextDay = day.steps(Day11.Grid(octopi, 3, 3), 1)
        assertThat(nextDay.flashCount).isEqualTo(8)
        assertThat(nextDay.octopi).containsEntry(Pos(1, 1), 9)
    }

    @Test
    fun `big growth`() {
        val growth = """
            11111
            19991
            19191
            19991
            11111
        """.trimIndent().lines()
        val expectedOutput = """
            34543
            40004
            50005
            40004
            34543
        """.trimIndent()
        val octopi = day.parseInput(growth)
        val dayOne = day.steps(Day11.Grid(octopi, 5, 5), 1)
        assertThat(dayOne.toString()).isEqualTo(expectedOutput)
    }

    @Test
    fun `Can calculate flashes`() {
        val octopi = day.parseInput(testInput)
        val dayTwo = day.steps(Day11.Grid(octopi, 10, 10), 2)
        assertThat(dayTwo.flashCount).isEqualTo(35)

    }

    @Test
    fun `Finds all flashing at expected generation for test input`() {
        val grid = Day11.Grid(day.parseInput(testInput), 10, 10)
        assertThat(day.allFlashingGeneration(grid)).isEqualTo(195)
    }

    @Test
    fun `Point at 0,0 only has three neighbours`() {
        val pos = Pos(0, 0)
        assertThat(pos.neighbours().filter { it.isInGrid(10, 10) }).hasSize(3)
    }


    @Test
    fun `Point at 3,1 has eight neighbours`() {
        val pos = Pos(3, 1)
        assertThat(pos.neighbours().filter { it.isInGrid(10, 10) }).hasSize(8)
    }

    @Test
    fun `All points has distinct neighbours`() {
        val points = (0..10).flatMap { y -> (0..10).map { x -> Pos(x, y) } }
        points.forEach { p ->
            assertThat(p.neighbours().size).isEqualTo(p.neighbours().toSet().size)
        }
    }

    @Test
    fun `Part 1`() {
        assertThat(day.part1()).isEqualTo(1665)
    }

    @Test
    fun `Part 2`() {
        assertThat(day.part2()).isEqualTo(235)
    }
}

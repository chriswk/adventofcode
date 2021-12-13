package com.chriswk.aoc.advent2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day13Test {
    val testInput = """
        6,10
        0,14
        9,10
        0,3
        10,4
        4,11
        6,0
        6,12
        4,1
        0,13
        10,12
        3,4
        3,0
        8,4
        1,10
        2,14
        8,10
        9,0

        fold along y=7
        fold along x=5
    """.trimIndent().lines()

    val day = Day13()

    @Test
    fun `Can parse grid`() {
        val (points, instructions) = day.parse(testInput)
        assertThat(points).hasSize(18)
        assertThat(instructions).hasSize(2)
    }

    @Test
    fun `Can fold`() {
        val (points, instructions) = day.parse(testInput)
        val paperAfterFold = day.fold(points, instructions.first())
        assertThat(paperAfterFold).hasSize(17)
    }

    @Test
    fun `Can print`() {
        val (points, instructions) = day.parse(testInput)
        day.printGrid(points)
        println("")
        val newGrid = day.fold(points, instructions.first())
        day.printGrid(newGrid)
    }
}

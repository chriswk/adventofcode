package com.chriswk.aoc.advent2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day25Test {

    val day = Day25()

    val testInput = """
        ...>>>>>...
    """.trimIndent()

    val biggerInput = """
        v...>>.vv>
        .vv>>.vv..
        >>.>v>...v
        >>v>>.>.v.
        v>v.vv.v..
        >.>>..v...
        .vv..>.>v.
        v.v..>>v.v
        ....v..v.>
    """.trimIndent().lines()

    @Test
    fun `can parse single line`() {
        val seabottom = day.parseInput(testInput.lines())
        assertThat(seabottom.cucumbers).hasSize(5)
    }

    @Test
    fun `Can move`() {
        val seabottom = day.parseInput(testInput.lines())
        val newBottom = seabottom.move()
        assertThat(newBottom.movesLastGeneration).isEqualTo(1)
    }

    @Test
    fun `Can move several times`() {
        val seabottom = day.parseInput(testInput.lines())
        val newBottom = seabottom.move()
        val secondBottom = newBottom.move()
        assertThat(secondBottom.movesLastGeneration).isEqualTo(2)
    }

    @Test
    fun `Handles east before south`() {
        val input = """
            ..........
            .>v....v..
            .......>..
            ..........
        """.trimIndent().lines()
        val bottom = day.parseInput(input)
        val new = bottom.move()
        assertThat(new.movesLastGeneration).isEqualTo(3)
    }

    @Test
    fun `Handles wrap around`() {
        val input = """
            ...>...
            .......
            ......>
            v.....>
            ......>
            .......
            ..vvv..
        """.trimIndent().lines()
        val bottom = day.parseInput(input)
        val next = bottom.move()
        assertThat(next.cucumbers.keys.count { it.y == 0}).isEqualTo(3)
    }

    @Test
    fun `Finds generation where no cucumbers moved`() {
        assertThat(day.stopsMoving(biggerInput)).isEqualTo(58)
    }
}

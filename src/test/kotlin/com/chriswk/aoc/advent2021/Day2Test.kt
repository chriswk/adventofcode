package com.chriswk.aoc.advent2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day2Test {

    @Test
    fun `Can parse instruction`() {
        val day = Day2()
        assertThat(day.parseInstructionsPart1("forward 5")).isEqualTo(Day2.Submarine(5, 0))
        assertThat(day.parseInstructionsPart1("down 10")).isEqualTo(Day2.Submarine(0, 10))
        assertThat(day.parseInstructionsPart1("up 10")).isEqualTo(Day2.Submarine(0, -10))
    }

    @Test
    fun `Can drive submarine`() {
        val day = Day2()
        val instructions = day.parseInstructions(
            """
            forward 5
            down 5
            forward 8
            up 3
            down 8
            forward 2
        """.trimIndent().lines()
        )
        val sub = day.drive(Day2.Submarine(0, 0), instructions)
        assertThat(sub.x).isEqualTo(15)
        assertThat(sub.depth).isEqualTo(10)
        assertThat(sub.product).isEqualTo(150)
    }

    @Test
    fun `Can calculate aim`() {
        val day = Day2()
        val instructions = day.parseInstructionsPart2(
            """
                        forward 5
                        down 5
                        forward 8
                        up 3
                        down 8
                        forward 2
        """.trimIndent().lines()
        )
        val sub = day.drivePart2(Day2.Submarine(0, 0), instructions)
        assertThat(sub.x).isEqualTo(15)
        assertThat(sub.depth).isEqualTo(60)
        assertThat(sub.product).isEqualTo(900)
    }

    @Test
    fun `Part 1`() {
        assertThat(Day2().part1()).isEqualTo(1938402)
    }

    @Test
    fun `Part 2`() {
        assertThat(Day2().part2()).isEqualTo(1947878632)
    }
}

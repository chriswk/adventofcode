package com.chriswk.aoc.advent2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day11Test {
    val smallInput: Seats = """
        L.LL.LL.LL
        LLLLLLL.LL
        L.L.L..L..
        LLLL.LL.LL
        L.LL.LL.LL
        L.LLLLL.LL
        ..L.L.....
        LLLLLLLLLL
        L.LLLLLL.L
        L.LLLLL.LL
    """.trimIndent().lines().map { it.toCharArray() }.toTypedArray()

    @Test
    fun part1SmallInput() {
        val day = Day11()
        val nextStep = smallInput.next(4, day::occupiedNeighbours)
        assertThat(nextStep.joinToString("\n") { it.joinToString("") }).isEqualTo(
            """
            #.##.##.##
            #######.##
            #.#.#..#..
            ####.##.##
            #.##.##.##
            #.#####.##
            ..#.#.....
            ##########
            #.######.#
            #.#####.##
        """.trimIndent()
        )
        val secondStep = nextStep.next(4, day::occupiedNeighbours)
        assertThat(secondStep.joinToString("\n") { it.joinToString(separator = "") }).isEqualTo(
            """
                #.LL.L#.##
                #LLLLLL.L#
                L.L.L..L..
                #LLL.LL.L#
                #.LL.LL.LL
                #.LLLL#.##
                ..L.L.....
                #LLLLLLLL#
                #.LLLLLL.L
                #.#LLLL.##
            """.trimIndent()
        )
    }

    @Test
    fun part1EquilibriumSmallInput() {
        val day = Day11()
        val stableSeats = day.findEquilibrium(smallInput, 4, day::occupiedNeighbours)
        assertThat(stableSeats.occupied()).isEqualTo(37)
    }

    @Test
    fun part1() {
        val day = Day11()
        assertThat(day.part1()).isEqualTo(2310)
    }

    @Test
    fun part2EquilibriumSmallInput() {
        val day = Day11()
        val stableSeats = day.findEquilibrium(smallInput, 5, day::occupiedNeighboursAxis)
        assertThat(stableSeats.occupied()).isEqualTo(26)
    }

    @Test
    fun part2() {
        assertThat(Day11().part2()).isEqualTo(2074)
    }

}
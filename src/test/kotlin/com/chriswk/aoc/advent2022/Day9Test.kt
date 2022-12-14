package com.chriswk.aoc.advent2022

import com.chriswk.aoc.util.CompassDirection
import com.chriswk.aoc.util.Point2D
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
class Day9Test {
    val day = Day9()
    val testInput = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent().lines()

    @Test
    fun can_parse_to_moves() {
        val moves = day.parseInput(testInput)
        assertThat(moves).hasSize(24)
        assertThat(moves.first()).isEqualTo(CompassDirection.East)
    }

    @Test
    fun head_moves_as_expected() {
        val moves = day.parseInput(testInput)
        val head = day.move(Point2D(0, 0), moves)
        val eastBound = head.take(5)
        assertThat(eastBound.toList()).isEqualTo(listOf(Point2D(0,0), Point2D(1, 0), Point2D(2, 0), Point2D(3, 0), Point2D(4, 0)))
    }

    @Test
    fun moves_diagonally() {
        val head = sequenceOf(Point2D(1, 1), Point2D(2, 1))
        val follow = head.follow().toList()
        assertThat(follow[1]).isEqualTo(Point2D(1, 1))
    }
    @Test
    fun visits_13_unique_points() {
        val moves = day.parseInput(testInput)
        val head = day.move(Point2D(0, 0), moves)
        assertThat(head.toList()).hasSize(25)
        assertThat(head.follow().toSet()).hasSize(13)
    }

    @Test
    fun part_1() {
        assertThat(day.part1()).isEqualTo(6212)
    }

    @Test
    fun part_2() {
        assertThat(day.part2()).isEqualTo(2522)
    }
}

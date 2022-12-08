package com.chriswk.aoc.advent2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day8Test {
    val day = Day8()
    val testInput = """
        30373
        25512
        65332
        33549
        35390
    """.trimIndent()

    @Test
    fun can_parse_tree() {
        val (columns, rows) = day.parseInput(testInput.lines())
        assertThat(columns).hasSize(5)
        assertThat(rows).hasSize(5)
    }

    @Test
    fun can_find_visible_trees_in_test_input() {
        val (columns, rows) = day.parseInput(testInput.lines())
        assertThat(day.findVisible(columns, rows)).isEqualTo(21)
    }

    @Test
    fun part_1() {
        assertThat(day.part1()).isEqualTo(1715)
    }

    @Test
    fun scenicScore() {
        val (columns, rows) = day.parseInput(testInput.lines())
        assertThat(day.findScenicScore(columns, rows)).isEqualTo(8)
    }

    @Test
    fun part_2() {
        assertThat(day.part2()).isEqualTo(374400)
    }
}

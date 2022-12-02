package com.chriswk.aoc.advent2022

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.*
class Day2Test {
    val day2 = Day2()
    val testInput = """
        A Y
        B X
        C Z
    """.trimIndent()

    @Test
    fun should_parse_single_line_to_round() {
        assertThat(Day2.Round.fromLine("A Y")).isEqualTo(Day2.Round(Day2.Play.Rock, Day2.Play.Paper))
        assertThat(Day2.Round.fromLine("B X")).isEqualTo(Day2.Round(Day2.Play.Paper, Day2.Play.Rock))
        assertThat(Day2.Round.fromLine("C Z")).isEqualTo(Day2.Round(Day2.Play.Scissors, Day2.Play.Scissors))
    }
    @Test
    fun should_calculate_score_correctly() {
        assertThat(day2.parseInput(testInput.lines()).sumOf { it.score() }).isEqualTo(15)
    }

    @Test
    fun part_1() {
        assertThat(day2.part1()).isEqualTo(15632)
    }

    @Test
    fun should_parse_single_line_from_end_result_requirement() {
        assertThat(Day2.Round.fromLinePart2("A Y")).isEqualTo(Day2.Round(Day2.Play.Rock, Day2.Play.Rock))
        assertThat(Day2.Round.fromLinePart2("B X")).isEqualTo(Day2.Round(Day2.Play.Paper, Day2.Play.Rock))
        assertThat(Day2.Round.fromLinePart2("C Z")).isEqualTo(Day2.Round(Day2.Play.Scissors, Day2.Play.Rock))
    }
    @Test
    fun should_calculate_score_for_part_2_correctly() {
        assertThat(day2.parseInputPart2(testInput.lines()).sumOf { it.score() }).isEqualTo(12)
    }

    @Test
    fun part_2() {
        assertThat(day2.part2()).isEqualTo(14416)
    }
}

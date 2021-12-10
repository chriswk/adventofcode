package com.chriswk.aoc.advent2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day10Test {

    val day = Day10()

    val testInput = """
        [({(<(())[]>[[{[]{<()<>>
        [(()[<>])]({[<{<<[]>>(
        {([(<{}[<>[]}>{[]{[(<()>
        (((({<>}<{<{<>}{[]{[]{}
        [[<[([]))<([[{}[[()]]]
        [{[{({}]{}}([{[{{{}}([]
        {<[[]]>}<{[{[{[]{()[[[]
        [<(<(<(<{}))><([]([]()
        <{([([[(<>()){}]>(<<{{
        <{([{{}}[<[[[<>{}]]]>[]]
    """.trimIndent().lines()

    @Test
    fun `can parse single line`() {
        val line = "(>"
        assertThat(day.handleLine(line).second).isEqualTo(25137)
    }

    @Test
    fun `A correct line yields 0 as error score`() {
        val line = "({}){<>[]}"
        assertThat(day.handleLine(line).second).isEqualTo(0)
    }

    @Test
    fun `Can sum error scores`() {
        assertThat(day.errorScore(testInput)).isEqualTo(26397)
    }

    @Test
    fun `Part 1`() {
        assertThat(day.part1()).isEqualTo(369105)
    }

    @Test
    fun `Can score completion of single line`() {
        assertThat(day.completionScore(testInput.first())).isEqualTo(288957)
    }

    @Test
    fun `Finds middle completion score of test input`() {
        assertThat(day.middleCompletionScore(testInput)).isEqualTo(288957)
    }

    @Test
    fun `Part 2`() {
        assertThat(day.part2()).isEqualTo(3999363569)
    }
}

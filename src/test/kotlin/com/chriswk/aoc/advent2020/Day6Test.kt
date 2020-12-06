package com.chriswk.aoc.advent2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day6Test {

    @Test
    fun handlesTestInput() {
        val group = """
            abcx
            abcy
            abcz
        """.trimIndent()
        val day = Day6()
        assertThat(day.toAnswers(group)).isEqualTo(6)
    }

    @Test
    fun part1() {
        assertThat(Day6().part1()).isEqualTo(6521)
    }

    @Test
    fun identifiesAnswersWhereAllMembersOfGroupAnsweredYes() {
        val group = """
            abcx
            abcy
            abcz
        """.trimIndent()
        val day = Day6()
        assertThat(day.toUnanimousAnswers(group)).isEqualTo(3)
    }
    @Test
    fun part2() {
        assertThat(Day6().part2()).isEqualTo(3305)
    }
}
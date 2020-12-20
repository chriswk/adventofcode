package com.chriswk.aoc.advent2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day19Test {
    val shortInput = """
        0: 4 1 5
        1: 2 3 | 3 2
        2: 4 4 | 5 5
        3: 4 5 | 5 4
        4: "a"
        5: "b"

        ababbb
        bababa
        abbbab
        aaabbb
        aaaabbb
    """.trimIndent().lines()

    @Test
    fun ruleParsingOfExample() {
        val day = Day19()
        val rulesMap = day.parseRules(shortInput)
        assertThat(rulesMap).containsAllEntriesOf(
            mapOf(
                4 to listOf(listOf(Atom('a'))),
                5 to listOf(listOf(Atom('b')))
            )
        )
    }

    @Test
    fun validatesCorrectRules() {
        val day = Day19()
        val valid = day.validMessages(shortInput.dropWhile { it.isNotBlank() }.drop(1), day.parseRules(shortInput))
        assertThat(valid).isEqualTo(2)
    }

    @Test
    fun part1() {
        assertThat(Day19().part1()).isEqualTo(272)
    }

    @Test
    fun part2() {
        assertThat(Day19().part2()).isEqualTo(374)
    }

}
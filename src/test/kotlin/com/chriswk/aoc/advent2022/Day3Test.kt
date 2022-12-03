package com.chriswk.aoc.advent2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day3Test {
    val day = Day3()
    val testInput = """
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent().lines()
    @Test
    fun priorityMapIsCorrect() {
        assertThat(day.priorityMap['z']).isEqualTo(26)
        assertThat(day.priorityMap['A']).isEqualTo(27)
        assertThat(day.priorityMap['S']).isEqualTo(45)
        assertThat(day.priorityMap['Z']).isEqualTo(52)
    }

    @Test
    fun canFindIntersectionsInTestInput() {
        assertThat(day.findIntersectionPriorities(testInput[0])).isEqualTo(listOf(16))
        assertThat(day.findIntersectionPriorities(testInput[1])).isEqualTo(listOf(38))
    }
    @Test
    fun findsCorrectSumForIntersections() {
        assertThat(day.inputToPriorities(testInput).sum()).isEqualTo(157)
    }

    @Test
    fun part_1() {
        assertThat(day.part1()).isEqualTo(7997)
    }

    @Test
    fun can_find_badge_for_group() {
        assertThat(day.badge(testInput.take(3))).isEqualTo(setOf('r'))
    }

    @Test
    fun priorities_for_test_group() {
        assertThat(day.badgeSum(testInput)).isEqualTo(70)
    }

    @Test
    fun part_2() {
        assertThat(day.part2()).isEqualTo(2545)
    }
}

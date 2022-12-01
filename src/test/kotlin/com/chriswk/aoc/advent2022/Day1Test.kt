package com.chriswk.aoc.advent2022

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.*


class Day1Test {
  val day1 = Day1()
    val exampleInput = """
        1000
        2000
        3000

        4000

        5000
        6000

        7000
        8000
        9000

        10000
        
    """.trimIndent().lines()
    @Test
    fun should_successfully_find_biggest_calorie() {
        assertThat(day1.maxCalories(day1.parseInput(exampleInput))).isEqualTo(24000)
    }

    @Test
    fun part_1() {
        assertThat(day1.part1()).isEqualTo(66186)
    }

    @Test
    fun should_find_sum_of_three_elves_with_most_calories() {
        assertThat(day1.maxCaloriesByTopThree(day1.parseInput(exampleInput))).isEqualTo(45000)
    }

    @Test
    fun part_2() {
        assertThat(day1.part2()).isEqualTo(196804)
    }
}

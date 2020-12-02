package com.chriswk.aoc.advent2020

import com.chriswk.aoc.advent2020.Day2.Companion.toPolicyAndPassword
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day2Test {

    val testInput = """
        1-3 a: abcde
        1-3 b: cdefg
        2-9 c: ccccccccc
    """.trimIndent().lines()

    @Test
    fun part1_example_succeeds() {
        val valid = testInput.map { it.toPolicyAndPassword() }.count { (policy, password) ->
            policy.validPart1(password)
        }
        assertThat(valid).isEqualTo(2)
    }

    @Test
    fun part1() {
        val day = Day2()
        assertThat(day.part1()).isEqualTo(447)
    }

    @Test
    fun part2_example_succeeds() {
        val valid = testInput.map { it.toPolicyAndPassword() }.count { (policy, password) ->
            policy.validPart2(password)
        }
        assertThat(valid).isEqualTo(1)
    }

    @Test
    fun part2() {
        val day = Day2()
        assertThat(day.part2()).isEqualTo(249)
    }
}
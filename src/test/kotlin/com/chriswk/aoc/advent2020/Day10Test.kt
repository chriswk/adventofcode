package com.chriswk.aoc.advent2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day10Test {
    val smallInput = """
            16
            10
            15
            5
            1
            11
            7
            19
            6
            12
            4
        """.trimIndent().lines().map { it.toInt() }
    val largeInput = """
            28
            33
            18
            42
            31
            14
            46
            20
            48
            47
            24
            23
            49
            45
            19
            38
            39
            11
            1
            32
            25
            35
            8
            17
            7
            9
            4
            2
            34
            10
            3
        """.trimIndent().lines().map { it.toInt() }
    @Test
    fun part1SmallInput() {
        val day = Day10()
        val diffMap = day.groupAdapters(smallInput)
        assertThat(diffMap[1]).isEqualTo(7)
        assertThat(diffMap[3]).isEqualTo(5)
    }
    @Test
    fun part1LargeInput() {
        val day = Day10()
        val diffMap = day.groupAdapters(largeInput)
        assertThat(diffMap[1]).isEqualTo(22)
        assertThat(diffMap[3]).isEqualTo(10)
    }

    @Test
    fun part1() {
        assertThat(Day10().part1()).isEqualTo(2346)
    }

    @Test
    fun part2SmallInput() {
        val day = Day10()
        assertThat(day.findPossibleValidPathsToUseAll(smallInput)).isEqualTo(8)
    }

    @Test
    fun part2LargeInput() {
        val day = Day10()
        assertThat(day.findPossibleValidPathsToUseAll(largeInput)).isEqualTo(19208)
    }

    @Test
    fun part2() {
        assertThat(Day10().part2()).isEqualTo(6044831973376)
    }
}
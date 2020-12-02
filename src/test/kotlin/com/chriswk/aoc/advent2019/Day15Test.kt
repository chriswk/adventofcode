package com.chriswk.aoc.advent2019

import com.chriswk.aoc.util.dayInputAsString
import com.chriswk.aoc.util.parseBigInstructions
import com.chriswk.aoc.util.toMutableMap
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day15Test {

    @Test
    fun part1() {
        val day15 = Day15()
        assertThat(day15.part1()).isEqualTo(240)

    }

    @Test
    fun part2() {
        val day15 = Day15()
        assertThat(day15.part2()).isEqualTo(322)
    }
}

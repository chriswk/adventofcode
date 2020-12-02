package com.chriswk.aoc.advent2018

import com.chriswk.aoc.util.dayInputAsLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class Day17Test {

    @Test
    fun part1() {
        assertThat(Day17.part1(dayInputAsLines(2018, 17))).isEqualTo(31953)
    }

    @Test
    fun part2() {
        assertThat(Day17.part2(dayInputAsLines(2018, 17))).isEqualTo(26410)
    }
}
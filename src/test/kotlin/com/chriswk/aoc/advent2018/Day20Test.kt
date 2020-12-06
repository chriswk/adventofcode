package com.chriswk.aoc.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day20Test {

    @Test
    fun part1() {
        assertThat(Day20().part1()).isEqualTo(4239)
    }

    @Test
    fun part2() {
        assertThat(Day20().part2()).isEqualTo(8205)
    }
}
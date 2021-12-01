package com.chriswk.aoc.advent2015

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day12Test {
    @Test
    fun `part 1`() {
        assertThat(Day12().part1()).isEqualTo(119433)
    }

    @Test
    fun `part 2`() {
        assertThat(Day12().part2()).isEqualTo(68466)
    }
}

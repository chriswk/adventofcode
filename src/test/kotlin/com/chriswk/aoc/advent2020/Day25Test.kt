package com.chriswk.aoc.advent2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigInteger

class Day25Test {

    @Test
    fun findsCorrectLoopSizeForExample() {
        val day = Day25()
        val loopSize = day.findLoopSize(5764801)
        assertThat(loopSize).isEqualTo(8)
    }

    @Test
    fun part1() {
        assertThat(Day25().part1()).isEqualTo(15217943)
    }
}
package com.chriswk.aoc2017

import com.chriswk.aoc.advent2017.Day1
import com.chriswk.aoc.util.dayInputAsString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class Day1Test {
    @Test
    fun solution1() {
        val timeTaken = measureTimeMillis {
            assertThat(Day1.partOne(dayInputAsString(2017, 1))).isEqualTo(997)
        }
        println("Part 1 took $timeTaken ms")
    }

    @Test
    fun solution2() {
        val timeTaken = measureTimeMillis {
            assertThat(Day1.partTwo(dayInputAsString(2017, 1))).isEqualTo(1358)
        }
        println("Part 2 took $timeTaken ms")

    }
}
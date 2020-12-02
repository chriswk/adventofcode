package com.chriswk.aoc.advent2018

import com.chriswk.aoc.util.dayInputAsLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class Day1Test {

    @Test
    fun part1() {
        val timeTaken = measureTimeMillis {
            assertThat(Day1.partOne(dayInputAsLines(2018, 1))).isEqualTo(538)
        }
        println("Part 1 took $timeTaken ms")
    }

    @Test
    fun part2() {
        val timeTaken = measureTimeMillis {
            assertThat(Day1.partTwo(dayInputAsLines(2018, 1))).isEqualTo(77271)
        }
        println("Part 2 took $timeTaken ms")
    }
}
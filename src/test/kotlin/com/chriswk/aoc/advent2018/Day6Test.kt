package com.chriswk.aoc.advent2018

import com.chriswk.aoc.util.dayInputAsLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class Day6Test {

    @Test
    fun example() {
        val timeTaken = measureTimeMillis {
            assertThat(Day6.chronalArea("""
            1, 1
            1, 6
            8, 3
            3, 4
            5, 5
            8, 9
        """.trimIndent().lines())).isEqualTo(17)
        }
        println("Time taken for example $timeTaken ms")
    }

    @Test
    fun solution1() {
        val timeTaken = measureTimeMillis {
            val input = dayInputAsLines(2018, 6)
            assertThat(Day6.chronalArea(input)).isEqualTo(3251)
        }
        println("part 1 took $timeTaken ms")
    }

    @Test
    fun solution2() {
        val timeTaken = measureTimeMillis {
            val input = dayInputAsLines(2018, 6)
            assertThat(Day6.totalArea(input)).isEqualTo(47841)
        }
        println("part 2 took $timeTaken ms")
    }
}
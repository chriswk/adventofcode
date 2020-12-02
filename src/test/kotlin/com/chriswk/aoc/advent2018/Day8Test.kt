package com.chriswk.aoc.advent2018

import com.chriswk.aoc.util.dayInputAsLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class Day8Test {

    @Test
    fun part1_example() {
        val timeTaken = measureTimeMillis {
            assertThat(Day8.part1(listOf("""2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"""))).isEqualTo(138)
        }
        println("Part 1 example Took $timeTaken ms")
    }

    @Test
    fun part1() {
        val timeTaken = measureTimeMillis {
            assertThat(Day8.part1(dayInputAsLines(2018, 8))).isEqualTo(49602)
        }
        println("Part 1 took $timeTaken ms")
    }

    @Test
    fun part2_example() {
        val timeTaken = measureTimeMillis {
            assertThat(Day8.part2(listOf("""2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"""))).isEqualTo(66)
        }
        println("Part 2 example took $timeTaken ms")
    }

    @Test
    fun part2() {
        val timeTaken = measureTimeMillis {
            assertThat(Day8.part2(dayInputAsLines(2018, 8))).isEqualTo(25656)
        }
        println("Part 2 took $timeTaken ms")

    }
}
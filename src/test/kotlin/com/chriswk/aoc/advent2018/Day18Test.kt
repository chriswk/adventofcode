package com.chriswk.aoc.advent2018

import com.chriswk.aoc.util.dayInputAsLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class Day18Test {
    val sample = """
.#.#...|#.
.....#|##|
.|..|...#.
..|#.....#
#.#|||#|#|
...#.||...
.|....|...
||...#|.#|
|.||||..|.
...#.|..|.
    """.trimIndent()


    @Test
    fun part1_example() {
        assertThat(Day18.part1(sample.lines())).isEqualTo(1147)
    }

    @Test
    fun part1() {
        val timeTaken = measureTimeMillis {
            assertThat(Day18.part1(dayInputAsLines(2018, 18))).isEqualTo(515496)
        }
        println("Part 1 took $timeTaken ms")
    }

    @Test
    fun part2() {
        val timeTaken = measureTimeMillis {
            assertThat(Day18.part2(dayInputAsLines(2018, 18))).isEqualTo(233058)
        }
        println("Part 2 took $timeTaken ms")

    }
}
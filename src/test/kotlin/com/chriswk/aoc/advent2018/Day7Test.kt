package com.chriswk.aoc.advent2018

import com.chriswk.aoc.util.dayInputAsLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class Day7Test {

    @Test
    fun examplePart1() {
        val timeTaken = measureTimeMillis {
            assertThat(Day7.part1("""
                Step C must be finished before step A can begin.
                Step C must be finished before step F can begin.
                Step A must be finished before step B can begin.
                Step A must be finished before step D can begin.
                Step B must be finished before step E can begin.
                Step D must be finished before step E can begin.
                Step F must be finished before step E can begin.
            """.trimIndent().lines())).isEqualTo("CABDFE")
        }
        println("Example took $timeTaken ms")
    }

    @Test
    fun solution1() {
        val timeTaken = measureTimeMillis {
            assertThat(Day7.part1(dayInputAsLines(2018, 7))).isEqualTo("BFKEGNOVATIHXYZRMCJDLSUPWQ")
        }
        println("Part 1 took $timeTaken ms")
    }


    @Test
    fun solution2() {
        val timeTaken = measureTimeMillis {
            assertThat(Day7.part2(requirements = dayInputAsLines(2018, 7))).isEqualTo(1020)
        }
        println("Part 2 took $timeTaken ms")

    }
}
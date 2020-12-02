package com.chriswk.aoc.advent2018

import com.chriswk.aoc.util.dayInputAsLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class Day15Test {

    @Test
    fun part1() {
        val timeTaken = measureTimeMillis {
            val ans = Day15.part1(dayInputAsLines(2018, 15))
            assertThat(ans).isEqualTo(214731)
        }
        println("Part 1 took $timeTaken ms")
    }

    @Test
    fun part2() {
        val timeTaken = measureTimeMillis {
            val ans = Day15.part2(dayInputAsLines(2018, 15))
            assertThat(ans).isEqualTo(53222)
        }
        println("Part 2 took $timeTaken ms")
    }

}
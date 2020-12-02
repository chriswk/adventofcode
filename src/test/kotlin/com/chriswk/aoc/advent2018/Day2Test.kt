package com.chriswk.aoc.advent2018

import com.chriswk.aoc.util.dayInputAsLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class Day2Test {

    @Test
    fun partOne() {
        val timeTaken = measureTimeMillis {
            assertThat(Day2.partOne(dayInputAsLines(2018, 2))).isEqualTo(5904)
        }
        println("Part 1 took $timeTaken ms")
    }

    @Test
    fun partTwo() {
        val timeTaken = measureTimeMillis {
            assertThat(Day2.partTwo(dayInputAsLines(2018, 2))).isEqualTo("jiwamotgsfrudclzbyzkhlrvp")
        }
        println("Part 2 took $timeTaken ms")
    }

}
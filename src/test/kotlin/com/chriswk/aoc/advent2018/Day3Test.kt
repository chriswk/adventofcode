package com.chriswk.aoc.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class Day3Test {

    @Test
    fun solution1() {
        val timeTaken = measureTimeMillis {
          assertThat(Day3.solution1()).isEqualTo(100595)
        }
        println("Took $timeTaken ms")
    }

    @Test
    fun solution2() {
        val timeTaken = measureTimeMillis {
            assertThat(Day3.solution2()).isEqualTo(415)
        }
        println("Took $timeTaken ms")
    }
}
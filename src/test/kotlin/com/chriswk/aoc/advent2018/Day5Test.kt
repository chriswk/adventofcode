package com.chriswk.aoc.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class Day5Test {

    @Test
    fun exampleTest() {
        assertThat(Day5.react("dabAcCaCBAcCcaDA").length).isEqualTo(10)
    }

    @Test
    fun solution1() {
        val timeTaken = measureTimeMillis {
            assertThat(Day5.solution1()).isEqualTo(10638)
        }
        println("Part 1 took ${timeTaken}ms")
    }

    @Test
    fun solution2() {
        val timeTaken = measureTimeMillis {
            assertThat(Day5.solution2()).isEqualTo(4944)
        }
        println("Part 2 took ${timeTaken} ms")
    }
}
package com.chriswk.aoc.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class Day4Test {

    @Test
    fun solution1() {
        assertThat(Day4.solution1()).isEqualTo(4716)
        val x = measureTimeMillis {
            Day4.solution1()
        }
        println("Time taken $x ms")
    }

    @Test
    fun solution2() {
        val x = measureTimeMillis {
            assertThat(Day4.solution2()).isEqualTo(117061)
        }
        println("Time taken $x ms")
    }
}
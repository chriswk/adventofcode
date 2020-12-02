package com.chriswk.aoc.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class Day14Test {

    @Test
    fun example_part1() {
        assertThat(Day14.partOne(9)).isEqualTo("5158916779")
        assertThat(Day14.partOne(5)).isEqualTo("0124515891")
        assertThat(Day14.partOne(18)).isEqualTo("9251071085")
        assertThat(Day14.partOne(2018)).isEqualTo("5941429882")

    }

    @Test
    fun part1() {
        val timeTaken = measureTimeMillis {
            assertThat(Day14.partOne(760221)).isEqualTo("1411383621")
        }
        println("Part 1 Took $timeTaken ms")
    }

    @Test
    fun part2() {
        val timeTaken = measureTimeMillis {
            assertThat(Day14.partTwo("760221")).isEqualTo(20177474)
        }
        println("Part 2 Took $timeTaken ms")

    }
}
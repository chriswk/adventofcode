package com.chriswk.aoc.advent2018

import com.chriswk.aoc.util.dayInputAsLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

@Disabled
class Day19Test {
    val sample = """
#ip 0
seti 5 0 1
seti 6 0 2
addi 0 1 0
addr 1 2 3
setr 1 0 0
seti 8 0 4
seti 9 0 5
    """.trimIndent()
    @Test
    fun part1_sample() {
        assertThat(Day19.partOne(sample.lines())).isEqualTo(6)
    }

    @Test
    fun partOne() {
        val timeTaken = measureTimeMillis {
            assertThat(Day19.partOne(dayInputAsLines(2018, 19))).isEqualTo(1256)
        }
        println("Part 1 took $timeTaken ms")
    }

    @Test
    fun partTwo() {
        val timeTaken = measureTimeMillis {
            assertThat(Day19.partTwo(dayInputAsLines(2018, 19))).isEqualTo(1256)
        }
        println("Part 2 took $timeTaken ms")
    }
}
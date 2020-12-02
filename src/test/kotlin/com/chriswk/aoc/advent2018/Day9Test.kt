package com.chriswk.aoc.advent2018

import com.chriswk.aoc.util.dayInputAsLines
import com.chriswk.aoc.util.dayInputAsString
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.system.measureTimeMillis

class Day9Test {

    @ParameterizedTest
    @MethodSource("gameProvider")
    fun example_part1(input: String, expected: Long) {
        val timeTaken = measureTimeMillis {
            Assertions.assertThat(Day9.part1(input)).isEqualTo(expected)
        }
        println("Part 1 examples took $timeTaken ms")

    }

    @Test
    fun part1() {
        val timeTaken = measureTimeMillis {
            Assertions.assertThat(Day9.part1(dayInputAsString(2018, 9))).isEqualTo(437654L)
        }
        println("Part 1 took $timeTaken ms")

    }

    @Test
    fun part2() {
        val timeTaken = measureTimeMillis {
            Assertions.assertThat(Day9.part2(dayInputAsString(2018, 9))).isEqualTo(3689913905L)
        }
        println("Part 2 took $timeTaken ms")

    }

    companion object {
        @JvmStatic
        fun gameProvider(): Stream<Arguments> {
            return Stream.of(
                    arguments("9 players; last marble is worth 25", 32),
                    arguments("10 players; last marble is worth 1618", 8317),
                    arguments("13 players; last marble is worth 7999", 146373),
                    arguments("17 players; last marble is worth 1104", 2764),
                    arguments("21 players; last marble is worth 6111", 54718),
                    arguments("30 players; last marble is worth 5807", 37305)
            )
        }
    }
}
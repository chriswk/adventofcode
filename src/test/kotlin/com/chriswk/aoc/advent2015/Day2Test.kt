package com.chriswk.aoc.advent2015

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day2Test {

    @ParameterizedTest
    @MethodSource("gifts")
    fun wrappingPaperNeeded(spec: String, expected: Int) {
        assertThat(Day2.Gift.fromSpec(spec).wrappingPaper).isEqualTo(expected)
    }

    @Test
    fun part1() {
        assertThat(Day2().part1()).isEqualTo(1598415)
    }

    @ParameterizedTest
    @MethodSource("ribbons")
    fun ribbonNeeded(spec: String, expected: Int) {
        assertThat(Day2.Gift.fromSpec(spec).ribbon()).isEqualTo(expected)
    }

    @Test
    fun part2() {
        assertThat(Day2().part2()).isEqualTo(3812909)
    }
    companion object {
        @JvmStatic
        fun gifts(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("2x3x4", 58),
                Arguments.of("1x1x10", 43)
            )
        }
        @JvmStatic
        fun ribbons(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("2x3x4", 34),
                Arguments.of("1x1x10", 14)
            )

        }
    }


}
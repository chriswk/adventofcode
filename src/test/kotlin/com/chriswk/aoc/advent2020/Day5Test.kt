package com.chriswk.aoc.advent2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day5Test {

    @Test
    fun finds_row() {
        val testRow = "FBFBBFF"
        val day = Day5()
        assertThat(day.findRow(testRow)).isEqualTo(44)
    }

    @ParameterizedTest
    @MethodSource("variousRows")
    fun findsCorrectRow(partition: String, expectedAnswer: Int) {
        val day = Day5()
        assertThat(day.findRow(partition)).isEqualTo(expectedAnswer)
    }

    @ParameterizedTest
    @MethodSource("variousColumns")
    fun findsCorrectColumn(partition: String, expectedAnswer: Int) {
        val day = Day5()
        assertThat(day.findColumn(partition)).isEqualTo(expectedAnswer)
    }

    @Test
    fun part1() {
        assertThat(Day5().part1()).isEqualTo(874)
    }

    @Test
    fun part2() {
        assertThat(Day5().part2()).isEqualTo(594)
    }

    companion object {
        @JvmStatic
        fun variousRows(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("BFFFBBF", 70),
                Arguments.of("FFFBBBF", 14),
                Arguments.of("BBFFBBF", 102)
            )
        }
        @JvmStatic
        fun variousColumns(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("RRR", 7),
                Arguments.of("RRL", 6),
                Arguments.of("RLL", 4)
            )
        }
    }
}
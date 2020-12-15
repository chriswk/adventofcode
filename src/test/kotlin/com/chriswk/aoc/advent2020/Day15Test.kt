package com.chriswk.aoc.advent2020

import com.chriswk.aoc.advent.Slow
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day15Test {

    @ParameterizedTest
    @MethodSource("testSequencePart1")
    fun generatesCorrectSequence(nth: Int, expected: Int) {
        val day = Day15()
        assertThat(day.findNthNumber("0,3,6", nth)).isEqualTo(expected)
    }

    @Test
    fun expect3As5thNumber() {
        val day = Day15()
        assertThat(day.findNthNumber("0,3,6", 5)).isEqualTo(3)
    }

    @ParameterizedTest
    @MethodSource("startingNumbers2020thNumber")
    fun expect2020thNumber(startingNumbers: String, expected: Int) {
        val day = Day15()
        assertThat(day.findNthNumber(startingNumbers, 2020)).isEqualTo(expected)
    }

    @Test
    fun part1() {
        assertThat(Day15().part1()).isEqualTo(852)
    }

    @Slow(6000)
    @Test
    fun part2() {
        assertThat(Day15().part2()).isEqualTo(6007666)
    }

    companion object {
        @JvmStatic
        fun testSequencePart1(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(4, 0),
                Arguments.of(5, 3),
                Arguments.of(6, 3),
                Arguments.of(7, 1),
                Arguments.of(8, 0),
                Arguments.of(9, 4),
                Arguments.of(10, 0)
            )
        }
        @JvmStatic
        fun startingNumbers2020thNumber(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("1,3,2", 1),
                Arguments.of("2,1,3", 10),
                Arguments.of("1,2,3", 27),
                Arguments.of("2,3,1", 78),
                Arguments.of("3,2,1", 438),
                Arguments.of("3,1,2", 1836),
            )
        }
    }
}
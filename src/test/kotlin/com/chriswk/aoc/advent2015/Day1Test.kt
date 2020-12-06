package com.chriswk.aoc.advent2015

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day1Test {

    @ParameterizedTest
    @MethodSource("exampleInstructions")
    fun testInputPart1(instructions: String, endFloor: Int) {
        val day = Day1()
        assertThat(day.endFloor(instructions)).isEqualTo(endFloor)
    }
    @Test
    fun part1() {
        assertThat(Day1().part1()).isEqualTo(280)
    }
    @Test
    fun part2() {
        assertThat(Day1().part2()).isEqualTo(1797)
    }

    @ParameterizedTest
    @MethodSource("examplesPart2")
    fun testInputPart2(instructions: String, expectedIdx: Int) {
        val day = Day1()
        assertThat(day.firstEntry(instructions, -1)).isEqualTo(expectedIdx)
    }

    companion object {
        @JvmStatic
        fun exampleInstructions(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("(())", 0),
                Arguments.of("()()", 0),
                Arguments.of("(((", 3),
                Arguments.of("(()(()(", 3),
                Arguments.of("))(((((", 3),
                Arguments.of("())", -1),
                Arguments.of("))(", -1),
                Arguments.of(")))", -3),
                Arguments.of(")())())", -3),
            )
        }
        @JvmStatic
        fun examplesPart2(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(")", 1),
                Arguments.of("()())", 5)
            )
        }
    }

}
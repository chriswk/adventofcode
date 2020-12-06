package com.chriswk.aoc.advent2015

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day3Test {

    @ParameterizedTest
    @MethodSource("moveInstructionsPart1")
    fun testInputPart1(input: String, expectedHouses: Int) {
        val day = Day3()
        assertThat(day.performMovesPart1(input).size).isEqualTo(expectedHouses)
    }

    @Test
    fun part1() {
        assertThat(Day3().part1()).isEqualTo(2081)
    }

    @ParameterizedTest
    @MethodSource("moveInstructionsPart2")
    fun testInputPart2(input: String, expectedHouses: Int) {
        val day = Day3()
        assertThat(day.performMovesPart2(input).size).isEqualTo(expectedHouses)
    }

    @Test
    fun part2() {
        assertThat(Day3().part2()).isEqualTo(2341)
    }


    companion object {
        @JvmStatic
        fun moveInstructionsPart1(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(">", 2),
                Arguments.of("><", 2),
                Arguments.of("^>v<", 4),
                Arguments.of("^v^v^v^v^", 2)
            )
        }

        @JvmStatic
        fun moveInstructionsPart2(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(">", 2),
                Arguments.of("^v", 3),
                Arguments.of("^>v<", 3),
                Arguments.of("^v^v^v^v^v", 11)
            )
        }
    }
}
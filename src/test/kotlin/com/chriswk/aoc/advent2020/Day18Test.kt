package com.chriswk.aoc.advent2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day18Test {

    @ParameterizedTest
    @MethodSource("simpleMath")
    fun canPerformMathWithBrackets(problem: String, answer: Long) {
        assertThat(Day18().eval(problem, false)).isEqualTo(answer)
    }

    fun part1() {
        assertThat(Day18().part1()).isEqualTo(29839238838303)
    }

    @ParameterizedTest
    @MethodSource("advancedMath")
    fun canPerformAdvancedMathWithBrackets(problem: String, answer: Long) {
        assertThat(Day18().eval(problem, true)).isEqualTo(answer)
    }

    fun part2() {
        assertThat(Day18().part2()).isEqualTo(201376568795521)
    }
    companion object {
        @JvmStatic
        fun simpleMath(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("2 * 3 + (4 * 5)", 26L),
                Arguments.of("5 + (8 * 3 + 9 + 3 * 4 * 3)", 437L),
                Arguments.of("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))", 12240L),
                Arguments.of("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2", 13632L),
            )
        }
        @JvmStatic
        fun advancedMath(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("2 * 3 + (4 * 5)", 46L),
                Arguments.of("5 + (8 * 3 + 9 + 3 * 4 * 3)", 1445L),
                Arguments.of("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))", 669060L),
                Arguments.of("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2", 23340L),
            )

        }
    }
}
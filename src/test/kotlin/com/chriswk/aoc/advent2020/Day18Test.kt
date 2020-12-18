package com.chriswk.aoc.advent2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day18Test {

    @Test
    fun canPerformMathOnNoBrackets() {
        val day = Day18()
        assertThat(day.processHomeworkLine("1 + 2 * 3 + 4 * 5 + 6")).isEqualTo(71)
    }


    @ParameterizedTest
    @MethodSource("simpleMath")
    fun canPerformMathWithBrackets(problem: String, answer: Long) {
        assertThat(Day18().doMath(problem)).isEqualTo(answer)
    }

    @Test
    fun bracketsTesting() {
        val problem = "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"
        val day = Day18()
        assertThat(day.doMath(problem)).isEqualTo(13632L)
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
    }
}
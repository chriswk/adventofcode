package com.chriswk.aoc.advent2015

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day5Test {
    @ParameterizedTest
    @MethodSource("exampleStringsPart1")
    fun findsNiceStrings(input: String, isNice: Boolean) {
        val day = Day5()
        assertThat(day.isNicePart1(input)).isEqualTo(isNice)
    }

    @Test
    fun part1() {
        assertThat(Day5().part1()).isEqualTo(238)
    }

    @ParameterizedTest
    @MethodSource("exampleStringsPart2")
    fun findsNiceStringsPart2(input: String, isNice: Boolean) {
        val day = Day5()
        assertThat(day.isNicePart2(input)).isEqualTo(isNice)
    }

    @Test
    fun part2() {
        assertThat(Day5().part2()).isEqualTo(69)
    }

    companion object {

        @JvmStatic
        fun exampleStringsPart1(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("ugknbfddgicrmopn", true),
                Arguments.of("aaa", true),
                Arguments.of("jchzalrnumimnmhp", false),
                Arguments.of("haegwjzuvuyypxyu", false),
                Arguments.of("dvszwmarrgswjxmb", false)
            )
        }
        @JvmStatic
        fun exampleStringsPart2(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("qjhvhtzxzqqjkmpb", true),
                Arguments.of("xxyxx", true),
                Arguments.of("uurcxstgmygtbstg", false),
                Arguments.of("ieodomkazucvgmuy", false),
            )
        }
    }
}
package com.chriswk.aoc.advent2015

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day8Test {
    @ParameterizedTest
    @MethodSource("exampleStrings")
    fun handlesDefaultCases(input: String, expectedLength: Int, expectedData: Int) {
        val day = Day8()
        val (l, d) = day.part1String(input)
        assertThat(l).isEqualTo(expectedLength)
        assertThat(d).isEqualTo(expectedData)
    }

    @Test
    fun part1WithTestInput() {
        val day = Day8()
        val input = """
            ""
            "abc"
            "aaa\"aaa"
            "\x27"
        """.trimIndent().lines()
        val i = day.parseMultiple(input)
        val answer = i.sumBy { it.first } - i.sumBy { it.second }
        assertThat(answer).isEqualTo(12)
    }

    @Test
    fun part1() {
        assertThat(Day8().part1()).isEqualTo(1350)
    }

    @ParameterizedTest
    @MethodSource("exampleStringsPart2")
    fun part2Tests(inputString: String, decodedString: String) {
        val day = Day8()
        val (_, decoded) = day.part2String(inputString)
        assertThat(decoded).isEqualTo(decodedString)
    }

    companion object {
        @JvmStatic
        fun exampleStrings(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("""""""", 2, 0),
                Arguments.of(""""abc"""", 5, 3),
                Arguments.of(""""aaa\"aaa"""", 10, 7),
                Arguments.of(""""\x27"""", 6, 1),
                Arguments.of(""""\\"""", 4, 1),
                Arguments.of(""""\""""", 4, 1),
                Arguments.of(""""saduyrntuswlnlkuppdro\\sicxosted"""", 34, 31),
                Arguments.of(""""q\xb7oh\"p\xce\"n"""", 19, 9)
            )
        }
        @JvmStatic
        fun exampleStringsPart2(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(""""\x27"""", """"\"\\x27\"""""),
                Arguments.of(""""abc"""",  """"\"abc\"""""),
                Arguments.of(""""aaa\"aaa"""", """"\"aaa\\\"aaa\"""""),
                Arguments.of("""""""", """"\"\"""""),
                Arguments.of("""\\""", """"\\\"""")
            )
        }
    }
}
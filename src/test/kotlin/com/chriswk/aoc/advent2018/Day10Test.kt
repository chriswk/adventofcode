package com.chriswk.aoc.advent2018

import com.chriswk.aoc.util.dayInputAsLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day10Test {
    val input = """
            position=< 9,  1> velocity=< 0,  2>
            position=< 7,  0> velocity=<-1,  0>
            position=< 3, -2> velocity=<-1,  1>
            position=< 6, 10> velocity=<-2, -1>
            position=< 2, -4> velocity=< 2,  2>
            position=<-6, 10> velocity=< 2, -2>
            position=< 1,  8> velocity=< 1, -1>
            position=< 1,  7> velocity=< 1,  0>
            position=<-3, 11> velocity=< 1, -2>
            position=< 7,  6> velocity=<-1, -1>
            position=<-2,  3> velocity=< 1,  0>
            position=<-4,  3> velocity=< 2,  0>
            position=<10, -3> velocity=<-1,  1>
            position=< 5, 11> velocity=< 1, -2>
            position=< 4,  7> velocity=< 0, -1>
            position=< 8, -2> velocity=< 0,  1>
            position=<15,  0> velocity=<-2,  0>
            position=< 1,  6> velocity=< 1,  0>
            position=< 8,  9> velocity=< 0, -1>
            position=< 3,  3> velocity=<-1,  1>
            position=< 0,  5> velocity=< 0, -1>
            position=<-2,  2> velocity=< 2,  0>
            position=< 5, -2> velocity=< 1,  2>
            position=< 1,  4> velocity=< 2,  1>
            position=<-2,  7> velocity=< 2, -2>
            position=< 3,  6> velocity=<-1, -1>
            position=< 5,  0> velocity=< 1,  0>
            position=<-6,  0> velocity=< 2,  0>
            position=< 5,  9> velocity=< 1, -2>
            position=<14,  7> velocity=<-2,  0>
            position=<-3,  6> velocity=< 2, -1>
        """.trimIndent().lines()
    @ParameterizedTest
    @MethodSource("examplePoints")
    fun parsingToPoint(pointString: String, expectedPoint: Day10.Point) {
        assertThat(Day10.parseToPoint(pointString)).isEqualTo(expectedPoint)
    }

    @Test
    fun canOutput() {
        val points = input.map { Day10.parseToPoint(it) }
        Day10.printPoints(points)
    }

    @Test
    fun part1_example() {
        Day10.part1(input)
    }

    @Test
    fun part1() {
        Day10.part1(dayInputAsLines(2018, 10))
    }

    companion object {
        @JvmStatic
        fun examplePoints(): Stream<Arguments> {
            return Stream.of(
                    arguments("position=< 9,  1> velocity=< 0,  2>", Day10.Point(9, 1, 0, 2)),
                    arguments("position=< 7,  0> velocity=<-1,  0>", Day10.Point(7, 0, -1, 0)),
                    arguments("position=< 3, -2> velocity=<-1,  1>", Day10.Point(3, -2, -1, 1)),
                    arguments("position=< 6, 10> velocity=<-2, -1>", Day10.Point(6, 10, -2, -1)),
                    arguments("position=< 2, -4> velocity=< 2,  2>", Day10.Point(2, -4, 2, 2)),
                    arguments("position=<-6, 10> velocity=< 2, -2>", Day10.Point(-6, 10, 2, -2)),
                    arguments("position=< 1,  8> velocity=< 1, -1>", Day10.Point(1, 8, 1, -1)),
                    arguments("position=< 1,  7> velocity=< 1,  0>", Day10.Point(1, 7, 1, 0)),
                    arguments("position=<-3, 11> velocity=< 1, -2>", Day10.Point(-3, 11, 1, -2)),
                    arguments("position=< 7,  6> velocity=<-1, -1>", Day10.Point(7, 6, -1, -1)),
                    arguments("position=<-2,  3> velocity=< 1,  0>", Day10.Point(-2, 3, 1, 0)),
                    arguments("position=<-4,  3> velocity=< 2,  0>", Day10.Point(-4, 3, 2, 0)),
                    arguments("position=<10, -3> velocity=<-1,  1>", Day10.Point(10, -3, -1, 1)),
                    arguments("position=< 5, 11> velocity=< 1, -2>", Day10.Point(5, 11, 1, -2)),
                    arguments("position=< 4,  7> velocity=< 0, -1>", Day10.Point(4, 7, 0, -1)),
                    arguments("position=< 8, -2> velocity=< 0,  1>", Day10.Point(8, -2, 0, 1)),
                    arguments("position=<15,  0> velocity=<-2,  0>", Day10.Point(15, 0, -2, 0)),
                    arguments("position=< 1,  6> velocity=< 1,  0>", Day10.Point(1, 6, 1, 0)),
                    arguments("position=< 8,  9> velocity=< 0, -1>", Day10.Point(8, 9, 0, -1)),
                    arguments("position=< 3,  3> velocity=<-1,  1>", Day10.Point(3, 3, -1, 1)),
                    arguments("position=< 0,  5> velocity=< 0, -1>", Day10.Point(0, 5, 0, -1)),
                    arguments("position=<-2,  2> velocity=< 2,  0>", Day10.Point(-2, 2, 2, 0)),
                    arguments("position=< 5, -2> velocity=< 1,  2>", Day10.Point(5, -2, 1, 2)),
                    arguments("position=< 1,  4> velocity=< 2,  1>", Day10.Point(1, 4, 2, 1)),
                    arguments("position=<-2,  7> velocity=< 2, -2>", Day10.Point(-2, 7, 2, -2)),
                    arguments("position=< 3,  6> velocity=<-1, -1>", Day10.Point(3, 6, -1, -1)),
                    arguments("position=< 5,  0> velocity=< 1,  0>", Day10.Point(5, 0, 1, 0)),
                    arguments("position=<-6,  0> velocity=< 2,  0>", Day10.Point(-6, 0, 2, 0)),
                    arguments("position=< 5,  9> velocity=< 1, -2>", Day10.Point(5, 9, 1, -2)),
                    arguments("position=<14,  7> velocity=<-2,  0>", Day10.Point(14, 7, -2, 0)),
                    arguments("position=<-3,  6> velocity=< 2, -1>", Day10.Point(-3, 6, 2, -1))
            )
        }
    }
}
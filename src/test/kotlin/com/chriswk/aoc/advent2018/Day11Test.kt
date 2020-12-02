package com.chriswk.aoc.advent2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.system.measureTimeMillis

@Disabled
class Day11Test {

    @ParameterizedTest
    @MethodSource("cellsAndRacks")
    fun powerLevels(x: Int, y: Int, gridSerial: Int, expected: Int) {
        assertThat(Day11.powerLevel(Cell(x, y), gridSerial)).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("totalPower")
    fun largestTotal(serialNumber: Int, coords: Cell) {
        assertThat(Day11.part1(300, serialNumber)).isEqualTo(coords)
        assertThat(Day11.part1(300, serialNumber)).isEqualTo(coords)
    }

    @Test
    fun part1() {
        val timeTaken = measureTimeMillis {
            assertThat(Day11.part1(300, 3214)).isEqualTo(Cell(21, 42))
        }
        println("Part 1 took $timeTaken ms")
    }

    @Test
    fun part2() {
        val timeTaken = measureTimeMillis {
            assertThat(Day11.part2(300, 3214)).isEqualTo(Cell(21, 42) to 2)
        }
        println("Part 2 took $timeTaken ms")

    }

    companion object {
        @JvmStatic
        fun cellsAndRacks(): Stream<Arguments> {
            return Stream.of(
                    arguments(3, 5, 8, 4),
                    arguments(122, 79, 57, -5),
                    arguments(217,196, 39, 0),
                    arguments(101, 153, 71, 4)
            )
        }

        @JvmStatic
        fun totalPower(): Stream<Arguments> {
            return Stream.of(
                    arguments(18, Cell(33, 45)),
                    arguments(42, Cell(21, 61))
            )
        }
    }
}
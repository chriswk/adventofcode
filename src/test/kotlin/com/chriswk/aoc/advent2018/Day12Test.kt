package com.chriswk.aoc.advent2018

import com.chriswk.aoc.util.dayInputAsLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class Day12Test {
    val exampleInput = """initial state: #..#.#..##......###...###

...## => #
..#.. => #
.#... => #
.#.#. => #
.#.## => #
.##.. => #
.#### => #
#.#.# => #
#.### => #
##.#. => #
##.## => #
###.. => #
###.# => #
####. => #"""

    @Test
    fun example_part1() {
        assertThat(Day12.part1(exampleInput.lines())).isEqualTo(325)
    }

    @Test
    fun part1() {
        val timeTaken = measureTimeMillis {
            assertThat(Day12.part1(dayInputAsLines(2018, 12))).isEqualTo(3120)
        }
        println("Part 1 took $timeTaken ms")
    }

    @Test
    fun part2() {
        val timeTaken = measureTimeMillis {
            assertThat(Day12.part2(dayInputAsLines(2018, 12))).isEqualTo(2950000001598L)
        }
        println("Part 2 took $timeTaken ms")
    }
}
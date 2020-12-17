package com.chriswk.aoc.advent2015

import com.chriswk.aoc.advent.DisableSlow
import com.chriswk.aoc.advent.Slow
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@DisableSlow
class Day10Test {

    @Test
    fun smallInputPart1() {
        val day = Day10()
        val testInput = generateSequence("1") { prev -> day.next(prev) }.drop(1).take(5).toList()
        assertThat(testInput).containsExactly(
            "11","21","1211","111221","312211"
        )
    }

    @Test
    fun part1() {
        assertThat(Day10().part1()).isEqualTo(329356)
    }

    @Test
    fun part2() {
        assertThat(Day10().part2()).isEqualTo(4666278)
    }
}
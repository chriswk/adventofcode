package com.chriswk.aoc.advent2020

import com.chriswk.aoc.advent.Slow
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day23Test {
    @Test
    fun `can parse input`() {
        val day = Day23()
        val game = day.parse("389125467")
        assertThat(game).hasSize(9)
    }

    @Test
    fun `can take a step`() {
        val day = Day23()
        val node = day.trip(input = "389125467", steps = 1)
        assertThat(node.toCircularString()).isEqualTo("54673289")
    }

    @Test
    fun `expected output after 10 steps`() {
        val day = Day23()
        val node = day.trip("389125467", 10)
        assertThat(node.toCircularString()).isEqualTo("92658374")
    }

    @Test
    fun part1() {
        assertThat(Day23().part1()).isEqualTo("25468379")
    }

    @Test
    @Slow(3000)
    fun part2Example() {
        val day = Day23()
        val node = day.tripPart2("389125467")
        assertThat(node.next.value).isEqualTo(934001)
        assertThat(node.next.next.value).isEqualTo(159792)
    }

    @Test
    @Slow(3000)
    fun part2() {
        assertThat(Day23().part2()).isEqualTo(474747880250)
    }
}
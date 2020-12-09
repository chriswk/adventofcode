package com.chriswk.aoc.advent2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day9Test {

    @Test
    fun handlesTestInput() {
        val preamble = (1L..25L).toList()
        val day = Day9()
        val validSeq = (preamble + 49L).windowed(25)

        assertThat(day.findFirstNumberWhichIsNotASum(25, validSeq.asSequence())).isNull()
        val firstInvalid = (preamble + 100L).windowed(25).asSequence()
        assertThat(day.findFirstNumberWhichIsNotASum(25, firstInvalid)).isEqualTo(100)
    }

    @Test
    fun part1() {
        assertThat(Day9().part1()).isEqualTo(2089807806)
    }

    @Test
    fun handlesTestInputPart2() {
        val input = """
            35
            20
            15
            25
            47
            40
            62
            55
            65
            95
            102
            117
            150
            182
            127
            219
            299
            277
            309
            576
        """.trimIndent().lines()
        val day = Day9()
        val numbers = input.map { it.toLong() }
        val i = numbers.asSequence().windowed(6)
        val weakness = day.findFirstNumberWhichIsNotASum(5, i)!!
        assertThat(day.findContinguousSumForWeakness(weakness, numbers)).isEqualTo(listOf(15L,25L,47L,40L))
    }

    @Test
    fun part2() {
        assertThat(Day9().part2()).isEqualTo(245848639L)
    }

}
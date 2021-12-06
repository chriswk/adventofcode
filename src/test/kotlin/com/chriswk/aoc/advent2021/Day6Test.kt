package com.chriswk.aoc.advent2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day6Test {
    val testInput = "3,4,3,1,2"
    val day = Day6()
    @Test
    fun `Can make tally from input`() {
        val tally = day.initalSetup(testInput)
        assertThat(tally).hasEntrySatisfying(3) { i -> assertThat(i).isEqualTo(2) }
    }

    @Test
    fun `Check state after one day`() {
        val tally = day.tallyAfterDays(testInput, 1)
        assertThat(tally).containsEntry(0, 1)
        assertThat(tally).containsEntry(1, 1)
        assertThat(tally).containsEntry(2, 2)
        assertThat(tally).containsEntry(3, 1)
        assertThat(tally).containsEntry(4, 0)
        assertThat(tally).containsEntry(5, 0)
        assertThat(tally).containsEntry(6, 0)
        assertThat(tally).containsEntry(7, 0)
        assertThat(tally).containsEntry(8, 0)
    }

    @Test
    fun `Check state after three days`() {
        val tally = day.tallyAfterDays(testInput, 3)
        assertThat(tally).containsEntry(0, 2)
        assertThat(tally).containsEntry(1, 1)
        assertThat(tally).containsEntry(2, 0)
        assertThat(tally).containsEntry(3, 0)
        assertThat(tally).containsEntry(4, 0)
        assertThat(tally).containsEntry(5, 1)
        assertThat(tally).containsEntry(6, 1)
        assertThat(tally).containsEntry(7, 1)
        assertThat(tally).containsEntry(8, 1)
    }

    @Test
    fun `Check state after ten days`() {
        val tally = day.tallyAfterDays(testInput, 4)
        assertThat(tally).containsEntry(0, 1)
        assertThat(tally).containsEntry(4, 1)
        assertThat(tally).containsEntry(5, 1)
        assertThat(tally).containsEntry(6, 3)
        assertThat(tally).containsEntry(7, 1)
        assertThat(tally).containsEntry(8, 2)
    }

    @Test
    fun `Can let time go by`() {
        assertThat(day.tallyAfterDays(testInput, 18).values.sum()).isEqualTo(26)
        val tallyAfterEighty = day.tallyAfterDays(testInput, 80)
        assertThat(tallyAfterEighty.values.sum()).isEqualTo(5934)
    }

    @Test
    fun `Handles lots of time`() {
        val tallyAfterTwoFiftySizDays = day.tallyAfterDays(testInput, 256)
        assertThat(tallyAfterTwoFiftySizDays.values.sum()).isEqualTo(26984457539)
    }

    @Test
    fun `Part 1`() {
        assertThat(day.part1()).isEqualTo(385391)
    }

    @Test
    fun `Part 2`() {
        assertThat(day.part2()).isEqualTo(1728611055389)
    }
}

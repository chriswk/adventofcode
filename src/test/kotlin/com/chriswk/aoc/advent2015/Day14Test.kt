package com.chriswk.aoc.advent2015

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day14Test {
    val day = Day14()
    @Test
    fun `Can read input format for single reindeer`() {
        val input = """Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds."""
        val reindeer = Day14.Reindeer(input)
        assertThat(reindeer.name).isEqualTo("Comet")
        assertThat(reindeer.topSpeed).isEqualTo(14)
        assertThat(reindeer.stamina).isEqualTo(10)
        assertThat(reindeer.restTime).isEqualTo(127)
    }

    @Test
    fun `Can calculate distance travelled`() {
        val input = """Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds."""
        val comet = Day14.Reindeer(input)
        val dancerInput ="""Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds."""
        val dancer = Day14.Reindeer(dancerInput)
        assertThat(comet.lapDistance).isEqualTo(140)
        assertThat(comet.interval).isEqualTo(137)
        assertThat(comet.fullLaps(1000)).isEqualTo(7)
        assertThat(comet.extraSeconds(1000)).isEqualTo(10)
        assertThat(comet.distanceTravelled(1000)).isEqualTo(1120)
        assertThat(dancer.lapDistance).isEqualTo(176)
        assertThat(dancer.distanceTravelled(1000)).isEqualTo(1056)
    }

    @Test
    fun `Calculate by point for leader per second`() {
        val testInput = """
            Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
            Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
        """.trimIndent().lines().map { Day14.Reindeer(it) }
        val day = Day14()
        val leaderboard = day.leaderBoardAfterSeconds(testInput, 140)
        assertThat(leaderboard.values).containsExactly(139, 1)
        assertThat(day.scoreByLeaderAtSecond(testInput, 1000)).isEqualTo(688)
    }

    @Test
    fun `Part 1`() {
        assertThat(day.part1()).isEqualTo(2660)
    }

    @Test
    fun `Part 2`() {
        assertThat(day.part2()).isEqualTo(1256)
    }
    
}

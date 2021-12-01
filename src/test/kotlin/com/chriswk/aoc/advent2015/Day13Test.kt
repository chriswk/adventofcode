package com.chriswk.aoc.advent2015

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day13Test {
    val testInput = """
        Alice would gain 54 happiness units by sitting next to Bob.
        Alice would lose 79 happiness units by sitting next to Carol.
        Alice would lose 2 happiness units by sitting next to David.
        Bob would gain 83 happiness units by sitting next to Alice.
        Bob would lose 7 happiness units by sitting next to Carol.
        Bob would lose 63 happiness units by sitting next to David.
        Carol would lose 62 happiness units by sitting next to Alice.
        Carol would gain 60 happiness units by sitting next to Bob.
        Carol would gain 55 happiness units by sitting next to David.
        David would gain 46 happiness units by sitting next to Alice.
        David would lose 7 happiness units by sitting next to Bob.
        David would gain 41 happiness units by sitting next to Carol.
    """.trimIndent()
    @Test
    fun `Can read input format with gain`() {
        val match = Day13.Match.fromLine("Alice would gain 54 happiness units by sitting next to Bob.")
        assertThat(match.person1).isEqualTo("Alice")
        assertThat(match.person2).isEqualTo("Bob")
        assertThat(match.happinessDelta).isEqualTo(54)
    }

    @Test
    fun `Can read input format with loss`() {
        val match = Day13.Match.fromLine("Alice would lose 79 happiness units by sitting next to Carol.")
        assertThat(match.person1).isEqualTo("Alice")
        assertThat(match.person2).isEqualTo("Carol")
        assertThat(match.happinessDelta).isEqualTo(-79)
    }

    @Test
    fun `Finds correct max happiness from testInput`() {
        val day = Day13()
        val happiness = day.maxHappinessDelta(testInput.lines().map { Day13.Match.fromLine(it) })
        assertThat(happiness).isEqualTo(330)
    }

    @Test
    fun `Part1`() {
        assertThat(Day13().part1()).isEqualTo(618)
    }

    @Test
    fun `Part 2`() {
        assertThat(Day13().part2()).isEqualTo(601)
    }
}

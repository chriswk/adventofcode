package com.chriswk.aoc.advent2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day21Test {

    val testInput = """
        Player 1 starting position: 4
        Player 2 starting position: 8
    """.trimIndent().lines()

    val day = Day21()
    @Test
    fun `Can play example game`() {
        val game = day.parseInput(testInput)
        val winner = day.playTurn(game).first { it.p1.score >= 1000 || it.p2.score >= 1000 }
        assertThat(winner.p1.score).isEqualTo(1000)
        assertThat(winner.p2.score).isEqualTo(745)
        assertThat(winner.rolls).isEqualTo(993)
    }
}

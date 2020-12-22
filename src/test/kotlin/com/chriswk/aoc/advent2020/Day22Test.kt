package com.chriswk.aoc.advent2020

import com.chriswk.aoc.advent.Slow
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day22Test {
    val smallGame = """
        Player 1:
        9
        2
        6
        3
        1

        Player 2:
        5
        8
        4
        7
        10
    """.trimIndent()

    @Test
    fun `can parse input`() {
        val day = Day22()
        val (p1, p2) = day.buildDecks(smallGame)
        assertThat(p1).containsExactly(9,2,6,3,1)
        assertThat(p2).containsExactly(5,8,4,7,10)
    }

    @Test
    fun `can play single round`(){
        val day = Day22()
        assertThat(day.decideRoundWinner(9,5)).isEqualTo(RoundResult.Player1Win)
        assertThat(day.decideRoundWinner(5,6)).isEqualTo(RoundResult.Player2Win)
    }

    @Test
    fun `keeps track of game state`() {
        val day = Day22()
        val game = day.buildDecks(smallGame)
        val (p1, p2) = day.playRound(game)
        assertThat(p1).containsExactly(2,6,3,1,9,5)
        assertThat(p2).containsExactly(8,4,7,10)
    }

    @Test
    fun `plays small game to end`() {
        val day = Day22()
        val result = day.playGamePart1(day.buildDecks(smallGame))
        assertThat(day.scoreCards(result)).isEqualTo(306)
    }

    @Test
    fun part1() {
        assertThat(Day22().part1()).isEqualTo(33694)
    }

    @Test
    fun `can play recursive combat`() {
        val day = Day22()
        val (winningDeck, result) = day.playGamePart2(day.buildDecks(smallGame))
        assertThat(result).isEqualTo(GameState.Player2Win)
        assertThat(day.scoreCards(winningDeck)).isEqualTo(291)
    }
    @Test
    @Slow(1000)
    fun part2() {
        assertThat(Day22().part2()).isEqualTo(31835)
    }
}
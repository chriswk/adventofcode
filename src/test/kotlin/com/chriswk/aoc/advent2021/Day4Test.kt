package com.chriswk.aoc.advent2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day4Test {

    val testInput = """
        7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19

 3 15  0  2 22
 9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6

14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
 2  0 12  3  7
    """.trimIndent().lines()
    val day = Day4()

    @Test
    fun `can parse a board`() {
        val (_, bingos) = day.parseInput(testInput)
        val board = bingos.first()
        assertThat(board.rows).hasSize(5)
        assertThat(board.columns).hasSize(5)
        assertThat(board.rows).containsExactly(
            listOf(22, 13, 17, 11, 0),
            listOf(8, 2, 23, 4, 24),
            listOf(21, 9, 14, 16, 7),
            listOf(6, 10, 3, 18, 5),
            listOf(1, 12, 20, 15, 19)
        )
        assertThat(board.columns).containsExactly(
            listOf(22, 8, 21, 6, 1),
            listOf(13, 2, 9, 10, 12),
            listOf(17, 23, 14, 3, 20),
            listOf(11, 4, 16, 18, 15),
            listOf(0, 24, 7, 5, 19)
        )
    }

    @Test
    fun `can play a round`() {
        val (draws, boards) = day.parseInput(testInput)
        val (drawn, winningBoard) = day.playGame(draws, boards)
        assertThat(winningBoard.rows.first()).containsExactly(14, 21, 17, 24, 4)
        assertThat(drawn.last()).isEqualTo(24)
        assertThat(winningBoard.score(drawn)).isEqualTo(4512)
    }

    @Test
    fun `Part 1`() {
        assertThat(day.part1()).isEqualTo(82440)
    }

    @Test
    fun `can find the last board to win`() {
        val (draws, boards) = day.parseInput(testInput)
        val (drawn, winningBoard) = day.playGameLast(draws, boards)
        assertThat(winningBoard.rows.first()).containsExactly(3, 15, 0, 2, 22)
        assertThat(winningBoard.score(drawn)).isEqualTo(1924)
    }

    @Test
    fun `Part 2`() {
        assertThat(day.part2()).isEqualTo(20774)
    }
}

package com.chriswk.aoc.advent2021

import com.chriswk.aoc.util.Pos
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day20Test {

    val day = Day20()

    val testInput = """
        ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#       
        
        #..#.
        #....
        ##..#
        ..#..
        ..###
    """.trimIndent()

    @Test
    fun `Can parse for test input`() {
        val p = day.parseInput(testInput)
        val enhanceIndex = p.findEnhancementIndex(Pos(2, 2))
        assertThat(enhanceIndex).isEqualTo(34)
    }
    @Test
    fun `Rerun enhance`() {
        val enhanced = day.enhance(testInput, 2)
        assertThat(enhanced.count { it.value }).isEqualTo(35)
    }
}

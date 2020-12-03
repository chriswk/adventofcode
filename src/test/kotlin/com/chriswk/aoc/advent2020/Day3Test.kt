package com.chriswk.aoc.advent2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day3Test {
    val input = """
            ..##.......
            #...#...#..
            .#....#..#.
            ..#.#...#.#
            .#...##..#.
            ..#.##.....
            .#.#.#....#
            .#........#
            #.##...#...
            #...##....#
            .#..#...#.#
        """.trimIndent().lines()

    @Test
    fun part1TestInput() {
        val day = Day3()
        val parsed = day.parse(input)
        val slope = day.createSlope(3, 1, parsed.size, parsed[0].size)
        assertThat(slope.count { parsed[it.y][it.x] == '#' }).isEqualTo(7)
    }
    @Test
    fun part1() {
        val day = Day3()
        assertThat(day.part1()).isEqualTo(294)
    }

    @Test
    fun part2TestInput() {
        val day = Day3()
        val map = day.parse(input)
        val slopes = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)
        val trees = slopes.map { (x, y) ->
            day.createSlope(x, y, map.size, map[0].size).count { map[it.y][it.x] == '#' }
        }
        assertThat(trees).containsExactly(2,7,3,4,2)
        assertThat(trees.fold(1) { a,e -> a*e }).isEqualTo(336)
    }

    @Test
    fun part2() {
        val day = Day3()
        assertThat(day.part2()).isEqualTo(5774564250)
    }
}
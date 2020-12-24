package com.chriswk.aoc.advent2020

import com.chriswk.aoc.util.HexDirection
import com.chriswk.aoc.util.Point2D
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day24Test {
    val smallInstructionSet = """
        sesenwnenenewseeswwswswwnenewsewsw
        neeenesenwnwwswnenewnwwsewnenwseswesw
        seswneswswsenwwnwse
        nwnwneseeswswnenewneswwnewseswneseene
        swweswneswnenwsewnwneneseenw
        eesenwseswswnenwswnwnwsewwnwsene
        sewnenenenesenwsewnenwwwse
        wenwwweseeeweswwwnwwe
        wsweesenenewnwwnwsenewsenwwsesesenwne
        neeswseenwwswnwswswnw
        nenwswwsewswnenenewsenwsenwnesesenew
        enewnwewneswsewnwswenweswnenwsenwsw
        sweneswneswneneenwnewenewwneswswnese
        swwesenesewenwneswnwwneseswwne
        enesenwswwswneneswsenwnewswseenwsese
        wnwnesenesenenwwnenwsewesewsesesew
        nenewswnwewswnenesenwnesewesw
        eneswnwswnwsenenwnwnwwseeswneewsenese
        neswnwewnwnwseenwseesewsenwsweewe
        wseweeenwnesenwwwswnew
    """.trimIndent().lines()

    @Test
    fun `can parse instruction`() {
        val instruction = "nwwswee"
        val day = Day24()
        val instructionSet = day.parseSingleLine(instruction)
        assertThat(instructionSet).hasSize(5)
        assertThat(instructionSet).containsExactly(HexDirection.NorthWest, HexDirection.West, HexDirection.SouthWest, HexDirection.East, HexDirection.East)
    }

    @Test
    fun `can follow instructions`() {
        val instruction = "nwwswee"
        val day = Day24()
        val instructionsList = day.parseSingleLine(instruction)
        val end = day.move(Point2D.ORIGIN, instructionsList)
        assertThat(end).isEqualTo(Point2D.ORIGIN)
    }

    @Test
    fun `flips the correct tiles for test input`() {
        val day = Day24()
        val instructionsList = day.parse(smallInstructionSet)
        val tileMap = day.flip(instructionsList)
        assertThat(tileMap.count { it.value }).isEqualTo(10)
    }

    @Test
    fun part1() {
        assertThat(Day24().part1()).isEqualTo(244)
    }

    @Test
    fun `can make art that changes`() {
        val day = Day24()
        val tileMap = day.flip(day.parse(smallInstructionSet))
        val day1 = day.makeArt(tileMap).drop(1).first()
        assertThat(day.countBlacks(day1)).isEqualTo(15)
    }

    @Test
    fun `can change art over a 100 days`() {
        val day = Day24()
        val tileMap = day.flip(day.parse(smallInstructionSet))
        val day100 = day.makeArt(tileMap).drop(100).first()
        assertThat(day.countBlacks(day100)).isEqualTo(2208)
    }

    @Test
    fun part2() {
        assertThat(Day24().part2()).isEqualTo(3665)
    }
}
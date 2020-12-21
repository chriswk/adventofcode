package com.chriswk.aoc.advent2020

import com.chriswk.aoc.advent2020.Day20.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day20Test {
    val shortInput = """
        Tile 2311:
        ..##.#..#.
        ##..#.....
        #...##..#.
        ####.#...#
        ##.##.###.
        ##...#.###
        .#.#.#..##
        ..#....#..
        ###...#.#.
        ..###..###

        Tile 1951:
        #.##...##.
        #.####...#
        .....#..##
        #...######
        .##.#....#
        .###.#####
        ###.##.##.
        .###....#.
        ..#.#..#.#
        #...##.#..

        Tile 1171:
        ####...##.
        #..##.#..#
        ##.#..#.#.
        .###.####.
        ..###.####
        .##....##.
        .#...####.
        #.##.####.
        ####..#...
        .....##...

        Tile 1427:
        ###.##.#..
        .#..#.##..
        .#.##.#..#
        #.#.#.##.#
        ....#...##
        ...##..##.
        ...#.#####
        .#.####.#.
        ..#..###.#
        ..##.#..#.

        Tile 1489:
        ##.#.#....
        ..##...#..
        .##..##...
        ..#...#...
        #####...#.
        #..#.#.#.#
        ...#.#.#..
        ##.#...##.
        ..##.##.##
        ###.##.#..

        Tile 2473:
        #....####.
        #..#.##...
        #.##..#...
        ######.#.#
        .#...#.#.#
        .#########
        .###.#..#.
        ########.#
        ##...##.#.
        ..###.#.#.

        Tile 2971:
        ..#.#....#
        #...###...
        #.#.###...
        ##.##..#..
        .#####..##
        .#..####.#
        #..#.#..#.
        ..####.###
        ..#.#.###.
        ...#.#.#.#

        Tile 2729:
        ...#.#.#.#
        ####.#....
        ..#.#.....
        ....#..#.#
        .##..##.#.
        .#.####...
        ####.#.#..
        ##.####...
        ##..#.##..
        #.##...##.

        Tile 3079:
        #.#.#####.
        .#..######
        ..#.......
        ######....
        ####.#..#.
        .#...#.##.
        #.#####.##
        ..#.###...
        ..#.......
        ..#.###...
    """.trimIndent()
    @Test
    fun canParseTiles() {
        val day = Day20()
        val tiles = day.parse(shortInput)
        assertThat(tiles).hasSize(9)
        assertThat(tiles.last().id).isEqualTo(3079)
    }
    @Test
    fun canGetSides() {
        val day = Day20()
        val tiles = day.parse(shortInput).map { it.id to it }.toMap()
        val tile = tiles.getValue(3079)
        assertThat(tile.sideFacing(Orientation.North)).isEqualTo("#.#.#####.")
        assertThat(tile.sideFacing(Orientation.West)).isEqualTo("#..##.#...")
        assertThat(tile.sideFacing(Orientation.South)).isEqualTo("..#.###...")
        assertThat(tile.sideFacing(Orientation.East)).isEqualTo(".#....#...")
    }

    @Test
    fun `can answer if it has a side`() {
        val day = Day20()
        val tiles = day.parse(shortInput).map { it.id to it }.toMap()
        val tile = tiles.getValue(3079)
        assertThat(tile.hasSide("#.#.#####.")).isTrue()
        assertThat(tile.hasSide("#.#.#####.".reversed())).isTrue()
    }

    @Test
    fun `can rotate to direction`() {
        val day = Day20()
        val tiles = day.parse(shortInput).map { it.id to it }.toMap()
        val tile = tiles.getValue(3079)
        val topMiddle = tile.findAndOrientNeighbour(Orientation.West, Orientation.East, tiles.values.toList())
        assertThat(topMiddle.id).isEqualTo(2311)
        val southNeighbour = tile.findAndOrientNeighbour(Orientation.South, Orientation.North, tiles.values.toList())
        assertThat(southNeighbour.id).isEqualTo(2473)
    }

    @Test
    fun `finds corners for small input`() {
        val day = Day20()
        val tiles = day.parse(shortInput)
        val image = day.buildImage(tiles)
        assertThat(day.cornerProduct(image)).isEqualTo(20899048083289)
    }

    @Test
    fun `builds expected image`() {
        val day = Day20()
        val tiles = day.parse(shortInput)
        val image = day.buildImage(tiles)
        val ids = image.flatMap { r -> r.map { it.id } }
        assertThat(ids).containsExactly(
            1951L, 2729L, 2971L, 2311L, 1427L, 1489L, 3079L, 2473L, 1171L
        )
    }
    @Test
    fun part1() {
        assertThat(Day20().part1()).isEqualTo(2699020245973)
    }

    @Test
    fun part2() {
        assertThat(Day20().part2()).isEqualTo(2020)
    }
}
package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.Point2D
import com.chriswk.aoc.util.report
import kotlin.math.sqrt

class Day20 : AdventDay(2020, 20) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day20()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }
    fun part1(): Long {
        val tiles = parseInput(inputAsString)
        val image = createImage(tiles)
        return image.first().first().id * image.first().last().id *
                image.last().first().id * image.last().last().id
    }

    fun part2(): Int {
        val seaMonsterOffsets = listOf(
            Point2D(0, 18), Point2D(1, 0), Point2D(1, 5), Point2D(1, 6), Point2D(1, 11), Point2D(1, 12),
            Point2D(1, 17), Point2D(1, 18), Point2D(1, 19), Point2D(2, 1), Point2D(2, 4), Point2D(2, 7),
            Point2D(2, 10), Point2D(2, 13), Point2D(2, 16)
        )
        val tiles = parseInput(inputAsString)
        val image = createImage(tiles)

        return imageToSingleTile(tiles, image)
            .orientations()
            .first { it.maskIfFound(seaMonsterOffsets) }
            .body
            .sumOf { row ->
                row.count { char -> char == '#' }
            }
    }

    private fun imageToSingleTile(tiles: List<Tile>, image: List<List<Tile>>): Tile {
        val rowsPerTile = tiles.first().body.size
        val body = image.flatMap { row ->
            (1 until rowsPerTile - 1).map { y ->
                row.joinToString("") { it.insetRow(y) }.toCharArray()
            }
        }.toTypedArray()
        return Tile(0, body)
    }
    fun cornerProduct(image: List<List<Tile>>): Long {
        return image.first().first().id * image.first().last().id * image.last().first().id * image.last().last().id
    }

    fun createImage(tiles: List<Tile>): List<List<Tile>> {
        val width = sqrt(tiles.count().toFloat()).toInt()
        var mostRecentTile: Tile = findTopCorner(tiles)
        var mostRecentRowHeader: Tile = mostRecentTile
        return (0 until width).map { row ->
            (0 until width).map { col ->
                when {
                    row == 0 && col == 0 ->
                        mostRecentTile
                    col == 0 -> {
                        mostRecentRowHeader =
                            mostRecentRowHeader.findAndOrientNeighbor(Orientation.South, Orientation.North, tiles)
                        mostRecentTile = mostRecentRowHeader
                        mostRecentRowHeader
                    }
                    else -> {
                        mostRecentTile =
                            mostRecentTile.findAndOrientNeighbor(Orientation.East, Orientation.West, tiles)
                        mostRecentTile
                    }
                }
            }
        }
    }

    fun findTopCorner(tiles: List<Tile>): Tile =
        tiles
            .first { tile -> tile.sharedSideCount(tiles) == 2 }
            .orientations()
            .first {
                it.isSideShared(Orientation.South, tiles) && it.isSideShared(Orientation.East, tiles)
            }

    enum class Orientation {
        North, East, South, West
    }

    class Tile(val id: Long, var body: Array<CharArray>) {

        private val sides: Set<String> = Orientation.values().map { sideFacing(it) }.toSet()
        private val sidesReversed = sides.map { it.reversed() }.toSet()

        fun sharedSideCount(tiles: List<Tile>): Int =
            sides.sumOf { side ->
                tiles
                    .filterNot { it.id == id }
                    .count { tile -> tile.hasSide(side) }
            }

        fun isSideShared(dir: Orientation, tiles: List<Tile>): Boolean =
            tiles
                .filterNot { it.id == id }
                .any { tile -> tile.hasSide(sideFacing(dir)) }

        fun findAndOrientNeighbor(mySide: Orientation, theirSide: Orientation, tiles: List<Tile>): Tile {
            val mySideValue = sideFacing(mySide)
            return tiles
                .filterNot { it.id == id }
                .first { it.hasSide(mySideValue) }
                .also { it.orientToSide(mySideValue, theirSide) }
        }

        fun insetRow(row: Int): String =
            body[row].drop(1).dropLast(1).joinToString("")

        fun maskIfFound(mask: List<Point2D>): Boolean {
            var found = false
            val maxWidth = mask.maxByOrNull { it.y }!!.y
            val maxHeight = mask.maxByOrNull { it.x }!!.x
            (0..(body.size - maxHeight)).forEach { x ->
                (0..(body.size - maxWidth)).forEach { y ->
                    val lookingAt = Point2D(x, y)
                    val actualSpots = mask.map { it + lookingAt }
                    if (actualSpots.all { body[it.x][it.y] == '#' }) {
                        found = true
                        actualSpots.forEach { body[it.x][it.y] = '0' }
                    }
                }
            }
            return found
        }

        fun orientations(): Sequence<Tile> = sequence {
            repeat(2) {
                repeat(4) {
                    yield(this@Tile.rotateClockwise())
                }
                this@Tile.flip()
            }
        }

        fun hasSide(side: String): Boolean =
            side in sides || side in sidesReversed

        private fun flip(): Tile {
            body = body.map { it.reversed().toCharArray() }.toTypedArray()
            return this
        }

        private fun rotateClockwise(): Tile {
            body = body.mapIndexed { x, row ->
                row.mapIndexed { y, _ ->
                    body[y][x]
                }.reversed().toCharArray()
            }.toTypedArray()
            return this
        }

        fun sideFacing(dir: Orientation): String =
            when (dir) {
                Orientation.North -> body.first().joinToString("")
                Orientation.South -> body.last().joinToString("")
                Orientation.West -> body.map { row -> row.first() }.joinToString("")
                Orientation.East -> body.map { row -> row.last() }.joinToString("")
            }

        private fun orientToSide(side: String, direction: Orientation) =
            orientations().first { it.sideFacing(direction) == side }

    }

    fun parseInput(input: String): List<Tile> =
        input.split("\n\n").map { it.lines() }.map { tileText ->
            val id = tileText.first().substringAfter(" ").substringBefore(":").toLong()
            val body = tileText.drop(1).map { it.toCharArray() }.toTypedArray()
            Tile(id, body)
        }
}


package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.Point2D
import com.chriswk.aoc.util.fileToString
import com.chriswk.aoc.util.report
import kotlin.math.sqrt

class Day20 : AdventDay(2020, 20) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day20()
            report {
                day.part1fmr()
            }
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }
    fun part1fmr(): Long {
        val input = "2020/input_20.txt".fileToString()
        val tiles = parse(input)
        val image = buildImage(tiles)
        return cornerProduct(image)
    }
    fun parse(input: String): List<Tile> {
        return input.split("\n\n").map { it.lines() }.map { tile ->
            val id = tile.first().substringAfter(" ").substringBefore(":").toLong()
            val bitmap = tile.drop(1).map { it.toCharArray() }.toTypedArray()
            Tile(id, bitmap)
        }
    }

    fun findTopLeftCorner(tiles: List<Tile>): Tile {
        return tiles
            .first { tile -> tile.sharedSideCount(tiles) == 2 }
            .orientations()
            .first {
                it.isSideShared(Orientation.South, tiles) && it.isSideShared(Orientation.East, tiles)
            }
    }
    fun buildImage(tiles: List<Tile>): List<List<Tile>> {
        //Assuming image is square
        val width = sqrt(tiles.size.toDouble()).toInt()
        var mostRecentTile: Tile = findTopLeftCorner(tiles)
        var mostRecentRowHeader: Tile = mostRecentTile
        return (0 until width).map { row ->
            (0 until width).map { col ->
                when {
                    row == 0 && col == 0 ->
                        mostRecentTile
                    col == 0 -> {
                        mostRecentRowHeader =
                            mostRecentRowHeader.findAndOrientNeighbour(Orientation.South, Orientation.North, tiles)
                        mostRecentTile = mostRecentRowHeader
                        mostRecentRowHeader
                    }
                    else -> {
                        mostRecentTile =
                            mostRecentTile.findAndOrientNeighbour(Orientation.East, Orientation.West, tiles)
                        mostRecentTile
                    }
                }
            }
        }
    }
    fun cornerProduct(image: List<List<Tile>>): Long {
        return image.first().first().id * image.first().last().id * image.last().first().id * image.last().last().id
    }
    fun part1(): Long {
        val tiles = parse(inputAsString)
        val image = buildImage(tiles)
        return cornerProduct(image)
    }

    fun part2(): Long {
        return 0
    }

    enum class CardinalDirection {
        NORTH,
        SOUTH,
        WEST,
        EAST
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

        fun findAndOrientNeighbour(mySide: Orientation, theirSide: Orientation, tiles: List<Tile>): Tile {
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

        fun flip(): Tile {
            body = body.map { it.reversed().toCharArray() }.toTypedArray()
            return this
        }

        fun rotateClockwise(): Tile {
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

        fun orientToSide(side: String, direction: Orientation) =
            orientations().first { it.sideFacing(direction) == side }

    }

    enum class Orientation {
        North, East, South, West
    }
}


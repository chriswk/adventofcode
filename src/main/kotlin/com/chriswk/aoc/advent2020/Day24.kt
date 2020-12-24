package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.HexDirection
import com.chriswk.aoc.util.Point2D
import com.chriswk.aoc.util.Point3D
import com.chriswk.aoc.util.report

class Day24: AdventDay(2020, 24) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day24()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun parseSingleLine(input: String): List<HexDirection> {
        return input.fold((emptyList<HexDirection>() to "")) { (acc, prevChar), nextChar ->
                when(prevChar) {
                    "s" -> when(nextChar) {
                        'e' -> acc + HexDirection.SouthEast to ""
                        'w' -> acc + HexDirection.SouthWest to ""
                        else -> error("Should never get here")
                    }
                    "n" -> when(nextChar) {
                        'e' -> acc + HexDirection.NorthEast to ""
                        'w' -> acc + HexDirection.NorthWest to ""
                        else -> error("Should never get here")
                    }
                    else -> {
                        when(nextChar) {
                            'e' -> acc + HexDirection.East to ""
                            'w' -> acc + HexDirection.West to ""
                            'n' -> acc to "n"
                            's' -> acc to "s"
                            else -> error("Should never get here")
                        }
                    }
                }
        }.first
    }

    fun move(origin: Point2D, instructions: List<HexDirection>): Point2D {
        return instructions.fold(origin) { p, instruction ->
            p + instruction.delta
        }
    }

    fun flip(instructions: List<List<HexDirection>>): Map<Point2D, Boolean> {
        return instructions.fold(mutableMapOf<Point2D, Boolean>()) { flippingMap, moves ->
            val endAt = move(Point2D.ORIGIN, moves)
            if (flippingMap.containsKey(endAt)) {
                flippingMap[endAt] = !flippingMap.getValue(endAt)
            } else {
                flippingMap[endAt] = true
            }
            flippingMap
        }
    }

    fun nextDay(tilesToCheck: List<Point2D>, tiles: Map<Point2D, Boolean>): Map<Point2D, Boolean> {
        return tilesToCheck.map { tile ->
            val self = tiles[tile] ?: false
            val blackCount = tile.hexNeighbours.count { tiles[it] ?: false }
            tile to when(self) {
                true -> blackCount in 1..2
                false -> blackCount == 2
            }
        }.toMap()
    }

    fun gatherTilesToCheck(tiles: Map<Point2D, Boolean>): List<Point2D> {
        return tiles.entries.filter { it.value }.flatMap { it.key.hexNeighbours }.distinct().toList()
    }

    fun makeArt(floor: Map<Point2D, Boolean>): Sequence<Map<Point2D, Boolean>> {
        return generateSequence(floor) { previousFloor ->
            nextDay(gatherTilesToCheck(previousFloor), previousFloor)
        }
    }

    fun parse(input: List<String>): List<List<HexDirection>> {
        return input.map { parseSingleLine(it) }
    }
    fun countBlacks(tiles: Map<Point2D, Boolean>) : Int {
        return tiles.count { it.value }
    }
    fun part1(): Int {
        return countBlacks(flip(parse(inputAsLines)))
    }

    fun part2(): Int {
        return countBlacks(makeArt(flip(parse(inputAsLines))).drop(100).first())
    }

}


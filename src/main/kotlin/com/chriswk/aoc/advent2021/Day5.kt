package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day5 : AdventDay(2021, 5) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day5()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }

        fun Int.diff(other: Int) = abs(this - other)
        fun diagonals(xMin: Int, yMin: Int, xMax: Int, yMax: Int): Set<Point> {
            return if (xMin.diff(xMax) != yMin.diff(yMax)) {
                emptySet()
            } else {
                val xRange = if (xMin < xMax) {
                    xMin..xMax
                } else {
                    (0..xMin.diff(xMax)).map { xMin - it }
                }
                val yRange = if (yMin < yMax) {
                    yMin..yMax
                } else {
                    (0..(yMin.diff(yMax))).map { yMin - it }
                }.toList()

                xRange.mapIndexed { idx, x ->
                    Point(x, yRange[idx])
                }.toSet()
            }
        }
    }

    val inputLines = parseInput(inputAsLines)

    fun part1(): Int {
        val lines = inputLines.filter { it.isHorizontal || it.isVertical }
        val points = findPoints(lines)
        return points.values.count { it > 1 }
    }

    fun findPoints(lines: List<Line>): Map<Point, Int> {
        return lines.fold(mutableMapOf()) { matches, line ->
            if (line.isVertical) {
                line.verticalPoints.forEach {
                    matches.merge(it, 1) { old, new -> old + new }
                }
            } else if (line.isHorizontal) {
                line.horizontalPoints.forEach {
                    matches.merge(it, 1) { old, new -> old + new }
                }
            } else {
                line.diagonalRange.forEach {
                    matches.merge(it, 1) { old, new -> old + new }
                }
            }
            matches
        }
    }

    fun part2(): Int {
        val points = findPoints(inputLines)
        return points.values.count { it > 1 }
    }

    fun parseInput(input: List<String>): List<Line> {
        return input.map { Line(it) }
    }

    fun generatePoints(minX: Int, minY: Int, maxX: Int, maxY: Int): List<Point> {
        return (minX until maxX).flatMap { x ->
            (minY until maxY).map { y ->
                Point(x, y)
            }
        }
    }

    fun findDangerSpots(point: List<Point>, lines: List<Line>): List<Pair<Point, Int>> {
        return point.map { p ->
            p to lines.count { it.covers(p) }
        }
    }

    data class Line(val xStart: Int, val xEnd: Int, val yStart: Int, val yEnd: Int) {
        constructor(input: String) : this(
            xStart = input.split("->").first().trim().split(",").first().toInt(),
            yStart = input.split("->").first().trim().split(",").last().toInt(),
            xEnd = input.split("->").last().trim().split(",").first().toInt(),
            yEnd = input.split("->").last().trim().split(",").last().toInt()
        )

        val verticalRange = min(yStart, yEnd)..max(yStart, yEnd)
        val verticalPoints = verticalRange.map { y -> Point(xStart, y) }
        val horizontalRange = min(xStart, xEnd)..max(xStart, xEnd)
        val horizontalPoints = horizontalRange.map { x -> Point(x, yStart) }
        val diagonalRange: Set<Point> = diagonals(xStart, yStart, xEnd, yEnd)
        val isVertical = xStart == xEnd
        val isHorizontal = yStart == yEnd


        fun covers(point: Point) = if (isVertical) {
            point.x == xStart && point.y in verticalRange
        } else if (isHorizontal) {
            point.y == yStart && point.x in horizontalRange
        } else {
            diagonalRange.contains(point)
        }
    }


    data class Point(val x: Int, val y: Int)
}

package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import kotlin.math.abs
import kotlin.math.sign

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

    }

    val inputLines = parseInput(inputAsLines)

    fun part1(): Int {
        val lines = inputLines.filter { it.isHorizontal || it.isVertical }
        val points = findPoints(lines)
        return points.values.count { it > 1 }
    }

    fun findPoints(lines: List<Line>): Map<Point, Int> {
        return lines.fold(mutableMapOf()) { matches, line ->
            line.allPoints.forEach {
                matches.merge(it, 1) { old, new -> old + new }
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


    data class Line(val start: Point, val end: Point) {
        val signX = sign((end.x - start.x).toDouble()).toInt()
        val signY = sign((end.y - start.y).toDouble()).toInt()
        val delta = Point(signX, signY)

        constructor(input: String) : this(
            start = Point(
                input.split("->").first().trim().split(",").first().toInt(),
                input.split("->").first().trim().split(",").last().toInt()
            ),
            end = Point(
                input.split("->").last().trim().split(",").first().toInt(),
                input.split("->").last().trim().split(",").last().toInt()
            )
        )

        val allPoints: List<Point> =
            generateSequence(start) { p ->
                if (p != end) {
                    p + delta
                } else {
                    null
                }
            }.toList()


        fun covers(p: Point): Boolean = allPoints.contains(p)

        val isVertical = start.x == end.x
        val isHorizontal = start.y == end.y

    }


    data class Point(val x: Int, val y: Int) {
        operator fun minus(other: Point): Point = Point(x - other.x, y - other.y)
        operator fun plus(other: Point): Point = Point(x + other.x, y + other.y)
    }
}

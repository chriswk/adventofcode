package com.chriswk.aoc.advent2018

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

object Day6 {
    data class Point(val x: Int, val y: Int) {
        fun dist(other: Point) = abs(x - other.x) + abs(y - other.y)
        fun onEdge(topLeft: Point, bottomRight: Point): Boolean {
            return x == topLeft.x || x == bottomRight.x || y == topLeft.y || y == bottomRight.y
        }
    }

    fun inputToPoints(input: List<String>): List<Point> {
        return input.map { it.toPoint() }
    }

    fun findTopLeftAndTopRight(points: List<Point>): Pair<Point, Point> {
        return points.fold(Pair(points.first(), points.first())) { (upperLeft, lowerRight), p ->
            Pair(Point(min(upperLeft.x, p.x), min(upperLeft.y, p.y)), Point(max(lowerRight.x, p.x), max(lowerRight.y, p.y)))
        }
    }

    fun grid(topLeft: Point, bottomRight: Point): List<Point> {
        return (topLeft.x..bottomRight.x).flatMap { x ->
            (topLeft.y..bottomRight.y).map { y ->
                Point(x, y)
            }
        }
    }

    fun chronalArea(input: List<String>): Int {
        val points = inputToPoints(input)
        val (topLeft, bottomRight) = findTopLeftAndTopRight(points)
        val grid = grid(topLeft, bottomRight)

        val allDistances = grid.associateWith { g ->
            val distances = points.associateWith { it.dist(g) }
            val minDist = distances.minByOrNull { it.value }!!.value
            val closest = distances.filterValues { it == minDist }.keys
            if (closest.count() == 1) {
                closest.single()
            } else {
                null
            }
        }

        val verticesWithInfinite = allDistances
                .filter { it.key.onEdge(topLeft, bottomRight) }
                .map { it.value }
                .distinct()
        return points.filterNot { it in verticesWithInfinite }
                .associateWith { v -> allDistances.count { it.value == v } }
                .maxByOrNull { it.value }?.value ?: throw IllegalStateException("Could not find solution")
    }

    fun totalArea(input: List<String>): Int {
        val points = inputToPoints(input)
        val (topLeft, bottomRight) = findTopLeftAndTopRight(points)
        val grid = grid(topLeft, bottomRight)
        return grid.associateWith { p -> 
            points.sumBy { 
                it.dist(p) 
            }
        }.filter { it.value < 10000 }
        .count()
    }

    fun String.toPoint(): Point {
        val (x, y) = this.split(",").map { it.trim() }
        return Point(x.toInt(), y.toInt())
    }


}

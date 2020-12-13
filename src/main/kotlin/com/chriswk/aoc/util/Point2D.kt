package com.chriswk.aoc.util

import kotlin.math.absoluteValue

data class Point2D(val x: Int, val y: Int) {
    operator fun plus(other: Point2D): Point2D {
        return Point2D(x + other.x, y + other.y)
    }

    operator fun times(factor: Int): Point2D {
        return Point2D(x * factor, y * factor)
    }

    fun rotateLeft(): Point2D {
        return Point2D(x = y * -1, y = x)
    }

    fun rotateRight(): Point2D {
        return Point2D(x = y, y = x * -1)
    }

    fun manhattanDistance(other: Point2D): Int {
        return (x - other.x).absoluteValue + (y - other.y).absoluteValue
    }

    companion object {
        val ORIGIN = Point2D(0, 0)
    }
}
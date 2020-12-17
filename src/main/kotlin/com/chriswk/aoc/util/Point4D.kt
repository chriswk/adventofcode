package com.chriswk.aoc.util

data class Point4D(val x: Int, val y: Int, val z: Int, val w: Int) {
    operator fun plus(other: Point4D): Point4D {
        return Point4D(x + other.x, y + other.y, z + other.z, w + other.w)
    }

    operator fun times(factor: Int): Point4D {
        return Point4D(x * factor, y * factor, z * factor, w * factor)
    }

    fun neighbours(): Sequence<Point4D> {
        return sequenceOf(-1, 0, 1).flatMap { x ->
            sequenceOf(-1, 0, 1).flatMap { y ->
                sequenceOf(-1, 0, 1).flatMap { z ->
                    sequenceOf(-1, 0, 1).map { w ->
                        Point4D(x, y, z, w)
                    }
                }
            }
        }.filterNot { it == Point4D(0, 0, 0, 0) }
            .map { this + it }
    }
}

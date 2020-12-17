package com.chriswk.aoc.util

data class Point3D(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Point3D): Point3D {
        return Point3D(x + other.x, y + other.y, z + other.z)
    }

    operator fun times(factor: Int): Point3D {
        return Point3D(x * factor, y * factor, z * factor)
    }

    fun neighbours(): Sequence<Point3D> {
        return sequenceOf(-1, 0, 1).flatMap { x ->
            sequenceOf(-1, 0, 1).flatMap { y ->
                sequenceOf(-1, 0, 1).map { z ->
                    Point3D(x, y, z)
                }
            }
        }.filterNot { it == Point3D(0 ,0, 0) }
            .map { this + it }
    }
}
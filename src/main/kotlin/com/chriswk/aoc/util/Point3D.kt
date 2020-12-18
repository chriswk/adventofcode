package com.chriswk.aoc.util

data class Point3D(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Point3D): Point3D {
        return Point3D(x + other.x, y + other.y, z + other.z)
    }

    operator fun times(factor: Int): Point3D {
        return Point3D(x * factor, y * factor, z * factor)
    }

    val neighbours: List<Point3D> by lazy {
        (x - 1..x + 1).flatMap { dx ->
            (y - 1..y + 1).flatMap { dy ->
                (z - 1..z + 1).mapNotNull { dz ->
                    Point3D(dx, dy, dz).takeUnless { it == this }
                }
            }
        }
    }
}
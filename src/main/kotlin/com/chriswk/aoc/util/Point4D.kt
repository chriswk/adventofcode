package com.chriswk.aoc.util

data class Point4D(val x: Int, val y: Int, val z: Int, val w: Int) {
    operator fun plus(other: Point4D): Point4D {
        return Point4D(x + other.x, y + other.y, z + other.z, w + other.w)
    }

    operator fun times(factor: Int): Point4D {
        return Point4D(x * factor, y * factor, z * factor, w * factor)
    }

    val neighbours: List<Point4D> by lazy {
        (x - 1..x + 1).flatMap { dx ->
            (y - 1..y + 1).flatMap { dy ->
                (z - 1..z + 1).flatMap { dz ->
                    (w - 1..w + 1).mapNotNull { dw ->
                        Point4D(dx, dy, dz, dw).takeUnless { it == this }
                    }
                }
            }
        }
    }
}

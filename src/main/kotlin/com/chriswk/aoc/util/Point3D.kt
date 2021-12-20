package com.chriswk.aoc.util

import kotlin.math.absoluteValue

data class Point3D(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Point3D): Point3D {
        return Point3D(x + other.x, y + other.y, z + other.z)
    }

    operator fun minus(other: Point3D): Point3D {
        return Point3D(x - other.x, y - other.y, z - other.z)
    }

    operator fun times(factor: Int): Point3D {
        return Point3D(x * factor, y * factor, z * factor)
    }

    infix fun manhattan(other: Point3D): Int {
        return (this.x - other.x).absoluteValue + (this.y - other.y).absoluteValue + (this.z - other.z).absoluteValue
    }
    fun face(direction: Int): Point3D {
        return when(direction) {
            0 -> this
            1 -> Point3D(x, -y, -z)
            2 -> Point3D(x, -z, y)
            3 -> Point3D(-y, -z, x)
            4 -> Point3D(y, -z, -x)
            5 -> Point3D(-x, -z, -y)
            else -> error("No such face")
        }
    }

    fun rotate(direction: Int): Point3D {
        return when(direction) {
            0 -> this
            1 -> Point3D(-y, x, z)
            2 -> Point3D(-x, -y, z)
            3 -> Point3D(y, -x, z)
            else -> error("No such rotation")
        }
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

package com.chriswk.aoc.util

import java.math.BigInteger

data class Box3(val min: Point3D, val max: Point3D): Iterable<Point3D> {
    init {
        require(min <= max)
    }
    val xRange = min.x .. max.x
    val yRange = min.y .. max.y
    val zRange = min.z .. max.z

    fun contains(point: Point3D): Boolean = point in min..max

    fun union(that: Box3): Box3 {
        val unionMin = min.min(that.min)
        val unionMax = max.max(that.max)
        return Box3(unionMin, unionMax)
    }

    fun intersect(that: Box3): Box3? {
        val intersectMin = min.max(that.min)
        val intersectMax = max.min(that.max)
        return if (intersectMin <= intersectMax) {
            Box3(intersectMin, intersectMax)
        } else {
            null
        }
    }

    fun contains(other: Box3): Boolean = this.union(other) == this


    override fun iterator(): Iterator<Point3D> {
        return iterator {
            (min.x..max.x).forEach { x ->
                (min.y..max.y).forEach { y ->
                    (min.z..max.z).forEach { z ->
                        yield(Point3D(x, y, z))
                    }
                }
            }
        }
    }
    val volume: Long = xRange.size().toLong() * yRange.size().toLong() * zRange.size().toLong()
}

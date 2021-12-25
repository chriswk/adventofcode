package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.intersect
import com.chriswk.aoc.util.intersects
import com.chriswk.aoc.util.report
import com.chriswk.aoc.util.size

class Day22 : AdventDay(2021, 22) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day22()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }

        val rangePattern = """x=(-?\d+)\.\.(-?\d+),y=(-?\d+)\.\.(-?\d+),z=(-?\d+)\.\.(-?\d+)""".toRegex()
    }

    private val inputCuboids = inputAsLines.map { Cuboid.of(it) }
    private val part1Cube = Cuboid(true, -50..50, -50..50, -50..50)

    private class Cuboid(val on: Boolean, val x: IntRange, val y: IntRange, val z: IntRange) {
        companion object {
            private val pattern =
                """^(on|off) x=(-?\d+)\.\.(-?\d+),y=(-?\d+)\.\.(-?\d+),z=(-?\d+)\.\.(-?\d+)$""".toRegex()

            fun of(input: String): Cuboid {
                val (s, x1, x2, y1, y2, z1, z2) = pattern.matchEntire(input)?.destructured
                    ?: error("Cannot parse input: $input")
                return Cuboid(
                    s == "on",
                    x1.toInt()..x2.toInt(),
                    y1.toInt()..y2.toInt(),
                    z1.toInt()..z2.toInt(),
                )
            }
        }

        fun intersects(other: Cuboid): Boolean =
            x.intersects(other.x) && y.intersects(other.y) && z.intersects(other.z)

        fun intersect(other: Cuboid): Cuboid? {
            return if (!intersects(other)) {
                null
            } else {
                Cuboid(!on, x.intersect(other.x), y intersect other.y, z intersect other.z)
            }
        }

        fun volume(): Long {
            return x.size().toLong() * y.size().toLong() * z.size().toLong() * if (on) {
                1
            } else {
                -1
            }
        }
    }

    private fun solve(cubesToUse: List<Cuboid> = inputCuboids): Long {
        val volumes = mutableListOf<Cuboid>()

        cubesToUse.forEach { cube ->
            volumes.addAll(volumes.mapNotNull { it.intersect(cube) })
            if (cube.on) volumes.add(cube)
        }

        return volumes.sumOf { it.volume() }
    }


    fun part1(): Long {
        return solve(inputCuboids.filter { it.intersects(part1Cube) })
    }

    fun part2(): Long {
        return solve(inputCuboids)
    }

}

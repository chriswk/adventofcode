package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.Point3D
import com.chriswk.aoc.util.pairs
import com.chriswk.aoc.util.report

class Day19 : AdventDay(2021, 19) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day19()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun parse3dPoint(input: String): Point3D {
        return input.split(",").let { p ->
            Point3D(x = p[0].toInt(), y = p[1].toInt(), z = p[2].toInt())
        }
    }

    data class Transform(val scanner: Point3D, val beacons: Set<Point3D>)
    data class Solution(val scanners: Set<Point3D>, val beacons: Set<Point3D>)

    fun solve(scanners: List<Set<Point3D>>): Solution {
        val base = scanners.first().toMutableSet()
        val foundScanners = mutableSetOf(Point3D(0, 0, 0))
        val unmappedSectors = ArrayDeque<Set<Point3D>>().apply { addAll(scanners.drop(1)) }
        while(unmappedSectors.isNotEmpty()) {
            val thisSector = unmappedSectors.removeFirst()
            when(val transform = findIntersects(base, thisSector)) {
                null -> unmappedSectors.add(thisSector)
                else -> {
                    base.addAll(transform.beacons)
                    foundScanners.add(transform.scanner)
                }
            }
        }
        return Solution(foundScanners, base)
    }

    fun parseInput(input: String): List<Set<Point3D>> {
        return input.split("\n\n").map { single ->
            single.lines()
                .drop(1)
                .map { parse3dPoint(it) }
                .toSet()
        }
    }

    val inputScanners = parseInput(inputAsString)

    fun findIntersects(left: Set<Point3D>, right: Set<Point3D>): Transform? {
        return (0 until 6).firstNotNullOfOrNull { face ->
            (0 until 4).firstNotNullOfOrNull { rotation ->
                val rightOrientations = right.map { it.face(face).rotate(rotation) }.toSet()
                left.firstNotNullOfOrNull { s1 ->
                    rightOrientations.firstNotNullOfOrNull { s2 ->
                        val diff = s1 - s2
                        val moved = rightOrientations.map { it + diff }.toSet()
                        if (moved.intersect(left).size >= 12) {
                            Transform(diff, moved)
                        } else {
                            null
                        }
                    }
                }
            }
        }
    }

    fun part1(): Int {
        return solve(inputScanners).beacons.size
    }


    fun part2(): Int {
        return solve(inputScanners).scanners.pairs().maxOf { it.first manhattan it.second }
    }

}

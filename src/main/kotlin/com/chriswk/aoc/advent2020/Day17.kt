package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.Point3D
import com.chriswk.aoc.util.Point4D
import com.chriswk.aoc.util.report

class Day17: AdventDay(2020, 17) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day17()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun evaluatePoint(world: World, point: Point3D, currentState: Boolean): Cube {
        val activeNeighbourCount = point.neighbours().count {
            world.getOrDefault(it, false)
        }
        val newState = when(currentState) {
            true -> activeNeighbourCount in 2..3
            false -> activeNeighbourCount == 3
        }
        return point to newState
    }

    fun evaluateHyperPoint(world: HyperWorld, point: Point4D, currentState: Boolean): HyperCube {
        val activeNeighbourCount = point.neighbours().count {
            world.getOrDefault(it, false)
        }
        val newState = when(currentState) {
            true -> activeNeighbourCount in 2..3
            false -> activeNeighbourCount == 3
        }
        return point to newState
    }

    fun gatherPointsToCheck(world: World): List<Point3D> {
        return world.entries.filter { it.value }.flatMap { it.key.neighbours() }.distinct().toList()
    }
    fun gatherHyperPointsToCheck(world: HyperWorld): List<Point4D> {
        return world.entries.filter { it.value }.flatMap { it.key.neighbours() }.distinct()
    }

    fun step(world: World): World {
        val allPoints = gatherPointsToCheck(world)
        return allPoints.map { point ->
            evaluatePoint(world, point, world.getOrDefault(point, false))
        }.filter { it.second }.toMap()
    }
    fun hyperStep(world: HyperWorld): HyperWorld {
        val allPoints = gatherHyperPointsToCheck(world)
        return allPoints.map { p ->
            evaluateHyperPoint(world, p, world.getOrDefault(p, false))
        }.filter { it.second }.toMap()
    }

    fun simulate(world: World, steps: Int): World {
        return generateSequence(world) { step(it) }.drop(steps).first()
    }
    fun simulateHyper(world: HyperWorld, steps: Int): HyperWorld {
        return generateSequence(world) { hyperStep(it) }.drop(steps).first()
    }

    fun cubes(input: List<String>): World {
        return input.flatMapIndexed { x, row ->
            row.mapIndexed { y, column ->
                Pair(Point3D(x, y, 0), column == '#')
            }
        }.toMap()
    }

    fun hyperCubes(input: List<String>): HyperWorld {
        return input.flatMapIndexed { x, row ->
            row.mapIndexed { y, column ->
                Pair(Point4D(x, y, 0, 0), column == '#')
            }
        }.toMap()

    }

    fun part1(): Int {
        return simulate(cubes(inputAsLines), 6).size
    }

    fun part2(): Int {
        return simulateHyper(hyperCubes(inputAsLines), 6).size
    }

}
typealias World = Map<Point3D, Boolean>
typealias HyperWorld = Map<Point4D, Boolean>
typealias HyperCube = Pair<Point4D, Boolean>
typealias Cube = Pair<Point3D, Boolean>
package com.chriswk.aoc.advent2019

import com.chriswk.aoc.util.dayInputAsLines
import com.chriswk.aoc.util.report
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.hypot

class Day10 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day10 = Day10()
            val input = dayInputAsLines(2019, 10)
            report { day10.part1(input) }
            report { day10.part2(input) }
        }
    }

    fun parseInput(input: List<String>): List<Point> {
        return input.withIndex()
            .flatMap { (y, row) -> row.withIndex().filter { it.value == '#' }.map { Point(it.index, y) } }
    }

    fun part1(input: List<String>): Int {
        val asteroids = parseInput(input)
        return visible(asteroids).maxOrNull() ?: -1
    }

    fun part2(input: List<String>): Int {
        val asteroids = parseInput(input)
        val station = findStation(asteroids)!!
        val other = (asteroids - station)
        val twohundrethTarget = shoot(station, other).drop(199).first()
        return twohundrethTarget.part2()
    }

    fun shoot(station: Point, other: List<Point>): Sequence<Point> {
        val remaining = other.toMutableList()
        var angle = -PI / 2
        var firstTarget = true
        return generateSequence {
            val asteroidsByAngle = remaining.groupBy { atan2(it.dy(station).toDouble(), it.dx(station).toDouble()) }
            val nextAngleTargets = asteroidsByAngle
                .filter { if (firstTarget) it.key >= angle else it.key > angle }
                .minByOrNull { it.key }
                ?: asteroidsByAngle.minByOrNull { it.key }!!
            angle = nextAngleTargets.key
            firstTarget = false
            val target =
                nextAngleTargets.value.minByOrNull {
                    hypot(
                        it.dx(station).toDouble(),
                        it.dy(station).toDouble()
                    )
                }!!
            remaining.remove(target)
            target
        }
    }

    fun findStation(points: List<Point>): Point? {
        return points.maxByOrNull { asteroid: Point ->
            points.filter { it != asteroid }
                .map { atan2(it.dy(asteroid).toDouble(), it.dx(asteroid).toDouble()) }
                .distinct()
                .count()
        }
    }

    fun visible(points: List<Point>): List<Int> {
        return points.map { asteroid ->
            points.filter { it != asteroid }
                .map { atan2(it.dy(asteroid).toDouble(), it.dx(asteroid).toDouble()) }
                .distinct()
                .count()
        }
    }
}
fun Point.part2() = this.x * 100 + this.y



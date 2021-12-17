package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.Pos
import com.chriswk.aoc.util.report
import com.chriswk.aoc.util.reportNano

class Day17: AdventDay(2021, 17) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day17()
            reportNano {
                day.part1()
            }
            report {
                day.part2()
            }
        }
        val targetRegex = """target area: x=(-?\d+)\.\.(-?\d+), y=(-?\d+)..(-?\d+)""".toRegex()
    }
    data class Probe(val currentPos: Pos, val velocity: Pos)
    data class TargetArea(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int) {
        operator fun contains(point: Pair<Int, Int>) = point.first in minX..maxX && point.second in minY..maxY
    }
    data class Velocity(val x: Int, val y: Int) {
        fun willBeWithin(target: TargetArea): Boolean {
            val seqX = seqX().takeWhile { (posX, _) -> posX <= target.maxX }.map { it.first }
            val seqY = seqY().takeWhile { (posY, _) -> posY >= target.minY }.map { it.first }

            return (seqX zip seqY).any { it in target }
        }

        private fun seqX() = generateSequence(0 to x) { (posX, velX) -> posX + velX to maxOf(0, velX - 1) }

        private fun seqY() = generateSequence(0 to y) { (posY, velY) -> posY + velY to velY - 1 }
    }

    val gravity: Pos = Pos(0, -1)


    fun target(input: String): TargetArea {
        val (startX, endX, startY, endY) = targetRegex.find(input)!!.destructured
        val xes = listOf(startX.toInt(), endX.toInt())
        val ys = listOf(startY.toInt(), endY.toInt())
        return TargetArea(xes.minOf { it }, xes.maxOf { it }, ys.minOf { it }, ys.maxOf { it })
    }

    val inputTarget = target(inputAsString)

    fun Int.gaussSum() = this * (this + 1) / 2

    fun part1(): Int {
        return (- inputTarget.minY - 1).gaussSum()
    }

    fun part2(): Int {
        val minY = inputTarget.minY
        val maxY = -inputTarget.minY - 1
        val minX = (1..inputTarget.minX).first { it.gaussSum() >= inputTarget.minX }
        val maxX = inputTarget.maxX

        return (minX..maxX).sumOf { x ->
            (minY..maxY).count { y ->
                Velocity(x, y).willBeWithin(inputTarget)
            }
        }    }

}

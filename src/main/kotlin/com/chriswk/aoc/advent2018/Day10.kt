package com.chriswk.aoc.advent2018

import com.chriswk.aoc.AdventDay
import java.lang.Thread.sleep

object Day10 : AdventDay(2018, 10) {
    val pointReg = """position=<([-|\s]?\d+), ([-|\s]?\d+)> velocity=<([-|\s]?\d+), ([-|\s]?\d+)>""".toRegex()
    var step = 0
    fun part1(input: List<String>) {
        var ps = input.map { parseToPoint(it) }
        step = 0
        while (!showsText(ps)) {
            ps = step(ps)
            step++
        }
        printPoints(ps)
    }

    fun step(points: List<Point>): List<Point> =
            points.map { it.step() }

    fun printPoints(p: List<Point>) {
        grid(p.minByOrNull { it.x }!!.x - 3, p.minByOrNull { it.y }!!.y - 3, p.maxByOrNull { it.x }!!.x + 3, p.maxByOrNull { it: Point -> it.y }!!.y + 3, p)
    }


    fun showsText(p: List<Point>): Boolean {
        val minY = p.minByOrNull { it.y }!!.y
        val maxY = p.maxByOrNull { it.y }!!.y
        return (maxY - minY) == 9
    }

    fun grid(minX: Int, minY: Int, maxX: Int, maxY: Int, p: List<Point>) {
        val points = p.groupBy { Pair(it.x, it.y) }
        println("------------------------------------------------------")
        println("Step ${step}")
        println()
        println()
        (minY..maxY).forEach { y ->
            (minX..maxX).forEach { x ->
                if (points.containsKey(x to y)) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
        sleep(1000)
    }


    fun parseToPoint(pointStr: String): Point {
        val (x, y, deltaX, deltaY) = pointReg.find(pointStr)!!.groupValues.drop(1).map { it.trim().toInt() }
        return Point(x, y, deltaX, deltaY)
    }

    data class Point(val x: Int, val y: Int, val deltaX: Int, val deltaY: Int) {
        fun step() = this.copy(x = x + deltaX, y = y + deltaY)
    }
}
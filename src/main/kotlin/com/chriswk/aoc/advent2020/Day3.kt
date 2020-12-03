package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import org.apache.logging.log4j.LogManager

class Day3 : AdventDay(2020, 3) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>): Unit {
            val day = Day3()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
        val logger = LogManager.getLogger()
    }
    fun parse(input: List<String>): Array<CharArray> {
        return input.map { it.toCharArray() }.toTypedArray()
    }
    val map = parse(inputAsLines)

    fun createSlope(slopeX: Int, slopeY: Int, height: Int, width: Int): Sequence<Point> {
        return generateSequence(Point(0, 0)) { p ->
            p.move(slopeX, slopeY, width)
        }.takeWhile { it.y < height }
    }

    fun part1(): Int {
        val width = map[0].size
        return createSlope(3, 1, map.size, width).count { map[it.y][it.x] == '#' }
    }
    fun part2(): Long {
        val width = map[0].size
        val height = map.size
        val slopes = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)
        val trees = slopes.map { (x, y) ->
            createSlope(x, y, height, width).count { map[it.y][it.x] == '#' }
        }
        return trees.fold(1) { a, e -> a*e }
    }
    data class Point(val x: Int, val y: Int) {
        fun move(slopeX: Int, slopeY: Int, width: Int): Point {
            return Point((x + slopeX) % width, y + slopeY)
        }
    }
}
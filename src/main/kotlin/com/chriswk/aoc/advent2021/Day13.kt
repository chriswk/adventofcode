package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.Pos
import com.chriswk.aoc.util.report

class Day13: AdventDay(2021, 13) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day13()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun fold(points: Set<Pos>, instruction: Instruction): Set<Pos> {
        return points.map { p ->
            when(instruction.alongY) {
                true -> p.foldUp(instruction.point)
                false -> p.foldLeft(instruction.point)
            }
        }.toSet()
    }

    fun printGrid(points: Set<Pos>) {
        val minX = points.minOf { it.x }
        val minY = points.minOf { it.y }
        val maxX = points.maxOf { it.x }
        val maxY = points.maxOf { it.y }
        (minY..maxY).forEach { y ->
            println((minX..maxX).joinToString(separator = "") { x ->
                if(points.contains(Pos(x, y))) {
                    "#"
                } else {
                    "."
                }
            })
        }
    }

    data class Instruction(val alongY: Boolean, val point: Int)

    fun parse(input: List<String>): Pair<Set<Pos>, List<Instruction>> {
        val points = input.takeWhile { it.isNotEmpty()}.map {
            val (x, y) = it.split(",")
            Pos(x.toInt(), y.toInt())
        }.toSet()
        val instructions = input.dropWhile { it.isNotEmpty() }.drop(1).map {
            if (it.contains("y=")) {
                Instruction(true, it.split("=").last().toInt())
            } else {
                Instruction(false, it.split("=").last().toInt())
            }
        }
        return points to instructions
    }

    fun part1(): Int {
        return 0
    }

    fun part2(): Int {
        return 0
    }

}

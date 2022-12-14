package com.chriswk.aoc.advent2022

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.CompassDirection
import com.chriswk.aoc.util.Point2D
import com.chriswk.aoc.util.report
import kotlin.math.absoluteValue
import kotlin.math.sign

class Day9: AdventDay(2022, 9) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day9()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun move(head: Point2D, moves: List<CompassDirection>): Sequence<Point2D> {
        return sequence {
            yield(head)
            moves.fold(head) { acc, dir ->
                val new = acc + dir.delta
                yield(new)
                new
            }
        }
    }



    data class Move(val direction: CompassDirection, val count: Int)

    fun parseInput(input: List<String>): List<CompassDirection> {
        return input.map { parseLine(it) }.flatMap { instruction -> (0.until(instruction.count)).map { instruction.direction }}
    }

    fun parseLine(input: String): Move {
        val (direction, count) = input.split(" ")
        return Move(direction = CompassDirection.fromString(direction), count = count.toInt())
    }

    fun part1(): Int {
        return move(Point2D(0, 0), parseInput(inputAsLines)).follow().toSet().size
    }

    fun part2(): Int {
        return move(Point2D(0, 0), parseInput(inputAsLines)).follow().follow().follow().follow().follow().follow().follow().follow().follow().toSet().size
    }

}

fun Sequence<Point2D>.follow(): Sequence<Point2D> = sequence {
    var tailX = 0
    var tailY = 0
    yield(Point2D(tailX, tailY))
    for (point in this@follow) {
        val deltaX = point.x - tailX
        val deltaY = point.y - tailY
        if (deltaX.absoluteValue >= 2 || deltaY.absoluteValue >= 2) {
            tailX = if (deltaX.absoluteValue >= deltaY.absoluteValue) {
                point.x - deltaX.sign
            } else {
                point.x
            }
            tailY = if (deltaX.absoluteValue <= deltaY.absoluteValue) {
                point.y - deltaY.sign
            } else {
                point.y
            }
            val new = Point2D(tailX, tailY)
            yield(new)
        } else {
            continue
        }
    }
}

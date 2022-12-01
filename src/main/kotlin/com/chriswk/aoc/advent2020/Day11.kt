package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.Pos
import com.chriswk.aoc.util.report
import java.lang.IllegalStateException

class Day11 : AdventDay(2020, 11) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day11()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }

        val neighbours = sequenceOf(
            Pair(-1, -1), Pair(0, -1), Pair(1, -1),
            Pair(-1, 0), Pair(1, 0),
            Pair(-1, 1), Pair(0, 1), Pair(1, 1)
        )



    }

    fun findEquilibrium(seats: Seats, tolerance: Int, comparisonFunction: (Seats, Seat) -> Int): Seats {
        return generateSequence(seats) {
            it.next(tolerance, comparisonFunction)
        }.zipWithNext().first { (current, next) -> current.contentDeepEquals(next) }.first
    }

    fun closestSeatAxis(seats: Seats, seat: Seat, axis: Seat): Char? {
        return generateSequence(seat + axis) { it + axis }.map {
            if (it in seats) {
                seats[it.first][it.second]
            } else { null }
        }.first { it == null || it != '.' }
    }

    fun occupiedNeighboursAxis(seats: Seats, seat: Seat): Int {
        return neighbours.mapNotNull { closestSeatAxis(seats, seat, it) }.count { it == '#' }
    }

    fun part1(): Int {
        val seats: Seats = inputAsLines.map { it.toCharArray() }.toTypedArray()
        return findEquilibrium(seats, 4, this::occupiedNeighbours).occupied()
    }
    fun occupiedNeighbours(seats: Seats, seat: Seat): Int {
        return neighbours.map { it + seat }.filter { it in seats }.count { seats[it.first][it.second] == '#' }
    }


    fun part2(): Int {
        val seats: Seats = inputAsLines.map { it.toCharArray() }.toTypedArray()
        return findEquilibrium(seats, 5, this::occupiedNeighboursAxis).occupied()
    }


}

typealias Seats = Array<CharArray>

operator fun Seats.contains(seat: Seat) = seat.first in this.indices && seat.second in this.first().indices
fun Seats.occupied() = this.sumOf { row -> row.count { it == '#' } }
fun Seats.next(tolerance: Int, countFunction: (Seats, Seat) -> Int): Seats {
    return this.mapIndexed { x, row ->
        row.mapIndexed { y, spot ->
            val occupied = countFunction(this, Seat(x, y))
            when {
                spot == 'L' && occupied == 0 -> '#'
                spot == '#' && occupied >= tolerance -> 'L'
                else -> spot
            }
        }.toCharArray()
    }.toTypedArray()
}

typealias Seat = Pair<Int, Int>

private operator fun Seat.plus(other: Seat): Seat = Seat(this.first + other.first, this.second + other.second)

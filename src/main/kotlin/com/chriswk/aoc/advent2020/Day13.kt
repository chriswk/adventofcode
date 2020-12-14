package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day13: AdventDay(2020, 13) {
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

    fun findEarliestDepartureAndBuses(input: List<String>): Pair<Int, List<Int>>  {
        val myDeparture = input.first().toInt()
        val buses = input.drop(1).first().split(",").filterNot { it == "x" }.map { it.toInt() }
        return myDeparture to buses
    }

    fun findClosestBus(earliestDeparture: Int, buses: List<Int>) : BusDeparture {
        val (busId, waitTime) = buses.map { id -> id to id - (earliestDeparture % id) }.minByOrNull { it.second }!!
        return BusDeparture(busId, waitTime)
    }

    fun solvePart2(input: String): Long {
        val busPairs = input.split(",")
            .mapIndexed { idx, b -> idx to b }
            .filterNot { it.second == "x" }
            .map { it.second.toLong() to it.first.toLong() }
        var currentJump = 1L
        var offset = 0L
        busPairs.forEach { (bus, diff) ->
            while ((offset + diff) % bus != 0L) {
                offset += currentJump
            }
            currentJump *= bus
        }
        return offset
    }

    fun part1(): Int {
        val (earliestDeparture, buses) = findEarliestDepartureAndBuses(inputAsLines)
        return findClosestBus(earliestDeparture, buses).part1
    }

    fun part2(): Long {
        return solvePart2(inputAsLines[1])
    }

    data class BusDeparture(val id: Int, val waitTime: Int) {
        val part1 = id*waitTime
    }
}
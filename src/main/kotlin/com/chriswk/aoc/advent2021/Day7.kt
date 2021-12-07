package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.advent2021.Day5.Companion.diff
import com.chriswk.aoc.util.report
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


typealias Crab = Int

class Day7: AdventDay(2021, 7) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day7()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
        val logger: Logger = LogManager.getLogger()
    }

    val inputCrabs = crabs(inputAsString)

    fun crabs(input: String): List<Int> {
        return input.split(",").map { it.toInt() }.sorted()
    }

    fun positionCosts(crabs: List<Crab>, gaussProduct: Boolean = false): Map<Int, Int> {
        val crabMap = crabs.groupingBy { it }.eachCount()
        val min = crabs.first()
        val max = crabs.last()
        return (min..max).map { pos ->
            pos to (crabMap.entries.fold(0) { a, e ->
                val distance = pos.diff(e.key)
                val cost = if (gaussProduct) { gaussDistance(distance) } else { distance }
                a + (cost * e.value)
            })
        }.toMap()
    }

    fun minimumPositionCost(crabs: List<Crab>, gaussProduct: Boolean = false): Int {
        val crabMap = crabs.groupingBy { it }.eachCount()
        val min = crabs.first()
        val max = crabs.last()
        return (min..max).minOf { pos ->
            crabMap.entries.fold(0) { totalCost, (crabAt, count) ->
                val distance = pos.diff(crabAt)
                val cost = if (gaussProduct) { gaussDistance(distance) } else { distance }
                totalCost + (cost * count)
            }
        }
    }


    fun gaussDistance(distance: Int): Int {
        return (distance * (distance+1)) / 2
    }

    fun part1(): Int {
        return minimumPositionCost(inputCrabs, gaussProduct = false)
    }

    fun part2(): Int {
        return minimumPositionCost(inputCrabs, gaussProduct = true)
    }

}

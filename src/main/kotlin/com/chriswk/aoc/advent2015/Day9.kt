package com.chriswk.aoc.advent2015

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.permutations
import com.chriswk.aoc.util.report
import org.apache.logging.log4j.LogManager
import kotlin.math.min

class Day9: AdventDay(2015, 9) {
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
        val logger = LogManager.getLogger()
    }

    fun distances(input: List<String>): Map<Distance, Int> {
        return input.flatMap {
            val (cities, distance) = it.split(" = ")
            val (from, to) = cities.split(" to ")
            listOf(
                (Distance(from, to)  to distance.toInt()),
                (Distance(to, from) to distance.toInt())
            )
        }.toMap()
    }

    fun totalDistance(distances: Map<Distance, Int>, journeys: List<String>, total: Int): Int {
        return if (journeys.isEmpty() || journeys.size == 1) {
             total
        } else {
            val (from, to) = journeys.take(2)
            totalDistance(distances, journeys.drop(1), total + distances.getOrDefault(Distance(from, to), 0))
        }
    }

    fun shortestDistance(distances: Map<Distance, Int>, journeys: List<List<String>>): Int {
         return journeys.minOf { totalDistance(distances, it, 0) } ?: 9999
    }

    fun longestDistance(distances: Map<Distance, Int>, journeys: List<List<String>>): Int {
        return journeys.maxOf { totalDistance(distances, it, 0) }
    }

    fun locations(distances: Map<Distance, Int>): List<List<String>> {
        return distances.keys.flatMap { (from, to) ->
            val l = mutableListOf(from, to)
            distances.keys.forEach {
                if(!l.contains(it.to)) { l.add(it.to) }
                if(!l.contains(it.from)) { l.add(it.from) }
            }
            l.permutations()
        }.distinct()
    }

    fun part1(): Int {
        val d = distances(inputAsLines)
        val journeys = locations(d)
        logger.info("${journeys.size} to compare")
        assert(journeys.all { it.size == 8 })
        return shortestDistance(d, journeys)
    }

    fun part2(): Int {
        val d = distances(inputAsLines)
        val journeys = locations(d)
        logger.info("${journeys.size} to compare")
        assert(journeys.all { it.size == 8 })
        return longestDistance(d, journeys)

    }

}
data class Distance(val from: String, val to: String)

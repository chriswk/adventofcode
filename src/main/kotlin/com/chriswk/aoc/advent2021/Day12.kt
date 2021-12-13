package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day12: AdventDay(2021, 12) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day12()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun parseCave(input: List<String>): Map<Cave, List<Cave>> {
        return input.map { it.split("-") }.flatMap { (left, right) ->
            listOf(Cave(left) to Cave(right), Cave(right) to Cave(left))
        }.groupBy({ it.first}, {it.second})
    }

    fun solve(graph: Map<Cave, List<Cave>>, cave: Cave = Cave("start"), visited: Set<Cave> = hashSetOf(cave), canVisitTwice: Boolean = false): Int {
        return graph[cave]!!.sumOf { destination ->
            when {
                destination.end -> 1
                destination.start -> 0
                destination.big -> solve(graph, destination, visited + destination, canVisitTwice)
                destination !in visited -> solve(graph, destination, visited + destination, canVisitTwice)
                canVisitTwice -> solve(graph, destination, visited + destination, false)
                else -> 0
            }
        }
    }

    fun part1(): Int {
        return solve(inputGraph)
    }

    val inputGraph = parseCave(inputAsLines)

    fun part2(): Int {
        return solve(inputGraph, canVisitTwice = true)
    }

    data class Cave(val id: String) {
        val big: Boolean = id[0].isUpperCase()
        val start = id == "start"
        val end = id == "end"
    }

}

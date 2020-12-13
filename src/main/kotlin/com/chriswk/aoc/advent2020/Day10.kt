package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day10: AdventDay(2020, 10) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day10()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun groupAdapters(adapters: List<Int>): Map<Int, Int> {
        val max = adapters.maxOrNull()!!
        return (listOf(0) + adapters + (max + 3))
            .sorted()
            .asSequence()
            .zipWithNext()
            .map { (p, n) -> n - p }
            .groupBy { it }
            .mapValues { it.value.size }
    }

    fun part1(): Int {
        val jolts = inputAsLines.map { it.toInt() }
        val diffMap = groupAdapters(jolts)
        return diffMap.getValue(1) * diffMap.getValue(3)
    }

    fun findPossibleValidPathsToUseAll(adapters: List<Int>): Long {
        val max = adapters.maxOrNull()!!
        val allAdapters = (listOf(max + 3) + adapters).sorted()
        val wayToReachAdapter = mutableMapOf(0 to 1L)
        allAdapters.forEach { jolt ->
            wayToReachAdapter[jolt] = (1..3).map {
                wayToReachAdapter.getOrDefault(jolt - it, 0)
            }.sum()
        }
        return wayToReachAdapter.getValue(allAdapters.last())
    }

    fun part2(): Long {
        return findPossibleValidPathsToUseAll(inputAsLines.map { it.toInt() })
    }

}
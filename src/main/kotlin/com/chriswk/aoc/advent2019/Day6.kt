package com.chriswk.aoc.advent2019

import com.chriswk.aoc.util.dayInputAsLines
import com.chriswk.aoc.util.report

class Day6(val orbits: List<String>) {
    val parents: Map<Child, Parent> = orbits.map { it.split(")") }.associate { it.last() to it.first() }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day6 = Day6(dayInputAsLines(2019, 6))
            report { day6.part1() }
            report { day6.part2() }
        }
    }

    fun checksum(): Int = parents.keys.sumOf { pathTo(it).size - 1 }
    fun pathTo(child: String, path: MutableList<String> = mutableListOf(child)): List<String> {
        return parents[child]?.let { parent ->
            path.add(parent)
            pathTo(parent, path)
        } ?: path
    }

    fun intersection(): Int {
        val youToRoot = pathTo("YOU")
        val santaToRoot = pathTo("SAN")
        val intersection = youToRoot.intersect(santaToRoot).first()
        return youToRoot.indexOf(intersection) + santaToRoot.indexOf(intersection) - 2 // Fence post for each
    }

    fun part1(): Int {
        return checksum()
    }

    fun part2(): Int {
        return intersection()
    }
}
typealias Parent = String
typealias Child = String

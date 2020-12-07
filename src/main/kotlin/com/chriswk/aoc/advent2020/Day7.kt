package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import com.chriswk.aoc.util.toInt
import org.apache.logging.log4j.LogManager

class Day7: AdventDay(2020, 7) {
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
        val logger = LogManager.getLogger(Day7::class.java)
    }

    val childRegex = """(\d+) (\S* \S*)""".toRegex()

    fun readRule(rule: String): List<Pair<ColoredBag, ColoredBag>> {
        val content = rule.split(" ")
        val parent = content.take(2).joinToString(" ")
        val children = content.drop(4).joinToString(" ").split(",")
        return children.mapNotNull {
            val child = it.trim()
            val match = childRegex.find(child)
            if (match != null) {
                (parent to 1) to (match.groups[2]!!.value to toInt(match.groups[1]))
            } else {
                null
            }
        }
    }

    fun toAscendantGraph(rules: List<Pair<ColoredBag, ColoredBag>>): Map<String, List<ColoredBag>> {
        return rules.groupBy { it.second.first }.mapValues { it.value.map { it.first } }
    }

    fun toAdjacencyGraph(rules: List<Pair<ColoredBag, ColoredBag>>): Map<String, List<ColoredBag>> {
        return rules.groupBy { it.first.first }.mapValues { it.value.map { it.second } }
    }

    fun parsePart1(input: List<String>): Map<String, List<ColoredBag>> {
        return toAscendantGraph(input.flatMap { readRule(it) })
    }
    fun parsePart2(input: List<String>): Map<String, List<ColoredBag>> {
        return toAdjacencyGraph(input.flatMap { readRule(it) })
    }
    fun findBagColors(graph: Map<String, List<String>>, visited: Set<String>, color: String): Set<String> {
        val direct = graph[color]?.toSet() ?: emptySet()
        val remainToVisit = direct - visited
        return if (remainToVisit.isEmpty()) {
            emptySet()
        } else {
            direct + remainToVisit.flatMap { findBagColors(graph, visited + color, it) }
        }
    }
    fun findHowManyBags(graph: Map<String, List<ColoredBag>>, color: String): Int {
        val children = graph[color] ?: emptyList()
        return if (children.isNotEmpty()) {
            1 + children.sumBy { it.second * findHowManyBags(graph, it.first) }
        } else {
            1
        }
    }

    fun part1(): Int {
        val graph = parsePart1(inputAsLines)
        return findBagColors(graph.mapValues { it.value.map { it.first }}, emptySet(), "shiny gold").size
    }

    fun part2(): Int {
        val graph = parsePart2(inputAsLines)
        return findHowManyBags(graph, "shiny gold") - 1
    }

}

typealias ColoredBag = Pair<String, Int>
typealias Graph = Map<String, Pair<String, Int>>
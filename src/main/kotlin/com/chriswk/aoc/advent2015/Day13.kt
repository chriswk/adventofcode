package com.chriswk.aoc.advent2015

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.permutations
import com.chriswk.aoc.util.report

class Day13 : AdventDay(2015, 13) {

    companion object {
        val happinessLine = """(.+) would (lose|gain) (\d+) happiness units by sitting next to (.+).""".toRegex()

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

    val inputRelationships: List<Match> = inputAsLines.map { Match.fromLine(it) }

    fun calculateHappiness(seatings: List<String>, relations: Map<Pair<String, String>, Match>): Int {
        val pairs = seatings.windowed(2).map { it.first() to it.last() } + (seatings.first() to seatings.last())
        return pairs.sumOf { relations[it.first to it.second]!!.happinessDelta + relations[it.second to it.first]!!.happinessDelta }
    }

    fun maxHappinessDelta(relationShips: List<Match>): Int {
        val people = relationShips.map { it.person1 }.distinct().toList()
        val allRelations = relationShips.associateBy { (it.person1 to it.person2) }
        return people.permutations().maxOfOrNull { calculateHappiness(it, allRelations) } ?: 0
    }

    fun part1(): Int {
        return maxHappinessDelta(inputRelationships)
    }

    fun addSelf(relationShips: List<Match>, name: String = "CWK"): List<Match> {
        return relationShips + relationShips.map { it.person1 }.distinct()
            .flatMap { listOf(Match(name, it, 0), Match(it, name, 0)) }
    }

    fun part2(): Int {
        return maxHappinessDelta(addSelf(inputRelationships))
    }

    data class Match(val person1: String, val person2: String, val happinessDelta: Int) {
        companion object {
            fun fromLine(line: String): Match {
                val (p1, result, delta, p2) = happinessLine.find(line)!!.destructured
                val deltaInt = delta.toInt()
                val happinessDelta = when (result) {
                    "gain" -> deltaInt
                    "lose" -> -deltaInt
                    else -> 0
                }
                return Match(p1, p2, happinessDelta)
            }
        }
    }
}

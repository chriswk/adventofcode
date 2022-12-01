package com.chriswk.aoc.advent2018

object Day7 {
    val dependency = """Step (\w) must be finished before step (\w) can begin\.""".toRegex()

    data class Requirement(val prereq: Char, val downstream: Char)

    fun part1(requirements: List<String>): String {
        val reqAsMap = parseReq(requirements)
        return findPath(reqAsMap)
    }

    fun part2(workers: Int = 5, requirements: List<String>): Int {
        val allPairs = allPairs(requirements)
        val dependedOn = generateDependencies(allPairs.map { it.second to it.first })
        val dependedBy = generateDependencies(allPairs)
        val allKeys = dependedBy.keys.union(dependedOn.keys)

        val ready = allKeys.filterNot { it in dependedOn }.map { it to it.toTime() }.toMutableList()
        val done = mutableListOf<Char>()
        var time = 0

        while (ready.isNotEmpty()) {
            ready.take(workers).forEachIndexed { idx, work ->
                ready[idx] = Pair(work.first, work.second - 1)
            }
            ready.filter { it.second == 0 }.forEach { workItem ->
                done.add(workItem.first)
                dependedBy[workItem.first]?.let { maybeReadyS ->
                    ready.addAll(
                            maybeReadyS.filter { maybeReady ->
                                dependedOn.getValue(maybeReady).all { it in done || it == workItem.first }
                            }.map {
                                it to it.toTime()
                            }.sortedBy { it.first }
                    )
                }
            }
            ready.removeIf { it.second == 0 }
            time++
        }
        return time
    }


    fun findNext(requirements: Map<Char, Set<Char>>, soFar: String): List<Char> {
        return requirements.filterNot {
            soFar.contains(it.key)
        }.filter {
            it.value.all { v -> soFar.contains(v) }
        }.map {
            it.key
        }
    }

    fun Char.toTime(): Int {
        return this.code - 4
    }

    tailrec fun findPath(requirements: Map<Char, Set<Char>>, soFar: String = ""): String {
        val next = nextStep(requirements, soFar)
        return if (next == null) soFar else findPath(requirements, soFar + next)
    }

    private fun nextStep(requirements: Map<Char, Set<Char>>, soFar: String): Char? {
        return findNext(requirements, soFar).minOrNull()
    }


    private fun allPairs(requirements: List<String>): List<Pair<Char, Char>> = requirements.map { row ->
        row.split(" ").run { this[1].first() to this[7].first() }
    }

    private fun generateDependencies(input: List<Pair<Char, Char>>): Map<Char, Set<Char>> {
        return input.groupBy { it.first }.mapValues { (_, value) -> value.map { it.second }.toSet() }
    }


    private fun parseReq(requirements: List<String>): Map<Char, Set<Char>> {
        val dependencies = requirements.asSequence().mapNotNull(dependency::matchEntire)
                .map { it.groupValues }
                .map { Requirement(it[1][0], it[2][0]) }
                .groupBy { it.downstream }
                .mapValues { it.value.fold(setOf<Char>()) { acc, dep -> acc + dep.prereq } }
        val steps = dependencies.values.flatten().union(dependencies.keys)
        return steps.map { Pair(it, dependencies[it] ?: emptySet()) }.toMap()
    }


}

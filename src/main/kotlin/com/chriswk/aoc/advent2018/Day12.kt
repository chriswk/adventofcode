package com.chriswk.aoc.advent2018

object Day12 {
    fun part1(input: List<String>): Int {
        val (pots, rules) = parseInput(input)
        return (1..20).fold(pots) { state, _ ->
            step(state, rules)
        }.fold(0) { sumSoFar, pot -> if (pot.hasPlant) sumSoFar + pot.idx else sumSoFar }
    }

    fun part2(input: List<String>): Long {
        val (pots, rules) = parseInput(input)
        var generation = 0
        var previous = pots
        var current = previous
        do {
            previous = current
            current = step(previous, rules)
            generation++
        } while (previous.map { it.hasPlant } != current.map { it.hasPlant } )
        val changes = current.first().idx - previous.first().idx
        val generationsLeft = 5e10.toLong() - generation
        val change = generationsLeft * changes
        return current.fold(0L) { totalSum, pot ->
            if (pot.hasPlant) { pot.idx + change + totalSum }
            else totalSum
        }
    }

    fun parseInput(input: List<String>): Pair<List<Pot>, List<Rule>> {
        val pots = input.first().substringAfter("initial state: ").mapIndexed { i, c -> Pot(i, c == '#')}
        val rules = input.drop(2).map { r ->
            val neighbourList = r.take(5).map { it == '#' }
            val result = r.last() == '#'
            Rule(neighbourList, result)
        }
        return pots to rules
    }

    fun step(currentPods: List<Pot>, rules: List<Rule>): List<Pot> {
        val newGeneration = expandGeneration(currentPods).windowed(5, 1).map { looking ->
            val actual = looking.map { it.hasPlant }
            val hasPlant = rules.firstOrNull { it.mask == actual }?.result ?: false
            Pot(looking[2].idx, hasPlant)
        }
        return newGeneration.compress()
    }

    fun List<Pot>.compress(): List<Pot> {
        return this.dropWhile { !it.hasPlant }.dropLastWhile { !it.hasPlant }
    }

    fun expandGeneration(currentGeneration: List<Pot>): List<Pot> {
        val currentStart = currentGeneration.first().idx
        val currentEnd = currentGeneration.last().idx
        return (currentStart - 5 until currentStart).map { Pot(it) } + currentGeneration + (currentEnd+1..currentEnd+5).map { Pot(it) }
    }



    data class Pot(val idx: Int, val hasPlant: Boolean = false)
    data class Rule(val mask: List<Boolean>, val result: Boolean)
}
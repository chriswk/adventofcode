package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day14: AdventDay(2021, 14) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day14()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun parseInput(input: List<String>): Pair<String, Map<String, List<String>>> {
        val template = input.first()
        val transformations = input.drop(2).associate { r ->
            val (from, to) = r.split(" -> ")
            from to listOf("${from.first()}$to", "$to${from.last()}")
        }
        return template to transformations
    }

    fun score(template: String, transformations: Map<String, List<String>>, steps: Int): Long {
        var state = template.zip(template.drop(1)) { a, b -> "$a$b" }.groupingBy { it }.eachCount().mapValues { it.value.toLong() }
        repeat(steps) {
            state = buildMap {
                state.forEach { (k, n) ->
                    transformations.getValue(k).forEach { dst ->
                        put(dst, getOrElse(dst) { 0 } + n )
                    }
                }
            }
        }
        val counts = buildMap<Char, Long> {
            put(template.first(), 1)
            put(template.last(), getOrElse(template.last()) { 0 } + 1)
            state.forEach { (pair, count) ->
                pair.forEach { element ->
                    put(element, getOrElse(element) { 0 } + count)
                }
            }
        }
        return (counts.values.maxOrNull()!! - counts.values.minOrNull()!!) / 2
    }


    fun part1(): Long {
        val (template, trans) = parseInput(inputAsLines)
        return score(template, trans, 10)
    }

    fun part2(): Long {
        val (template, trans) = parseInput(inputAsLines)
        return score(template, trans, 40)
    }

}

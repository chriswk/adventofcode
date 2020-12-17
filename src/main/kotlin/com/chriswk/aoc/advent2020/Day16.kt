package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day16 : AdventDay(2020, 16) {
    val rangesReg = """(\d+)-(\d+)""".toRegex()

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day16()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun findValidRanges(input: List<String>): Map<String, Sequence<IntRange>> {
        return input.map {
            val key = it.substringBefore(":")
            val ranges = rangesReg.findAll(it).map {
                IntRange(it.groupValues[1].toInt(), it.groupValues[2].toInt())
            }
            key to ranges
        }.toMap()
    }

    fun parseRanges(input: String): Map<String, Sequence<IntRange>> {
        val ranges = input.substringBefore("your ticket").trim().lines()
        return findValidRanges(ranges)
    }

    fun parseTickets(input: String): List<Ticket> {
        return input.substringAfter("nearby tickets:").trim().lines().map { Ticket(it) }
    }

    fun solvePart1(input: String): Int {
        val rules = parseRanges(input).values.asSequence().flatten()
        val tickets = parseTickets(input)
        return tickets.flatMap { it.invalidNumbers(rules) }.sum()
    }

    fun findProductOfDepartureCols(placements: Map<String, Int>, ticket: Ticket): Long {
        return placements.entries.filter { it.key.startsWith("departure") }.map { (k, v) ->
            ticket.numbers[v]
        }.product()
    }

    fun solvePart2(input: String): Long {
        val rules = parseRanges(input)
        val myTicket = Ticket(input.substringAfter("your ticket:").lines().drop(1).first())
        val tickets = parseTickets(input).filter { it.isValid(rules.values.asSequence().flatten()) }
        val rulesToPlace = performIntersection(rules, myTicket, tickets)
        val placements = findMinima(rulesToPlace)
        return findProductOfDepartureCols(placements, myTicket)
    }

    fun findMinima(rulesToPlace: List<Set<String>>): Map<String, Int> {
        val map = rulesToPlace.mapIndexed { idx, e -> idx to e }.toMap()
        return map.entries.sortedBy { it.value.size }.fold(emptyMap<String, Int>()) { r, rule ->
            val remainingKeys = rule.value - r.keys
            r.plus(remainingKeys.first() to rule.key)
        }
    }

    fun performIntersection(
        rules: Map<String, Sequence<IntRange>>,
        myTicket: Ticket,
        tickets: List<Ticket>
    ): List<Set<String>> {
        return tickets.fold(myTicket.validRules(rules)) { ruleSet, ticket ->
            ticket.validRules(rules).mapIndexed { idx, otherRuleset ->
                otherRuleset.intersect(ruleSet[idx])
            }
        }
    }

    fun part1(): Int {
        return solvePart1(inputAsString)
    }

    fun part2(): Long {
        return solvePart2(inputAsString)
    }

    data class Ticket(val numbers: List<Int>) {
        constructor(ticketString: String) : this(
            numbers = ticketString.split(",").map { it.toInt() }
        )

        fun invalidNumbers(validRanges: Sequence<IntRange>): List<Int> {
            return numbers.filter { ticketNumber -> validRanges.none { ticketNumber in it } }
        }

        fun isValid(validRanges: Sequence<IntRange>): Boolean {
            return numbers.all { ticketNumber -> validRanges.any { ticketNumber in it } }
        }

        fun validRules(rules: Map<String, Sequence<IntRange>>): List<Set<String>> {
            return numbers.map { number ->
                rules.entries.filter {
                    it.value.any { number in it }
                }.map { it.key }.toSet()
            }
        }
    }
}

fun List<Int>.product(): Long = this.fold(1) { acc, e -> acc * e }
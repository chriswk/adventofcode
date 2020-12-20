package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day19 : AdventDay(2020, 19) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>): Unit {
            val day = Day19()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun parseRules(rules: List<String>): MutableMap<Int, List<List<Rule>>> {
        return rules.takeWhile { it.isNotBlank() }.map { ruleLine ->
            val (id, rhs) = ruleLine.split(": ")
            val sides = rhs.split(" | ")
            id.toInt() to sides.map { side ->
                side.split(' ').map { part ->
                    if (part.startsWith('"')) {
                        Atom(part[1])
                    } else {
                        Reference(part.toInt())
                    }
                }
            }
        }.toMap().toMutableMap()
    }

    fun validMessages(messages: List<String>, ruleSet: MutableMap<Int, List<List<Rule>>>): Int {
        return messages.count { message -> message.validForRule(0, 0, ruleSet).any { it == message.length }}
    }

    fun part1(): Int {
        val rules = parseRules(inputAsLines)
        val messages = inputAsLines.dropWhile { it.isNotBlank() }.drop(1)
        return validMessages(messages, rules)
    }

    fun part2(): Int {
        val rules = parseRules(inputAsLines)
        rules[8] = listOf(
            listOf(Reference(42)),
            listOf(Reference(42), Reference(8))
        )
        rules[11] = listOf(
            listOf(Reference(42), Reference(31)),
            listOf(Reference(42), Reference(11), Reference(31))
        )
        return validMessages(inputAsLines.dropWhile { it.isNotBlank() }.drop(1), rules)
    }

}
sealed class Rule {
}
data class Reference(val id: Int): Rule()
data class Atom(val symbol: Char): Rule()

fun String.validForRule(ruleId: Int, position: Int = 0, ruleMap: Map<Int, List<List<Rule>>>): List<Int> {
    return ruleMap.getValue(ruleId).flatMap { rules ->
        var validPositions = listOf(position)
        rules.forEach { rule ->
            validPositions = validPositions.mapNotNull { idx ->
                when {
                    rule is Atom && getOrNull(idx) == rule.symbol ->
                        listOf(idx+1)
                    rule is Reference ->
                        validForRule(rule.id, idx, ruleMap)
                    else ->
                        null
                }
            }.flatten()
        }
        validPositions
    }
}
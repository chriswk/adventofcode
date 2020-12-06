package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day6 : AdventDay(2020,6) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day6()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun toAnswers(group: String): Int {
        val answers = group.lines().map { it.toSet() }
        return answers.fold(answers.first()) { ans, member ->
            ans.union(member)
        }.size
    }

    fun toUnanimousAnswers(group: String): Int {
        val answers = group.lines().map { it.toSet() }
        return answers.fold(answers.first()) { unanimous, member ->
            unanimous.intersect(member)
        }.size
    }

    fun part1(): Int {
        return inputAsString.split("\n\n".toRegex()).map { toAnswers(it) }.sum()
    }

    fun part2(): Int {
        return inputAsString.split("\n\n".toRegex()).map { toUnanimousAnswers(it) }.sum()
    }
}
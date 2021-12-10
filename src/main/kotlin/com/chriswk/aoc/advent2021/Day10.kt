package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day10: AdventDay(2021, 10) {
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
        val errors = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
        val closers = mapOf('(' to ')', '{' to '}', '[' to ']', '<' to '>')
        val completion = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)
    }
    fun handleLine(line: String): Pair<ArrayDeque<Char>, Int> {
        val stack = ArrayDeque<Char>()
        line.forEach {
            when(it) {
                '(', '{', '[', '<' -> stack.addFirst(closers[it]!!)
                ')', '}', ']', '>' -> {
                    val expectedClose = stack.firstOrNull()?: ' '
                    if (it == expectedClose) {
                        stack.removeFirst()
                    } else {
                        return stack to errors[it]!!
                    }
                }
            }
        }
        return stack to 0
    }

    fun errorScore(lines: List<String>): Int {
        return lines.sumOf { handleLine(it).second }
    }

    fun completionScore(line: String): Long {
        val (missing, errors) = handleLine(line)
        return if (errors == 0) {
            missing.fold(0) { soFar, char ->
                soFar * 5 + completion[char]!!
            }
        } else {
            0
        }
    }

    fun completionScores(lines: List<String>): List<Long> {
        return lines.map { completionScore(it) }.filter { it > 0 }
    }

    fun middleCompletionScore(lines: List<String>): Long {
        val scores = completionScores(lines).sorted()
        return scores[scores.size / 2]
    }

    fun part1(): Int {
        return errorScore(inputAsLines)
    }

    fun part2(): Long {
        return middleCompletionScore(inputAsLines)
    }

}

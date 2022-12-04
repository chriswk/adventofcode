package com.chriswk.aoc.advent2022

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.intersects
import com.chriswk.aoc.util.report

class Day4: AdventDay(2022, 4) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day4()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun parseElf(elf: String): IntRange {
        val (from, to) = elf.split("-")
        return (from.toInt()..(to.toInt()))
    }
    fun parseAssignment(assignment: String): Pair<IntRange, IntRange> {
        val (elf1, elf2) = assignment.split(",")
        return parseElf(elf1) to parseElf(elf2)
    }

    fun parseInput(assignments: List<String>): List<Pair<IntRange, IntRange>> {
        return assignments.map { parseAssignment(it) }
    }

    fun findOverlaps(assignments: List<Pair<IntRange, IntRange>>): Int {
        return assignments.count { overlaps(it) }
    }

    fun overlaps(elves: Pair<IntRange, IntRange>): Boolean {
        val (elf1, elf2) = elves

        return if (elf1.first < elf2.first) {
            elf1.last >= elf2.last
        } else if (elf1.first > elf2.first) {
            elf2.last >= elf1.last
        } else {
            true
        }
    }

    fun part1(): Int {
        return findOverlaps(parseInput(inputAsLines))
    }

    fun part2(): Int {
        return parseInput(inputAsLines).count { it.first.intersects(it.second) }
    }

}

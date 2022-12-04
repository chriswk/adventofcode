package com.chriswk.aoc.advent2022

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day3: AdventDay(2022, 3) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day3()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }

    }
    val priorityMap = ('a'..'z').flatMap { letter ->
        val lowerCase = letter to letter.code - 96
        val upperchar = letter.uppercaseChar()
        val upperCase = upperchar to upperchar.code - 38
        listOf(lowerCase, upperCase)
    }.toMap()

    fun findIntersectionPriorities(input: String): List<Int> {
        val half = input.length / 2
        val (firstCompartment, secondCompartment) = input.chunked(half).map { it.toSet() }
        return firstCompartment.intersect(secondCompartment).map { priorityMap[it]!! }
    }

    fun inputToPriorities(input: List<String>): List<Int> {
        return input.map { findIntersectionPriorities(it).sum() }
    }
    fun badge(list: List<String>): Set<Char> {
        val (elf, elf2, elf3) = list
        return elf.toSet().intersect(elf2.toSet()).intersect(elf3.toSet())
    }

    fun badgeSum(badges: List<String>): Int {
        return badges.chunked(3).flatMap { badge(it) }.sumOf { priorityMap[it]!! }
    }
    fun part1(): Int {
        return inputToPriorities(inputAsLines).sum()
    }

    fun part2(): Int {
        return badgeSum(inputAsLines)
    }



}

package com.chriswk.aoc.advent2022

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
class Day1: AdventDay(2022, 1) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>): Unit {
            val day = Day1()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }


    val inputAsInts = parseInput(inputAsLines)

    fun parseInput(input: List<String>): List<Int> {
        return input.fold((mutableListOf<Int>() to 0)) { (elves, elf), e ->
            if (e == "") {
                elves.add(elf)
                elves to 0
            } else {

                elves to elf + e.toInt(10)
            }
        }.first.toList()
    }


    fun maxCalories(elves: List<Int>): Int {
        return elves.max()
    }

    fun maxCaloriesByTopThree(elves: List<Int>): Int {
        return elves.sortedDescending().take(3).sum()
    }
    fun part1(): Int {
        return maxCalories(inputAsInts)
    }

    fun part2(): Int {
        return maxCaloriesByTopThree(inputAsInts)
    }
}

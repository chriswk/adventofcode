package com.chriswk.aoc.advent2023

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day1: AdventDay(2023, 1) {
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

    fun part1(): Int {
        return inputAsLines.map { line ->
            val digits = line.filter { it.isDigit() }.map { it.digitToInt() }
            digits.first() * 10 + digits.last()
        }.sum()
    }

    fun part2(): Int {
         return inputAsLines.map { line ->
             val digits = line
                .replace("one", "one1one")
                .replace("two", "two2two")
                .replace("three", "three3three")
                .replace("four", "four4four")
                .replace("five", "five5five")
                .replace("six", "six6six")
                .replace("seven", "seven7seven")
                .replace("eight", "eight8eight")
                .replace("nine", "nine9nine")
                .filter { it.isDigit() }
                .map{ it.digitToInt() }
            digits.first() *10 + digits.last()
        }.sum()
    }
}
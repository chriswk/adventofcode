package com.chriswk.aoc.`2020`

import java.math.BigInteger

class Day1 {
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

            report {
                day.part2ByCombinations()
            }
        }
    }

    fun findPair(goal: Int, options: Set<Int>): Pair<Int,Int>? {
        return options.mapNotNull { a ->
            if(options.contains(goal-a)) {
                a to (goal-a)
            } else {
                null
            }
        }.firstOrNull()
    }
    fun findTriplet(goal: Int, options:Set<Int>): Triple<Int, Int, Int>? {
        return options.asSequence().mapNotNull { a ->
            val pair = findPair(goal - a, options-a)
            if (pair != null) {
                Triple(a, pair.first, pair.second)
            } else {
                null
            }
        }.firstOrNull()
    }
    fun findByCombinations(goal: Int, options: Set<Int>): List<Int> {
        return options.combinations(3).first { (a,b,c) -> a+b+c == goal }
    }


    fun part1(): Int {
        val numbers = "day1.txt".fileToLines().map { it.toInt() }.toSet()
        val (a,b) = findPair(2020, numbers)!!
        return a*b
    }

    fun part2(): Int {
        val numbers = "day1.txt".fileToLines().map { it.toInt() }.toSet()
        return findTriplet(2020, numbers)?.let { (a,b,c) ->
            a*b*c
        } ?: 0

    }

    fun part2ByCombinations(): Int {
        val numbers = "day1.txt".fileToLines().map { it.toInt() }.toSet()
        val ans = findByCombinations(2020, numbers)
        return ans.fold(1) { acc, e -> acc*e }

    }
}
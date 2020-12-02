package com.chriswk.aoc.`2020`

class Day1 {
    companion object {
        @JvmStatic
        fun main(args: Array<String>): Unit {
            val day = Day1()
            val numbers = "day1.txt".fileToLines().map { it.toInt() }.sorted()
            report {
                day.part1(numbers)
            }
            report {
                day.part2(numbers)
            }
            report {
                day.part2ByCombinations(numbers)
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
    fun findByCombinations(goal: Int, options: List<Int>): List<Int> {
        return options.combinations(3).first { (a,b,c) -> a+b+c == goal }
    }


    fun part1(numbers: List<Int>): Int {
        val (a,b) = findPair(2020, numbers.toSortedSet())!!
        return a*b
    }

    fun part2(numbers: List<Int>): Int {
        return findTriplet(2020, numbers.toSortedSet())?.let { (a,b,c) ->
            a*b*c
        } ?: 0

    }

    fun part2ByCombinations(numbers: List<Int>): Int {
        val ans = findByCombinations(2020, numbers)
        return ans.fold(1) { acc, e -> acc*e }

    }

    fun fmrPartOne(): Int = fmrFindTarget("day1.txt".fileToLines().map { it.toInt() }, 2020)!!

    private fun fmrFindTarget(expences: List<Int>, target: Int): Int? {
        return expences
            .firstOrNull { expences.contains(target - it) }
            ?.let { (target - it) * it }
    }

    fun fmrPartTwo(): Int {
        val expenses = "day1.txt".fileToLines().map { it.toInt() }
        return expenses
            .map { it to fmrFindTarget(expenses, 2020 - it) }
            .first { it.second != null }
            .let { it.first * it.second!! }
    }
}
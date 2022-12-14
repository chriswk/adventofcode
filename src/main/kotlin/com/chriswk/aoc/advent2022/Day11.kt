package com.chriswk.aoc.advent2022

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day11: AdventDay(2022, 11) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day11()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }


    fun parseInput(input: List<String>): List<Monkey> {
        return input.chunked(7).map { monkey ->
            val id = monkey.first().substringAfter(" ").substringBefore(":").toInt()
            val items = monkey[1].substringAfter("Starting items: ").split(", ").map { it.toLong() }
            val operationText = monkey[2].substringAfter("Operation: new = old").trim()
            val (op, opValue) = operationText.split(" ")
            val test = monkey[3].substringAfter("Test: divisible by ").toInt()
            val trueMonkey = monkey[4].substringAfter("If true: throw to monkey ").substringBefore("\n").toInt()
            val falseMonkey = monkey[5].substringAfter("If false: throw to monkey ").substringBefore("\n").toInt()
            val operation = { old: Long, divisor: Int, modNumber: Int ->
                val v = if (opValue == "old") old else opValue.toLong()
                val newValue = when (op) {
                    "*" -> old * v
                    "+" -> old + v
                    else -> error("only + and * implemented")
                }
                newValue / divisor % modNumber
            }
            val action = { newValue: Long -> if (newValue.mod(test) == 0) { trueMonkey } else { falseMonkey } }
            Monkey(id = id, items = ArrayDeque(items), test, operation, action)
        }
    }

    fun part1(): Long {
        return parseInput(inputAsLines).rounds(20, 3).monkeyBusiness()
    }

    fun part2(): Long {
        return parseInput(inputAsLines).rounds(10000, 1).monkeyBusiness()
    }

    data class Monkey(val id: Int, val items: ArrayDeque<Long>, val modNumber: Int, val operation: (Long, Int, Int) -> Long, val next: (Long) -> Int) : MutableList<Long> by items {
        var count = 0
        fun inspectAndCount(old: Long, divisor: Int, modNumber: Int): Long {
            count++
            return operation(old, divisor, modNumber)
        }
    }
}
fun List<Day11.Monkey>.rounds(rounds: Int, divisor: Int): List<Day11.Monkey> {
    val modNumber = asSequence().map { monkey -> monkey.modNumber }.reduce(Int::times)
    repeat(rounds) { round(divisor, modNumber) }
    return this
}


fun List<Day11.Monkey>.round(divisor: Int, modNumber: Int) {
    forEach { monkey ->
        monkey.removeAll { item ->
            val newValue = monkey.inspectAndCount(item, divisor, modNumber)
            val newMonkey = monkey.next(newValue)
            get(newMonkey).add(newValue)
        }
    }
}
fun List<Day11.Monkey>.monkeyBusiness() = asSequence().map { it.count }.sortedDescending().take(2).map { it.toLong() }.reduce(Long::times)

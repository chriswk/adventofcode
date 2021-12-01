package com.chriswk.aoc.advent2015

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day12 : AdventDay(2015, 12) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day12()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    val numbersRegex = """-?\d+""".toRegex()
    fun part1(): Int {
        return numbersRegex.findAll(inputAsString).map { it.value.toInt() }.sum()
    }

    fun part2(): Int {
        val jsonObject = Parser.default().parse(inputAsString.reader()) as JsonObject
        return jsonObject.sumJson()
    }

    fun Any?.sumJson(): Int = when (this) {
        is Int -> this
        is JsonArray<*> -> map { it.sumJson() }.sum()
        is JsonObject -> if (containsValue("red")) 0 else map { it.value.sumJson() }.sum()
        else -> 0
    }


}

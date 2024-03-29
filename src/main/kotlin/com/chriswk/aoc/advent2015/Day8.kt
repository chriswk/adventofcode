package com.chriswk.aoc.advent2015

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import org.apache.logging.log4j.LogManager

class Day8 : AdventDay(2015, 8) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day8()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
        val logger = LogManager.getLogger(Day8::javaClass)
    }

    fun part1String(input: String): Pair<Int, Int> {
        return input.length to encode(input).length
    }
    fun part2String(input: String): Pair<String, String> {
        return input to decode(input)
    }

    val hexChar = """\\x([\da-f][\da-f])""".toRegex()
    val otherEscapes = """\\(["\\])""".toRegex()

    fun encode(input: String): String {
        return input.drop(1).dropLast(1).replace(hexChar) { r ->
            "" + r.groupValues[1].toInt(16).toChar()
        }.replace(otherEscapes) { r ->
            "" + r.groupValues[1]
        }
    }

    fun decode(input: String): String {
        return "-${input.replace(quotes, "__")}-"
    }

    fun parseMultiple(input: List<String>): List<Pair<Int, Int>> {
        return input.map { part1String(it) }
    }
    fun parseMultiplePart2(input: List<String>): List<Pair<String, String>> {
        return input.map { part2String(it) }
    }

    fun part1(): Int {
        val i = parseMultiple(inputAsLines)
        return i.sumOf { it.first - it.second }
    }

    val quotes = Regex("""(")|(\\)""")
    fun part2(): Int {
        val i = parseMultiplePart2(inputAsLines)
        return i.sumOf { it.second.length - it.first.length }
    }

}

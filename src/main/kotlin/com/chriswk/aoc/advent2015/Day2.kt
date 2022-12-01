package com.chriswk.aoc.advent2015

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import kotlin.math.min

class Day2 : AdventDay(2015, 2) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day2()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun part1(): Int {
        return inputAsLines.map { Gift.fromSpec(it) }.sumOf { it.wrappingPaper }
    }

    fun part2(): Int {
        return inputAsLines.map { Gift.fromSpec(it) }.sumOf { it.ribbon() }
    }

    data class Gift(val length: Int, val width: Int, val height: Int) {
        val area = 2 * length * width + 2 * width * height + 2 * height * length
        val slack = min(height * width, min(length * width, length * height))
        val wrappingPaper = area + slack
        val faces = listOf(2 * (length + width), 2 * (width + height), 2 * (length + height))
        val sides = length * width * height
        fun ribbon(): Int {
            return faces.minOrNull()!! + sides
        }
        companion object {
            fun fromSpec(spec: String): Gift {
                val (l, w, h) = spec.split("x").map { it.toInt() }
                return Gift(l, w, h)
            }
        }
    }
}

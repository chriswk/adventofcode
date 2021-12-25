package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day24: AdventDay(2021, 24) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day24()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    val blocks = inputAsLines.chunked(18)

    fun List<String>.lastOf(command: String) = last { it.startsWith(command) }.split(" ").last().toInt()

    fun solve(min: Boolean = true, input: List<List<String>> = blocks): Long {
        val result = MutableList(14) { -1 }
        val buffer = ArrayDeque<Pair<Int, Int>>()
        val best = if (min) 1 else 9
        blocks.forEachIndexed { idx, instructions ->
            if ("div z 26" in instructions) {
                val offset = instructions.lastOf("add x")
                val (lastIndex, lastOffset) = buffer.removeFirst()
                val diff = offset + lastOffset
                if (diff >= 0) {
                    result[lastIndex] = if (min) best else best - diff
                    result[idx] = if (min) best + diff else best
                } else {
                    result[lastIndex] = if (min) best - diff else best
                    result[idx] = if (min) best else best + diff
                }
            } else {
                buffer.addFirst(idx to instructions.lastOf("add y"))
            }
        }
        return result.joinToString("").toLong()
    }

    fun part1(): Long {
        return solve(false)
    }

    fun part2(): Long {
        return solve()
    }

}

package com.chriswk.aoc.advent2018

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.Pos
import com.chriswk.aoc.util.report

class Day20 : AdventDay(2018, 20) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day20()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun parse(input: String, start: Pos = Pos(0, 0)): MutableMap<Pos, Int> {
        val map = mutableMapOf(start to 0)
        val detours = ArrayDeque<Pos>()
        var current = start
        input.forEach {
            when(it) {
                '(' -> detours.addFirst(current)
                ')' -> current = detours.removeFirst()
                '|' -> current = detours.first()
                '^' -> println("start")
                '$' -> println("finish")
                else -> {
                    val nextDistance = map.getValue(current) + 1
                    current = current.next(it)
                    map[current] = minOf(map.getOrDefault(current, Integer.MAX_VALUE), nextDistance)
                }
            }
        }
        return map
    }

    fun part1(): Int {
        return parse(inputAsString).maxByOrNull { it.value }?.value ?: -1
    }
    fun part2(): Int {
        return parse(inputAsString).count { it.value >= 1000 }
    }
}

fun String.isDirection() = this.all { it.isDirection() }
fun Char.isDirection() = this in setOf('N', 'E', 'S', 'W')
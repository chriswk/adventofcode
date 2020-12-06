package com.chriswk.aoc.advent2015

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.Pos
import com.chriswk.aoc.util.report

class Day3: AdventDay(2015, 3) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day3()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }
    fun performMovesPart1(moves: String): Set<Pos> {
        return moves.runningFold(Pos(0, 0)) { last, move ->
            last.next(move)
        }.toSet()
    }
    fun performMovesPart2(instructions: String): Set<Pos> {
        return instructions.chunked(2).runningFold(listOf(Pos(0, 0), Pos(0,0))) { givers, moves ->
            givers.mapIndexed { i, g ->
                g.next(moves.getOrNull(i))
            }
        }.flatten().toSet()
    }

    fun part1(): Int {
        return performMovesPart1(inputAsString).size
    }

    fun part2(): Int {
        return performMovesPart2(inputAsString).size
    }

}
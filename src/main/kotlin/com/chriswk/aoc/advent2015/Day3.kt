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
        return moves.fold(setOf(Pos(0,0)) to Pos(0,0)) { (visited, prev), move ->
            val next = prev.next(move)
            visited + next to next
        }.first
    }
    fun performMovesPart2(instructions: String): Set<Pos> {
        val (moves, _, _) = instructions.chunked(2).fold(
            Triple(
                setOf(Pos(0, 0)),
                Pos(0, 0),
                Pos(0,0)
            )
        ) { (houses, santa, robo), inst ->
            val newSanta = santa.next(inst[0])
            val newRobo = inst.getOrNull(1)?.let { robo.next(it) } ?: robo
            Triple(houses + newSanta + newRobo, newSanta, newRobo)
        }
        return moves
    }

    fun part1(): Int {
        return performMovesPart1(inputAsString).size
    }

    fun part2(): Int {
        return performMovesPart2(inputAsString).size
    }

}
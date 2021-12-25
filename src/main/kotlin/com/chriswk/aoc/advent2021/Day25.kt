package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.Pos
import com.chriswk.aoc.util.report

class Day25 : AdventDay(2021, 25) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day25()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }



    fun parseInput(input: List<String>): Seabottom {
        return Seabottom(cucumbers = input.flatMapIndexed { y, l ->
            l.mapIndexedNotNull { x, c ->
                if (c == '.') {
                    null
                } else if (c == 'v') {
                    Pos(x, y) to Cucumber(Pos(x, y), Direction.SOUTH)
                } else if (c == '>') {
                    Pos(x, y) to Cucumber(Pos(x, y), Direction.EAST)
                } else {
                    null
                }
            }
        }.toMap(), maxX = input[0].length, maxY = input.size)
    }

    fun makeMoves(initial: Seabottom): Sequence<Seabottom> {
        return generateSequence(initial) { prev ->
            prev.move()
        }
    }

    data class Cucumber(val pos: Pos, val facing: Direction)

    data class Seabottom(
        val cucumbers: Map<Pos, Cucumber>,
        val movesLastGeneration: Int = 0,
        val generation: Int = 0,
        val maxX: Int,
        val maxY: Int
    ) {
        fun move(): Seabottom {
            return moveEast().moveSouth()
        }

        private fun moveEast(): Seabottom {
            val notMoving =
                cucumbers.values.filter { it.facing == Direction.SOUTH || cucumbers.containsKey(it.pos.east(maxX)) }.toSet()
            val moving = cucumbers.values.toSet() - notMoving
            val moved = moving.map { it.copy(pos = it.pos.east(maxX)) }.toSet()
            val newCucumbers = (moved + notMoving).associateBy { it.pos }
            return copy(cucumbers = newCucumbers, movesLastGeneration = moved.size)
        }

        private fun moveSouth(): Seabottom {
            val notMoving =
                cucumbers.values.filter { it.facing == Direction.EAST || cucumbers.containsKey(it.pos.south(maxY)) }.toSet()
            val moving = cucumbers.values.toSet() - notMoving
            val moved = moving.map { it.copy(pos = it.pos.south(maxY)) }.toSet()
            val newCucumbers = (moved + notMoving).associateBy { it.pos }
            return copy(cucumbers = newCucumbers, movesLastGeneration = movesLastGeneration + moved.size, generation = generation + 1)
        }

    }

    enum class Direction {
        EAST,
        SOUTH
    }

    fun stopsMoving(input: List<String>): Int {
        return makeMoves(parseInput(input)).dropWhile { it.movesLastGeneration > 0 || it.generation == 0 }.first().generation
    }

    fun part1(): Int {
        return stopsMoving(inputAsLines)
    }

    fun part2(): Int {
        return 0
    }
}

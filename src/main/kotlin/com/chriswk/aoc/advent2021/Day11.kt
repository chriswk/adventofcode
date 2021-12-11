package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.Pos
import com.chriswk.aoc.util.report

class Day11 : AdventDay(2021, 11) {
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

    fun parseInput(lines: List<String>): MutableMap<Pos, Int> {
        return lines.flatMapIndexed { y, line ->
            line.mapIndexed { x, level ->
                Pos(x, y) to Character.getNumericValue(level)
            }
        }.toMap().toMutableMap()
    }


    fun step(grid: Grid): Sequence<Grid> {
        return generateSequence(grid) { p ->
            p.step()
        }
    }

    fun allFlashingGeneration(grid: Grid): Int {
        return step(grid).first { it.allFlashing }.generation
    }

    fun steps(grid: Grid, days: Int): Grid {
        return step(grid).drop(days).first()
    }

    data class Grid(val octopi: MutableMap<Pos, Int>, val maxX: Int, val maxY: Int, val flashCount: Int = 0, val generation: Int = 0, val allFlashing: Boolean = false) {
        val size = octopi.size
        fun step(): Grid {
            octopi.keys.forEach { k -> octopi[k] = octopi.getValue(k) + 1 }
            flash(emptySet()) // flash excited octopi
            val flashedThisGen = octopi.values.count { it > 9 }
            octopi.entries.filter { it.value > 9 }.forEach { (k, _) ->
                octopi[k] = 0
            }
            return copy(flashCount = flashCount + flashedThisGen, generation = generation + 1, allFlashing = flashedThisGen == size)
        }

        fun flash(visited: Set<Pos>): Set<Pos> {
            val flashingOctopi = octopi.keys.filter { !visited.contains(it) && octopi.getValue(it) > 9 }
            val flashedOctopi = flashingOctopi.flatMap { k -> k.neighbours().filter { it.isInGrid(maxX, maxY) } }
            flashedOctopi.forEach {
                octopi[it] = octopi.getValue(it) + 1
            }
            return if (flashingOctopi.isEmpty()) {
                visited
            } else {
                flash(visited.union(flashingOctopi))
            }
        }

        override fun toString(): String {
            return (0.until(maxX)).joinToString("\n") { x ->
                (0.until(maxY)).joinToString("") { y ->
                    "${octopi[Pos(x, y)]!!}"
                }
            }
        }
    }


    fun part1(): Int {
        val octopuses = parseInput(inputAsLines)
        val grid = Grid(octopuses, inputAsLines.size, inputAsLines[0].length)
        return steps(grid, 100).flashCount
    }

    fun part2(): Int {
        val octopuses = parseInput(inputAsLines)
        val grid = Grid(octopuses, inputAsLines.size, inputAsLines[0].length)
        return allFlashingGeneration(grid)
    }

}

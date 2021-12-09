package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.Pos
import com.chriswk.aoc.util.report

class Day9 : AdventDay(2021, 9) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day9()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun parseInput(input: List<String>): Array<IntArray> {
        return input.map { line -> line.map { it.digitToInt() }.toIntArray() }.toTypedArray()
    }


    fun findMinima(grid: Array<IntArray>): List<Point> {
        val maxRow = grid.size
        val maxCol = grid[0].size

        return grid.flatMapIndexed { x, inner ->
            inner.mapIndexed { y, height ->
                Point(Pos(x, y), height)
            }
        }.filter { it.isMinimal(grid, maxRow, maxCol) }
    }

    fun computeBasins(minima: List<Point>, grid: Array<IntArray>): List<Set<Point>> {
        val maxRow = grid.size
        val maxCol = grid[0].size
        return minima.map { basin(it, grid, maxRow, maxCol) }
    }

    fun basin(p: Point, grid: Array<IntArray>, maxRow: Int, maxCol: Int): Set<Point> {
        return p.neighbours(grid, maxRow, maxCol).fold(setOf(p)) { points, point ->
            points + if (point.height - p.height >= 1) { basin(point, grid, maxRow, maxCol) } else { emptySet() }
        }
    }


    data class Point(val pos: Pos, val height: Int) {
        val risk: Int = height + 1
        fun isMinimal(grid: Array<IntArray>, maxRow: Int, maxCol: Int): Boolean {
            val all = pos.cardinalNeighbours(maxRow, maxCol).map { Point(it, grid[it.x][it.y]) } + this
            val sorted = all.sortedBy { it.height }
            return sorted[0] == this && sorted[1].height > this.height
        }

        fun neighbours(grid: Array<IntArray>, maxRow: Int, maxCol: Int): List<Point> {
            return pos.cardinalNeighbours(maxRow, maxCol).filter { neighbour -> grid[neighbour.x][neighbour.y] < 9 }
                .map { n -> Point(n, grid[n.x][n.y]) }
        }
    }

    val grid = parseInput(inputAsLines)

    val minima = findMinima(grid)

    fun part1(): Int {
        return minima.sumOf { it.risk }
    }

    fun part2(): Int {
        return computeBasins(minima, grid)
            .sortedByDescending { it.size }
            .take(3)
            .fold(1) { acc, e -> acc * e.size }
    }

    fun Set<Int>.product() = this.fold(1) { acc, e -> acc * e }

}

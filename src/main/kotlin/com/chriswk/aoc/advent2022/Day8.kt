package com.chriswk.aoc.advent2022

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day8: AdventDay(2022, 8) {
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
    }

    fun parseInput(lines: List<String>): Pair<List<List<Int>>, List<List<Int>>> {
        val rows = lines.map { line -> line.map { it.digitToInt() }}
        val columns = List(lines.size) { m -> rows.map { it[m] } }
        return columns to rows
    }

    fun findVisible(columns: List<List<Int>>, rows: List<List<Int>>): Int {
        val visible = List(rows.size) { MutableList(columns.size) { false } }
        for (i in rows.indices) {
            for (j in columns.indices) {
                visible[i][j] = rows[i].visible(j) || columns[j].visible(i)
            }
        }
        return visible.sumOf { it.count { it }}
    }

    fun findScenicScore(columns: List<List<Int>>, rows: List<List<Int>>): Int {
        var scenicScore = -1
        rows.forEachIndexed { i, r ->
            columns.forEachIndexed { j, c ->
                scenicScore = maxOf(scenicScore, (i to j).scenicScore(r, c))
            }
        }
        return scenicScore
    }

    fun part1(): Int {
        val (columns, rows) = parseInput(inputAsLines)
        return findVisible(columns, rows)
    }

    fun part2(): Int {
        val (columns, rows) = parseInput(inputAsLines)
        return findScenicScore(columns, rows)
    }
    fun Pair<Int, Int>.scenicScore(row: List<Int>, column: List<Int>): Int {
        val right = row.scenicScore(second)
        val left = row.reversed().scenicScore(row.size - 1 - second)
        val down = column.scenicScore(first)
        val up = column.reversed().scenicScore(column.size - 1 - first)
        return right*left*up*down
    }
    fun List<Int>.scenicScore(i: Int): Int {
        val rest = drop(i + 1)
        return if (rest.none { it >= get(i) }) rest.count()
        else 1 + rest.takeWhile { it < get(i) }.count()
    }
    fun List<Int>.visible(i: Int) = drop(i + 1).none { it >= get(i) } || reversed().drop(size - i).none { it >= get(i) }
}

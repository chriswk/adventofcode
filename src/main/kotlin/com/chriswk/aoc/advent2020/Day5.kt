package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.binaryParser
import com.chriswk.aoc.util.report

class Day5 : AdventDay(2020, 5) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day5()
            report {
                day.part1()
            }
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }


    fun part1(): Int {
        return inputAsLines.map { toSeatId(it) }.maxOrNull() ?: -1
    }

    fun part2(): Int {
        val ids = inputAsLines.map { toSeatId(it) }.sorted()
        val min = ids.first()
        val max = ids.last()
        val idsSum = ids.sum()
        val totalSum = (max*(max+1))/2 - (min*(min-1))/2
        return totalSum - idsSum
    }

    fun toSeatId(placement: String): Int {
        return binaryParser(placement, setOf('F', 'L'), setOf('B', 'R'))
    }

    fun findRow(partition: String): Int {
        return binaryParser(partition, 'F' , 'B')
    }



    fun findColumn(partition: String): Int {
        return binaryParser(partition, 'L', 'R')
    }
}
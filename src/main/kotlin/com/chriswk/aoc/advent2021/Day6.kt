package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.SortedMap

class Day6: AdventDay(2021, 6) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day6()
            repeat(10) {
                println("chris")
                report {
                    day.part1()
                }
                report {
                    day.part2()
                }
                println("fmr")
                day.fmr()
            }
        }
        val logger: Logger = LogManager.getLogger()

    }

    fun Tally.day(n: Int): Tally {
        return step(this).drop(n).first()
    }

    fun initalSetup(input: String): Tally {
        return input.split(",").map { it.toInt() }.groupBy { it }.mapValues { it.value.size.toLong() }.toSortedMap()
    }

    fun tallyAfterDays(input: String, days: Int): Tally {
        return initalSetup(input).day(days)
    }

    private fun step(tally: Tally): Sequence<Tally> {
        return generateSequence(tally) { t ->
            val zeros = t[0] ?: 0
            val newTally = (0 ..7).associateWith { it ->
                t[it+1] ?: 0
            }.toSortedMap()

            newTally[8] = zeros
            newTally.merge(6, zeros) { old, new -> old + new } // Insert new sizes
            newTally
        }
    }

    fun fmr() {
        val fishes = inputAsString.split(",").map { it.toLong() }.groupingBy { it }.eachCount()
        val ar = LongArray(9) { fishes[it.toLong()]?.toLong() ?: 0L }
        val seq = generateSequence(ar) { it.cycle() }

        report {
            seq.drop(80).first().sum()
        }
        report {
            seq.drop(256).first().sum()
        }
    }

    private fun LongArray.cycle(): LongArray {
        return LongArray(this.size) { idx ->
            when(idx) {
                6 -> this[0] + this[7]
                8 -> this[0]
                else -> this[idx+1]
            }
        }
    }

    fun part1(): Long {
        return tallyAfterDays(inputAsString, 80).values.sum()
    }

    fun part2(): Long {
        return tallyAfterDays(inputAsString, 256).values.sum()
    }

}
typealias Tally = SortedMap<Int, Long>

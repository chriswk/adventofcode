package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import java.util.BitSet

class Day8 : AdventDay(2021, 8) {
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

        val maxSegments = 7

        val numbers = listOf(
            BitSet(maxSegments).apply { listOf(0, 1, 2, 4, 5, 6).forEach { set(it) } }, // 0
            BitSet(maxSegments).apply { listOf(2, 5).forEach { set(it) } }, // 1
            BitSet(maxSegments).apply { listOf(0, 2, 3, 4, 6).forEach { set(it) } }, // 2
            BitSet(maxSegments).apply { listOf(0, 2, 3, 5, 6).forEach { set(it) } }, // 3
            BitSet(maxSegments).apply { listOf(1, 2, 3, 5).forEach { set(it) } }, // 4
            BitSet(maxSegments).apply { listOf(0, 1, 3, 5, 6).forEach { set(it) } }, // 5
            BitSet(maxSegments).apply { listOf(0, 1, 3, 4, 5, 6).forEach { set(it) } }, // 6
            BitSet(maxSegments).apply { listOf(0, 2, 5).forEach { set(it) } }, // 7
            BitSet(maxSegments).apply { listOf(0, 1, 2, 3, 4, 5, 6).forEach { set(it) } }, // 8
            BitSet(maxSegments).apply { listOf(0, 1, 2, 3, 5, 6).forEach { set(it) } } // 9
        )
        val uniqueCardinality =
            numbers.groupBy { it.cardinality() }.filter { it.value.size == 1 }.values.flatten().map { it.cardinality() }
                .filter { it < maxSegments }
        val uniqueCardinalityDigits = numbers.mapIndexed { index, bitSet -> index to bitSet }
            .filter { it.second.cardinality() in uniqueCardinality }.toMap().mapKeys { it.value.cardinality() }
        val uniqueSegmentTotals =
            (0 until maxSegments).groupBy { segment -> numbers.count { it[segment] } }.filter { it.value.size == 1 }
                .mapValues { it.value.single() }.map { it.value to it.key }.toMap()

    }

    data class Signal(val input: List<BitSet>, val output: List<BitSet>)

    fun findDigits(signals: List<Signal>): Int {
        val uniqueNumbers =
            numbers.groupBy { it.cardinality() }.filter { it.value.size == 1 }.values.flatten().map { it.cardinality() }
        return signals.sumOf { it.output.count { it.cardinality() in uniqueNumbers } }
    }

    val inputAsSignals = parseInput(inputAsLines)

    fun parseInput(input: List<String>): List<Signal> {
        return input.map { toSignal(it) }
    }

    fun String.toBitSet(): BitSet {
        val s = this
        return BitSet(maxSegments).apply { (0 until maxSegments).map { set(it, s.contains('a' + it)) } }
    }

    private fun BitSet.getBitsSet(): Set<Int> {
        return (0 until size()).filter { this[it] }.toSet()
    }

    fun toSignal(input: String): Signal {
        val (input, display) = input.split("|").map { it.trim() }
        val patterns = input.split(" ").map { it.trim() }.map { it.toBitSet() }
        val output = display.split(" ").map { it.trim() }.map { it.toBitSet() }
        return Signal(patterns, output)
    }

    fun signalToNumber(
        signal: Signal
    ): Int {
        val possibilities = (0 until maxSegments).associateWith { (0 until maxSegments).toSet() }.toMutableMap()
        val uniqueEntries = signal.input.filter { it.cardinality() in uniqueCardinality }
            .map { it to uniqueCardinalityDigits[it.cardinality()]!! }
        uniqueEntries.forEach { (entry, real) ->
            real.getBitsSet().forEach { seg ->
                possibilities[seg] = possibilities[seg]!!.intersect(entry.getBitsSet())
            }
        }
        uniqueSegmentTotals.forEach { (seg, total) ->
            val entrySeg = (0 until maxSegments).first { en -> signal.input.count { it[en] } == total }
            possibilities[seg] = setOf(entrySeg)
        }
        if (possibilities.values.any { it.size == 1 }) {
            do {
                val old = possibilities.map { it.value.size }
                val ones = possibilities.filter { it.value.size == 1 }.values.map { it.single() }

                for (seg in possibilities.keys) {
                    val current = possibilities[seg]!!
                    if (current.size == 1 && current.single() in ones) {
                        continue
                    }
                    possibilities[seg] = current - ones
                }
                if (possibilities.map { it.value.size } == old) {
                    break
                }
                if (possibilities.values.all { it.size == 1 }) {
                    break
                }
            } while (possibilities.map { it.value.size } != old && possibilities.values.any { it.size > 1 })
        }
        val used = possibilities.mapValues { it.value.single() }
        val digits = signal.output.map { digi ->
            BitSet().apply {
                (0 until maxSegments).forEach {
                    this[it] = digi[used[it]!!]
                }
            }
        }
        val mapped = digits.map { numbers.indexOf(it) }
        return mapped.joinToString("").toInt()

    }

    fun parseSignalsToNumbers(input: List<Signal>): Int {
        return input.sumOf { signal ->
            signalToNumber(signal)
        }
    }

    fun part1(): Int {
        return findDigits(inputAsSignals)
    }

    fun part2(): Int {
        return parseSignalsToNumbers(inputAsSignals)
    }

}

package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.Pos
import com.chriswk.aoc.util.asInt
import com.chriswk.aoc.util.report
import java.util.PriorityQueue
import kotlin.math.max

class Day15: AdventDay(2021, 15) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day15()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun buildRiskMap(input: List<String>): Map<Pos, Int> {
        return input.flatMapIndexed { y, line ->
            line.mapIndexed { x, risk ->
                Pos(x, y) to risk.asInt()
            }
        }.toMap()
    }

    fun lowestRisk(riskMap: Map<Pos, Int>, start: Pos, end: Pos): Int {
        val maxX = riskMap.maxOf { it.key.x } + 1
        val maxY = riskMap.maxOf { it.key.y } + 1
        return dijkstra(riskMap, start, end, maxX = maxX, maxY = maxY)[end]!!
    }

    fun expandMap(riskMap: Map<Pos, Int>, times: Int = 4): Map<Pos, Int> {
        val sizeX = riskMap.maxOf { it.key.x } + 1
        val sizeY = riskMap.maxOf { it.key.y } + 1
        // First expand x
        val xExpanded: Map<Pos, Int> = buildMap {
            riskMap.entries.forEach { entry ->
                put(entry.key, entry.value)
                (1..times).forEach { multiplier ->
                    val newXCoord = sizeX * multiplier
                    val newPos = entry.key + Pos(newXCoord, 0)
                    val previousPos = newPos - Pos(sizeX, 0)
                    val previousValue = this.getValue(previousPos)
                    val newRisk = max((previousValue + 1) % 10, 1)
                    put(newPos, newRisk)
                }
            }
        }

        val yExpanded: Map<Pos, Int> = buildMap {
            xExpanded.entries.forEach { entry ->
                put(entry.key, entry.value)
                (1..times).forEach { multiplier ->
                    val newYCoord = sizeY * multiplier
                    val newPos = entry.key + Pos(0, newYCoord)
                    val previousPos = newPos - Pos(0, sizeY)
                    val previousValue = this.getValue(previousPos)
                    val newRisk = max((previousValue + 1) % 10, 1)
                    put(newPos, newRisk)
                }
            }
        }
        return yExpanded
    }

    private val riskComparator: Comparator<Pair<Pos, Int>> =
        Comparator { o1, o2 -> o1!!.second.compareTo(o2!!.second) }

    private fun dijkstra(riskMap: Map<Pos, Int>, start: Pos, target: Pos, maxX: Int, maxY: Int): Map<Pos, Int> {
        val accumulatedRiskMap: MutableMap<Pos, Int> = mutableMapOf(start to 0)
        val q = PriorityQueue(riskComparator)
        q.add(start to 0)
        while(q.isNotEmpty()) {
            val (current, myRisk) = q.remove()
            current.cardinalNeighbours(maxX, maxY).forEach { neighbour ->
                if (riskMap.containsKey(neighbour) && !accumulatedRiskMap.containsKey(neighbour)) {
                    val totalRisk = myRisk + riskMap.getValue(neighbour)
                    accumulatedRiskMap[neighbour] = totalRisk
                    if (neighbour == target) {
                        return accumulatedRiskMap
                    }
                    q.add(neighbour to totalRisk)
                }
            }
        }
        return accumulatedRiskMap
    }

    val inputRisk = buildRiskMap(inputAsLines)

    fun part1(): Int {
        return lowestRisk(inputRisk, Pos(0, 0), inputRisk.maxByOrNull { it.key }!!.key)
    }

    fun part2(): Int {
        val expanded = expandMap(inputRisk)
        val target = expanded.maxByOrNull { it.key }!!.key
        return lowestRisk(expandMap(inputRisk), Pos(0, 0), target)
    }

}

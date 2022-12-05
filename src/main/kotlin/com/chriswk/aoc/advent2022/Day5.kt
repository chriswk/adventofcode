package com.chriswk.aoc.advent2022

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.reportableString
import java.util.*

class Day5: AdventDay(2022, 5) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day5()
            reportableString {
                day.part1()
            }
            reportableString {
                day.part2()
            }
        }
        val operationRegEx = """move (\d+) from (\d+) to (\d+)""".toRegex()
    }

    fun parseInput(input: List<String>): Pair<List<ArrayDeque<Char>>, List<Operation>> {
        val stackCount = (input.first().length + 1) / 4
        val stacks = List(stackCount) { ArrayDeque<Char>() }
        val ops = mutableListOf<Operation>()
        input.forEach { line ->
            when {
                line.contains('[') -> {
                    for (idx in 1 until line.length step 4){
                        if (line[idx].isLetter()) {
                            stacks[(idx - 1) / 4].addLast(line[idx])
                        }
                    }
                }

                line.startsWith("move") -> {
                    ops.add(Operation(line))
                }
            }
        }
        return stacks to ops
    }
    fun applyOps(stacks: List<ArrayDeque<Char>>, operations: List<Operation>): List<ArrayDeque<Char>> {
        operations.forEach { op ->
            repeat(op.cratesToMove) {
                stacks[op.toStack].addFirst(stacks[op.fromStack].removeFirst())
            }
        }
        return stacks
    }

    fun crateMover9001(stacks: List<ArrayDeque<Char>>, operations: List<Operation>): List<ArrayDeque<Char>> {
        operations.forEach { op ->
            val top = mutableListOf<Char>()
            repeat(op.cratesToMove) {
                top.add(stacks[op.fromStack].removeFirst())
            }
            top.reversed().forEach {
                stacks[op.toStack].push(it)
            }
        }
        return stacks
    }

    fun getStacksAndOps(): Pair<List<ArrayDeque<Char>>, List<Operation>> {
        return parseInput(inputAsLines)
    }

    fun part1(): String {
        val (stacks, ops) = getStacksAndOps()
        val endResult = applyOps(stacks, ops)
        return endResult.joinToString(separator = "") { it.first().toString() }
    }

    fun part2(): String {
        val (stacks, ops) = getStacksAndOps()
        val endResult = crateMover9001(stacks, ops)
        return endResult.joinToString(separator = "") { it.first().toString() }
    }

    data class Operation(val cratesToMove: Int, val fromStack: Int, val toStack: Int) {
        constructor(line: String, match: MatchResult.Destructured = operationRegEx.find(line)!!.destructured): this(
            cratesToMove = match.component1().toInt(),
            fromStack = match.component2().toInt() - 1, // To get correct index
            toStack = match.component3().toInt() - 1 // To get correct index
        )
    }
}

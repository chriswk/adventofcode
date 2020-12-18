package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day18: AdventDay(2020, 18) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day18()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun op(op: MathOp, x: Long, y: Long): Long {
        return when(op) {
            MathOp.MULTIPLICATION -> x * y
            MathOp.PLUS -> x + y
        }
    }

    fun processHomeworkLine(line: String): Long {
        if (line.isEmpty()) {
            return 0L
        }
        val pieces = if (line.endsWith(" * ") || line.endsWith(" + ")) {
            line.split(" ").dropLast(1)
        } else {
            line.split(" ")
        }
        return pieces.drop(1).fold(SoFar(sum=pieces.first().toLong())) { soFar, nextPart ->
            if (soFar.operation != null) {
                SoFar(sum = op(soFar.operation, soFar.sum, nextPart.toLong()), operation = null)
            } else {
                soFar.copy(operation = MathOp.from(nextPart)!!)
            }
        }.sum
    }

    fun doMath(problem: String): Long {
        return if (!problem.contains(")")) {
            processHomeworkLine(problem)
        } else if (!problem.contains("(")) {
            //No more brackets, just calculate to end of brackets
                doMath(problem.substringBefore(")"))
        } else {
            val beforeBracketsString = problem.substringBefore("(")
            if (beforeBracketsString.isNotEmpty()) {
                val beforeBrackets = doMath(beforeBracketsString)
                val inBrackets = problem.substringAfter("(")
                if (beforeBracketsString.endsWith(" + ")) {
                    beforeBrackets + doMath(inBrackets)
                } else {
                    beforeBrackets * doMath(inBrackets)
                }
            } else {
                doMath(problem.substringAfter("("))
            }
        }
    }

    fun part1(): Int {
        return 0
    }

    fun part2(): Int {
        return 0
    }

    data class SoFar(val sum: Long, val operation: MathOp? = null)
    enum class MathOp {
        PLUS,
        MULTIPLICATION;
        companion object {
            fun from(c: String): MathOp? {
                return when (c) {
                    "+" -> PLUS
                    "*" -> MULTIPLICATION
                    else -> null
                }
            }
        }
    }
}
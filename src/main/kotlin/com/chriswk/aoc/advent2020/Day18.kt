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


    fun part1(): Long {
        return inputAsLines.map { eval(it, false) }.sum()
    }

    fun part2(): Long {
        return inputAsLines.map { eval(it, true) }.sum()
    }

    // converts the expression to rpn using shunting-yard
    // https://en.wikipedia.org/wiki/Shunting-yard_algorithm
    fun eval(exp: String, opPrecedence: Boolean): Long {
        val expLength = exp.length
        val outputQ = CharArray(expLength)
        val opStack = CharArray(expLength)
        var outHead = expLength
        var opStackHead = -1
        exp.toCharArray().forEach {
            when(it) {
                ' ' -> {}
                '(' -> opStack[++opStackHead] = it
                ')' -> {
                    while (opStack[opStackHead] != '(') {
                        outputQ[--outHead] = opStack[opStackHead--]
                    }
                    opStackHead--
                }
                '+', '*' -> {
                    while (opStackHead >= 0 && (!opPrecedence || opStack[opStackHead] == '+') && opStack[opStackHead] != '(') {
                        outputQ[--outHead] = opStack[opStackHead--]
                    }
                    opStack[++opStackHead] = it
                }
                else -> outputQ[--outHead] = it
            }
        }
        System.arraycopy(opStack, 0, outputQ, outHead - (opStackHead + 1), opStackHead + 1);

        return evalRpn(outputQ, outHead - (opStackHead + 1));
    }

    fun evalRpn(rpn: CharArray, size: Int): Long {
        val stack = LongArray(size)
        var head = -1
        var i = rpn.size - 1
        while (i >= size) {
            when(rpn[i]) {
                '+' -> stack[--head] = stack[head+1] + stack[head]
                '*' -> stack[--head] = stack[head+1] * stack[head]
                else -> stack[++head] = (rpn[i] - '0').toLong()
            }
            i--
        }
        return stack[head]
    }
}
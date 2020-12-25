package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import java.math.BigInteger

class Day25: AdventDay(2020, 25) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day25()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    val mod = 20201227
    fun findLoopSize(publicKey: Long): Int {
        return generateSequence(1L) { it.modPow() }.indexOf(publicKey)
    }

    fun findPrivateKey(loopSize: Int, subject: Long = 7): Long {
        return generateSequence(1L) { it.modPow(subject) }.drop(loopSize).first()
    }

    fun parse(input: List<String>): Pair<Long, Long> {
        return input.first().toLong() to input.last().toLong()
    }
    fun part1(): Long {
        val (cardPubkey, doorPubKey) = parse(inputAsLines)
        return findPrivateKey(findLoopSize(cardPubkey), doorPubKey)
    }

    fun part2(): Int {
        return 0
    }

    fun Long.modPow(subject: Long = 7) = this * subject % mod
}
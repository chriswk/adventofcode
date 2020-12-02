package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day2 : AdventDay(2020, 2) {
    private val passwords = inputAsLines.map { it.toPolicyAndPassword() }
    companion object {
        @JvmStatic
        fun main(args: Array<String>): Unit {
            val day = Day2()
            report {
                day.part1()
            }

            report {
                day.part2()
            }
        }
        fun String.toPolicyAndPassword(): Pair<Policy, String> {
            val (policy, password) = this.split(":")
            val (minMax, letter) = policy.split(" ")
            val (min, max) = minMax.split("-")
            return Policy(min.toInt(), max.toInt(), letter[0]) to password.trim()
        }

    }

    fun part1(): Int {
        return passwords.count { (policy, password) ->
            policy.validPart1(password)
        }
    }
    fun part2(): Int {
        return passwords.count { (policy, password) ->
            policy.validPart2(password)
        }
    }

    data class Policy(val min: Int, val max: Int, val character: Char) {
        fun validPart1(password: String) = password.count { it == character } in min..max
        fun validPart2(password: String) = (password[min-1] == character) xor (password[max-1] == character)
    }
}
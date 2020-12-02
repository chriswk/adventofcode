package com.chriswk.aoc.`2020`

import org.apache.logging.log4j.LogManager

class Day2 {
    companion object {
        val logger = LogManager.getLogger(Day2::class.java)
        @JvmStatic
        fun main(args: Array<String>): Unit {
            val day = Day2()
           val data = day.parseFile()
            report {
                day.part1(data)
            }

            report {
                day.part2(data)
            }
        }
        fun String.toPolicyAndPassword(): Pair<Policy, String> {
            val (policy, password) = this.split(":")
            val (minMax, letter) = policy.split(" ")
            val (min, max) = minMax.split("-")
            return Policy(min.toInt(), max.toInt(), letter[0]) to password.trim()
        }

    }

    fun parseFile(): List<Pair<Policy, String>> {
        return "day2.txt".fileToLines().map { it.toPolicyAndPassword() }
    }

    fun part1(passwords: List<Pair<Policy, String>>): Int {
        return passwords.count { (policy, password) ->
            policy.validPart1(password)
        }
    }
    fun part2(passwords: List<Pair<Policy, String>>): Int {
        return passwords.count { (policy, password) ->
            policy.validPart2(password)
        }
    }

    data class Policy(val min: Int, val max: Int, val character: Char) {
        fun validPart1(password: String) = password.count { it == character } in min..max
        fun validPart2(password: String) = (password[min-1] == character) xor (password[max-1] == character)
    }
}
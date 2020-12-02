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
            return Policy(min.toInt(), max.toInt(), letter.toCharArray()[0]) to password.trim()
        }

    }

    fun parseFile(): List<Pair<Policy, String>> {
        return "day2.txt".fileToLines().map { it.toPolicyAndPassword() }
    }

    fun part1(passwords: List<Pair<Policy, String>>): Int {
        return passwords.count { (policy, password) ->
            password.count { it == policy.character } in policy.min..policy.max
        }
    }
    fun part2(passwords: List<Pair<Policy, String>>): Int {
        return passwords.count { (policy, password) ->
            val (mi, ma) = password[policy.min-1] to password[policy.max-1]
            mi != ma && (mi == policy.character || ma == policy.character)
        }
    }

    data class Policy(val min: Int, val max: Int, val character: Char)
}
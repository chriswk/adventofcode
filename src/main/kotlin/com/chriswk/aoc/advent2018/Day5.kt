package com.chriswk.aoc.advent2018

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.fileToString

object Day5 : AdventDay(2018, 5) {


    fun react(polymer: String, ignore: Char? = null): String {
        return polymer.fold(mutableListOf<Char>()) { done, char ->
            when {
                ignore != null && char.equals(ignore, true) -> Unit
                done.lastOrNull() matches char -> done.removeAt(done.lastIndex)
                else -> done.add(char)
            }
            done
        }
        .joinToString("")

    }


    private infix fun Char?.matches(other: Char): Boolean {
        return when {
            this == null -> false
            this.toUpperCase() != other.toUpperCase() -> false
            else -> this != other
        }
    }

    fun solution1(): Int {
        return react(inputAsString).length
    }

    fun solution2(): Int {
        return ('A'..'Z').map { react(inputAsString, it).length }.minOrNull() ?: throw IllegalStateException("Could not find a solution")
    }
}

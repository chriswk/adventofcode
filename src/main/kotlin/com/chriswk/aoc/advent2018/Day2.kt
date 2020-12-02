package com.chriswk.aoc.advent2018

object Day2 {
    fun partOne(input: List<String>): Int {
        val (twos, threes) = input.asSequence().map { line ->
            val charCount = line.groupBy { c -> c }.mapValues { it.value.size }
            val hasTwoChars = charCount.entries.any { it.value == 2 }
            val hasThreeChars = charCount.entries.any { it.value == 3 }
            (hasTwoChars to hasThreeChars)
        }.fold((0 to 0)) { (twos, threes), (hasTwoChars, hasThreeChars) ->
            when(hasTwoChars) {
                true -> when(hasThreeChars) {
                    true -> (twos+1 to threes+1)
                    false -> (twos + 1 to threes)
                }
                false -> when(hasThreeChars) {
                    true -> (twos to threes + 1)
                    false -> (twos to threes)
                }
            }
        }
        return twos * threes
    }

    fun partTwo(input: List<String>): String {
        return input.asSequence().mapIndexedNotNull { i, checksum ->
            input.subList(i, input.size).map {
                candidate -> candidate.filterIndexed {
                    i, c -> c == checksum[i]}
            }.firstOrNull {
                candidate -> candidate.length == checksum.length - 1
            }
        }.first()
    }
}
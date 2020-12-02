package com.chriswk.aoc.advent2018

object Day14 {
    val scores = sequence {
        var elf1Idx = 0
        var elf2Idx = 1
        yield(3)
        yield(7)
        val recipeScores = mutableListOf(3, 7)
        while(true) {
            val elf1Score = recipeScores[elf1Idx]
            val elf2Score = recipeScores[elf2Idx]
            val score = elf1Score + elf2Score
            val newRecipeScores = getSingleDigits(score)
            recipeScores.addAll(newRecipeScores)
            yieldAll(newRecipeScores)
            elf1Idx = move(elf1Idx, elf1Score, recipeScores.size)
            elf2Idx = move(elf2Idx, elf2Score, recipeScores.size)
        }
    }

    fun partOne(recipeCount: Int): String {
        return scores.drop(recipeCount).take(10).joinToString(separator = "")
    }

    fun partTwo(recipeCount: String): Int {
        val toFind = recipeCount.map { it.toString().toInt() }
        return scores.windowed(recipeCount.length).indexOf(toFind)
    }

    fun getSingleDigits(num: Int): List<Int> {
        return when {
            num < 10 -> listOf(num)
            else -> listOf(num / 10, num % 10)
        }
    }

    fun move(currentIdx: Int, score: Int, listSize: Int): Int {
        return (currentIdx + score + 1) % listSize
    }
}
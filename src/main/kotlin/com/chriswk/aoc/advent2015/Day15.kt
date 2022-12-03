package com.chriswk.aoc.advent2015

import com.chriswk.aoc.AdventDay

import com.chriswk.aoc.util.report
import java.lang.Math.max

class Day15: AdventDay(2015, 15) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day15()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
        val regex = """(.*): capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (-?\d+)""".toRegex()
    }

    private fun List<Ingredient>.capacity(num: List<Int>) =
        0L.coerceAtLeast(mapIndexed { index: Int, ingredient: Ingredient -> num[index] * ingredient.capacity }.sum()
            .toLong()
        )
    private fun List<Ingredient>.durability(num: List<Int>) =
        0L.coerceAtLeast(mapIndexed { index: Int, ingredient: Ingredient -> num[index] * ingredient.durability }.sum()
            .toLong()
        )
    private fun List<Ingredient>.flavor(num: List<Int>) =
        0L.coerceAtLeast(mapIndexed { index: Int, ingredient: Ingredient -> num[index] * ingredient.flavor }.sum()
            .toLong()
        )
    private fun List<Ingredient>.texture(num: List<Int>) =
        0L.coerceAtLeast(mapIndexed { index: Int, ingredient: Ingredient -> num[index] * ingredient.texture }.sum()
            .toLong()
        )
    private fun List<Ingredient>.calories(num: List<Int>) =
        0L.coerceAtLeast(mapIndexed { index: Int, ingredient: Ingredient -> num[index] * ingredient.calories }.sum()
            .toLong()
        )

    private fun score(ingredients: List<Ingredient>, i: Int, j: Int, k: Int, l: Int): Long {
        val capacity = ingredients.capacity(listOf(i, j, k, l))
        val durability = ingredients.durability(listOf(i, j, k, l))
        val flavor = ingredients.flavor(listOf(i, j, k, l))
        val texture = ingredients.texture(listOf(i, j, k, l))
        return capacity * durability * flavor * texture
    }
    
    fun maxValueFor500Calories(ingredients: List<Ingredient>): Long {
        return (0..100).asSequence().flatMap { i ->
            (0..(100 - i)).asSequence().flatMap { j ->
                (0..(100 - i - j)).asSequence().flatMap { k ->
                    (0..(100 - i - j - k)).asSequence().map { l ->
                        ingredients.calories(listOf(i, j, k, l)) to score(ingredients, i, j, k, l)
                    }
                }
            }
        }.filter { it.first == 500L }.maxOf { it.second }
    }

    fun calculateMaxValue(ingredients: List<Ingredient>): Long {
        return (0..100).asSequence().flatMap { i ->
            (0 .. (100 - i)).asSequence().flatMap { j ->
                (0 .. (100 - i - j)).asSequence().flatMap { k ->
                    (0 .. (100-i-j-k)).asSequence().map { l ->
                        score(ingredients, i, j, k, l)
                    }
                }
            }
        }.max()
    }

    fun parseInput(input: List<String>): List<Ingredient> {
        return input.map { Ingredient(it) }
    }
    fun part1(): Long {
        return calculateMaxValue(parseInput(inputAsLines))
    }

    fun part2(): Long {
        return maxValueFor500Calories(parseInput(inputAsLines))
    }

    data class Ingredient(val name: String, val capacity: Int, val durability: Int, val flavor: Int, val texture: Int, val calories: Int) {
        constructor(str: String, mr: MatchResult.Destructured = regex.find(str)!!.destructured) : this(
            mr.component1(),
            mr.component2().toInt(),
            mr.component3().toInt(),
            mr.component4().toInt(),
            mr.component5().toInt(),
            mr.component6().toInt(),
        )
    }
}

package com.chriswk.aoc.advent2015

import com.chriswk.aoc.AdventDay

import com.chriswk.aoc.util.report
import java.lang.Math.max

class Day: AdventDay(2015, 15) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day()
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

    fun parseInput(input: List<String>): List<Ingredient> {
        return input.map { Ingredient(it) }
    }
    fun part1(): Int {
        return 0
    }

    fun part2(): Int {
        return 0
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

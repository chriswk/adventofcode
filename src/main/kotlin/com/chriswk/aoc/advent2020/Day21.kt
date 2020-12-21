package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import com.chriswk.aoc.util.reportableString

class Day21: AdventDay(2020, 21) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day21()
            report {
                day.part1()
            }
            reportableString {
                day.part2()
            }
        }
    }

    fun reduce(input: Map<String, List<Allergen>>): Map<String, Set<String>> {
        return input.entries.map {
            val reducedPossibles = it.value.drop(1).fold(it.value.first().possibles) { previous, allergen ->
                previous.intersect(allergen.possibles)
            }
            it.key to reducedPossibles
        }.toMap()
    }

    fun assignIngredientToAllergen(allergens: Map<String, Set<String>>): Map<String, String> {
        val ingredientToAllergen = mutableMapOf<String, String>()
        while(ingredientToAllergen.size < allergens.size) {
            allergens.entries.sortedBy { it.value.size }.forEach { e ->
                val remainingKeys = e.value - ingredientToAllergen.keys
                if (remainingKeys.size == 1) {
                    ingredientToAllergen[remainingKeys.first()] = e.key
                }
            }
        }
        return ingredientToAllergen

    }

    fun parse(input: List<String>): Map<String, List<Allergen>> {
        return input.flatMap { line ->
            val ingredients = line.substringBefore("(").trim().split(" ").toSet()
            val names = line.substringAfter("contains ").substringBefore(")").split(", ")
            names.map { Allergen(it, ingredients) }
        }.groupBy { it.name }
    }

    fun countAppearences(dangerousIngredients: Set<String>, recipe: List<String>): Int {
        return recipe.count { it !in dangerousIngredients }
    }

    fun answerPart1(input: List<String>, allergens: Map<String, String>): Int {
        return input.map { line ->
            line.substringBefore("(").trim().split(" ")
        }.sumBy { countAppearences(allergens.keys, it) }
    }

    fun answerPart2(allergens: Map<String, String>): String {
        return allergens.entries.sortedBy { it.value }.joinToString(separator = ",") { it.key }
    }

    fun part1(): Int {
        val allergens = parse(inputAsLines)
        val candidates = reduce(allergens)
        val correctMap = assignIngredientToAllergen(candidates)
        return answerPart1(inputAsLines, correctMap)
    }

    fun part2(): String {
        val allergens = parse(inputAsLines)
        val candidates = reduce(allergens)
        val correctMap = assignIngredientToAllergen(candidates)
        return answerPart2(correctMap)
    }

    data class Allergen(val name: String, val possibles: Set<String>)

}
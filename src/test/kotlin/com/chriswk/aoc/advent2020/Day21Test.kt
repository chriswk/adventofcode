package com.chriswk.aoc.advent2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day21Test {
    val smallInput = """
        mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
        trh fvjkl sbzzf mxmxvkd (contains dairy)
        sqjhc fvjkl (contains soy)
        sqjhc mxmxvkd sbzzf (contains fish)
    """.trimIndent().lines()
    @Test
    fun canParseSmallInput() {
        val day = Day21()
        val allergenMap = day.parse(smallInput)
        assertThat(allergenMap.getValue("dairy")).hasSize(2)
        assertThat(allergenMap.getValue("soy")).hasSize(1)
        assertThat(allergenMap.getValue("fish")).hasSize(2)
    }

    @Test
    fun canReduceCandidates() {
        val day = Day21()
        val allergenMap = day.parse(smallInput)
        val reducedMap = day.reduce(allergenMap)
        assertThat(reducedMap).hasSize(3)
    }

    @Test
    fun canBuildCorrectIngredientToAllergen() {
        val day = Day21()
        val allergenMap = day.parse(smallInput)
        val reducedMap = day.assignIngredientToAllergen(day.reduce(allergenMap))
        assertThat(reducedMap).containsAllEntriesOf(mapOf(
            "mxmxvkd" to "dairy",
            "sqjhc" to "fish",
            "fvjkl" to "soy"
        ))

    }

    @Test
    fun findsCorrectAppearances() {
        val day = Day21()
        val allergenMap = day.parse(smallInput)
        val reducedMap = day.assignIngredientToAllergen(day.reduce(allergenMap))
        assertThat(day.answerPart1(smallInput, reducedMap)).isEqualTo(5)
    }

    @Test
    fun part1() {
        assertThat(Day21().part1()).isEqualTo(2389)
    }

    @Test
    fun alphabeticalAllergenList() {
        val day = Day21()
        val allergenMap = day.parse(smallInput)
        val reducedMap = day.assignIngredientToAllergen(day.reduce(allergenMap))
        assertThat(day.answerPart2(reducedMap)).isEqualTo("mxmxvkd,sqjhc,fvjkl")
    }
    @Test
    fun part2() {
        assertThat(Day21().part2()).isEqualTo("fsr,skrxt,lqbcg,mgbv,dvjrrkv,ndnlm,xcljh,zbhp")
    }
}
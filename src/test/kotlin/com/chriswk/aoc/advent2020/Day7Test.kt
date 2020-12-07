package com.chriswk.aoc.advent2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day7Test {

    @Test
    fun parsesRule() {
        val day = Day7()
        val rules = day.readRule("light red bags contain 1 bright white bag, 2 muted yellow bags.")
        assertThat(rules).hasSize(2)
        assertThat(rules.first()).isEqualTo(Pair("light red", "bright white"))
        assertThat(rules[1]).isEqualTo(Pair("light red", "muted yellow"))
        assertThat(day.toAscendantGraph(rules)).containsKeys("bright white", "muted yellow")
    }

    @Test
    fun parsesExampleInput() {
        val input = """
            light red bags contain 1 bright white bag, 2 muted yellow bags.
            dark orange bags contain 3 bright white bags, 4 muted yellow bags.
            bright white bags contain 1 shiny gold bag.
            muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
            shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
            dark olive bags contain 3 faded blue bags, 4 dotted black bags.
            vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
            faded blue bags contain no other bags.
            dotted black bags contain no other bags.
        """.trimIndent().lines()
        val day = Day7()
        val map = day.parsePart1(input)
        val bagColors = day.findBagColors(map.mapValues { bag -> bag.value.map { it.first }}, emptySet(), "shiny gold")
        assertThat(bagColors).hasSize(4)
    }

    @Test
    fun part1() {
        assertThat(Day7().part1()).isEqualTo(185)
    }

    @Test
    fun findsCorrectNumberOfBags() {
        val input = """
            shiny gold bags contain 2 dark red bags.
            dark red bags contain 2 dark orange bags.
            dark orange bags contain 2 dark yellow bags.
            dark yellow bags contain 2 dark green bags.
            dark green bags contain 2 dark blue bags.
            dark blue bags contain 2 dark violet bags.
            dark violet bags contain no other bags.    
        """.trimIndent().lines()
        val day = Day7()
        val map = day.parsePart2(input)
        val f = day.findHowManyBags(map, "shiny gold") - 1
        assertThat(f).isEqualTo(126)
    }

    @Test
    fun anotherTestCase() {
        val input = """
            light red bags contain 1 bright white bag, 2 muted yellow bags.
            dark orange bags contain 3 bright white bags, 4 muted yellow bags.
            bright white bags contain 1 shiny gold bag.
            muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
            shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
            dark olive bags contain 3 faded blue bags, 4 dotted black bags.
            vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
            faded blue bags contain no other bags.
            dotted black bags contain no other bags.
        """.trimIndent().lines()
        val day = Day7()
        val map = day.parsePart2(input)
        val f = day.findHowManyBags(map, "shiny gold") - 1
        assertThat(f).isEqualTo(3 + 1*(3+4) + 2*(5+6))
    }
}
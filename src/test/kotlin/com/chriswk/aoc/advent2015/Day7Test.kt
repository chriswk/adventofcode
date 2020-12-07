package com.chriswk.aoc.advent2015

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@ExperimentalUnsignedTypes
@ExperimentalStdlibApi
class Day7Test {

    @Test
    fun parsingInstructionsWorks() {
        val input = """
            123 -> x
            456 -> y
            x AND y -> d
            x OR y -> e
            x LSHIFT 2 -> f
            y RSHIFT 2 -> g
            NOT x -> h
            NOT y -> i
        """.trimIndent().lines()
        val day = Day7()
        val instructions = day.parseInstructions(input)
        assertThat(instructions).hasSize(8)
    }

    @Test
    fun runsProgram() {
        val input = """
            123 -> x
            456 -> y
            x AND y -> d
            x OR y -> e
            x LSHIFT 2 -> f
            y RSHIFT 2 -> g
            NOT x -> h
            NOT y -> i
        """.trimIndent().lines()
        val day = Day7()
        val instructions = day.parseInstructions(input)
        val registry = mutableMapOf<String, Int>()
        instructions.forEach { day.runInstruction(registry, it) }
        assertThat(registry).containsAllEntriesOf(mapOf(
            "d" to 72,
            "e" to 507,
            "f" to 492,
            "g" to 114,
            "h" to 65412,
            "i" to 65079,
            "x" to 123,
            "y" to 456
        ))
    }
    @Test
    fun walksGraph() {
        val input = """
            123 -> x
            456 -> y
            x AND y -> d
            x OR y -> e
            x LSHIFT 2 -> f
            y RSHIFT 2 -> g
            NOT x -> h
            NOT y -> i
        """.trimIndent().lines()
        val day = Day7()
        val instructions = day.parseInstructions(input)
        val graph = day.buildDependencyGraph(instructions)
        val registry = day.walkGraph(mutableMapOf(), graph, "d")
        println(registry)
        assertThat(registry).hasSize(3)
        assertThat(registry).containsAllEntriesOf(mapOf(
            "x" to 123,
            "y" to 456,
            "d" to 72
        ))
        assertThat(day.walkGraph(mutableMapOf(), graph, "h")).containsAllEntriesOf(mapOf(
            "x" to 123,
            "h" to day.inverse(123)
        ))
    }
    @Test
    fun buildsGraphCorrectly() {
        val input = """
            NOT x -> h
            NOT y -> i
            x AND y -> d
            x OR y -> e
            x AND m -> n
            x LSHIFT 2 -> f
            123 -> x
            456 -> y
            NOT l -> m
            y RSHIFT 2 -> g
            5 LSHIFT 2 -> l
        """.trimIndent().lines()
        val day = Day7()
        val instructions = day.parseInstructions(input)
        val graph = day.buildDependencyGraph(instructions)
        val registry = day.walkGraph(mutableMapOf(), graph, "n")
        println(registry)
        assertThat(registry).hasSize(4)
        assertThat(registry).containsAllEntriesOf(mapOf(
            "x" to 123,
            "l" to 20,
            "m" to 65515,
            "n" to 107
        ))
    }

    @Test
    fun part1() {
        assertThat(Day7().part1()).isEqualTo(46065)
    }
}
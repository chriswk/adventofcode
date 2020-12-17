package com.chriswk.aoc.advent2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day17Test {
    val smallInput = """
            .#.
            ..#
            ###
        """.trimIndent().lines()

    @Test
    fun canParseFlatSection() {
        val day = Day17()
        val pocketUniverse = day.cubes(smallInput)
        assertThat(pocketUniverse).hasSize(9)
        assertThat(pocketUniverse.count { it.value }).isEqualTo(5)
    }

    @Test
    fun canPerformOneGeneration() {
        val day = Day17()
        val pocketUniverse = day.cubes(smallInput)
        val nextGen = day.step(pocketUniverse)
        assertThat(nextGen.values.count { it }).isEqualTo(11)
    }

    @Test
    fun canPerformMultipleGenerations() {
        val day = Day17()
        val pocketUniverse = day.cubes(smallInput)
        val bootProcess = day.simulate(pocketUniverse, 3)
        assertThat(bootProcess.values).hasSize(38)
    }

    @Test
    fun canPerformFullBoot() {
        val day = Day17()
        val pocketUniverse = day.cubes(smallInput)
        val bootProcess = day.simulate(pocketUniverse, 6)
        assertThat(bootProcess.values).hasSize(112)
    }

    @Test
    fun part1() {
        assertThat(Day17().part1()).isEqualTo(362)
    }

    @Test
    fun canPerformStepWith4D() {
        val day = Day17()
        val world = day.hyperCubes(smallInput)
        val nextStep = day.hyperStep(world)
        assertThat(nextStep).hasSize(29)
    }

    @Test
    fun completesBootPart2() {
        val day = Day17()
        val world = day.hyperCubes(smallInput)
        assertThat(day.simulateHyper(world, 6)).hasSize(848)
    }

    @Test
    fun part2() {
        assertThat(Day17().part2()).isEqualTo(1980)
    }
}
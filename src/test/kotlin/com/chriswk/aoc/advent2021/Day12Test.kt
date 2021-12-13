package com.chriswk.aoc.advent2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day12Test {

    val smallCave = """
        start-A
        start-b
        A-c
        A-b
        b-d
        A-end
        b-end
    """.trimIndent().lines()

    val biggerExample = """
        dc-end
        HN-start
        start-kj
        dc-start
        dc-HN
        LN-dc
        HN-end
        kj-sa
        kj-HN
        kj-dc
    """.trimIndent().lines()

    val day = Day12()
    @Test
    fun `Can create cave`() {
        val cave = day.parseCave(smallCave)
        assertThat(cave).containsEntry(Day12.Cave("start"), listOf(Day12.Cave("A"), Day12.Cave("b")))
    }

    @Test
    fun `Can find correct paths`() {
        val graph = day.parseCave(smallCave)
        assertThat(day.solve(graph)).isEqualTo(10)
    }

    @Test
    fun `Can find paths for larger example`() {
        val graph = day.parseCave(biggerExample)
        assertThat(day.solve(graph)).isEqualTo(19)
    }

    @Test
    fun `Part 1`() {
        assertThat(day.part1()).isEqualTo(5576)
    }

    @Test
    fun `Can find paths when allowed to visit small caves twice`() {
        val graph = day.parseCave(smallCave)
        assertThat(day.solve(graph, canVisitTwice = true)).isEqualTo(36)
    }

    @Test
    fun `Part 2`() {
        assertThat(day.part2()).isEqualTo(152837)
    }
}

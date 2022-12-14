package com.chriswk.aoc.advent2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day11Test {
    val day = Day11()
    val testInput = """
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1
    """.trimIndent().lines()
    @Test
    fun can_parse_monkeys() {
        val monkeys = day.parseInput(testInput)
        assertThat(monkeys).hasSize(4)
    }

    @Test
    fun rounds() {
        val monkeys = day.parseInput(testInput)
        assertThat(monkeys.rounds(20, 3).monkeyBusiness()).isEqualTo(10605L)
    }

    @Test
    fun part_1() {
        assertThat(day.part1()).isEqualTo(54036)
    }

    @Test
    fun part_2() {
        assertThat(day.part2()).isEqualTo(13237873355L)
    }
}

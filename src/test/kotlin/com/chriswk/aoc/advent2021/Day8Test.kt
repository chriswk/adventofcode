package com.chriswk.aoc.advent2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day8Test {
    val testInput = """
        acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf
    """.trimIndent()

    val lines = """
        be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
        edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
        fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
        fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
        aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
        fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
        dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
        bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
        egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
        gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
    """.trimIndent().lines()
    val day = Day8()


    @Test
    fun `Can find number of ones, fours, sevens and eights`() {
        val outputs = day.parseInput(lines)
        assertThat(day.findDigits(outputs)).isEqualTo(26)
    }

    @Test
    fun `Part 1`() {
        assertThat(day.part1()).isEqualTo(470)
    }

    @Test
    fun `Can convert signal to number`() {
        val signal = day.toSignal(testInput)
        assertThat(day.signalToNumber(signal)).isEqualTo(5353)
    }

    @Test
    fun `Can convert large test input`() {
        val signals = day.parseInput(lines)
        assertThat(day.parseSignalsToNumbers(signals)).isEqualTo(61229)
    }

    @Test
    fun `Part 2`() {
        assertThat(day.part2()).isEqualTo(989396)
    }
}

package com.chriswk.aoc.advent2018

import com.chriswk.aoc.util.fileToLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class Day16Test {

    val sample = """
Before: [3, 2, 1, 1]
9 2 1 2
After:  [3, 2, 2, 1]
    """.trimIndent().lines()

    @Test
    fun canParseSingleSample() {
        val (instruction, before, after) = Day16.parseSingleSample(sample)
        assertThat(before).isEqualTo(Day16.Register(3, 2, 1, 1))
        assertThat(after).isEqualTo(Day16.Register(3, 2, 2, 1))
        assertThat(instruction).isEqualTo(Day16.Instruction(9, 2, 1, 2))
    }

    @Test
    fun canFindMatchingOpCodes() {
        assertThat(Day16.findOpCodesWithMatchingOutput(Day16.parseSingleSample(sample))).isEqualTo(3)
    }

    @Test
    fun part1() {
        val timeTaken = measureTimeMillis {
            assertThat(Day16.partOne("2018/Day16_samples.txt".fileToLines(), 3)).isEqualTo(493)
        }
        println("Part 1 took $timeTaken ms")
    }


    @Test
    fun part2() {
        val timeTaken = measureTimeMillis {
            assertThat(Day16.partTwo("2018/Day16_program.txt".fileToLines())).isEqualTo(Day16.Register(445, 2, 2, 445))
        }
        println("Part 2 took $timeTaken ms")
    }
}
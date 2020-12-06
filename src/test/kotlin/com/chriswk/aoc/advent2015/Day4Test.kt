package com.chriswk.aoc.advent2015

import com.chriswk.aoc.advent.DisableSlow
import com.chriswk.aoc.advent.Slow
import com.chriswk.aoc.util.md5
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@DisableSlow
class Day4Test {

    @Test
    fun md5HashingWorks() {
        assertThat("abcdef609043".md5()).startsWith("00000")
    }

    @Test
    fun part1() {
        assertThat(Day4().part1()).isEqualTo(117946)
    }

    @Test
    @Slow(3000)
    fun part2() {
        assertThat(Day4().part2()).isEqualTo(3938038)
    }
}
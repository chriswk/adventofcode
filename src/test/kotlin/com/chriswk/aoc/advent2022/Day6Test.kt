package com.chriswk.aoc.advent2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day6Test {
    val day = Day6()

    @Test
    fun can_find_initial_packet() {
        assertThat(day.startOfPacket("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 4)).isEqualTo(7)
        assertThat(day.startOfPacket("bvwbjplbgvbhsrlpgdmjqwftvncz", 4)).isEqualTo(5)
        assertThat(day.startOfPacket("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 4)).isEqualTo(10)
        assertThat(day.startOfPacket("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 4)).isEqualTo(11)
    }

    @Test
    fun part_1() {
        assertThat(day.part1()).isEqualTo(1876)
    }

    @Test
    fun part_2() {
        assertThat(day.part2()).isEqualTo(2202)
    }
}

package com.chriswk.aoc.advent2021

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class Day16Test {

    val example = "8A004A801A8002F478"

    val day = Day16()
    @Test
    fun `Can parse bits to ints`() {
        val bits = listOf(true, true, true, true)
        assertThat(day.toLong(bits)).isEqualTo(15)
    }

    @Test
    fun `Can parse example input`() {
        val input = day.hexToBits("8A004A801A8002F478")
        val (packet, remainder) = day.parsePacket(input)
        assertThat(day.sumVersion(packet)).isEqualTo(16)
    }

    @Test
    fun `Part 1`() {
        assertThat(day.part1()).isEqualTo(1007)
    }

    @ParameterizedTest
    @CsvSource(value = ["C200B40A82,3", "04005AC33890,54", "880086C3E88112,7", "CE00C43D881120,9", "D8005AC2A8F0,1", "F600BC2D8F,0", "9C005AC2F8F0,0", "9C0141080250320F1802104A08,1"])
    fun `Can evaluate packets`(input: String, expected: String) {
        val exp = expected.toLong()
        val ins = day.hexToBits(input)
        val (packet, _) = day.parsePacket(ins)
        assertThat(day.eval(packet)).isEqualTo(exp)
    }

    @Test
    fun `Part 2`() {
        assertThat(day.part2()).isEqualTo(834151779165)
    }
}

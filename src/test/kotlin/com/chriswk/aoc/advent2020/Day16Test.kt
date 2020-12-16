package com.chriswk.aoc.advent2020

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day16Test {

    @Test
    fun canParseToRanges() {
        val singleDescription = "departure location: 49-258 or 268-960"
        val day = Day16()
        assertThat(day.findValidRanges(listOf(singleDescription)).values.asSequence().flatten().toList()).containsExactlyInAnyOrder(
            IntRange(49, 258), IntRange(268, 960)
        )
    }

    @Test
    fun canParseTickets() {
        val ticket = "765,721,63,432,949,277,838,143,894,681,976,948,949,605,810,368,595,729,593,373"
        val day = Day16()
        assertThat(day.parseTickets(ticket)).hasSize(1)
        assertThat(day.parseTickets(ticket).first().numbers).hasSize(20)
    }

    @Test
    fun canValidateTicket() {
        val rules = """
            class: 1-3 or 5-7
            row: 6-11 or 33-44
            seat: 13-40 or 45-50
        """.trimIndent()
        val day = Day16()
        val ticket = day.parseTickets("7,1,14")
        assertThat(ticket.first().invalidNumbers(day.parseRanges(rules).values.asSequence().flatten())).isEmpty()
    }

    @Test
    fun part1SmallInput() {
        val rules = """
            class: 1-3 or 5-7
            row: 6-11 or 33-44
            seat: 13-40 or 45-50

            your ticket:
            7,1,14

            nearby tickets:
            7,3,47
            40,4,50
            55,2,20
            38,6,12
        """.trimIndent()
        val day = Day16()
        assertThat(day.solvePart1(rules)).isEqualTo(71)
    }
    @Test
    fun part1() {
        assertThat(Day16().part1()).isEqualTo(28884)
    }

    @Test
    fun canExcludeIndicesAsCandidatesForRule() {
        val input = """
class: 0-1 or 4-19
row: 0-5 or 8-19
seat: 0-13 or 16-19

your ticket:
11,12,13

nearby tickets:
3,9,18
15,1,5
5,14,9
        """.trimIndent()
        val day = Day16()
        val rules = day.parseRanges(input)
        val myTicket = Day16.Ticket(input.substringAfter("your ticket:").lines().drop(1).first())
        val tickets = day.parseTickets(input).filter { it.isValid(rules.values.asSequence().flatten()) }
        val r = day.performIntersection(rules, myTicket, tickets)
        val actualMap = day.findMinima(r)
        assertThat(day.findProductOfDepartureCols(actualMap, myTicket)).isEqualTo(0)
    }

    @Test
    fun part2() {
        assertThat(Day16().part2()).isEqualTo(1001849322119)
    }
}
package com.chriswk.aoc.advent2018

import com.chriswk.aoc.util.dayInputAsLines
import com.chriswk.aoc.util.fileToLines
import com.chriswk.aoc.util.rangeTo
import com.chriswk.aoc.util.toLocalDateTime
import java.time.Duration
import java.time.LocalDateTime

object Day4 {
    val lines = dayInputAsLines(2018, 4)
    val guardPattern = """(\d+)""".toRegex()
    val shifts = lines.asSequence().map { stringToLine(it) }
            .sortedBy {
                it.timestamp
            }.fold(mutableListOf(GuardShift(-1))) { acc, line ->
                when {
                    line.action.contains("falls asleep") -> acc[acc.lastIndex] = acc.last().copy(asleepFrom = line.timestamp)
                    line.action.contains("wakes up") -> {
                        acc[acc.lastIndex] = acc.last().copy(awakeAt = line.timestamp)
                        acc += GuardShift(guardId = acc.last().guardId)
                    }
                    else -> {
                        val id = guardPattern.find(line.action)!!.groupValues[0].toInt()
                        acc[acc.lastIndex] = GuardShift(id)
                    }
                }
                acc
            }.filter {
                it.asleepFrom != null && it.awakeAt != null
            }.groupBy { it.guardId }


    fun solution1(): Int {
        val guardId = guardMostAsleep(shifts).maxByOrNull { it.value }!!.key
        val minute = minuteMostAsleep(shifts[guardId]!!).maxByOrNull { it.value }!!.key
        return guardId * minute
    }

    fun solution2(): Int {
        val guardMinute = shifts.mapValues {
            minuteMostAsleep(it.value).maxByOrNull { guard ->
                guard.value
            }!!
        }.maxByOrNull {
            it.value.value
        }!!
        return guardMinute.key * guardMinute.value.key
    }

    fun minuteMostAsleep(guardShifts: List<GuardShift>): Map<Int, Int> {
        return guardShifts.flatMap { shift ->
            (shift.asleepFrom!!..shift.awakeAt!!).map {
                it.minute
            }
        }.groupBy { it }.mapValues { it.value.size }
    }

    fun guardMostAsleep(guardShifts: Map<Int, List<GuardShift>>): Map<Int, Long> {
        return guardShifts.mapValues { (_, v) ->
            v.fold(0L) { acc, shift ->
                acc + Duration.between(shift.asleepFrom!!, shift.awakeAt!!).toMinutes()
            }
        }
    }

    fun stringToLine(line: String): Line {
        val (date, action) = line.split("]")
        return Line(date.substringAfter("[").toLocalDateTime(), action)
    }

    data class Line(val timestamp: LocalDateTime, val action: String)

    data class GuardShift(val guardId: Int, val asleepFrom: LocalDateTime? = null, val awakeAt: LocalDateTime? = null)
}
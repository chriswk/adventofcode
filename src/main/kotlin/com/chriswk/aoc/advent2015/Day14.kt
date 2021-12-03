package com.chriswk.aoc.advent2015

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import kotlin.math.min

class Day14: AdventDay(2015, 14) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day14()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
        val logger: Logger = LogManager.getLogger(Day14::class.java)
        val descriptionReg = """^(\S+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds""".toRegex()
    }

    val inputReindeers: List<Reindeer> = inputAsLines.map { Reindeer(it) }

    fun part1(): Int {
        return inputReindeers.maxOf { it.distanceTravelled(2503) }
    }

    fun leaderBoardAfterSeconds(reindeers: List<Reindeer>, seconds: Int): Map<Reindeer, Int> {
        return (1..seconds).map { second ->
            reindeers.maxByOrNull { it.distanceTravelled(second) }!!
        }.foldIndexed(mutableMapOf()) { idx, scores, leading ->
            scores.merge(leading, 1) { old, add -> old + add }
            logger.info("Leader ${leading} at second: ${idx+1} having travelled ${leading.distanceTravelled(idx+1)} with points ${scores[leading]}")
            scores
        }
    }

    fun scoreByLeaderAtSecond(reindeers: List<Reindeer>, seconds: Int): Int {
        return leaderBoardAfterSeconds(reindeers, seconds).values.maxOf { it }
    }

    fun part2(): Int {
        val leaderboard = leaderBoardAfterSeconds(inputReindeers, 2503)
        return scoreByLeaderAtSecond(inputReindeers, 2503)
    }


    data class Reindeer(val name: String, val topSpeed: Int, val stamina: Int, val restTime: Int) {
        constructor(line: String, match: MatchResult.Destructured = descriptionReg.find(line)!!.destructured): this(
            name = match.component1(),
            topSpeed = match.component2().toInt(),
            stamina = match.component3().toInt(),
            restTime = match.component4().toInt()
        )
        val lapDistance = topSpeed * stamina
        val interval = stamina + restTime
        fun fullLaps(seconds: Int): Int = seconds / interval
        fun extraSeconds(seconds: Int): Int = min(seconds % interval, stamina)
        fun distanceTravelled(seconds: Int) = fullLaps(seconds) * lapDistance + extraSeconds(seconds) * topSpeed
    }
}

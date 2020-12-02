package com.chriswk.aoc.advent2018

import java.util.LinkedList
import kotlin.math.absoluteValue

object Day9 {
    val score = """(\d+) players; last marble is worth (\d+)""".toRegex()

    fun play(players: Int, highestMarble: Int): LongArray {
        val scores = LongArray(players)
        val marbles = LinkedList<Int>().also { it.add(0) }
        (1..highestMarble).forEach { marble ->
            when {
                marble % 23 == 0 -> {
                    scores[marble % players] += marble + with(marbles) {
                        shift(-7)
                        removeFirst().toLong()
                    }
                    marbles.shift(1)
                }
                else -> {
                    with(marbles) {
                        shift(1)
                        addFirst(marble)
                    }
                }
            }
        }
        return scores
    }
    fun part1(result:String): Long {
        val (playerCount, lastMarble) = score.find(result)!!.groupValues.drop(1).map { it.toInt() }
        return play(playerCount, lastMarble).maxOrNull()!!
    }

    fun <T> LinkedList<T>.shift(n: Int): Unit =
            when {
                n < 0 -> repeat(n.absoluteValue) {
                    addLast(removeFirst())
                }
                else -> repeat(n) {
                    addFirst(removeLast())
                }
            }

    fun part2(result: String): Long {
        val (playerCount, lastMarble) = score.find(result)!!.groupValues.drop(1).map { it.toInt() }
        return play(playerCount, lastMarble*100).maxOrNull()!!
    }
}
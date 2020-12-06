package com.chriswk.aoc.util

import java.lang.IllegalArgumentException
import kotlin.math.pow

data class Pos(val x: Int, val y: Int) : Comparable<Pos> {
    override fun compareTo(other: Pos): Int {
        val yCmp = y.compareTo(other.y)
        return if(yCmp == 0) {
            return x.compareTo(other.x)
        } else {
            yCmp
        }
    }

    fun next(move: Char): Pos {
        return when(move) {
            in "^N" -> north()
            in "E>" -> east()
            in "Sv" -> south()
            in "W<" -> west()
            else -> throw IllegalArgumentException("Don't know how to go $move")
        }
    }
    fun distanceTo(other: Pos): Int {
        return Math.abs(other.x - x) + Math.abs(other.y - y)
    }
    fun directDistance(other: Pos): Double {
        return Math.sqrt((other.x - x).toDouble().pow(2) + (other.y - y).toDouble().pow(2))
    }

    fun isPositive(): Boolean = x >= 0 && y >= 0
    fun north(): Pos = Pos(x, y - 1)
    fun south(): Pos = Pos(x, y + 1)
    fun west(): Pos = Pos(x - 1, y)
    fun east(): Pos = Pos(x + 1, y)

}
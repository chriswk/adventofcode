package com.chriswk.aoc.util

import kotlin.math.pow

data class Pos(val x: Int, val y: Int) : Comparable<Pos> {
    override fun compareTo(other: Pos): Int {
        val yCmp = y.compareTo(other.y)
        return if (yCmp == 0) {
            return x.compareTo(other.x)
        } else {
            yCmp
        }
    }

    fun foldLeft(onPoint: Int): Pos {
        return if (x > onPoint) {
            copy(x = x - ((x - onPoint) * 2))
        } else {
            this
        }
    }

    fun foldUp(onPoint: Int): Pos {
        return if (y > onPoint) {
            copy(y = y - ((y - onPoint) + 2))
        } else {
            this
        }
    }
    fun next(move: Char?): Pos {
        return when (move) {
            null -> this
            in "^N" -> north()
            in "E>" -> east()
            in "Sv" -> south()
            in "W<" -> west()
            else -> throw IllegalArgumentException("Don't know how to go $move")
        }
    }

    fun manhattanDistanceTo(other: Pos): Int {
        return Math.abs(other.x - x) + Math.abs(other.y - y)
    }

    fun directDistance(other: Pos): Double {
        return Math.sqrt((other.x - x).toDouble().pow(2) + (other.y - y).toDouble().pow(2))
    }

    fun isInGrid(maxX: Int, maxY: Int) = isPositive() && x < maxX && y < maxY
    fun isPositive(): Boolean = x >= 0 && y >= 0
    fun north(): Pos = Pos(x, y - 1)
    fun south(): Pos = Pos(x, y + 1)
    fun west(): Pos = Pos(x - 1, y)
    fun east(): Pos = Pos(x + 1, y)

    fun neighbours(): List<Pos> = listOf(
        Pos(x - 1, y - 1), Pos(x, y - 1), Pos(x + 1, y - 1),
        Pos(x - 1, y), Pos(x + 1, y),
        Pos(x - 1, y + 1), Pos(x, y + 1), Pos(x + 1, y + 1)
    )

    fun cardinalNeighbours(maxX: Int, maxY: Int): List<Pos> {
        return listOf(north(), south(), west(), east()).filter { it.x >= 0 && it.y >= 0 && it.x < maxX && it.y < maxY }
    }

    fun toIndex(width: Int): Int = width * y + x

}

operator fun Pos.plus(other: Pos): Pos {
    return Pos(this.x + other.x, this.y + other.y)
}

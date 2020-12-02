package com.chriswk.aoc.advent2017

object Day1 {
    fun partOne(str: String): Int {
        val sum = str.fold(Pair('a', 0)) { (lastChar, count), c ->
            when (lastChar) {
                c -> Pair(c, count + c.asInt())
                else -> Pair(c, count)
            }
        }.second
        return if (str.first() == str.last()) {
            sum + str.last().asInt()
        } else {
            sum
        }
    }

    fun Char.asInt(): Int {
        return this.toString().toInt()
    }

    fun partTwo(str: String): Int {
        return str.toList().foldInHalf().filter { (first, second) ->
            first == second
        }.sumBy { (first, _) ->
            first.asInt() * 2
        }
    }
}


fun <T> List<T>.foldInHalf(): List<Pair<T, T>> {
    val (first, second) = this.chunked(this.size / 2)
    return first.zip(second)
}


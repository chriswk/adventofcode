package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.Pos
import com.chriswk.aoc.util.report
import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.pow

val light = Color.WHITE
val dark = Color.BLACK

fun color(c: Char) = when (c) {
    '.' -> light
    '#' -> dark
    else -> error("wtf '$c'")
}

operator fun BufferedImage.get(row: Int, column: Int): Int {
    val outside = getRGB(0, 0)
    return (row - 1..row + 1).map { y ->
        if (y !in 0 until height) listOf(outside, outside, outside)
        else (column - 1..column + 1).map { x ->
            if (x !in 0 until width) outside
            else getRGB(x, y)
        }
    }
        .flatten()
        .fold(0) { acc, i -> acc * 2 + if (i == dark.rgb) 1 else 0 }
}

fun BufferedImage.enhanced(alg: List<Color>) =
    BufferedImage(width, height, type).also { result ->
        (0 until width).forEach { x ->
            (0 until height).forEach { y -> result.setRGB(x, y, alg[this[y, x]].rgb) }
        }
    }

fun BufferedImage.count(): Int = (0 until width).sumOf { x ->
    (0 until height).count { y -> getRGB(x, y) == dark.rgb }
}

val border = 50

fun renderAsBitmap(data: List<String>): BufferedImage {
    val image = BufferedImage(
        border + data[0].length + border,
        border + data.size + border,
        BufferedImage.TYPE_BYTE_INDEXED
    )
    (0 until image.width).forEach { x ->
        (0 until image.height).forEach { y -> image.setRGB(x, y, light.rgb) }
    }
    data.forEachIndexed { row, line ->
        line.forEachIndexed { col, c -> image.setRGB(col + border, row + border, color(c).rgb) }
    }

    return image
}

fun part1(input: List<String>): Int {
    val alg = input.first().map { color(it) }
    var image = renderAsBitmap(input.drop(2))
    repeat(2) { image = image.enhanced(alg) }
    return image.count()
}

fun part2(input: List<String>): Int {
    val alg = input.first().map { color(it) }
    var image = renderAsBitmap(input.drop(2))
    repeat(50) { image = image.enhanced(alg) }
    return image.count()
}

class Day20 : AdventDay(2021, 20) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day20()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    data class Scanner(val enhancement: Array<Boolean>, val inputImage: Map<Pos, Boolean>) {
        val lowerRight = inputImage.keys.maxOf { it }
        val upperLeft = inputImage.keys.minOf { it }
        fun findEnhancementIndex(p: Pos): Int {
            return p.enhanceIdx(inputImage)
        }

        private fun Pos.enhanceIdx(image: Map<Pos, Boolean>): Int {
            return this.neighbours(true).foldIndexed(0.0) { idx, acc, curr ->
                val power = 8 - idx
                if (image[curr] == true) {
                    acc + 2.0.pow(power)
                } else {
                    acc
                }
            }.toInt()
        }

        fun enhance(): Sequence<Scanner> {
            return generateSequence(this) { prev ->
                val newImage = (-2..lowerRight.y + 1).flatMap { y ->
                    (-2 .. lowerRight.x + 1).map { x ->
                        val enhanceCoord = Pos(x, y).enhanceIdx(prev.inputImage)
                        Pos(x, y) to prev.enhancement[enhanceCoord]
                    }
                }.toMap()
                this.copy(inputImage = newImage)
            }
        }
    }



    fun parseImage(image: List<String>): Map<Pos, Boolean> {
        return image.flatMapIndexed { y, l ->
            l.mapIndexed { x, c ->
                Pos(x, y) to (c == '#')
            }
        }.toMap()
    }

    fun parseInput(input: String): Scanner {
        val (enhancement, image) = input.split("\n\n")
        return Scanner(enhancement = enhancement.map { it == '#' }.toTypedArray(), parseImage(image.lines()))
    }

    fun enhance(input: String, times: Int): Map<Pos, Boolean> {
        return parseInput(input).enhance().drop(times).first().inputImage
    }


    fun part1(): Int {
        return part1(inputAsLines)
    }

    fun part2(): Int {
        return part2(inputAsLines)
    }

}

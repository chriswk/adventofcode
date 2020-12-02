package com.chriswk.aoc.advent2019

import com.chriswk.aoc.util.dayInputAsString
import com.chriswk.aoc.util.report
import java.lang.IllegalStateException

class Day8 {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day8 = Day8()
            val input = dayInputAsString(2019, 8)
            report { day8.part1(input = input, width = 25, height = 6) }
            report { day8.part2(input = input, width = 25, height = 6); 2}
        }
    }

    fun layers(image: String, wide: Int, height: Int): List<Map<Char, Int>> {
        return image.chunked(wide * height).map { layer -> layer.groupingBy { it }.eachCount() }
    }

    fun part1(input: String, width: Int, height: Int): Int {
        val layers = layers(input, width, height)
        return layers.minByOrNull { it: Map<Char, Int> -> it.getOrDefault('0', 0) }?.let {
            it.getOrDefault('1', 0) * it.getOrDefault('2', 0)
        } ?: throw IllegalStateException("Corrupt image")
    }

    fun List<String>.pixelAt(at: Int): Char =
            if (map { it[at] }.firstOrNull { it != '2' } == '1') {
                '#'
            } else {
                ' '
            }

    fun part2(input: String, width: Int, height: Int) {
        val layers = input.chunked(width * height)
        (0 until width * height)
                .map { layers.pixelAt(it) }
                .chunked(width)
                .forEach { println(it.joinToString(separator = ""))}
    }
}
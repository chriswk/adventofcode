package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.Pos
import com.chriswk.aoc.util.report

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

    data class Scanner(val enhancement: String, val inputImage: Map<Pos, Char>) {
        fun findEnhancementIndex(p: Pos): Int {
            val f = (p.neighbours() + p).sorted()
            val enhancementIndex = f.joinToString(separator = "") { "${inputImage[it] ?: "."}" }
            val binNumber = enhancementIndex.replace('#', '1').replace('.', '0')
            val number = binNumber.toInt(2)
            return number

        }

        fun enhance(): Scanner {
            return copy(inputImage = inputImage.keys.associateWith {
                findNewChar(it)
            })
        }

        fun findNewChar(p: Pos): Char {
            val index = findEnhancementIndex(p)
            return enhancement.get(index)
        }
        fun findLitPixels(): Int {
            return inputImage.values.count { it == '#' }
        }
    }


    fun parseImage(image: List<String>): Map<Pos, Char> {
        return image.flatMapIndexed { y, l ->
            l.mapIndexed { x, c ->
                Pos(x, y) to c
            }
        }.toMap()
    }

    fun parseInput(input: String): Scanner {
        val (enhancement, image) = input.split("\n\n")
        return Scanner(enhancement = enhancement, parseImage(image.lines()))
    }


    fun part1(): Int {
        val inputScanner = parseInput(inputAsString)
        val firstIteration = inputScanner.enhance()
        val secondIteration = firstIteration.enhance()
        return inputScanner.findLitPixels()
    }

    fun part2(): Int {
        return 0
    }

}

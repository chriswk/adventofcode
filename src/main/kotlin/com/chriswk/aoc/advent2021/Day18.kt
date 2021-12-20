package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import java.lang.Math.round
import kotlin.math.roundToInt

class Day18 : AdventDay(2021, 18) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day18()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun findMagnitude(list: List<String>): Int {
        return list.map { parseFish(it) }.reduce { acc, fish -> acc + fish }.magnitude()
    }



    fun part1(): Int {
        return findMagnitude(inputAsLines)
    }

    fun part2(): Int {
        return findBiggestMagnitude(inputAsLines)
    }

    private fun findBiggestMagnitude(inputAsLines: List<String>): Int {
        return inputAsLines.flatMap { l1 ->
            inputAsLines.mapNotNull { l2 ->
                if (l1 != l2) {
                    (parseFish(l1) + parseFish(l2)).magnitude()
                } else {
                    null
                }
            }
        }.maxOrNull()!!
    }

    fun parseFish(input: String): Fish {
        if (!input.startsWith("[")) {
            return FishNumber(input.toInt())
        }
        val contents = input.removeSurrounding("[", "]")
        var nesting = 0
        val index = contents.indexOfFirst {
            if (it == '[') {
                nesting++
            } else if (it == ']') {
                nesting--
            } else if (it == ',' && nesting == 0) return@indexOfFirst true
            false
        }
        return FishPair(parseFish(contents.substring(0, index)), parseFish(contents.substring(index + 1)))
    }

    sealed class Fish {
        abstract fun magnitude(): Int
        abstract fun split(): Fish
        abstract fun shouldSplit(): FishNumber?
        abstract fun shouldExplode(level: Int = 0): FishPair?
        abstract fun flatten(): List<Fish>
        abstract fun replace(replacement: Pair<Fish, Fish>): Fish

        operator fun plus(fish: Fish): Fish {
            val fishPair = FishPair(this, fish)
            return fishPair.reduce()
        }

        fun reduce(): Fish {
            var reduced = this
            while (true) {
                val exploding = reduced.shouldExplode()
                if (exploding != null) {
                    val flattened = reduced.flatten()
                    val index = flattened.indexOf(exploding)
                    reduced = reduced.replace(exploding to FishNumber(0))

                    val numberToLeft = flattened.take(index).filterIsInstance<FishNumber>().lastOrNull()
                    if (numberToLeft != null) {
                        reduced =
                            reduced.replace(numberToLeft to FishNumber(numberToLeft.value + (exploding.left as FishNumber).value))
                    }

                    val numberToRight = flattened.drop(index + 1).filterIsInstance<FishNumber>().drop(2).firstOrNull()
                    if (numberToRight != null) {
                        reduced =
                            reduced.replace(numberToRight to FishNumber(numberToRight.value + (exploding.right as FishNumber).value))
                    }
                    continue
                }
                val splitting = reduced.shouldSplit()
                if (splitting != null) {
                    val pair = FishPair(
                        FishNumber(splitting.value / 2),
                        FishNumber(((splitting.value + 0.5) / 2.0).roundToInt())
                    )
                    reduced = reduced.replace(splitting to pair)
                    continue
                }
                break
            }
            return reduced
        }
    }

    class FishNumber(val value: Int) : Fish() {
        override fun magnitude(): Int = value
        override fun split(): Fish = when {
            value > 9 -> FishPair(FishNumber(value.floorDiv(2)), FishNumber(round(value.toDouble() / 2.0).toInt()))
            else -> this
        }

        override fun shouldSplit(): FishNumber? {
            return if (value > 9) {
                this
            } else {
                null
            }
        }

        override fun shouldExplode(level: Int): FishPair? = null

        override fun flatten(): List<Fish> = emptyList()

        override fun replace(replacement: Pair<Fish, Fish>): Fish {
            return if (this === replacement.first) {
                replacement.second
            } else {
                this
            }
        }

        override fun toString(): String {
            return "$value"
        }
    }

    class FishPair(val left: Fish, val right: Fish) : Fish() {
        override fun magnitude(): Int {
            return left.magnitude() * 3 + right.magnitude() * 2
        }

        override fun split(): Fish {
            return FishPair(left.split(), right.split())
        }

        override fun shouldSplit(): FishNumber? {
            return left.shouldSplit() ?: right.shouldSplit()
        }

        override fun shouldExplode(level: Int): FishPair? {
            return when (level) {
                4 -> this
                else -> left.shouldExplode(level + 1) ?: right.shouldExplode(level + 1)
            }
        }

        override fun flatten(): List<Fish> {
            return listOf(listOf(left), left.flatten(), listOf(right), right.flatten()).flatten()
        }

        override fun replace(replacement: Pair<Fish, Fish>): Fish {
            return if (this === replacement.first) {
                replacement.second
            } else {
                FishPair(left.replace(replacement), right.replace(replacement))
            }
        }

        override fun toString(): String {
            return "[$left,$right]"
        }

    }
}

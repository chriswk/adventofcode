package com.chriswk.aoc

import com.chriswk.aoc.util.dayInputAsLines
import com.chriswk.aoc.util.dayInputAsString

abstract class AdventDay(val year: Int, val day: Int) {
    val inputAsLines = dayInputAsLines(year, day)
    val inputAsString = dayInputAsString(year, day)

}
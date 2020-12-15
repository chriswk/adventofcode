package com.chriswk.aoc

import com.chriswk.aoc.util.dayInputAsString

abstract class AdventDay(year: Int, val day: Int) {
    val inputAsString = dayInputAsString(year, day)
    val inputAsLines = inputAsString.lines()

}
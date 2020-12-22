package com.chriswk.aoc

import com.chriswk.aoc.util.dayInputAsString

abstract class AdventDay(year: Int, val day: Int) {
    val inputAsString by lazy { dayInputAsString(year, day) }
    val inputAsLines by lazy { inputAsString.lines() }

}
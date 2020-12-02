package com.chriswk.aoc.advent2018

data class Cell(val x: Int, val y: Int) {
    val rackId: Int = x + 10
    val initialPowerLevel = rackId * y
}

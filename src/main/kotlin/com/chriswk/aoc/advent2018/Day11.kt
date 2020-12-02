package com.chriswk.aoc.advent2018

object Day11 {

    fun part1(squareSize: Int, gridSerial: Int): Cell {
        return (1..squareSize - 3).flatMap { y ->
            (1..squareSize - 3).map { x ->
                val cell = Cell(x, y)
                Pair(cell, squarePower(cell, gridSerial, 3))
            }
        }.maxByOrNull { it.second }!!.first
    }

    fun part2(squareSize: Int, gridSerial: Int): Pair<Cell, Int> {
        val cells = (1..squareSize).flatMap { y ->
            (1..squareSize).map { x ->
                Cell(y, x)
            }
        }
        val powers = (1..300).map { square ->
            val max = cells.asSequence().filter { it.y + square < 300 && it.x + square < 300 }.map {
                it to squarePower(it, gridSerial, square)
            }.maxByOrNull { it.second }!!
            square to max
        }
        val x = powers.maxByOrNull { it.second.second }!!
        return x.second.first to x.first
    }


    private fun squarePower(topLeft: Cell, gridSerial: Int, squareSize: Int): Int {
        return (topLeft.y until topLeft.y + squareSize).flatMap { y ->
            (topLeft.x until topLeft.x + squareSize).map { x ->
                powerLevel(Cell(x, y), gridSerial)
            }
        }.sum()
    }

    fun powerLevel(cell: Cell, gridSerial: Int): Int {
        val rackIdPwr = (cell.initialPowerLevel + gridSerial) * cell.rackId
        return hundreds(rackIdPwr) - 5
    }

    private fun hundreds(power: Int): Int {
        return (power % 1000) / 100
    }

}
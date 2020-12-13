package com.chriswk.aoc.advent2020

import com.chriswk.aoc.util.Point2D
import com.chriswk.aoc.util.fileToLines
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day12Test {
    val smallInput = """
        F10
        N3
        F7
        R90
        F11
    """.trimIndent().lines()

    @Test
    fun part1SmallInput() {
        val day = Day12()
        val instr = day.readInstructions(smallInput)
        val endShip = day.performInstructions(Day12.Ship(), instr)
        assertThat(endShip.location.manhattanDistance(Point2D.ORIGIN)).isEqualTo(25)
    }

    @Test
    fun testRotationsFromEast() {
        val ship = Day12.Ship(facing = Day12.Direction.East)
        assertThat(ship.turnLeft(1).facing).isEqualTo(Day12.Direction.North)
        assertThat(ship.turnLeft(2).facing).isEqualTo(Day12.Direction.West)
        assertThat(ship.turnLeft(3).facing).isEqualTo(Day12.Direction.South)
    }

    @Test
    fun part1Fmr() {
        val input = "2020/Day12fmr.txt".fileToLines()
        val day = Day12()
        val instr = day.readInstructions(input)
        val endShip = day.performInstructions(Day12.Ship(), instr)
        assertThat(endShip.location.manhattanDistance(Point2D.ORIGIN)).isEqualTo(1496)
    }

    @Test
    fun part1() {
        assertThat(Day12().part1()).isEqualTo(1710)
    }

    @Test
    fun testRotations() {
        val p = Point2D(-4, 10)
        assertThat(p.rotateRight()).isEqualTo(Point2D(10, 4))
        assertThat(p.rotateLeft()).isEqualTo(Point2D(-10, -4))
    }

    @Test
    fun waypointRotationSymmetry() {
        val w = Day12.Waypoint(Point2D(-4, 10))
        assertThat(w.turnLeft(3)).isEqualTo(w.turnRight(1))
        assertThat(w.turnLeft(2)).isEqualTo(w.turnRight(2))
        assertThat(w.turnLeft(1)).isEqualTo(w.turnRight(3))
        assertThat(w.turnLeft(4)).isEqualTo(w)
        assertThat(w.turnRight(4)).isEqualTo(w)
        assertThat(w.turnRight(2)).isNotEqualTo(w)
    }

    @Test
    fun part2SmallInput() {
        val day = Day12()
        val instr = day.readInstructions(smallInput)
        val endShip = day.performInstructionsPart2(Day12.Ship(), Day12.Waypoint(Point2D(-1, 10)), instr)
        assertThat(endShip.location.manhattanDistance(Point2D.ORIGIN)).isEqualTo(286)
    }

    @Test
    fun part2() {
        assertThat(Day12().part2()).isEqualTo(62045)
    }
}
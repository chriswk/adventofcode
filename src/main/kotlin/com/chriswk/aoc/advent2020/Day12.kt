package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.advent2019.Robot
import com.chriswk.aoc.util.Point2D
import com.chriswk.aoc.util.Pos
import com.chriswk.aoc.util.report
import kotlin.math.E

class Day12 : AdventDay(2020, 12) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day12()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun readInstructions(ops: List<String>): List<Pair<Action, Int>> {
        return ops.map {
            Action.valueOf(it.take(1)) to it.drop(1).toInt()
        }
    }

    fun performInstructions(ship: Ship, instructions: List<Pair<Action, Int>>): Ship {
        return instructions.fold(ship) { s, (action, amount) ->
            when (action) {
                Action.N -> s.move(Direction.North, amount)
                Action.E -> s.move(Direction.East, amount)
                Action.S -> s.move(Direction.South, amount)
                Action.W -> s.move(Direction.West, amount)
                Action.L -> s.turnLeft(amount / 90)
                Action.R -> s.turnRight(amount / 90)
                Action.F -> s.forward(amount)
            }
        }
    }

    fun performInstructionsPart2(ship: Ship, waypoint: Waypoint, instructions: List<Pair<Action, Int>>): Ship {
        return instructions.fold(ship to waypoint) { (s, w), (action, amount) ->
            when(action) {
                Action.N -> s to w.move(Direction.North, amount)
                Action.E -> s to w.move(Direction.East, amount)
                Action.S -> s to w.move(Direction.South, amount)
                Action.W -> s to w.move(Direction.West, amount)
                Action.F -> s.copy(location = s.location + (w.location * amount)) to w
                Action.L -> s to w.turnLeft(amount / 90)
                Action.R -> s to w.turnRight(amount / 90)
            }
        }.first
    }

    fun part1(): Int {
        val endShip = performInstructions(Ship(), readInstructions(inputAsLines))
        return endShip.location.manhattanDistance(Point2D.ORIGIN)
    }

    fun part2(): Int {
        val endShip = performInstructionsPart2(Ship(), Waypoint(Point2D(-1, 10)), readInstructions(inputAsLines))
        return endShip.location.manhattanDistance(Point2D.ORIGIN)
    }

    data class Ship(val facing: Direction = Direction.East, val location: Point2D = Point2D(0, 0)) {
        fun move(dir: Direction, amount: Int): Ship {
            return copy(location = location + (dir.offset * amount))
        }

        fun forward(amount: Int): Ship {
            return copy(location = location + (facing.offset * amount))
        }

        fun turnLeft(times: Int): Ship {
            return (0 until times).fold(this) { s, _ ->
                s.copy(facing = s.facing.turnLeft)
            }
        }

        fun turnRight(times: Int): Ship {
            return (0 until times).fold(this) { s, _ ->
                s.copy(facing = s.facing.turnRight)
            }
        }
    }

    data class Waypoint(val location: Point2D) {
        fun move(dir: Direction, amount: Int): Waypoint {
            return copy(location = location + (dir.offset * amount))
        }
        fun turnLeft(times: Int): Waypoint {
            return (0 until times).fold(this) { w, _ ->
                w.copy(location = w.location.rotateLeft())
            }
        }
        fun turnRight(times: Int): Waypoint {
            return (0 until times).fold(this) { w, _ ->
                w.copy(location = w.location.rotateRight())
            }
        }
    }

    sealed class Direction {
        abstract val turnLeft: Direction
        abstract val turnRight: Direction
        abstract val offset: Point2D

        object North : Direction() {
            override val turnLeft = West
            override val turnRight = East
            override val offset = Point2D(-1, 0)
        }
        object East : Direction() {
            override val turnLeft = North
            override val turnRight = South
            override val offset = Point2D(0, 1)
        }
        object South : Direction() {
            override val turnLeft = East
            override val turnRight = West
            override val offset = Point2D(1, 0)
        }
        object West : Direction() {
            override val turnLeft = South
            override val turnRight = North
            override val offset = Point2D(0, -1)
        }
    }
    enum class Action {
        N,
        E,
        S,
        W,
        L,
        R,
        F
    }
}

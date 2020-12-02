package com.chriswk.aoc.advent2018

object Day18 {
    fun part1(input: List<String>, minutes: Int = 10, print: Boolean = false): Int {
        val area = parseInput(input)
        if (print) {
            println("Inital state")
            print(area)
        }
        val endState = (1..minutes).fold(area) { previous, minute ->
            val res = step(previous)
            if (print) {
                println("Minute $minute")
                print(res)
            }
            res
        }.flatten()
        return resourceValueOfPoints(endState)
    }

    private fun resourceValueOfPoints(area: List<Point>): Int {
        return resourceValueOfAreaTypes(area.map { it.areaType })
    }

    private fun resourceValueOfAreaTypes(area: List<AreaType>): Int {
        return area.count { it == AreaType.Tree } * area.count { it == AreaType.Lumberyard }
    }

    fun part2(input: List<String>, target: Int = 1e9.toInt()): Int {
        val area = parseInput(input)
        var previous: List<List<Point>>
        var next = area
        val found = mutableMapOf(signature(area) to 0)
        var minute = 0
        while (true) {
            minute++
            previous = next
            next = step(previous)
            val signature = signature(next)
            if (found.containsKey(signature)) {
                return handleFoundLoop(minute, found, signature, target)
            } else {
                found[signature] = minute
            }
        }
    }

    private fun handleFoundLoop(minute: Int, found: MutableMap<String, Int>, signature: String, target: Int): Int {
        val loopLength = minute - found.getValue(signature)
        val remaining = target - minute
        val extraEntries = remaining % loopLength
        val targetEntry = found.getValue(signature) + extraEntries
        val correctSignature = found.filter { it.value == targetEntry }.keys.first()
        return resourceValueOfAreaTypes(parseLine(correctSignature))
    }

    fun signature(area: List<List<Point>>): String {
        return area.joinToString(separator = "") { it.joinToString(separator = "") { it.toString() } }
    }

    fun print(area: List<List<Point>>) {
        println(area.joinToString(separator = "\n") { line ->
            line.joinToString(separator = "") { it.areaType.toString() }
        })
        println()
    }


    fun step(area: List<List<Point>>): List<List<Point>> {
        return area.map { line ->
            line.map { point -> transition(point, area) }
        }
    }

    fun transition(point: Point, area: List<List<Point>>): Point {
        val neighbours = point.neighbours(area)
        val byType = neighbours.groupBy { it.areaType }
        return when (point.areaType) {
            AreaType.Open -> {
                if ((byType[AreaType.Tree]?.size ?: 0) >= 3) {
                    point.copy(areaType = AreaType.Tree)
                } else {
                    point
                }
            }
            AreaType.Tree -> {
                if ((byType[AreaType.Lumberyard]?.size ?: 0) >= 3) {
                    point.copy(areaType = AreaType.Lumberyard)
                } else {
                    point
                }
            }
            AreaType.Lumberyard -> {
                if ((byType[AreaType.Lumberyard]?.size ?: 0) >= 1 && (byType[AreaType.Tree]?.size ?: 0) >= 1) {
                    point
                } else {
                    point.copy(areaType = AreaType.Open)
                }
            }
        }
    }

    fun parseInput(input: List<String>): List<List<Point>> {
        return input.asSequence().mapIndexed { y, line ->
            line.mapIndexed { x, c ->
                val location = Location(x, y)
                val areaType = parseAreaType(c)
                Point(location, areaType)
            }
        }.toList()
    }

    fun parseLine(line: String): List<AreaType> {
        return line.map { c ->
            parseAreaType(c)
        }
    }

    fun parseAreaType(c: Char): AreaType {
        return when (c) {
            '|' -> AreaType.Tree
            '.' -> AreaType.Open
            '#' -> AreaType.Lumberyard
            else -> throw IllegalArgumentException("Don't know that kind of ground $c")
        }
    }


    data class Location(val x: Int, val y: Int)
    data class Point(val location: Location, val areaType: AreaType) {
        private val neighbourCoords = listOf(
                Location(location.x - 1, location.y - 1),
                Location(location.x, location.y - 1),
                Location(location.x + 1, location.y - 1),
                Location(location.x - 1, location.y),
                Location(location.x + 1, location.y),
                Location(location.x - 1, location.y + 1),
                Location(location.x, location.y + 1),
                Location(location.x + 1, location.y + 1)
        )

        fun neighbours(area: List<List<Point>>) = neighbourCoords.filter {
            it.x > -1 && it.x < area[0].size
        }.filter {
            it.y > -1 && it.y < area.size
        }.map {
            area[it.y][it.x]
        }

        override fun toString(): String {
            return when (areaType) {
                AreaType.Open -> "."
                AreaType.Tree -> "|"
                AreaType.Lumberyard -> "#"
            }
        }

    }

    enum class AreaType {
        Open, Tree, Lumberyard
    }
}
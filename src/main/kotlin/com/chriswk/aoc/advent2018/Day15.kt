package com.chriswk.aoc.advent2018

import java.util.ArrayDeque
import java.util.Deque

object Day15 {


    fun parseInput(input: List<String>): Pair<GameState, Set<Player>> {
        val cave = input.map { it.map { if (it == '#') '#' else '.' }.joinToString(separator = "") }
        val gameState = GameState(cave)
        val players = input.mapIndexed { y, line ->
            line.mapIndexedNotNull { x, c ->
                when (c) {
                    'E' -> Player(Point(x, y), Faction.Elf)
                    'G' -> Player(Point(x, y), Faction.Gnome)
                    else -> null
                }
            }
        }.flatten().toSet()
        return gameState to players
    }

    fun part1(input: List<String>): Int {
        val (gameState, players) = parseInput(input)
        val (rounds, played) = simulate(players, gameState)
        val sum = played.sumBy { it.hitPoints }
        println("Rounds $rounds - Sum: ${sum}")
        return rounds * sum
    }

    fun part2(input: List<String>): Int {
        var attackPower = 4
        val (gameState, players) = parseInput(input)
        val startingElvesCount = players.count { it.faction == Faction.Elf }
        var survivingElves = 0
        var result = (0 to emptySet<Player>())
        while (survivingElves != startingElvesCount) {
            val modifiedPlayers = players.map {
                if (it.faction == Faction.Elf) {
                    it.copy(attack = attackPower)
                } else {
                    it
                }
            }.toSet()
            result = simulate(modifiedPlayers, gameState)
            survivingElves = result.second.count { it.faction == Faction.Elf }
            attackPower++
        }
        val totalHp = result.second.sumBy { it.hitPoints }
        println("${result.first} - ${totalHp}")
        return result.first * totalHp
    }

    tailrec fun closestTarget(current: Set<Point>, gameState: GameState, targets: Set<Point>, ignore: Set<Point> = current): Point? {
        val nextGeneration = current.flatMap { neighbours(it, gameState) }
                .filter { !ignore.contains(it) }
                .toSet()
        if (nextGeneration.isEmpty()) return null
        val found = nextGeneration.filter { reached -> targets.any { reached.isInRange(it) } }
        return if (!found.isEmpty()) {
            found.sortedWith(pointComparator).first()
        } else {
            closestTarget(nextGeneration, gameState, targets, ignore + current)
        }
    }

    fun move(player: Player, gameState: GameState, targets: Set<Point>, occupied: Set<Point>): Point {
        val closest = closestTarget(current = setOf(player.location),
                targets = targets,
                ignore = occupied + player.location,
                gameState = gameState)
        if (closest == null || closest == player.location) {
            return player.location
        }
        val openPoints: Deque<Point> = ArrayDeque()
        val closed = mutableListOf<Point>()
        var current = player.location
        val comeFrom = mutableMapOf<Point, Point>()
        if (targets.any { current.isInRange(it) }) return current
        do {
            if (openPoints.isNotEmpty()) {
                current = openPoints.poll()
            }
            val next = neighbours(current, gameState)
                    .filter { !occupied.contains(it) }
                    .filter { !closed.contains(it) }
                    .filter { !openPoints.contains(it) }
            comeFrom.putAll(next.associateWith { current })
            if (next.contains(closest)) {
                return findFirst(comeFrom, closest, player.location)
            }
            next.forEach { openPoints.offerLast(it) }
            closed.add(current)
        } while (openPoints.isNotEmpty())
        return player.location
    }

    tailrec fun findFirst(comeFrom: Map<Point, Point>, current: Point, startedAt: Point): Point = when {
        (current == startedAt) -> startedAt
        (comeFrom[current] == startedAt) -> current
        else -> findFirst(comeFrom, comeFrom[current]!!, startedAt)
    }

    fun nextRound(players: Set<Player>, gameState: GameState): Pair<Boolean, Set<Player>> {
        val playerTurns = players.sortedWith(playerComparison).toMutableList()
        val doneSoFar = mutableListOf<Player>()
        var roundComplete = true
        while (playerTurns.isNotEmpty()) {
            val curPlayer = playerTurns.removeAt(0)
            val others = (playerTurns + doneSoFar)
            val enemies = others.filter { it.faction != curPlayer.faction }
            if (enemies.isEmpty()) {
                roundComplete = false
            }
            val playerAfterMove = curPlayer.copy(location = move(
                    player = curPlayer,
                    gameState = gameState,
                    targets = enemies.map { it.location }.toSet(),
                    occupied = others.map { it.location }.toSet()
            ))
            val target = enemies.filter { it.location.isInRange(playerAfterMove.location) }.sortedWith(targetComparison).firstOrNull()
            if (target != null) {
                val damagedTarget = target.attackedBy(playerAfterMove)
                playerTurns.updateAfterAttack(target, damagedTarget)
                doneSoFar.updateAfterAttack(target, damagedTarget)
            }
            doneSoFar.add(playerAfterMove)
        }
        return roundComplete to doneSoFar.toSet()
    }

    fun simulate(players: Set<Player>, gameState: GameState): Pair<Int, Set<Player>> {
        var newPlayers = players
        var rounds = 0
        while (newPlayers.distinctBy { it.faction }.size == 2) {
            val round = nextRound(newPlayers, gameState)
            newPlayers = round.second
            if (round.first) rounds++
        }
        return rounds to newPlayers
    }

    fun isPassable(p: Point, gameState: GameState): Boolean = with(p) { gameState.xRange.contains(x) && gameState.yRange.contains(y) && gameState.cave[y][x] == '.' }
    fun neighbours(p: Point, gameState: GameState): List<Point> = listOf(CardinalDirection.North, CardinalDirection.West, CardinalDirection.East, CardinalDirection.South).map { p.move(it) }.filter { isPassable(it, gameState) }
    val playerComparison = compareBy<Player>({ it.location.y }, { it.location.x })
    val targetComparison = compareBy<Player>({ it.hitPoints }, { it.location.y }, { it.location.x })
    val pointComparator = compareBy<Point>({ it.y }, { it.x })

    data class GameState(val cave: List<String>) {
        val xRange: IntRange = 0 until cave[0].length
        val yRange: IntRange = 0 until cave.size
    }

    data class Point(val x: Int, val y: Int) {

        fun distanceTo(other: Point): Int = Math.abs(other.x - x) + Math.abs(other.y - y)
        fun move(direction: CardinalDirection): Point {
            return when (direction) {
                CardinalDirection.North -> copy(y = y - 1)
                CardinalDirection.West -> copy(x = x - 1)
                CardinalDirection.South -> copy(y = y + 1)
                CardinalDirection.East -> copy(x = x + 1)
            }
        }
    }

    fun Point.isInRange(other: Point): Boolean = this.distanceTo(other) == 1
    private fun MutableList<Player>.updateAfterAttack(target: Player, updatedTarget: Player?) {
        val idx = indexOf(target)
        if (idx >= 0) {
            if (updatedTarget == null) {
                removeAt(idx)
            } else {
                this[idx] = updatedTarget
            }
        }
    }

    data class Player(val location: Point, val faction: Faction, val hitPoints: Int = 200, val attack: Int = 3) {
        fun attackedBy(attacker: Player): Player? {
            val remainingHp = hitPoints - attacker.attack
            return if (remainingHp > 0) {
                copy(hitPoints = remainingHp)
            } else {
                null
            }
        }
    }

    enum class Faction {
        Elf, Gnome
    }

    enum class CardinalDirection {
        North, West, East, South
    }
}
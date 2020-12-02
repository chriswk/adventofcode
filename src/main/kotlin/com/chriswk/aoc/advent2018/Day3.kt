package com.chriswk.aoc.advent2018

import com.chriswk.aoc.util.dayInputAsLines

object Day3 {
    val lines = dayInputAsLines(2018, 3)
    val idPattern = """(\d+)""".toRegex()

    fun parseClaim(line: String): Claim {
        val (id, actu) = line.split("@")
        val (startCoord, size) = actu.trim().split(":")
        val (startX, startY) = startCoord.trim().split(",")
        val (deltaX, deltaY) = size.split("x")
        val startYInt = startY.trim().toInt()
        val startXInt = startX.trim().toInt()
        val deltaXInt = deltaX.trim().toInt()
        val deltaYInt = deltaY.trim().toInt()
        return Claim(id = idPattern.find(id)!!.groupValues[0].toInt(),
                startX = startXInt,
                startY = startYInt,
                endX = startXInt + deltaXInt - 1,
                endY = startYInt + deltaYInt - 1)
    }


    fun solution1(): Int {
        return lines
                .map { parseClaim(it) }
                .flatMap { it.toList() }
                .groupBy { it }
                .filter { it.value.size > 1 }
                .size
    }

    fun solution2(): Int {
        val cloth = mutableMapOf<Int, Int>()
        val claims = lines.map { parseClaim(it) }
        val uncovered = claims.map { it.id }.toMutableSet()
        claims.forEach { claim ->
            claim.toList().forEach {spot ->
                val found = cloth.getOrPut(spot) { claim.id }
                if (found != claim.id) {
                    uncovered.remove(found)
                    uncovered.remove(claim.id)
                }
            }
        }
        return uncovered.first()
    }

    data class Claim(val id: Int, val startX: Int, val startY: Int, val endX: Int, val endY: Int) {
        fun toList(): List<Int> {
            return (startX..endX).flatMap { x ->
                (startY..endY).map { y ->
                    y * 1000 + x
                }
            }
        }
    }
}
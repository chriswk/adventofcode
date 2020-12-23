package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.asInt
import com.chriswk.aoc.util.report
import com.chriswk.aoc.util.reportableString

class Day23 : AdventDay(2020, 23) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day23()
            reportableString {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun parse(input: String): Map<Long, Node> {
        var prev: Node = Node(Character.getNumericValue(input[0]).toLong())
        val cups = mutableMapOf(prev.value to prev)
        input.drop(1).forEach {
            val value = Character.getNumericValue(it).toLong()
            val n = Node(value)
            prev.insertAfter(n)
            prev = n
            cups[value] = n
        }
        return cups
    }

    fun buildRest(cups: MutableMap<Long, Node>, current: Node, last: Node): Map<Long, Node> {
        var previous = last
        (10..1_000_000L).forEach { v ->
            val next = Node(v)
            previous.next = next
            next.previous = previous
            cups[v] = next
            previous = next
        }
        previous.next = current
        current.previous = previous
        return cups
    }

    fun step(curr: Long, cups: MutableMap<Long, Node>, maxVal: Long): Pair<Long, MutableMap<Long, Node>> {
        val currentCup = cups.getValue(curr)
        val a = currentCup.next.remove()
        val b = currentCup.next.remove()
        val c = currentCup.next.remove()
        val destination = findDestination(curr, cups, a, b, c, maxVal)
        destination.insertAfter(c)
        destination.insertAfter(b)
        destination.insertAfter(a)
        return currentCup.next.value to cups
    }

    private fun findDestination(
        current: Long,
        cups: MutableMap<Long, Node>,
        a: Node,
        b: Node,
        c: Node,
        maxVal: Long
    ): Node {
        var dest = current - 1
        if (dest < 1) {
            dest = maxVal
        }
        var destination = cups.getValue(dest)
        while (destination == a || destination == b || destination == c) {
            dest -= 1
            if (dest < 1) {
                dest = maxVal
            }
            destination = cups.getValue(dest)
        }
        return destination
    }

    fun trip(input: String, steps: Int = 100): Node {
        val cups = parse(input).toMutableMap()
        val current = Character.getNumericValue(input.first()).toLong()
        return generateSequence((current to cups)) { (curr, cupMap) ->
            step(curr, cupMap, 9)
        }.drop(steps).first().second.getValue(1)
    }

    fun tripPart2(input: String, steps: Int = 10_000_000): Node {
        val cups = parse(input).toMutableMap()
        val current = Character.getNumericValue(input.first()).toLong()
        val last = cups.getValue(Character.getNumericValue(input.last()).toLong())
        buildRest(cups = cups, current = cups.getValue(current), last = last)

        return generateSequence((current to cups)) { (curr, cupMap) ->
            step(curr, cupMap, 1_000_000)
        }.drop(steps).first().second.getValue(1)
    }

    fun part1(): String {
        val startingNode = trip(inputAsString)
        return startingNode.toCircularString()
    }

    fun part2(): Long {
        val startingNode = tripPart2(inputAsString)
        return startingNode.next.value * startingNode.next.next.value
    }

}

class Node(val value: Long) {
    var previous: Node = this
    var next: Node = this

    fun remove(): Node {
        previous.next = next
        next.previous = previous
        return this
    }

    fun jumpAhead(count: Int): Node {
        return (0.until(count)).fold(this) { node, _ -> node.next }
    }

    fun jumpBack(count: Int): Node {
        return (0.until(count)).fold(this) { node, _ -> node.previous }
    }

    fun insertAfter(node: Node): Node {
        next.previous = node
        node.previous = this
        node.next = next
        this.next = node
        return node
    }

    fun insertBefore(node: Node): Node {
        previous.next = node
        node.previous = previous
        node.next = this
        this.previous = node
        return node
    }

    override fun toString(): String {
        return "prev: ${previous.value} -> this: ${value} -> next: ${next.value}"
    }

    fun toCircularString(): String {
        return next.toString(StringBuilder(), this)
    }

    fun toString(prefix: StringBuilder, stop: Node): String {
        return if (this == stop) {
            prefix.toString()
        } else {
            prefix.append(value)
            return next.toString(prefix, stop)
        }
    }
}

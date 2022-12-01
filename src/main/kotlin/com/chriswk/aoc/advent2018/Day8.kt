package com.chriswk.aoc.advent2018

object Day8 {

    fun parseInputToInts(input: List<String>): Sequence<Int> {
        return input.asSequence().flatMap { it.split(" ").asSequence() }.map { it.toInt() }
    }

    fun part1(input: List<String>): Int {
        val ints = parseInputToInts(input).toMutableList()
        return subTree(ints).sumOfMetaData
    }

    fun part2(input: List<String>): Int {
        val ints = parseInputToInts(input).toMutableList()
        return subTree(ints).part2()
    }

    fun subTree(data: MutableList<Int>): Node {
        val numNodes = data.removeAt(0)
        val numMetaData = data.removeAt(0)
        val children = (1..numNodes).map { subTree(data) }
        val metaData = (1..numMetaData).map { data.removeAt(0) }
        return Node(children, metaData)
    }

    data class Node(val children: List<Node>, val metaData: List<Int>) {
        val sumOfMetaData: Int = metaData.sum() + children.sumOf { it.sumOfMetaData }
        fun part2(): Int {
            return if (children.isEmpty()) {
                metaData.sum()
            } else {
                metaData.map {
                    children.getOrElse(it-1) { Node(emptyList(), emptyList()) }.part2()
                }.sum()
            }
        }

    }
}

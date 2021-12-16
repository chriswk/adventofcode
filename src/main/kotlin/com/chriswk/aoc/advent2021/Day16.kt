package com.chriswk.aoc.advent2021

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report
import java.math.BigInteger
import java.util.LinkedList

class Day16 : AdventDay(2021, 16) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day16()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
        val hexCharToBits: Map<Char, Bits> = mapOf(
            '0' to listOf(false, false, false, false),
            '1' to listOf(false, false, false, true),
            '2' to listOf(false, false, true, false),
            '3' to listOf(false, false, true, true),
            '4' to listOf(false, true, false, false),
            '5' to listOf(false, true, false, true),
            '6' to listOf(false, true, true, false),
            '7' to listOf(false, true, true, true),
            '8' to listOf(true, false, false, false),
            '9' to listOf(true, false, false, true),
            'A' to listOf(true, false, true, false),
            'B' to listOf(true, false, true, true),
            'C' to listOf(true, true, false, false),
            'D' to listOf(true, true, false, true),
            'E' to listOf(true, true, true, false),
            'F' to listOf(true, true, true, true)
        )
    }

    fun String.hexToBin() = BigInteger(this, 16).toString(2).replace(' ', '0')
    fun String.toBits() = this.flatMap { hexCharToBits.getValue(it) }
    fun Bits.tail() = this.drop(1)
    fun toLong(bits: Bits): Long {
        return bits.fold(0L) { acc, b -> (acc shl 1) or if (b) 1 else 0 }
    }

    fun hexToBits(input: String): Bits {
        return input.toBits()
    }

    fun parseSubpacketsNumber(bits: Bits, n: Long): Pair<LinkedList<Packet>, Bits> {
        return if (n == 0L)
            (LinkedList<Packet>() to bits)
        else {
            val (packet, bits2) = parsePacket(bits)
            val (packets, bits3) = parseSubpacketsNumber(bits2, n - 1)
            packets.addFirst(packet)
            packets to bits3
        }
    }

    fun parseLiteral(bits: Bits): Pair<Bits, Bits> {
        val (literal, remainder) = bits.splitAt(5)
        return if (literal.first()) {
            val (tailBits, rem) = parseLiteral(remainder)
            (literal.tail() + tailBits) to rem
        } else {
            literal.tail() to remainder
        }
    }

    fun parseSubpacketsLength(bits: Bits): LinkedList<Packet> {
        return if (bits.isEmpty()) {
            LinkedList()
        } else {
            val (packet, remainder) = parsePacket(bits)
            val packets = parseSubpacketsLength(remainder)
            packets.addFirst(packet)
            packets
        }
    }

    fun parsePacket(bits: Bits): Pair<Packet, Bits> {
        val (versionBits, afterVersion) = bits.splitAt(3)
        val version = toLong(versionBits)
        val (typeIdBits, afterTypeId) = afterVersion.splitAt(3)
        val typeId = toLong(typeIdBits)
        return when(typeId) {
            4L -> {
                val (valueBits, rest) = parseLiteral(afterTypeId)
                val value = toLong(valueBits)
                Literal(version, value) to rest
            }
            else -> {
                val (lengthTypeBits, afterLengthType) = afterTypeId.splitAt(1)
                val lengthType = lengthTypeBits.first()
                if (lengthType) {
                    val (numberBits, remainder) = afterLengthType.splitAt(11)
                    val numberOfBits = toLong(numberBits)
                    val (subpackets, rest) = parseSubpacketsNumber(remainder, numberOfBits)
                    Operator(version, typeId, subpackets = subpackets) to rest
                } else {
                    val (number, remainder) = afterLengthType.splitAt(15)
                    val length = toLong(number)
                    val (subpacketsBits, rest) = remainder.splitAt(length.toInt())
                    val subpackets = parseSubpacketsLength(subpacketsBits)
                    Operator(version, typeId, subpackets = subpackets) to rest
                }
            }
        }
    }

    fun sumVersion(packet: Packet): Long = when(packet) {
        is Literal -> packet.version
        is Operator -> packet.version + packet.subpackets.sumOf { sumVersion(it) }
        else -> 0
    }

    fun Bits.splitAt(idx: Int): Pair<Bits, Bits> {
        return this.take(idx) to this.drop(idx)
    }

    fun eval(packet: Packet): Long {
        return when(packet) {
            is Literal -> packet.value
            is Operator -> {
                val subvalues = packet.subpackets.map { eval(it) }
                when(packet.typeId) {
                    0L -> subvalues.sum()
                    1L -> subvalues.fold(1L) { acc, e -> acc * e }
                    2L -> subvalues.minOf { it }
                    3L -> subvalues.maxOf { it }
                    5L -> {
                        val (first, second) = subvalues
                        if (first > second) {
                            1L
                        } else {
                            0L
                        }
                    }
                    6L -> {
                        val (first, second) = subvalues
                        if (first < second) {
                            1L
                        } else {
                            0L
                        }
                    }
                    7L -> {
                        val (first, second) = subvalues
                        if (first == second) {
                            1L
                        } else {
                            0L
                        }
                    }
                    else -> 0
                }
            }
            else -> 0L
        }
    }

    open class Packet(open val version: Long)
    data class Literal(override val version: Long, val value: Long): Packet(version)
    data class Operator(override val version: Long, val typeId: Long, val subpackets: LinkedList<Packet>): Packet(version)

    fun part1(): Long {
        val bits = inputAsString.toBits()
        val (packet, remainder) = parsePacket(bits)
        return sumVersion(packet)
    }

    fun part2(): Long {
        val bits = inputAsString.toBits()
        val (packet, remainder) = parsePacket(bits)
        return eval(packet)
    }

}

typealias Bits = List<Boolean>

package com.chriswk.aoc.util

import kotlinx.coroutines.channels.Channel
import java.io.File
import java.io.InputStream
import java.nio.charset.Charset
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.system.measureTimeMillis

class Util {}
val utf8 = Charset.forName("UTF-8")
fun String.toInputStream() = Util::class.java.classLoader.getResourceAsStream(this)
fun InputStream.lines() = this.readBytes().toString(utf8).lines()
fun String.fileToLines(): List<String> {
    val path = "/$this"
    val res = object {}.javaClass.getResource(path)
    return File(res.toURI()).readLines()
}

fun dayInputAsLines(year: Int, day: Int): List<String> {
    return "$year/Day$day.txt".fileToLines()
}
fun dayInputAsString(year:Int, day: Int): String {
    return "$year/Day$day.txt".fileToString()
}

fun String.fileToString(): String {
    val path = "/$this"
    return object {}.javaClass.getResource(path).readText().substringBeforeLast("\n")
}
fun String.isNumber(base: Int = 10): Boolean {
    return this.toLongOrNull(base) != null
}
fun String.isNumberBetween(min: Long, max: Long, base: Int = 10): Boolean {
    return this.toLongOrNull(base) in min..max
}

fun <T> List<T>.permutations(): List<List<T>> {
    return if (this.size <= 1) {
        listOf(this)
    } else {
        val elementToInsert = first()
        drop(1).permutations().flatMap { permutation ->
            (0..permutation.size).map { i ->
                permutation.toMutableList().apply { add(i, elementToInsert) }
            }
        }
    }
}

suspend fun <T> Channel<T>.andSend(msg: T) : Channel<T> = this.also { send(msg) }

fun <T> List<T>.toChannel(capacity: Int = Channel.UNLIMITED): Channel<T> {
    return Channel<T>(capacity).also { this.forEach { e -> it.offer(e) }}
}

fun report(f: () -> Number) {
    var ans: Number?
    val timeTaken = measureTimeMillis { ans = f() }
    println("Answer [$ans] - took $timeTaken ms")
}

fun parseInstructions(instructions: String): IntArray {
    return instructions.split(",").map { it.toInt() }.toIntArray()
}

fun parseBigInstructions(instructions: String): LongArray {
    return instructions.split(",").map { it.toLong() }.toLongArray()
}
tailrec fun gcd(a: Int, b: Int): Int = if(a == 0) kotlin.math.abs(b) else gcd(b % a, a)
tailrec fun gcd(a: Long, b: Long): Long = if(a == 0L) kotlin.math.abs(b) else gcd(b % a, a)
fun IntArray.toMutableMap(): MutableMap<Long, Long> = this.withIndex().associate { it.index.toLong() to it.value.toLong() }.toMutableMap()
fun LongArray.toMutableMap(): MutableMap<Long, Long> = this.withIndex().associate { it.index.toLong() to it.value }.toMutableMap()
fun <T> Iterable<T>.combinations(length: Int) =
        sequence {
            @Suppress("UNCHECKED_CAST")
            val pool = this as? List<T> ?: toList()
            val n = pool.size
            if(length > n) return@sequence
            val indices = IntArray(length) { it }
            while(true) {
                yield(indices.map { pool[it] })
                var i = length
                do {
                    i--
                    if(i == -1) return@sequence
                } while(indices[i] == i + n - length)
                indices[i]++
                for(j in i+1 until length) indices[j] = indices[j - 1] + 1
            }
        }
val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
fun <T> List<T>.infiniteCycle(): Sequence<T> {
    val data = this
    return sequence {
        while(true) {
            yieldAll(data)
        }
    }
}

fun <T> Sequence<T>.infiniteCycle(): Sequence<T> {
    val seq = this
    return sequence {
        while(true) {
            yieldAll(seq)
        }
    }
}
internal class LocalDateTimeProgressionIterator(start: LocalDateTime, val endInclusive: LocalDateTime, val stepMinutes: Long) : Iterator<LocalDateTime> {

    var current = start
    override fun hasNext() = current < endInclusive

    override fun next(): LocalDateTime {
        val next = current
        current = current.plusMinutes(stepMinutes)
        return next
    }
}

operator fun LocalDateTime.rangeTo(other: LocalDateTime) = LocalDateTimeProgression(this, other)

class LocalDateTimeProgression(override val start: LocalDateTime, override val endInclusive: LocalDateTime, val stepMinutes: Long = 1) : Iterable<LocalDateTime>, ClosedRange<LocalDateTime> {
    override fun iterator(): Iterator<LocalDateTime> = LocalDateTimeProgressionIterator(start, endInclusive, stepMinutes)
    infix fun step(minutes: Long) = LocalDateTimeProgression(start, endInclusive, minutes)
}

fun String.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.parse(this, format)
}
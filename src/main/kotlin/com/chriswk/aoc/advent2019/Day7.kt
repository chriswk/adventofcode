package com.chriswk.aoc.advent2019

import com.chriswk.aoc.util.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import com.chriswk.aoc.advent2019.IntCodeComputer

class Day7(val program: IntArray) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day7 = Day7(parseInstructions(dayInputAsString(2019, 7)))
            report { day7.part1() }
            report { day7.part2() }
        }
        operator fun invoke(): Day7 {
            return Day7(parseInstructions(dayInputAsString(2019, 7)))
        }
    }

    fun part1(): Long {
        return listOf(0L,1L,2L,3L,4L).permutations().map { run(settings = it) }.maxOrNull() ?: Long.MIN_VALUE
    }
    fun part2(): Long = runBlocking {
        listOf(5L,6L,7L,8L,9L).permutations().map { runAmplified(it) }.maxOrNull() ?: Long.MIN_VALUE
    }

    fun run(settings: List<Long>): Long {
        return (0..4).fold(0L) { prev, id ->
            val cpu = IntCodeComputer(program = program.copyOf().toMutableMap(), input = mutableListOf(settings[id], prev).toChannel())
            cpu.run().lastOrNull() ?: Long.MIN_VALUE
        }
    }

    private suspend fun runAmplified(settings: List<Long>) = coroutineScope {
        val a = IntCodeComputer(program.copyOf().toMutableMap(), listOf(settings[0], 0).toChannel())
        val b = IntCodeComputer(program.copyOf().toMutableMap() ,a.output.andSend(settings[1]))
        val c = IntCodeComputer(program.copyOf().toMutableMap(), b.output.andSend(settings[2]))
        val d = IntCodeComputer(program.copyOf().toMutableMap(), c.output.andSend(settings[3]))
        val e = IntCodeComputer(program.copyOf().toMutableMap(), d.output.andSend(settings[4]))
        val channelSpy = ChannelSpy(e.output, a.input)
        coroutineScope {
            launch { channelSpy.listen() }
            launch { a.runSuspending() }
            launch { b.runSuspending() }
            launch { c.runSuspending() }
            launch { d.runSuspending() }
            launch { e.runSuspending() }
        }
        channelSpy.spy.receive()
    }

}

class ChannelSpy<T>(val input: Channel<T>, private val output: Channel<T>, val spy: Channel<T> = Channel(Channel.CONFLATED)) {
    suspend fun listen() = coroutineScope {
        for(received in input) {
            spy.send(received)
            output.send(received)
        }
    }
}
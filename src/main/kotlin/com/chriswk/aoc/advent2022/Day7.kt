package com.chriswk.aoc.advent2022

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.report

class Day7: AdventDay(2022, 7) {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val day = Day7()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun parseCommands(input: List<String>): MutableMap<String, Directory> {
        var directories : MutableMap<String, Directory> = mutableMapOf()
        var currentDir : Directory? = null
        input.forEach { line ->
            if (line.startsWith("$")) {
                    val args = line.split(" ")
                    when (args[1]) {
                        "cd" -> {
                            currentDir = when (args[2]) {
                                "/" -> {
                                    directories.computeIfAbsent("/") { path -> Directory(path = "/") }
                                }

                                ".." -> {
                                    currentDir?.parent ?: directories["/"]
                                }

                                else -> {
                                    val key = path(currentDir!!.path, args[2])
                                    currentDir = directories.computeIfAbsent(key) { path ->
                                        Directory(
                                            path = path,
                                            parent = currentDir ?: directories["/"]
                                        )
                                    }
                                    currentDir
                                }
                            }
                        }
                        "ls" -> {}
                    }
                }
            else if (line.startsWith("dir")) {
                val (_, name) = line.split(" ")
                val key = path(currentDir!!.path, name)
                val dir = directories.computeIfAbsent(key) { Directory(path = it, parent = currentDir!!) }
                currentDir!!.folders[name] = dir
            } else {
                val (size, name) = line.split(" ")
                val file = File(size.toLong(10), name)
                currentDir!!.files.add(file)
            }
        }
        return directories
    }

    fun path(folder: String, file: String): String {
        return listOf(folder, file).joinToString(separator = "/").replace("//", "/")
    }

    fun foldersSmallerThan(bytes: Long, tree: Map<String, Directory>): List<Long> {
        return tree.values.asSequence().map { it.fileSize() }.filter { it < bytes }.toList()
    }

    fun candidateSize(folders: List<Long>, totalSize: Long, requiredSize: Long): Long {
        val fileSizes = folders.sorted()
        val freeSpace = totalSize - fileSizes.last()
        val missingSpace = requiredSize - freeSpace
        return fileSizes.first { it > missingSpace }
    }
    fun part1(): Long {
        return foldersSmallerThan(100000L, parseCommands(inputAsLines)).sum()
    }

    fun part2(): Long {
        return candidateSize(parseCommands(inputAsLines).values.map { it.fileSize() }, 70000000, 30000000)
    }

    data class Directory(val path: String, val parent: Directory? = null, val files: MutableSet<File> = mutableSetOf(), val folders: MutableMap<String, Directory> = mutableMapOf()) {
        fun fileSize(): Long {
            val fileSize = files.sumOf { it.size }
            val folderSize = folders.values.map { it.fileSize() }
            return fileSize + folderSize.sum()
        }
        override fun toString(): String {
            return "Directory(path='$path')"
        }
    }

    data class File(val size: Long, val name: String)
}

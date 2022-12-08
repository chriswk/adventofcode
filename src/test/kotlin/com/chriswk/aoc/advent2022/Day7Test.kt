package com.chriswk.aoc.advent2022

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day7Test {
    val day = Day7()
    val testInput = """
        $ cd /
        $ ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        $ cd a
        $ ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        $ cd e
        $ ls
        584 i
        $ cd ..
        $ cd ..
        $ cd d
        $ ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent()

    @Test
    fun can_parse_test_input() {
        val tree = day.parseCommands(testInput.lines())
        assertThat(tree["/a/e"]!!.fileSize()).isEqualTo(584)
        assertThat(tree["/a"]!!.fileSize()).isEqualTo(94853)
        assertThat(tree["/d"]!!.fileSize()).isEqualTo(24933642)
        assertThat(tree["/"]!!.fileSize()).isEqualTo(48381165)
    }

    @Test
    fun findsCorrectSizeOfTestInputFoldersBelow100000() {
        val tree = day.parseCommands(testInput.lines())
        assertThat(day.foldersSmallerThan(100000, tree).sum()).isEqualTo(95437)
    }

    @Test
    fun part1() {
        assertThat(day.part1()).isEqualTo(1581595)
    }

    @Test
    fun finds_size_to_delete_for_test_input() {
        val tree = day.parseCommands(testInput.lines())
        assertThat(day.candidateSize(tree.values.map { it.fileSize() }, 70000000, 30000000)).isEqualTo(24933642L)
    }

    @Test
    fun part2() {
        assertThat(day.part2()).isEqualTo(1544176)
    }
}

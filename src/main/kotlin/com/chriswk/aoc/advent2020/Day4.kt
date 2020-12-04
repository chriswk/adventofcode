package com.chriswk.aoc.advent2020

import com.chriswk.aoc.AdventDay
import com.chriswk.aoc.util.isNumber
import com.chriswk.aoc.util.isNumberBetween
import com.chriswk.aoc.util.report

class Day4 : AdventDay(2020, 4) {

    companion object {
        @JvmStatic
        fun main(args: Array<String>): Unit {
            val day = Day4()
            report {
                day.part1()
            }
            report {
                day.part2()
            }
        }
    }

    fun part1(): Int {
        return passports.count { it.isValidPart1() }
    }

    fun part2(): Int {
        return passports.count { it.isValidPart2() }
    }

    val passports = parse(inputAsLines)
    fun parse(input: List<String>): List<Passport> {
        return input.fold(mutableListOf<String>()) { a, n ->
            if (n.isBlank()) {
                a.add("")
                a
            } else {
                if (a.isEmpty()) {
                    a.add(n)
                } else {
                    a.set(a.size - 1, a.get(a.size - 1) + " " + n)
                }
                a
            }
        }.map {
            val data = it.trim().split(" ").map { it.split(":") }
            val properties = data.associate { it[0] to it[1] }
            Passport(
                byr = properties["byr"],
                iyr = properties["iyr"],
                eyr = properties["eyr"],
                hgt = properties["hgt"],
                hcl = properties["hcl"],
                ecl = properties["ecl"],
                pid = properties["pid"],
                cid = properties["cid"]
            )
        }
    }


    data class Passport(
        val byr: String? = null,
        val iyr: String? = null,
        val eyr: String? = null,
        val hgt: String? = null,
        val hcl: String? = null,
        val ecl: String? = null,
        val pid: String? = null,
        val cid: String? = null
    ) {
        fun byrValidPart1() = byr?.isNotBlank() ?: false
        fun iyrValidPart1() = iyr?.isNotBlank() ?: false
        fun eyrValidPart1() = eyr?.isNotBlank() ?: false
        fun hgtValidPart1() = hgt?.isNotBlank() ?: false
        fun hclValidPart1() = hcl?.isNotBlank() ?: false
        fun eclValidPart1() = ecl?.isNotBlank() ?: false
        fun pidValidPart1() = pid?.isNotBlank() ?: false
        fun cidValidPart1() = cid?.isNotBlank() ?: true
        fun byrValidPart2() = byr != null && byr.isNumberBetween(1920, 2002)
        fun iyrValidPart2() = iyr != null && iyr.isNumberBetween(2010, 2020)
        fun eyrValidPart2() = eyr != null && eyr.isNumberBetween(2020, 2030)
        fun hgtValidPart2() = hgt != null && (hgt.endsWith("cm") && hgt.substringBeforeLast("cm").isNumberBetween(150, 193)
                || hgt.endsWith("in") && hgt.substringBeforeLast("in").isNumberBetween(59, 76))
        fun hclValidPart2() = hcl != null && hcl.startsWith("#") && hcl.substringAfter("#").isNumber(16)
        fun eclValidPart2() = ecl != null && ecl in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
        fun pidValidPart2() = pid != null && pid.matches(Regex("""\d{9}"""))
        fun isValidPart1() : Boolean {
            return byrValidPart1() &&
                    iyrValidPart1() &&
                    eyrValidPart1() &&
                    hgtValidPart1() &&
                    hclValidPart1() &&
                    eclValidPart1() &&
                    pidValidPart1() &&
                    cidValidPart1()
        }
        fun isValidPart2() : Boolean {
            return byrValidPart2() &&
                    iyrValidPart2() &&
                    eyrValidPart2() &&
                    hgtValidPart2() &&
                    hclValidPart2() &&
                    eclValidPart2() &&
                    pidValidPart2()
        }
    }


}
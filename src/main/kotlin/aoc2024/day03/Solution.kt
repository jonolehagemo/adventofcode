package aoc2024.day03

import extensions.filePathToStringList
import extensions.println

fun String.sumMultiplications(): Int =
    Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
        .findAll(this)
        .sumOf { it.groupValues[1].toInt() * it.groupValues[2].toInt() }

fun String.sumFromDoToDont(): Int =
    Regex("do\\(\\)(.*?)don't\\(\\)")
        .findAll("do()${this}don't()")
        .sumOf { it.groupValues[1].sumMultiplications() }

fun main() {
    val input = "aoc2024/Day03Input.txt".filePathToStringList().joinToString("")
    input.sumMultiplications().println()
    input.sumFromDoToDont().println()
}

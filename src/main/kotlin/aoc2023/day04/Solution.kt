package aoc2023.day04

import extensions.filePathToStringList
import extensions.println
import kotlin.math.pow

fun String.toIntSet(): Set<Int> = this.trim().split("\\s+".toRegex()).map { it.toInt() }.toSet()

fun List<String>.toWinningNumbersCount(): List<Int> = map { line ->
    val (winners, numbers) = line.substringAfter(":").split("|")
    (winners.toIntSet() intersect numbers.toIntSet()).size
}

fun List<Int>.sumCopies(): Int =
    reversed().fold(emptyList<Int>()) { result, acc -> listOf(1 + result.subList(0, acc).sum()) + result }.sum()

fun main() {
    val wins = "aoc2023/Day04Input.txt".filePathToStringList().toWinningNumbersCount()
    wins.sumOf { 2.0.pow(it - 1).toInt() }.println()
    wins.sumCopies().println()
}
package aoc2023.day04

import extensions.filePathToStringList
import extensions.println
import kotlin.math.pow

fun String.toSet(): Set<String> = trim().split("\\s+".toRegex()).toSet()

fun List<String>.toWinningNumbersCount(): List<Int> =
    map { it.substringAfter(":").split("|").let { (w, n) -> (w.toSet() intersect n.toSet()).size } }

fun List<Int>.sumCopies(): Int = reversed().fold(listOf(0)) { r, i -> listOf(1 + r.subList(0, i).sum()) + r }.sum()

fun main() {
    val wins = "aoc2023/Day04Input.txt".filePathToStringList().toWinningNumbersCount()
    wins.sumOf { 2.0.pow(it - 1).toInt() }.println()
    wins.sumCopies().println()
}

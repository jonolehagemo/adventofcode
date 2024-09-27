package aoc2023.day04

import extensions.filePathToStringList
import extensions.println
import kotlin.math.pow

fun String.toIntSet(): Set<Int> = this.trim().split("\\s+".toRegex()).map { it.toInt() }.toSet()

fun List<String>.toWinningNumbersCount(): List<Int> = map { line ->
    val (winners, numbers) = line.substringAfter(":").split("|")
    (winners.toIntSet() intersect numbers.toIntSet()).size
}

fun countCopies(index: Int, copyCards: List<List<Int>>): Int = 1 + copyCards[index].sumOf { countCopies(it, copyCards) }

fun List<Int>.sumCopies(): Int {
    val copies = this.mapIndexed { index, value ->
        List(this.subList(index + 1, index + 1 + value).size) { subIndex -> index + 1 + subIndex }
    }

    return copies.withIndex().sumOf { (index, _) -> countCopies(index, copies) }
}

fun main() {
    val wins = "aoc2023/Day04Input.txt".filePathToStringList().toWinningNumbersCount()
    wins.sumOf { 2.0.pow(it - 1).toInt() }.println()
    wins.sumCopies().println()
}
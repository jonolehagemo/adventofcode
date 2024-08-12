package aoc2023.day04

import extensions.filePathToStringList
import kotlin.math.pow

fun String.toIntSet(): Set<Int> = this.trim().split("\\s+".toRegex()).map { it.toInt() }.toSet()

fun List<String>.toWinningNumbersCount(): List<Int> = this.map { line ->
    val (winners, numbers) = line.substring(line.indexOf(":") + 1).split("|")
    return@map (winners.toIntSet() intersect numbers.toIntSet()).size
}

fun Int.toPoints(): Int = 2.toDouble().pow(this - 1).toInt()

fun countCopies(index: Int, copyCards: List<List<Int>>): Int = 1 + copyCards[index].sumOf { countCopies(it, copyCards) }

fun task1(cards: List<Int>): Int = cards.sumOf { it.toPoints() }

fun task2(cards: List<Int>): Int {
    val copies = cards.mapIndexed { index, value ->
        List(cards.subList(index + 1, index + 1 + value).size) { subIndex -> index + 1 + subIndex }
    }

    return copies.withIndex().sumOf { (index, _) -> countCopies(index, copies) }
}

fun main() {
    val lines = "aoc2023/Day04Input.txt".filePathToStringList()
    val cards = lines.toWinningNumbersCount()
    println("task1 ${task1(cards)}")
    println("task2 ${task2(cards)}")
}
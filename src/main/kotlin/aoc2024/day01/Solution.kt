package aoc2024.day01

import extensions.filePathToStringList
import extensions.println
import kotlin.math.abs

fun Pair<List<Int>, List<Int>>.sumDistances(): Int = first.sorted().zip(second.sorted()) { l, r -> abs(l - r) }.sum()

fun Pair<List<Int>, List<Int>>.sumSimilarityScore(): Int {
    val scores = second.groupingBy { it }.eachCount()
    return first.sumOf { it * scores.getOrDefault(it, 0) }
}

fun main() {
    val input = "aoc2024/Day01Input.txt".filePathToStringList()
    val left = input.map { it.substringBefore(' ').toInt() }
    val right = input.map { it.substringAfterLast(' ').toInt() }
    (left to right).sumDistances().println()
    (left to right).sumSimilarityScore().println()
}

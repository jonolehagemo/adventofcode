package aoc2023.day13

import extensions.filePathToListOfStringList
import extensions.println
import extensions.transpose

fun List<String>.patternSum(difference: Int): Int = sum(difference) * 100 + transpose().sum(difference)

fun List<String>.sum(expectedDifference: Int): Int =
    (1..<size)
        .map { index ->
            index to subList(0, index).reversed().joinToString("|")
                .zip(subList(index, size).joinToString("|"))
                .count { (a, b) -> a != b }
        }
        .firstOrNull { (_, actualDifference) -> actualDifference == expectedDifference }
        ?.first ?: 0

fun main() {
    "aoc2023/Day13Input.txt".filePathToListOfStringList().sumOf { it.patternSum(0) }.println()
    "aoc2023/Day13Input.txt".filePathToListOfStringList().sumOf { it.patternSum(1) }.println()
}
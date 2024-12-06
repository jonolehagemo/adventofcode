package aoc2024.day02

import extensions.filePathToStringList
import extensions.println

fun List<Int>.isSafe(levels: Int): Boolean =
    when {
        0 < levels -> indices.any { index -> filterIndexed { i, _ -> i != index }.isSafe(0) }
        (zipWithNext { a, b -> b - a }.all { it in 1..3 })
            .or(zipWithNext { a, b -> b - a }.all { it in -3..-1 }) -> true
        else -> false
    }

fun main() {
    val input = "aoc2024/Day02Input.txt".filePathToStringList().map { line -> line.split(' ').map { it.toInt() } }
    input.count { it.isSafe(0) }.println()
    input.count { it.isSafe(1) }.println()
}

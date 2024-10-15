package aoc2022.day06

import extensions.filePathToString
import extensions.println

fun String.marker(size: Int): Int =
    windowed(size)
        .withIndex()
        .first { it.value.toSet().size == size }
        .index
        .plus(size)

fun main() {
    val input = "aoc2022/Day06Input.txt".filePathToString()
    input.marker(4).println()
    input.marker(14).println()
}

package aoc2022.day03

import extensions.filePathToStringList
import extensions.println

fun String.splitAtIndex(index: Int): List<String> = listOf(take(index), substring(index))

fun List<String>.findCommonChar(): Char = map { it.toSet() }.reduce { result, item -> result intersect item }.first()

fun Char.toValue(): Int = if ('a'.code <= code && code <= 'z'.code) code - 'a'.code + 1 else code - 'A'.code + 27

fun main() {
    val input = "aoc2022/Day03Input.txt".filePathToStringList()
    input.sumOf { it.splitAtIndex(it.length / 2).findCommonChar().toValue() }.println()
    input.chunked(3).sumOf { it.findCommonChar().toValue() }.println()
}

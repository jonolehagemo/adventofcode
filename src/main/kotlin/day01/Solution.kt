package day01

import extensions.filePathToStringList
import extensions.println

fun String.findFirst(mapping: Map<String, Char>): Char =
    if (first().isDigit()) first()
    else if (mapping.keys.any { startsWith(it) }) mapping.filterKeys { startsWith(it) }.values.first()
    else drop(1).findFirst(mapping)

fun String.findLast(mapping: Map<String, Char>): Char =
    if (last().isDigit()) last()
    else if (mapping.keys.any { endsWith(it) }) mapping.filterKeys { endsWith(it) }.values.last()
    else dropLast(1).findLast(mapping)

fun String.calculate(mapping: Map<String, Char>): Int = "".plus(findFirst(mapping)).plus(findLast(mapping)).toInt()

fun main() {
    val mapping = mapOf(
        "one" to '1', "two" to '2', "three" to '3', "four" to '4', "five" to '5', "six" to '6', "seven" to '7',
        "eight" to '8', "nine" to '9'
    )
    "Day01Input.txt".filePathToStringList().sumOf { it.calculate(emptyMap()) }.println()
    "Day01Input.txt".filePathToStringList().sumOf { it.calculate(mapping) }.println()
}
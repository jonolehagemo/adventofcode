package day01

import java.io.File

fun findFirst(line: String, mapping: Map<String, Char>): Char =
    if (line.first().isDigit()) line.first()
    else if (mapping.keys.any { line.startsWith(it) }) mapping.filterKeys { line.startsWith(it) }.values.first()
    else findFirst(line.drop(1), mapping)

fun findLast(line: String, mapping: Map<String, Char>): Char =
    if (line.last().isDigit()) line.last()
    else if (mapping.keys.any { line.endsWith(it) }) mapping.filterKeys { line.endsWith(it) }.values.last()
    else findLast(line.dropLast(1), mapping)

fun process(filePath: String, mapping: Map<String, Char>): Int =
    File(ClassLoader.getSystemResource(filePath).file)
        .readLines()
        .sumOf { "".plus(findFirst(it, mapping)).plus(findLast(it, mapping)).toInt() }

fun main() {
    println(process("Day01Input.txt", emptyMap()))
    val mapping = mapOf(
        "one" to '1', "two" to '2', "three" to '3', "four" to '4', "five" to '5', "six" to '6', "seven" to '7',
        "eight" to '8', "nine" to '9'
    )
    println(process("Day01Input.txt", mapping))
}
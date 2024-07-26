package day01

import extensions.filePathToStringList
import extensions.println


fun String.replaced(): String =
    listOf("one o1e", "two t2o", "three t3e", "four f4r", "five f5e", "six s6x", "seven s7n", "eight e8t", "nine n9e")
        .fold(this) { result, s -> result.replace(s.split(" ")[0], s.split(" ")[1]) }

fun String.toInteger(): Int = ("" + first { it.isDigit() } + last { it.isDigit() }).toInt()

fun main() {
    "Day01Input.txt".filePathToStringList().sumOf { it.toInteger() }.println()
    "Day01Input.txt".filePathToStringList().sumOf { it.replaced().toInteger() }.println()
}
package aoc2022.day25

import extensions.filePathToStringList
import extensions.println
import kotlin.math.pow

fun Long.toSnafu(): String {
    val list =
        this
            .toString(5)
            .map { it.toString() }
            .reversed()
            .toMutableList()

    (0..<list.size).forEach { index ->
        when (list[index]) {
            "3" -> {
                list[index] = "="
                if (index + 1 < list.size) {
                    list[index + 1] = list[index + 1].toInt().plus(1).toString()
                } else {
                    list.add("1")
                }
            }
            "4" -> {
                list[index] = "-"
                if (index + 1 < list.size) {
                    list[index + 1] = list[index + 1].toInt().plus(1).toString()
                } else {
                    list.add("1")
                }
            }
            "5" -> {
                list[index] = "0"
                if (index + 1 < list.size) {
                    list[index + 1] = list[index + 1].toInt().plus(1).toString()
                } else {
                    list.add("1")
                }
            }
            else -> list[index]
        }
    }

    return list.reversed().joinToString("")
}

fun String.toDecimal(): Long =
    foldIndexed(0) { index, result, char ->
        val place = 5.0.pow(-1.0 + length - index).toLong()
        val digit =
            when (char) {
                '=' -> -2
                '-' -> -1
                else -> char.digitToInt()
            }
        result + (digit * place)
    }

fun main() {
    val input = "aoc2022/Day25Input.txt".filePathToStringList()
    input.sumOf { it.toDecimal() }.toSnafu().println()
}

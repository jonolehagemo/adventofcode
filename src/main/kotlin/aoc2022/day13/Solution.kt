package aoc2022.day13

import extensions.filePathToListOfStringList
import extensions.println

fun String.firstToken(): String {
    if (!first().isDigit()) {
        return first().toString()
    }

    toCharArray().fold("") { s, c ->
        if (c.isDigit()) {
            return@fold s + c
        } else {
            return s
        }
    }

    return ""
}

fun compare(
    a: String,
    b: String,
): Int {
    var leftString = a
    var rightString = b

    while (leftString.isNotBlank()) {
        val left = leftString.firstToken()
        val right = rightString.firstToken()
        leftString = leftString.drop(left.length)
        rightString = rightString.drop(right.length)

        when {
            left == right -> continue
            right.isBlank() -> return 1
            left.first().isDigit() && right.first().isDigit() -> {
                return if (left.toInt() <= right.toInt()) -1 else 1
            }
            left.first().isDigit() && right == "[" -> leftString = "$left]$leftString"
            left == "[" && right.first().isDigit() -> rightString = "$right]$rightString"
            left == "]" -> return -1
            right == "]" -> return 1
            else -> continue
        }
    }

    return 0
}

fun main() {
    val input =
        "aoc2022/Day13Input.txt"
            .filePathToListOfStringList()
    input
        .withIndex()
        .map { (index, list) -> IndexedValue(index, compare(list[0], list[1])) }
        .filter { (_, value) -> value == -1 }
        .sumOf { it.index + 1 }
        .println()
    val added = listOf("[[2]]", "[[6]]")
    input
        .flatten()
        .plus(added)
        .sortedWith { a, b -> compare(a, b) }
        .withIndex()
        .filter { it.value in added }
        .map { it.index + 1 }
        .reduce { a, b -> a * b }
        .println()
}

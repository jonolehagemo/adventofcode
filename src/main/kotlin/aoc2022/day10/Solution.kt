package aoc2022.day10

import extensions.filePathToStringList
import extensions.println

fun List<String>.signalStrengths(): List<Int> {
    var x = 1
    var toAdd = 0
    val result = mutableListOf<Int>()

    for (instruction in this) {
        x += toAdd
        result.add(x)

        if (instruction == "noop") {
            toAdd = 0
        } else {
            result.add(x)
            toAdd = instruction.substringAfter(" ").toInt()
        }
    }

    return result
}

fun List<Int>.toImage(): String =
    this
        .chunked(40)
        .joinToString("\n") { line ->
            line
                .withIndex()
                .joinToString("") { (index, value) ->
                    if (index in (value - 1..value + 1)) "#" else "."
                }
        }

fun main() {
    val signalStrengths =
        "aoc2022/Day10Input.txt"
            .filePathToStringList()
            .signalStrengths()
    signalStrengths
        .withIndex()
        .filter { (index, _) -> ((index + 21) % 40) == 0 }
        .sumOf { (index, value) -> (index + 1) * value }
        .println()
    signalStrengths.toImage().println()
}

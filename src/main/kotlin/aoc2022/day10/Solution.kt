package aoc2022.day10

import extensions.filePathToStringList
import extensions.println

fun List<String>.signalStrengths(start: Int): List<Int> =
    this
        .flatMap { instruction ->
            if (instruction == "noop") {
                listOf(0)
            } else {
                listOf(0, instruction.substringAfter(" ").toInt())
            }
        }.fold(listOf(start)) { list, v ->
            list + (list.last() + v)
        }

fun List<Int>.toImage(): String =
    this
        .take(240)
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
            .signalStrengths(1)
    signalStrengths
        .withIndex()
        .filter { (index, _) -> ((index + 21) % 40) == 0 }
        .sumOf { (index, value) -> (index + 1) * value }
        .println()
    signalStrengths.toImage().println()
}

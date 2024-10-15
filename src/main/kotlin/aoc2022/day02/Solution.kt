package aoc2022.day02

import extensions.filePathToStringList
import extensions.println

data class Combination(
    val round: String,
    val opponent: Char,
    val me: Char,
    val result: Char,
)

fun combinations(): List<Combination> =
    listOf(
        // Losses
        Combination("A Y", 'A', 'B', 'Z'),
        Combination("B Z", 'B', 'C', 'Z'),
        Combination("C X", 'C', 'A', 'Z'),
        // Draws
        Combination("A X", 'A', 'A', 'Y'),
        Combination("B Y", 'B', 'B', 'Y'),
        Combination("C Z", 'C', 'C', 'Y'),
        // Wins
        // Losses
        Combination("A Z", 'A', 'C', 'X'),
        Combination("B X", 'B', 'A', 'X'),
        Combination("C Y", 'C', 'B', 'X'),
    )

fun Char.toRps(): Char = if (this.code > 'C'.code) (this.code - 'X'.code + 'A'.code).toChar() else this

fun Char.toValue(): Int = this.code - 'A'.code + 1

fun Char.toResult(): Int = (this.code - 'X'.code) * 3

fun main() {
    "aoc2022/Day02Input.txt"
        .filePathToStringList()
        .map { round ->
            combinations().first {
                round.first() == it.opponent && round.last().toRps() == it.me
            }
        }.sumOf {
            it.me.toValue() + it.result.toResult()
        }.println()

    "aoc2022/Day02Input.txt"
        .filePathToStringList()
        .map { round ->
            combinations().first {
                round.first() == it.opponent && round.last() == it.result
            }
        }.sumOf { it.me.toValue() + it.result.toResult() }
        .println()
}

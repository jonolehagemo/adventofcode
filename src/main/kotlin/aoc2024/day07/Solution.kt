package aoc2024.day07

import extensions.filePathToStringList
import extensions.println

val add = fun (
    a: Long,
    b: Long,
): Long = a + b

val multiply = fun (
    a: Long,
    b: Long,
): Long = a * b

val concat = fun (
    a: Long,
    b: Long,
): Long = (a.toString() + b.toString()).toLong()

fun calculate(
    a: Long,
    list: List<Long>,
    operators: List<(Long, Long) -> Long>,
): List<Long> =
    if (list.isEmpty()) {
        listOf(a)
    } else {
        operators.map { it(a, list.first()) }.flatMap { calculate(it, list.drop(1), operators) }
    }

fun List<Pair<Long, List<Long>>>.sum(operators: List<(Long, Long) -> Long>): Long =
    this
        .map { it.first to calculate(it.second.first(), it.second.drop(1), operators) }
        .filter { it.first in it.second }
        .sumOf { it.first }

fun main() {
    val input =
        "aoc2024/Day07Input.txt"
            .filePathToStringList()
            .map { s ->
                s.substringBefore(':').toLong() to
                    s.substringAfter(": ").split(' ').map { it.toLong() }
            }

    input.sum(listOf(add, multiply)).println()
    input.sum(listOf(add, multiply, concat)).println()
}

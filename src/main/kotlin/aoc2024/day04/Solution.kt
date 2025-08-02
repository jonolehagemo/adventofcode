package aoc2024.day04

import datastructures.Coordinate
import datastructures.Grid
import extensions.filePathToGrid
import extensions.println

fun slice(
    start: Coordinate,
    direction: Coordinate,
    length: Int,
): List<Coordinate> = generateSequence(start) { it.plus(direction) }.take(length).toList()

fun directions() =
    listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)
        .map { (y, x) -> Coordinate(y, x) }

fun Grid.findWord(word: String): List<List<Coordinate>> =
    findCoordinateByValue(word.first())
        .flatMap { from ->
            directions()
                .map { direction ->
                    val slice = slice(from, direction, word.length)
                    slice to slice.map { tile(it) }.joinToString("")
                }
        }.filter { (_, s) ->
            s == word
        }.map {
            it.first
        }

fun main() {
    val input = "aoc2024/Day04Input.txt".filePathToGrid(' ')
    input
        .findWord("XMAS")
        .count()
        .println()

    input
        .findWord("MAS")
        .filter { it[0].row != it[1].row && it[0].column != it[1].column }
        .groupingBy { it[1] }
        .eachCount()
        .count { it.value == 2 }
        .println()
}

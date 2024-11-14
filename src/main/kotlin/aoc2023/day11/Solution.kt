package aoc2023.day11

import datastructures.LongCoordinate
import datastructures.LongGrid
import extensions.filePathToStringList
import extensions.println
import extensions.toLongGrid
import extensions.transpose

fun List<String>.rowsToExpand(): List<Int> =
    this
        .withIndex()
        .filter { (_, line) -> (line.toCharArray().all { it == line.first() }) }
        .map { (index, _) -> index }

fun List<String>.columnsToExpand(): List<Int> =
    this
        .transpose()
        .rowsToExpand()

fun LongGrid.expand(
    n: Int,
    rowsToExpand: List<Int>,
    columnsToExpand: List<Int>,
): LongGrid =
    LongGrid(
        coordinateCharMap =
            nodes()
                .associate { coordinate ->
                    LongCoordinate(
                        row = coordinate.row + rowsToExpand.filter { it <= coordinate.row }.size.times(n - 1),
                        column = coordinate.column + columnsToExpand.filter { it <= coordinate.column }.size.times(n - 1),
                    ) to '#'
                },
        defaultValue = defaultValue,
    )

fun List<LongCoordinate>.sumShortestPath(): Long =
    this
        .flatMap { a -> this.map { b -> listOf(a, b).sorted() to a.shortestPath(b) } }
        .distinctBy { it.first }
        .sumOf { it.second }

fun process(
    input: List<String>,
    expandFactor: Int,
): Long =
    input
        .toLongGrid('.')
        .expand(expandFactor, input.rowsToExpand(), input.columnsToExpand())
        .nodes()
        .sumShortestPath()

fun main() {
    val input = "aoc2023/Day11Input.txt".filePathToStringList()
    process(input, 2).println()
    process(input, 1_000_000).println()
}

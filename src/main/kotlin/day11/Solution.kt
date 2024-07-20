package day11

import datastructures.Coordinate
import datastructures.Grid
import extensions.filePathToStringList
import extensions.println
import extensions.toGrid


fun List<String>.rotateLeft(): List<String> =
    ((maxOf { it.length } - 1) downTo 0)
        .map { column -> this.indices.joinToString("") { row -> this[row][column].toString() } }

fun List<String>.rowsToExpand(): List<Int> = this
    .withIndex()
    .filter { (_, line) -> (line.toCharArray().all { it == line.first() }) }
    .map { (index, _) -> index }

fun List<String>.columnsToExpand(): List<Int> = this
    .rotateLeft()
    .withIndex()
    .filter { (_, line) -> (line.toCharArray().all { it == line.first() }) }
    .map { (index, line) -> line.length - index - 1 }
    .sorted()

fun Grid.expand(n: Int, rowsToExpand: List<Int>, columnsToExpand: List<Int>): Grid = Grid(
    coordinateCharMap = nodes()
        .associate { coordinate ->
            Coordinate(
                row = coordinate.row + rowsToExpand.filter { it <= coordinate.row }.size.times(n - 1),
                column = coordinate.column + columnsToExpand.filter { it <= coordinate.column }.size.times(n - 1),
            ) to '#'
        },
    defaultValue = defaultValue
)

fun List<Coordinate>.sumShortestPath(): Long =
        flatMap { a -> map { b -> listOf(a, b).sorted() to a.shortestPath(b) } }
        .distinctBy { it.first }
        .sumOf { it.second }

fun process(input: List<String>, expandFactor: Int): Long = input
        .toGrid('.')
        .expand(expandFactor, input.rowsToExpand(), input.columnsToExpand())
        .nodes()
        .sumShortestPath()

fun main() {
    val input = "Day11Input.txt".filePathToStringList()
    process(input, 2).println()
    process(input, 1_000_000).println()
}

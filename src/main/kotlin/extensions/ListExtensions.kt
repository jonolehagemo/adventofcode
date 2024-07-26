package extensions

import datastructures.Coordinate
import datastructures.Grid

fun List<Double>.toProduct(): Double = this.reduce { a, b -> a * b }

fun List<String>.toGrid(defaultValue: Char): Grid = Grid(
    coordinateCharMap = this
        .withIndex()
        .flatMap { (row, list) ->
            list
                .withIndex()
                .filter { (_, char) -> char != defaultValue }
                .map { (column, char) -> Coordinate(row.toLong(), column.toLong()) to char }
        }
        .toMap(),
    defaultValue = defaultValue
)

fun List<String>.toTextString(separator: String = "\n"): String = this.joinToString(separator)

fun List<String>.toTextStrings(other: List<String>, separator: String = "\n"): String = this
    .zip(other)
    .map { (a, b) -> a.plus(" | ").plus(b) }
    .toTextString(separator)

fun List<String>.transpose(): List<String> =
    (0..<maxOf { it.length })
        .map { column -> this.indices.joinToString("") { row -> this[row][column].toString() } }

fun List<String>.rotateLeft(): List<String> =
    ((maxOf { it.length } - 1) downTo 0)
        .map { column -> this.indices.joinToString("") { row -> this[row][column].toString() } }

fun List<String>.rotateRight(): List<String> =
    (0..<maxOf { it.length })
        .map { column -> this.indices.joinToString("") { row -> this[row][column].toString() }.reversed() }
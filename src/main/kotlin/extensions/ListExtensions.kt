package extensions

import datastructures.Coordinate
import datastructures.Grid

fun List<Double>.toProduct(): Double = this.reduce { a, b -> a * b }

fun List<String>.toGrid(defaultValue: Char): Grid = Grid(
    this
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

fun List<String>.transpose(): List<String> =
    (0..<maxOf { it.length })
        .map { column -> this.indices.joinToString("") { row -> this[row][column].toString() } }

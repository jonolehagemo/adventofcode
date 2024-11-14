package extensions

import datastructures.Coordinate
import datastructures.IntGrid
import datastructures.LongCoordinate
import datastructures.LongGrid

fun List<Double>.toProduct(): Double = this.reduce { a, b -> a * b }

fun List<Long>.toProduct(): Long = this.reduce { a, b -> a * b }

fun List<String>.toLongGrid(defaultValue: Char): LongGrid =
    LongGrid(
        coordinateCharMap =
            this
                .withIndex()
                .flatMap { (row, list) ->
                    list
                        .withIndex()
                        .filter { (_, char) -> char != defaultValue }
                        .map { (column, char) -> LongCoordinate(row.toLong(), column.toLong()) to char }
                }.toMap(),
        defaultValue = defaultValue,
    )

fun List<String>.toIntGrid(defaultValue: Char): IntGrid =
    IntGrid(
        coordinateCharMap =
            this
                .withIndex()
                .flatMap { (row, list) ->
                    list
                        .withIndex()
                        .filter { (_, char) -> char != defaultValue }
                        .map { (column, char) -> Coordinate(row, column) to char }
                }.toMap(),
        defaultValue = defaultValue,
    )

fun List<String>.toTextString(separator: String = "\n"): String = this.joinToString(separator)

fun List<String>.toTextStrings(
    other: List<String>,
    separator: String = "\n",
): String =
    this
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

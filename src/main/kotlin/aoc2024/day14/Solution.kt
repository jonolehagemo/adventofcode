package aoc2024.day14


import datastructures.Coordinate
import datastructures.Grid
import extensions.filePathToStringList
import extensions.println

fun String.toCoordinate(): Coordinate = substringAfter('=').split(',').map { it.toInt() }.let { (x, y) -> Coordinate(y, x) }

fun String.toCoordinatePair(): Pair<Coordinate, Coordinate> = split(' ').let { (p, v) -> p.toCoordinate() to v.toCoordinate() }

fun Pair<Coordinate, Coordinate>.calculatePosition(
    width: Int,
    height: Int,
    seconds: Int,
): Coordinate =
    let { (p, v) ->
        Coordinate(
            row = (p.row + ((height + v.row) * seconds)) % height,
            column = (p.column + ((width + v.column) * seconds)) % width
        )
    }

fun main() {
    val width = 101
    val height = 103
    val input = "aoc2024/Day14Input.txt"
        .filePathToStringList()
        .map { it.toCoordinatePair() }

    input
        .map { it.calculatePosition(width, height, 100) }
        .filter { it.row != (height.floorDiv(2)) && it.column != (width.floorDiv(2)) }
        .map { Pair(it.row < (height.floorDiv(2)), it.column < (width.floorDiv(2))) to it }
        .groupBy({ it.first }, {it.second})
        .mapValues { it.value.size }
        .map { it.value }
        .reduce { a, b -> a * b }
        .println()

    (1..1_000_000).firstOrNull { seconds ->
        input
            .map {
                it.calculatePosition(
                    width,
                    height,
                    seconds
                )
            }
            .let { list ->
                Grid(
                    coordinateCharMap = list.associateWith { '*' },
                    rowLength = height,
                    columnLength = width
                ).toString().contains("********")
            }
    }
        ?.println()
}

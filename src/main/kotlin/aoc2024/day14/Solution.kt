package aoc2024.day14


import datastructures.Coordinate
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
        Coordinate(p.row + ((v.row * seconds) % height), p.column + ((v.column * seconds) % width))
    }

fun main() {
    val seconds = 100
    val width = 101
    val height = 103
    val input = "aoc2024/Day14Input.txt".filePathToStringList().map { it.toCoordinatePair() }
    input.println()
}

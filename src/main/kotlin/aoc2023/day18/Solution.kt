package aoc2023.day18

import datastructures.Coordinate
import extensions.filePathToStringList
import extensions.println
import kotlin.math.abs
import kotlin.math.absoluteValue

fun List<String>.toCoordinates1(): List<Coordinate> = map {
    val (direction, length) = it.split(" ")
    direction.toDirection() * length.toLong()
}

fun List<String>.toCoordinates2(): List<Coordinate> = map {
    val hex = it.substringAfter('#').substringBefore(')')
    val direction = hex.takeLast(1)
    val length = hex.dropLast(1)
    direction.toDirection() * length.toLong(16)
}

fun String.toDirection(): Coordinate = when (this) {
    "R", "0" -> Coordinate.ORIGIN.east()
    "D", "1" -> Coordinate.ORIGIN.south()
    "L", "2" -> Coordinate.ORIGIN.west()
    "U", "3" -> Coordinate.ORIGIN.north()
    else -> Coordinate.ORIGIN
}

fun List<Coordinate>.calculateAreaByCircumference(): Long {
    val coordinates = fold(listOf(Coordinate.ORIGIN)) { list, item -> list + (list.last() + item) }
    // https://en.wikipedia.org/wiki/Shoelace_formula
    val area = coordinates.zipWithNext { a, b -> a.column * b.row - a.row * b.column }.sum().absoluteValue / 2
    // https://en.wikipedia.org/wiki/Pick%27s_theorem
    val boundary = coordinates.zipWithNext { a, b -> abs(a.shortestPath(b)) }.sum()
    val interior = area - (boundary / 2) + 1
    return interior + boundary
}

fun main() {
    val input = "aoc2023/Day18Input.txt".filePathToStringList()
    input.toCoordinates1().calculateAreaByCircumference().println()
    input.toCoordinates2().calculateAreaByCircumference().println()
}
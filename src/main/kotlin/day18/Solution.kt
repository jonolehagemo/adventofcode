package day18

import datastructures.Coordinate
import extensions.filePathToStringList
import extensions.println
import kotlin.math.abs
import kotlin.math.absoluteValue

fun List<String>.toCoordinates1(): List<Coordinate> = map {
        val (direction , length) = it.split(" ")
        direction.toDirection() * length.toLong()
    }

fun List<String>.toCoordinates2(): List<Coordinate> = map {
        val hex = it.split(" ")[2].substringAfter('#').substringBefore(')')
        val direction = hex.takeLast(1)
        val length = hex.dropLast(1)
        direction.toDirection() * length.toLong(16)
    }

fun List<Coordinate>.toRelativeCoordinates(): List<Coordinate> = fold(listOf()){ result, item ->
        result + ((result.lastOrNull() ?: Coordinate.ORIGIN) + item)
    }

fun String.toDirection(): Coordinate = when (this) {
    "R", "0" -> Coordinate.ORIGIN.east()
    "D", "1" -> Coordinate.ORIGIN.south()
    "L", "2" -> Coordinate.ORIGIN.west()
    "U", "3" -> Coordinate.ORIGIN.north()
    else -> Coordinate.ORIGIN
}

fun List<Coordinate>.calculateAreaByCircumference(): Long {
    // https://en.wikipedia.org/wiki/Pick%27s_theorem
    val area = zip(drop(1) + take(1)) { a, b -> a.column * b.row - a.row * b.column }.sum().absoluteValue / 2
    val boundary = zip(drop(1) + take(1)) { a, b -> abs(a.shortestPath(b)) }.sum()
    val interior = area - (boundary / 2) + 1
    return interior + boundary
}

fun main() {
    "Day18Input.txt"
        .filePathToStringList()
        .toCoordinates1()
        .toRelativeCoordinates()
        .calculateAreaByCircumference()
        .println()

    "Day18Input.txt"
        .filePathToStringList()
        .toCoordinates2()
        .toRelativeCoordinates()
        .calculateAreaByCircumference()
        .println()

}
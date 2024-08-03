package day18

import datastructures.Coordinate
import extensions.filePathToStringList
import extensions.println
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.floor

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
    val area = zip(drop(1) + take(1)) { i, j -> i.column * j.row - i.row * j.column }
        .sum().absoluteValue / 2
    val b = zip(drop(1) + take(1)) { i, j -> abs(i.shortestPath(j)) }.sum()
    val i = area - floor((b / 2).toDouble()).toLong() + 1
    return i + b
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
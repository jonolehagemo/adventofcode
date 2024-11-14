package aoc2022.day09

import datastructures.Coordinate
import extensions.filePathToStringList
import extensions.println

fun Char.toDirection(): Coordinate =
    when (this) {
        'U' -> Coordinate.ORIGIN.north()
        'D' -> Coordinate.ORIGIN.south()
        'L' -> Coordinate.ORIGIN.west()
        'R' -> Coordinate.ORIGIN.east()
        else -> Coordinate.ORIGIN
    }

fun List<Coordinate>.move(direction: Coordinate): List<Coordinate> =
    drop(1).fold(listOf(first() + direction)) { knots, next -> knots + findTail(knots.last(), next) }

fun findTail(
    head: Coordinate,
    next: Coordinate,
): Coordinate {
    if (next in head.neighbours().plus(head)) {
        return next
    }

    val delta = head - next

    return when {
        delta.column == 0 ->
            next.copy(row = next.row + if (delta.row > 0) 1 else -1)

        delta.row == 0 ->
            next.copy(column = next.column + if (delta.column > 0) 1 else -1)

        else ->
            next.copy(
                row = next.row + if (delta.row > 0) 1 else -1,
                column = next.column + if (delta.column > 0) 1 else -1,
            )
    }
}

fun List<String>.positions(ropeLength: Int): List<Pair<Coordinate, Coordinate>> {
    var rope = (0..<ropeLength).map { _ -> Coordinate.ORIGIN }
    val positions = mutableListOf(rope.first().copy() to rope.last().copy())

    forEach { line ->
        val direction = line.first().toDirection()
        val steps = line.substringAfter(" ").toInt()

        (0..<steps).forEach { _ ->
            rope = rope.move(direction)
            positions += rope.first().copy() to rope.last().copy()
        }
    }

    return positions
}

fun List<Pair<Coordinate, Coordinate>>.count(): Int =
    this
        .map { it.second }
        .toSet()
        .count()

fun main() {
    val input = "aoc2022/Day09Input.txt".filePathToStringList()
    listOf(2, 10).forEach { ropeLength ->
        input
            .positions(ropeLength)
            .count()
            .println()
    }
}

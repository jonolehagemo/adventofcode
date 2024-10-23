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

fun List<String>.positions(): List<Pair<Coordinate, Coordinate>> {
    var head = Coordinate.ORIGIN
    var tail = Coordinate.ORIGIN
    val positions = mutableListOf(head.copy() to tail.copy())
    // "head -> $head, tail -> $tail".println()

    forEach { line ->
        val direction = line.first().toDirection()
        val steps = line.substringAfter(" ").toInt()
        // "\n\nline -> $line, direction -> $direction, steps -> $steps".println()

        (0..<steps).forEach { _ ->
            head += direction

            if (tail !in head.neighbours().plus(head)) {
                tail =
                    when {
                        (head.row != tail.row) && (head.column != tail.column) -> positions.last().first
                        (head.row == tail.row).xor(head.column == tail.column) -> tail + direction
                        else -> Coordinate.ORIGIN
                    }
            }

            // "head -> $head, tail -> $tail".println()
            positions += head to tail
        }
    }

    // positions.println()

    return positions
}

fun main() {
    val input = "aoc2022/Day09Input.txt".filePathToStringList()
    input
        .positions()
        .map { it.second }
        .toSet()
        .count()
        .println()
}

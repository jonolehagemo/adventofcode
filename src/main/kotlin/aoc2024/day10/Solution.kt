package aoc2024.day10

import datastructures.Coordinate
import datastructures.Grid
import extensions.filePathToGrid
import extensions.println

fun Grid.toTrails(): List<Pair<Coordinate, Coordinate>> =
    findCoordinateByValue('0')
        .flatMap { start -> toTrail(start, emptySet()).map { end -> start to end } }

fun Grid.toTrail(
    current: Coordinate,
    visited: Set<Coordinate>
): List<Coordinate> =
    if (tile(current) == '9') {
        listOf(current)
    } else {
        neighboursNEWS(current)
            .filter { isInBounds(it) && tile(it).code == tile(current).code + 1 }
            .flatMap { toTrail(it, visited + current) }
    }

fun main() {
    val grid = "aoc2024/Day10Input.txt".filePathToGrid(' ')
    val trails = grid.toTrails()
    trails.distinct().size.println()
    trails.size.println()
}
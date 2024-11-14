package aoc2023.day10

import datastructures.LongCoordinate
import datastructures.LongGrid
import extensions.filePathToGrid
import kotlin.math.absoluteValue

fun tileToDirection(tile: Char): List<LongCoordinate> =
    when (tile) {
        '|' -> listOf(LongCoordinate(1, 0), LongCoordinate(-1, 0))
        '-' -> listOf(LongCoordinate(0, 1), LongCoordinate(0, -1))
        'L' -> listOf(LongCoordinate(-1, 0), LongCoordinate(0, 1))
        'J' -> listOf(LongCoordinate(-1, 0), LongCoordinate(0, -1))
        '7' -> listOf(LongCoordinate(1, 0), LongCoordinate(0, -1))
        'F' -> listOf(LongCoordinate(1, 0), LongCoordinate(0, 1))
        'S' -> listOf(LongCoordinate(1, 0), LongCoordinate(-1, 0), LongCoordinate(0, 1), LongCoordinate(0, -1))
        else -> emptyList()
    }

tailrec fun dfs(
    previous: LongCoordinate,
    current: LongCoordinate,
    grid: LongGrid,
    visited: List<LongCoordinate>,
): List<LongCoordinate> {
    val currentTile = grid.tile(current)
    val next =
        tileToDirection(currentTile).firstNotNullOf { direction ->
            val nextCoordinate = current + direction
            if (nextCoordinate != previous) {
                nextCoordinate
            } else {
                null
            }
        }
    return if (currentTile == 'S' && visited.isNotEmpty()) {
        return visited
    } else {
        dfs(current, next, grid, visited.plus(current))
    }
}

fun main() {
    val grid = "aoc2023/Day10Input.txt".filePathToGrid('.')
    val start = grid.findCoordinateByTile('S').first()
    val loop = dfs(LongCoordinate(-1, -1), start, grid, emptyList())
    println("Task 1: ${loop.size / 2}")
    // https://en.wikipedia.org/wiki/Pick%27s_theorem
    val area =
        loop
            .zip(loop.drop(1) + loop.take(1)) { a, b -> a.column * b.row - a.row * b.column }
            .sum()
            .absoluteValue / 2
    println("Task 2: ${area - loop.size / 2 + 1}")
}

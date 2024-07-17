package day10



import datastructures.Coordinate
import datastructures.Grid
import extensions.filePathToGrid
import kotlin.math.absoluteValue

fun tileToDirection(tile: Char): List<Pair<Int, Int>> = when (tile) {
    '|' -> listOf(Pair(1, 0), Pair(-1, 0))
    '-' -> listOf(Pair(0, 1), Pair(0, -1))
    'L' -> listOf(Pair(-1, 0), Pair(0, 1))
    'J' -> listOf(Pair(-1, 0), Pair(0, -1))
    '7' -> listOf(Pair(1, 0), Pair(0, -1))
    'F' -> listOf(Pair(1, 0), Pair(0, 1))
    'S' -> listOf(Pair(1, 0), Pair(-1, 0), Pair(0, 1), Pair(0, -1))
    else -> emptyList()
}

fun findLoop(grid: Grid, start: Coordinate): List<Coordinate> = dfs(Coordinate(-1, -1), start, grid, emptyList())

tailrec fun dfs(previous: Coordinate, current: Coordinate, grid: Grid, visited: List<Coordinate>): List<Coordinate> {
    val currentTile = grid.tile(current)
    val next = tileToDirection(currentTile).firstNotNullOf { direction ->
        val nextCoordinate = Coordinate(
            row = current.row + direction.first,
            column = current.column + direction.second
        )
        if (nextCoordinate.row != previous.row || nextCoordinate.column != previous.column)
            nextCoordinate
        else null
    }

    return if (currentTile == 'S' && visited.isNotEmpty()) {
        return visited
    } else dfs(current, next, grid, visited.plus(current))
}

fun main() {
    val grid = "Day10Input.txt".filePathToGrid('.')
    val start = grid.findCoordinateByTile('S').first()
    val loop = findLoop(grid, start)
    println("Task 1: ${loop.size / 2}")

    // https://en.wikipedia.org/wiki/Pick%27s_theorem
    val area = loop
        .zip(loop.drop(1) + loop.take(1)) { a, b ->
            a.column * b.row - a.row * b.column
        }
        .sum()
        .absoluteValue / 2
    println("Task 2: ${area - loop.size / 2 + 1}")
}
package aoc2023.day16

import datastructures.Grid
import datastructures.Coordinate
import datastructures.LongGrid
import extensions.filePathToGrid
import extensions.println

fun Grid.energized(
    startCoordinate: Coordinate,
    startDirection: Coordinate,
): List<Pair<Coordinate, Coordinate>> {
    val visited: MutableSet<Pair<Coordinate, Coordinate>> = mutableSetOf()
    val queue: ArrayDeque<Pair<Coordinate, Coordinate>> = ArrayDeque((rowRange().last * columnRange().last).toInt())
    queue.addLast(startCoordinate to startDirection)

    while (queue.isNotEmpty()) {
        val (previous, direction) = queue.removeFirst()
        val current = previous + direction

        if (Pair(current, direction) !in visited && current.row in rowRange() && current.column in columnRange()) {
            visited.add(Pair(current, direction))

            when {
                (tile(current) == '\\') ->
                    queue.addLast(current to Coordinate(direction.column, direction.row))

                (tile(current) == '/') ->
                    queue.addLast(current to Coordinate(-direction.column, -direction.row))

                (tile(current) == '|' && direction.row == 0) -> {
                    queue.addLast(current to Coordinate(-1, 0))
                    queue.addLast(current to Coordinate(1, 0))
                }

                (tile(current) == '-' && direction.column == 0) -> {
                    queue.addLast(current to Coordinate(0, -1))
                    queue.addLast(current to Coordinate(0, 1))
                }

                else -> queue.addLast(current to direction)
            }
        }
    }

    return visited.toList()
}

fun main() {
    val grid = "aoc2023/Day16Input.txt".filePathToGrid('.')
    grid
        .energized(Coordinate(0, -1), Coordinate(0, 1))
        .groupBy { it.first }
        .count()
        .println()

    val startRows =
        grid
            .rowRange()
            .toList()
            .map { it.toInt() }
            .flatMap {
                listOf(
                    Coordinate(it, -1) to Coordinate(0, 1),
                    Coordinate(it, grid.columnRange().last + 1) to Coordinate(0, -1),
                )
            }

    val startColumns =
        grid
            .columnRange()
            .toList()
            .map { it.toInt() }
            .flatMap {
                listOf(
                    Coordinate(-1, it) to Coordinate(1, 0),
                    Coordinate(grid.rowRange().last + 1, it) to Coordinate(-1, 0),
                )
            }

    startRows
        .plus(startColumns)
        .maxOf { (start, direction) ->
            grid
                .energized(start, direction)
                .groupBy { it.first }
                .count()
        }.println()
}

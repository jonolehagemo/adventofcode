package aoc2023.day16

import datastructures.Coordinate
import datastructures.Grid
import extensions.filePathToGrid
import extensions.println


fun Grid.energized(
    startCoordinate: Coordinate,
    startDirection: Coordinate
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

                (tile(current) == '|' && direction.row == 0L) -> {
                    queue.addLast(current to Coordinate(-1L, 0L))
                    queue.addLast(current to Coordinate(1L, 0L))
                }

                (tile(current) == '-' && direction.column == 0L) -> {
                    queue.addLast(current to Coordinate(0L, -1L))
                    queue.addLast(current to Coordinate(0L, 1L))
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

    val startRows = grid.rowRange().toList().map { it.toInt() }
        .flatMap {
            listOf(
                Coordinate(it.toLong(), -1L) to Coordinate(0L, 1L),
                Coordinate(it.toLong(), grid.columnRange().last + 1L) to Coordinate(0L, -1L),
            )
        }

    val startColumns = grid.columnRange().toList().map { it.toInt() }
        .flatMap {
            listOf(
                Coordinate(-1L, it.toLong()) to Coordinate(1L, 0L),
                Coordinate(grid.rowRange().last + 1L, it.toLong()) to Coordinate(-1L, 0L),
            )
        }

    startRows.plus(startColumns)
        .maxOf { (start, direction) ->
            grid.energized(start, direction)
                .groupBy { it.first }
                .count()
        }
        .println()
}
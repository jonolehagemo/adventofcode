package aoc2023.day16

import datastructures.LongCoordinate
import datastructures.LongGrid
import extensions.filePathToGrid
import extensions.println

fun LongGrid.energized(
    startCoordinate: LongCoordinate,
    startDirection: LongCoordinate,
): List<Pair<LongCoordinate, LongCoordinate>> {
    val visited: MutableSet<Pair<LongCoordinate, LongCoordinate>> = mutableSetOf()
    val queue: ArrayDeque<Pair<LongCoordinate, LongCoordinate>> = ArrayDeque((rowRange().last * columnRange().last).toInt())
    queue.addLast(startCoordinate to startDirection)

    while (queue.isNotEmpty()) {
        val (previous, direction) = queue.removeFirst()
        val current = previous + direction

        if (Pair(current, direction) !in visited && current.row in rowRange() && current.column in columnRange()) {
            visited.add(Pair(current, direction))

            when {
                (tile(current) == '\\') ->
                    queue.addLast(current to LongCoordinate(direction.column, direction.row))

                (tile(current) == '/') ->
                    queue.addLast(current to LongCoordinate(-direction.column, -direction.row))

                (tile(current) == '|' && direction.row == 0L) -> {
                    queue.addLast(current to LongCoordinate(-1L, 0L))
                    queue.addLast(current to LongCoordinate(1L, 0L))
                }

                (tile(current) == '-' && direction.column == 0L) -> {
                    queue.addLast(current to LongCoordinate(0L, -1L))
                    queue.addLast(current to LongCoordinate(0L, 1L))
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
        .energized(LongCoordinate(0, -1), LongCoordinate(0, 1))
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
                    LongCoordinate(it.toLong(), -1L) to LongCoordinate(0L, 1L),
                    LongCoordinate(it.toLong(), grid.columnRange().last + 1L) to LongCoordinate(0L, -1L),
                )
            }

    val startColumns =
        grid
            .columnRange()
            .toList()
            .map { it.toInt() }
            .flatMap {
                listOf(
                    LongCoordinate(-1L, it.toLong()) to LongCoordinate(1L, 0L),
                    LongCoordinate(grid.rowRange().last + 1L, it.toLong()) to LongCoordinate(-1L, 0L),
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

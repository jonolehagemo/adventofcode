package aoc2023.day23

import datastructures.Coordinate
import datastructures.Graph
import datastructures.Grid
import extensions.filePathToStringList
import extensions.toGrid


fun Grid.toGraph() =
    Graph(
        nodes().associateWith { start ->
            neighbours(start).map { neighbour -> edge(start, neighbour, 1) }.toSet()
        },
    )

fun Grid.edge(
    previous: Coordinate,
    current: Coordinate,
    steps: Int = 0,
): Pair<Coordinate, Int> =
    when {
        previous == finish ->
            previous to 0

        neighboursCount(current) == 2 ->
            neighbours(current)
                .firstOrNull { next -> next != previous }
                .let { next -> edge(current, next ?: previous, steps + 1) }

        else ->
            current to steps
    }

fun List<String>.removeSlopes(): List<String> = this.map {
    it
        .replace('^', '.')
        .replace('>', '.')
        .replace('v', '.')
        .replace('<', '.')
}

fun process(mapString: List<String>): Int {
    val grid = mapString.toGrid('#')
    val graph = grid.toGraph()

    return graph.longestPath(grid.start, grid.finish)
}

fun main() {
    val mapString = "aoc2023/Day23Input.txt".filePathToStringList()

    println("Task 1")
    println(process(mapString))

    println("Task 2")
    println(process(mapString.removeSlopes()))
}

package aoc2024.day18

import datastructures.Coordinate
import datastructures.Graph
import datastructures.Grid
import extensions.cartesianProduct
import extensions.filePathToStringList
import extensions.println

fun Grid.toGraph(): Graph =
    Graph(adjacencyList = coordinates().associateWith { c ->
        neighboursNEWS(c).map { it to 1 }.toSet()
    })

fun Graph.shortestPath(start: Coordinate, finish: Coordinate): Int =
    shortestPathDijkstra(start)
        .getOrDefault(finish, Graph.DijkstraLookup(distance = -1))
        .distance

fun process(length: Int, takenBytes: Int, filepath: String): Int {
    val allMemory = (0..length+1).cartesianProduct((0..length+1)).map { (x, y) -> Coordinate(y, x) }
    val corrupted = filepath.filePathToStringList().map { it.split(',').let { (x, y) -> Coordinate(y.toInt(), x.toInt()) } }
    val availableMemory = allMemory.toSet().minus(corrupted.take(takenBytes).toSet())
    val grid = Grid(coordinateCharMap = availableMemory.associateWith { '.' }, defaultValue = '#' )
    val graph = grid.toGraph()
    return graph.shortestPath(Coordinate(0, 0), Coordinate(length, length))
}

fun main() {
    val length = 70
    val takenBytes = 1024
    val filepath = "aoc2024/Day18Input.txt"

    process(length, takenBytes, filepath).println()

    val index = generateSequence(takenBytes) { it + 1 }
        .map { it to process(length, it, filepath) }
        .takeWhile { it.second != -1 }
        .last()
        .first

    filepath.filePathToStringList()[index].println()

}
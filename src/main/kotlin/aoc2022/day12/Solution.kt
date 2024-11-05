package aoc2022.day12

import datastructures.Graph
import datastructures.Grid
import extensions.filePathToStringList
import extensions.println
import extensions.toGrid

fun Grid.toGraph(): Graph =
    Graph(
        adjacencyList =
            export()
                .associate { (coordinate, tile) ->
                    coordinate to
                        neighboursNEWS(coordinate)
                            .filter { neighbor -> tile(neighbor).code in 'a'.code..tile.code + 1 }
                            .map { neighbor -> neighbor to 1 }
                            .toSet()
                },
    )

fun main() {
    val inputGrid = "aoc2022/Day12Input.txt".filePathToStringList().toGrid(' ')
    val start = inputGrid.findCoordinateByTile('S').first()
    val finish = inputGrid.findCoordinateByTile('E').first()
    val grid = inputGrid.plus(start to 'a').plus(finish to 'z')
    val graph = grid.toGraph()
    graph
        .shortestPathDijkstra(start)
        .getOrDefault(finish, Graph.DijkstraLookup(distance = -1))
        .distance
        .println()
    graph
        .reverseEdges()
        .shortestPathDijkstra(finish)
        .filter { lookup -> grid.tile(lookup.key) == 'a' }
        .minBy { it.value.distance }
        .value.distance
        .println()
}

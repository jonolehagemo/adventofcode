package aoc2022.day12

import datastructures.Graph
import datastructures.Grid
import extensions.filePathToStringList
import extensions.println
import extensions.toGrid

fun Grid.toGraph(lambdaFunction: (current: Char, next: Char) -> Boolean): Graph =
    Graph(
        adjacencyList =
            export()
                .associate { (coordinate, tile) ->
                    coordinate to
                        neighboursNEWS(coordinate)
//                            .onEach { neighbor ->
//                                println(
//                                    "$tile -> tile($neighbor).code (${
//                                        tile(
//                                            neighbor,
//                                        )
//                                    } = ${
//                                        lambdaFunction(
//                                            tile.code,
//                                            tile(neighbor).code,
//                                        )
//                                    }",
//                                )
// //                            }.filter { neighbor -> tile(neighbor).code <= tile.code + 1 }
//                            }
                            .filter { neighbor ->
                                lambdaFunction(
                                    tile,
                                    tile(neighbor),
                                )
                            }.map { neighbor -> neighbor to 1 }
                            .toSet()
//                            .also { it.println() }
                },
    )

fun main() {
    val inputGrid = "aoc2022/Day12Input.txt".filePathToStringList().toGrid(' ')
    val start = inputGrid.findCoordinateByTile('S').first()
    val finish = inputGrid.findCoordinateByTile('E').first()
    val grid = inputGrid.plus(start to 'a').plus(finish to 'z')
    val graph = grid.toGraph(lambdaFunction = { current, next -> next.code in 'a'.code..current.code + 1 })
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

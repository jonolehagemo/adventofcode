package datastructures

import java.util.PriorityQueue

data class Graph(
    val adjacencyList: Map<Coordinate, Set<Pair<Coordinate, Int>>>,
) {
    fun reverseEdges(): Graph =
        Graph(
            adjacencyList
                .entries
                .flatMap { (key, set) ->
                    set.map { Triple(it.first, key, it.second) }
                }.groupBy {
                    it.first
                }.mapValues { (_, list) ->
                    list
                        .map { (_, coordinate, weight) -> coordinate to weight }
                        .toSet()
                },
        )

    fun longestPath(
        start: Coordinate,
        finish: Coordinate,
        currentSteps: Int = 0,
        visited: Set<Coordinate> = emptySet(),
    ): Int =
        if (start == finish) {
            currentSteps
        } else {
            adjacencyList
                .getOrDefault(start, emptyList())
                .filter { (neighbor, _) -> neighbor !in visited }
                .maxOfOrNull { (neighbor, neighborSteps) ->
                    longestPath(
                        neighbor,
                        finish,
                        currentSteps + neighborSteps,
                        visited.plus(neighbor),
                    )
                } ?: 0
        }

    fun shortestPathDijkstra(start: Coordinate): Map<Coordinate, DijkstraLookup> {
        val distances =
            mutableMapOf<Coordinate, DijkstraLookup>()
                .withDefault { DijkstraLookup() }
                .apply { put(start, DijkstraLookup(distance = 0)) }
        val priorityQueue =
            PriorityQueue<Pair<Coordinate, Int>>(compareBy { it.second })
                .apply { add(start to 0) }

        while (priorityQueue.isNotEmpty()) {
            val (node, currentDistance) = priorityQueue.poll()
            adjacencyList[node]?.forEach { (adjacent, weight) ->
                val totalDistance = currentDistance + weight
                if (totalDistance < distances.getValue(adjacent).distance) {
                    distances[adjacent] = DijkstraLookup(distance = totalDistance, previous = node)
                    priorityQueue.add(adjacent to totalDistance)
                }
            }
        }
        return distances
    }

    data class DijkstraLookup(
        val distance: Int = Int.MAX_VALUE,
        val previous: Coordinate? = null,
    )
}

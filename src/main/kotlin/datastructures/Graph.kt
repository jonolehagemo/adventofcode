package datastructures


data class Graph(val adjacencyList: Map<Coordinate, Set<Pair<Coordinate, Int>>>) {
    fun longestPath(
        start: Coordinate,
        finish: Coordinate,
        currentSteps: Int = 0,
        visited: Set<Coordinate> = emptySet()
    ): Int =
        if (start == finish) currentSteps
        else adjacencyList.getOrDefault(start, emptyList())
            .filter { (neighbor, _) -> neighbor !in visited }
            .maxOfOrNull { (neighbor, neighborSteps) ->
                longestPath(neighbor, finish, currentSteps + neighborSteps, visited.plus(neighbor))
            } ?: 0
}

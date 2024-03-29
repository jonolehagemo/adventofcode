package day25

import java.io.File

fun getInput(filePath: String): Graph = Graph(
    File(ClassLoader.getSystemResource(filePath).file)
        .readLines()
        .sorted()
        .flatMap { line ->
            val (from, toList) = line.split(": ")
            toList
                .split(" ")
                .flatMap { listOf(from to it, it to from) }
        }
        .groupBy { entry -> entry.first }
        .mapValues { entry -> entry.value.map { it.second to 1 }.sortedBy { it.first }.toSet() }
)

class Graph(val adjacencyList: Map<String, Set<Pair<String, Int>>>) {
    private val vertices = adjacencyList.keys
    private val edges = adjacencyList.mapValues { entry -> entry.value.map { pair -> pair.first }.toSet() }
    private val weights = adjacencyList
        .flatMap { entry -> entry.value.map { pair -> Pair(entry.key, pair.first) to pair.second } }
        .toMap()


    fun minimumCut(): Graph {
        val (from, to) = vertices
            .flatMap { from ->
                val shortestPathTree = dijkstra(from)
                vertices
                    .filterNot { it == from }
                    .flatMap { dest ->
                        shortestPath(shortestPathTree, from, dest)
                            .zipWithNext { a, b -> if (a < b) a to b else b to a }
                    }
            }
            .groupBy { it }
            .mapValues { it.value.size }
            .maxBy { it.value }
            .key
        println("minimumCut() is removing the edge between $from and $to")
        val result = adjacencyList
            .mapValues {
                when (it.key) {
                    from -> it.value.minus(to to 1)
                    to -> it.value.minus(from to 1)
                    else -> it.value
                }
            }

        return Graph(result)
    }

    fun dijkstra(start: String): Map<String, String?> {
        val visited: MutableSet<String> = mutableSetOf() // a subset of vertices, for which we know the true distance
        val previous: MutableMap<String, String?> = vertices.associateWith { null }.toMutableMap()
        val delta = vertices.associateWith { Int.MAX_VALUE }.toMutableMap()
        delta[start] = 0

        while (visited != vertices) {
            // let v be the closest vertex that has not yet been visited
            val v: String = delta.filter { !visited.contains(it.key) }.minBy { it.value }.key
            edges.getValue(v).minus(visited).forEach { neighbor ->
                val newPath = delta.getValue(v) + weights.getValue(Pair(v, neighbor))

                if (newPath < delta.getValue(neighbor)) {
                    delta[neighbor] = newPath
                    previous[neighbor] = v
                }
            }

            visited.add(v)
        }

        return previous.toMap()
    }

    private fun shortestPath(shortestPathTree: Map<String, String?>, start: String, end: String): List<String> {
        fun pathTo(start: String, end: String): List<String> =
            if (shortestPathTree[end] == null) listOf(end)
            else listOf(pathTo(start, shortestPathTree[end] ?: ""), listOf(end)).flatten()

        return pathTo(start, end)
    }
}

fun getClusterSize(from: String, edges: Map<String, Set<String>>): Int =
    if (!edges.containsKey(from)) 1
    else 1 + edges.getOrDefault(from, emptySet()).sumOf{getClusterSize(it, edges)}

fun process(graph: Graph): Int {
    println("graph created")
    val dividedGraph = graph
        .minimumCut().also { println("Cut 1") }
        .minimumCut().also { println("Cut 2") }
        .minimumCut().also { println("Cut 3") }
    println("divided")
    val paths = dividedGraph
        .dijkstra(dividedGraph.adjacencyList.keys.first())
        .mapValues { (it.value ?: it.key) }
    val edges = paths
        .toList()
        .map { it.second to it.first }
        .sortedBy { it.first }
        .groupBy { it.first }
        .mapValues { entry -> entry.value.map { it.second }.filter { it != entry.key }.sorted().toSet() }
    return paths
        .filter { it.key == it.value }
        .map { it.key }
        .also { that -> println(that) }
        .map { getClusterSize(it, edges) }
        .fold(1){ sum, acc -> sum * acc }
}

fun main() {
    println("Task 1")
    println(process(getInput("Day25Input.txt")))

    println("Task 2: Merry Christmas!")
    println("Credits to Daniel Elisenberg and his inspiration in Rust -> https://github.com/DanielElisenberg/aoc2023/blob/main/src/day25.rs")
}

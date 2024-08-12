package aoc2023.day25

import java.io.File

fun getInput(filePath: String): Graph = Graph(
    File(ClassLoader.getSystemResource(filePath).file)
        .readLines()
        .flatMap { line ->
            val (from, toList) = line.split(": ")
            toList
                .split(" ")
                // make 2 directed edges, need the possibility to traverse both ways
                .flatMap { listOf(from to it, it to from) }
        }
        .groupBy { entry -> entry.first }
        .mapValues { entry -> entry.value.map { it.second to 1 }.toSet() }
)

class Graph(val adjacencyList: Map<String, Set<Pair<String, Int>>>) {
    private val vertices = adjacencyList.keys
    private val edges = adjacencyList
        .mapValues { entry -> entry.value.map { pair -> pair.first }.toSet() }
    private val weights = adjacencyList
        .flatMap { entry -> entry.value.map { pair -> Pair(entry.key, pair.first) to pair.second } }
        .toMap()


    fun minimumCut(): Graph {
        val (from, to) = vertices
            .flatMap { from ->
                val shortestPathTree = dijkstra(from) // finding all connected vertices related to from
                vertices
                    .filterNot { it == from } // filter out self-referencing edges
                    .flatMap { to ->
                        shortestPath(shortestPathTree, from, to) // get a list of the vertices connecting from and to
                            .zipWithNext { a, b -> if (a < b) a to b else b to a } // create vertices pairs using zip with next and represent the edge in alphabetical order
                    }
            }
            .groupBy { it } // group by the edge
            .mapValues { it.value.size } // find all occurrences
            .maxBy { it.value } // find the edge with the most occurrences
            .key // get the edge
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

    fun dijkstra(from: String): Map<String, String?> {
        val visited: MutableSet<String> = mutableSetOf() // a subset of vertices, for which we know the true distance
        val previous: MutableMap<String, String?> = vertices.associateWith { null }.toMutableMap()
        /*
         * delta represents the length of the shortest distance paths
         * from `from` to v, for v in vertices.
         *
         * The values are initialized to infinity, as we'll be getting the key with the min value
         */
        val delta = vertices.associateWith { Int.MAX_VALUE }.toMutableMap()
        delta[from] = 0

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

    private fun shortestPath(shortestPathTree: Map<String, String?>, from: String, to: String): List<String> {
        fun pathTo(from: String, to: String): List<String> =
            if (shortestPathTree[to] == null) listOf(to)
            else listOf(pathTo(from, shortestPathTree[to] ?: ""), listOf(to)).flatten()

        return pathTo(from, to)
    }
}

fun getClusterSize(from: String, edges: Map<String, Set<String>>): Int =
    if (!edges.containsKey(from)) 1
    else 1 + edges.getOrDefault(from, emptySet()).sumOf { getClusterSize(it, edges) }

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
        .groupBy { it.first }
        .mapValues { entry -> entry.value.map { it.second }.filter { it != entry.key }.sorted().toSet() }

    return paths
        .filter { it.key == it.value }
        .map { getClusterSize(it.key, edges) }
        .fold(1) { sum, acc -> sum * acc }
}

fun main() {
    println("Task 1")
    println(process(getInput("aoc2023/Day25Input.txt")))

    println("Task 2: Merry Christmas!")
    println("Credits to Daniel Elisenberg and his inspiration in Rust -> https://github.com/DanielElisenberg/aoc2023/blob/main/src/day25.rs")
    println("Credits to Alex Woods and his inspiration repo -> https://github.com/alexhwoods/alexhwoods.com")
}

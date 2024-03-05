package day23

import java.io.File
import kotlin.math.sqrt

fun getInput(filePath: String): String =
    File(ClassLoader.getSystemResource(filePath).file).readLines().joinToString("")

fun String.removeSlopes(): String = this
    .replace('^', '.')
    .replace('>', '.')
    .replace('v', '.')
    .replace('<', '.')

fun String.toGrid(): Grid {
    val side = sqrt(this.length.toDouble()).toInt()

    return Grid(this
        .withIndex()
        .filter { (_, char) -> char != '#' }
        .associate { (index, char) -> Coordinate((index - (index % side)) / side, index % side) to char }
    )
}

data class Coordinate(val y: Int, val x: Int) {
    override fun toString(): String = "'$y-$x'"

    fun north(): Coordinate = Coordinate(y - 1, x)

    fun south(): Coordinate = Coordinate(y + 1, x)

    fun east(): Coordinate = Coordinate(y, x + 1)

    fun west(): Coordinate = Coordinate(y, x - 1)
}

data class Grid(val coordinateCharMap: Map<Coordinate, Char>)

fun Grid.start(): Coordinate = coordinateCharMap.keys.minBy { it.y }

fun Grid.finish(): Coordinate = coordinateCharMap.keys.maxBy { it.y }

fun Grid.tile(coordinate: Coordinate): Char = coordinateCharMap.getOrDefault(coordinate, '#')

fun Grid.neighboursCount(coordinate: Coordinate): Int =
    if (tile(coordinate) == '#') 0
    else (if (tile(coordinate.north()) != '#') 1 else 0) +
            (if (tile(coordinate.south()) != '#') 1 else 0) +
            (if (tile(coordinate.east()) != '#') 1 else 0) +
            (if (tile(coordinate.west()) != '#') 1 else 0)

fun Grid.neighbours(coordinate: Coordinate): Set<Coordinate> = when (tile(coordinate)) {
    '^' -> setOf(coordinate.north())
    'v' -> setOf(coordinate.south())
    '>' -> setOf(coordinate.east())
    '<' -> setOf(coordinate.west())
    '.' -> {
        val mutableList: MutableList<Coordinate> = mutableListOf()
        if (tile(coordinate.north()) !in setOf('#', 'v')) mutableList.add(coordinate.north())
        if (tile(coordinate.south()) !in setOf('#', '^')) mutableList.add(coordinate.south())
        if (tile(coordinate.east()) !in setOf('#', '<')) mutableList.add(coordinate.east())
        if (tile(coordinate.west()) !in setOf('#', '>')) mutableList.add(coordinate.west())

        mutableList.toSet()
    }

    else -> emptySet()
}

fun Grid.nodes(): Set<Coordinate> = coordinateCharMap
    .keys
    .map { it to neighboursCount(it) }
    .filter { (_, count) -> count != 2 }
    .map { it.first }
    .toSet()

private fun Grid.edge(previous: Coordinate, current: Coordinate, steps: Int = 0): Pair<Coordinate, Int> = when {
    previous == finish() ->
        previous to 0

    neighboursCount(current) == 2 ->
        neighbours(current)
            .firstOrNull { next -> next != previous }
            .let { next -> edge(current, next ?: previous, steps + 1) }

    else ->
        current to steps
}

fun Grid.toGraph() = Graph(nodes()
    .associateWith { start ->
        neighbours(start)
            .map { neighbour -> edge(start, neighbour, 1) }
            .toSet()
    }
)

data class Graph(val adjacencyList: Map<Coordinate, Set<Pair<Coordinate, Int>>>)

fun Graph.longestPath(
    start: Coordinate,
    finish: Coordinate,
    currentSteps: Int = 0,
    visited: Set<Coordinate> = emptySet()
): Int {
    if (start == finish)
        return currentSteps

    return adjacencyList.getOrDefault(start, emptyList())
        .filter { (neighbor, _) -> neighbor !in visited }
        .maxOfOrNull { (neighbor, neighborSteps) ->
            longestPath(neighbor, finish, currentSteps + neighborSteps, visited.plus(neighbor))
        } ?: Int.MIN_VALUE
}

fun process(mapString: String): Int {
    val grid = mapString.toGrid()
    val graph = grid.toGraph()

    return graph.longestPath(grid.start(), grid.finish())
}

fun main() {
    val mapString = getInput("Day23Input.txt")

    println("Task 1")
    println(process(mapString))

    println("Task 2")
    println(process(mapString.removeSlopes()))
}

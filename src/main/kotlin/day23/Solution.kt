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

data class Grid(val coordinateCharMap: Map<Coordinate, Char>) {
    fun start(): Coordinate = coordinateCharMap.keys.minBy { it.y }
    fun finish(): Coordinate = coordinateCharMap.keys.maxBy { it.y }
    fun tile(c: Coordinate): Char = coordinateCharMap.getOrDefault(c, '#')
    private fun tileCount(c: Coordinate): Int = if (coordinateCharMap.getOrDefault(c, '#') != '#') 1 else 0

    fun neighboursCount(c: Coordinate): Int =
        if (tile(c) == '#') 0 else setOf(c.north(), c.south(), c.east(), c.west()).sumOf { tileCount(it) }

    fun neighbours(c: Coordinate): List<Coordinate> = when (tile(c)) {
        '^' -> listOf(c.north())
        'v' -> listOf(c.south())
        '>' -> listOf(c.east())
        '<' -> listOf(c.west())
        '.' -> {
            val mutableList: MutableList<Coordinate> = mutableListOf()
            if (tile(c.north()) !in setOf('#', 'v')) mutableList.add(c.north())
            if (tile(c.south()) !in setOf('#', '^')) mutableList.add(c.south())
            if (tile(c.east()) !in setOf('#', '<')) mutableList.add(c.east())
            if (tile(c.west()) !in setOf('#', '>')) mutableList.add(c.west())

            mutableList
        }

        else -> emptyList()
    }

    fun nodes(): List<Coordinate> = coordinateCharMap
        .keys
        .map { it to neighboursCount(it) }
        .filter { (_, count) -> count != 2 }
        .map { it.first }

    private fun edge(previous: Coordinate, current: Coordinate, steps: Int = 0): Pair<Coordinate, Int> = when {
        previous == finish() ->
            previous to 0

        neighboursCount(current) == 2 ->
            neighbours(current)
                .firstOrNull { next -> next != previous }
                .let { next -> edge(current, next ?: previous, steps + 1) }

        else ->
            current to steps
    }

    fun toGraph() =
        Graph(nodes().associateWith { start -> neighbours(start).map { neighbour -> edge(start, neighbour, 1) } })
}

data class Graph(val adjacencyList: Map<Coordinate, List<Pair<Coordinate, Int>>>) {
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

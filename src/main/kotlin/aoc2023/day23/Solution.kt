package aoc2023.day23

import extensions.filePathToStringList
import extensions.toGrid

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

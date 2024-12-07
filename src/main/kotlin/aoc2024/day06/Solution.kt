package aoc2024.day06

import datastructures.Coordinate
import datastructures.Grid
import extensions.filePathToGrid
import extensions.println

fun Coordinate.turnRight(): Coordinate = Coordinate(this.column, this.row * -1)

fun Grid.path(): Set<Pair<Coordinate, Coordinate>> {
    var current = findCoordinateByTile('^').first() to Coordinate.ORIGIN.north()
    val result = mutableSetOf<Pair<Coordinate, Coordinate>>()

    while (true) {
        if (isOutOfBounds(current.first)) return result

        if (current in result) return emptySet()

        result.add(current)

        current =
            if (tile(current.first + current.second) == '#') {
                current.first + current.second.turnRight() to current.second.turnRight()
            } else {
                current.first + current.second to current.second
            }
    }
}

fun Grid.add(
    coordinate: Coordinate,
    char: Char,
): Grid = Grid(coordinateCharMap.plus(coordinate to char))

fun main() {
    val input = "aoc2024/Day06Input.txt".filePathToGrid('.')
    input
        .path()
        .distinctBy { it.first }
        .count()
        .println() // 5030

    input
        .export()
        .filter { it.second == '.' }
        .also { it.count().println() }
        .count { (coordinate, _) -> input.add(coordinate, '#').path().isEmpty() }
        .println()
}

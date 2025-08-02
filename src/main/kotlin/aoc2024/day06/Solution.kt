package aoc2024.day06

import datastructures.Coordinate
import datastructures.Grid
import extensions.filePathToGrid
import extensions.println

fun Grid.path(): Pair<Set<Coordinate>, Boolean> {
    var location = start
    var direction = Coordinate.ORIGIN.north()
    val visited = mutableSetOf<Pair<Coordinate, Coordinate>>()

    while (isInBounds(location) && (location to direction) !in visited) {
        visited += location to direction
        val next = location + direction

        if (tile(next) == '#') {
            direction = direction.turnRight()
        } else {
            location = next
        }
    }

    return visited.map { it.first }.toSet() to isInBounds(location)
}

fun main() {
    val input = "aoc2024/Day06Input.txt".filePathToGrid('.')
    val start = input.findCoordinateByValue('^').first()
    val lab = Grid(coordinateCharMap = input.coordinateCharMap, start = start)
    val guardPath = lab.path()

    guardPath
        .first
        .count()
        .println() // 5030

    guardPath
        .first
        .filter { obstacle -> obstacle != start }
        .count { obstacle ->
            lab
                .plus(obstacle to '#')
                .path()
                .second
        }
        .println() // 1928
}

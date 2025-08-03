package aoc2024.day12

import datastructures.Coordinate
import datastructures.Grid
import extensions.filePathToGrid
import extensions.println

data class Region(
    val coordinate: Coordinate = Coordinate.ORIGIN,
    val value: Char = ' ',
    val area: Int = 0,
    val perimeter: Int = 0,
    val sides: Int = 0
)

fun Grid.toRegions(): List<Region> =
    mutableSetOf<Coordinate>()
        .let { visited ->
            export()
                .map { (coordinate, tile) ->
                    if (visited.contains(coordinate) || tile == defaultValue) {
                        return@map Region()
                    }
                    val queue = mutableListOf(coordinate)
                    var area = 0
                    var perimeter = 0
                    var sides = 0

                    while (queue.isNotEmpty()) {
                        val current = queue.removeFirst()
                        visited.add(current)
                        val neighbours = current.neighboursNESWClockwise()
                        queue.addAll(neighbours.filter { it !in visited && it !in queue && tile(it) == tile })
                        area += 1
                        perimeter += neighbours.count { tile(it) != tile }
                        // external corners
                        sides += neighbours.plus(current.north()).zipWithNext().count{ (a, b) -> tile(a) != tile && tile(b) != tile }
                        // internal corners
                        sides += current
                            .neighboursAllClockwise()
                            .plus(current.north())
                            .windowed(3, 2)
                            .count { list -> tile(list[0]) == tile && tile(list[2]) == tile && tile(list[1]) != tile }
                    }
                    return@map Region(coordinate, value = tile, area = area, perimeter = perimeter, sides = sides)
                }
        }

fun main() {
    val input = "aoc2024/Day12Input.txt".filePathToGrid(' ')
    val regions = input.toRegions()
    regions.sumOf { it.area * it.perimeter }.println()
    regions.sumOf { it.area * it.sides }.println()
}
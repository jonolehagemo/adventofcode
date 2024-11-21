package aoc2022.day14

import datastructures.Coordinate
import extensions.filePathToStringList
import extensions.println

fun List<String>.toCoordinateSet(): Set<Coordinate> =
    flatMap { line ->
        line
            .split(" -> ")
            .map {
                it.split(",").let { (x, y) -> Coordinate(y.toInt(), x.toInt()) }
            }.zipWithNext { a, b -> a.toList(b) }
            .flatten()
    }.toSet()

fun Set<Coordinate>.getSandUnits(from: Coordinate): Int {
    val result = this.toMutableSet()
    val abyss = maxOf { it.row }

    while (from !in result) {
        var sand = from

        while (sand !in result) {
            when {
                sand.row == abyss -> return result.minus(this).size
                sand.south() !in result -> sand = sand.south()
                sand.southWest() !in result -> sand = sand.southWest()
                sand.southEast() !in result -> sand = sand.southEast()
                else -> result.add(sand)
            }
        }
    }

    return result.minus(this).size
}

fun main() {
    val input =
        "aoc2022/Day14Input.txt".filePathToStringList().toCoordinateSet()
    val from = Coordinate(0, 500)
    input.getSandUnits(from).println()

    val floor =
        (input.maxOf { it.row } + 2).let { floorLevel ->
            (
                Coordinate(floorLevel, from.column - floorLevel)
                    .toList(Coordinate(floorLevel, from.column + floorLevel))
            ).toSet()
        }
    input.plus(floor).getSandUnits(from).println()
}

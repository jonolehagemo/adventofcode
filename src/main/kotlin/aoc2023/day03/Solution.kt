package aoc2023.day03

import datastructures.Coordinate
import datastructures.Grid
import extensions.filePathToGrid
import extensions.println
import extensions.toProduct

data class Part(
    val coordinate: Coordinate,
    val symbol: Char,
    val partNumber: Int,
)

fun Grid.getParts(): List<Part> =
    coordinates()
        .filter {
            tile(it).isDigit() && !tile(it.west()).isDigit()
        }.mapNotNull { coordinate ->
            val slice = sliceEast(coordinate.west()).takeWhile { it.second.isDigit() }
            val gearCoordinate =
                slice
                    .flatMap { it.first.neighbours() }
                    .firstOrNull { !tile(it).isDigit() && tile(it) != defaultValue }
                    ?: return@mapNotNull null
            Part(
                coordinate = gearCoordinate,
                symbol = tile(gearCoordinate),
                partNumber = slice.map { it.second }.joinToString("").toInt(),
            )
        }

fun List<Part>.sumGearRatios(): Int =
    this
        .filter { it.symbol == '*' }
        .groupBy { it.coordinate }
        .filter { entry -> entry.value.size == 2 }
        .mapValues { entry -> entry.value.map { it.partNumber }.toProduct() }
        .map { it.value }
        .sum()

fun main() {
    val parts = "aoc2023/Day03Input.txt".filePathToGrid('.').getParts()
    parts.sumOf { it.partNumber }.println()
    parts.sumGearRatios().println()
}

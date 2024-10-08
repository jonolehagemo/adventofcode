package aoc2023.day03

import datastructures.Coordinate
import datastructures.Grid
import extensions.filePathToGrid
import extensions.println

data class Part(val coordinate: Coordinate, val symbol: Char, val partNumber: Int)

fun Grid.getParts(): List<Part> {
    val seen = mutableSetOf<Coordinate>()
    val result = mutableListOf<Part>()

    for (y in rowRange()) {
        for (x in columnRange()) {
            var coordinate = Coordinate(y, x)
            var char = tile(coordinate)
            if (coordinate !in seen && char.isDigit()) {
                var neighbours = listOf<Coordinate>()
                var numberString = ""
                seen.add(coordinate)

                while (char.isDigit()) {
                    neighbours = neighbours.plus(coordinate.neighbours())
                    numberString = numberString.plus(char)
                    coordinate = coordinate.east()
                    char = tile(coordinate)
                    seen.add(coordinate)
                }

                val symbol = neighbours.toSet().firstOrNull { c -> tile(c) !in "0123456789$defaultValue" }
                if (symbol != null)
                    result.add(Part(symbol, tile(symbol), numberString.toInt()))
            }
        }
    }

    return result.toList()
}

fun List<Part>.sumGearRatios(): Int = this
    .filter { it.symbol == '*' }
    .groupBy { it.coordinate }
    .filter { entry -> entry.value.size == 2 }
    .mapValues { entry -> entry.value.map { it.partNumber }.reduce { item, sum -> sum * item } }
    .map { it.value }
    .sum()

fun main() {
    val parts = "aoc2023/Day03Input.txt".filePathToGrid('.').getParts()
    parts.sumOf { it.partNumber }.println()
    parts.sumGearRatios().println()
}
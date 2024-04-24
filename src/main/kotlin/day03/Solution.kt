package day03

import datastructures.Coordinate
import datastructures.Grid
import datastructures.filePathAsGrid

fun Grid.getNumbers(): List<Triple<Coordinate, Char, Int>> {
    val seen = mutableSetOf<Coordinate>()
    val result = mutableListOf<Triple<Coordinate, Char, Int>>()

    for (y in yRange()) {
        for (x in xRange()) {
            var coordinate = Coordinate(y, x)
            var char = tile(coordinate)
            if (coordinate !in seen && "0123456789".contains(char)) {
                var neighbours = listOf<Coordinate>()
                var numberString = ""
                seen.add(coordinate)

                while ("0123456789".contains(char)) {
                    neighbours = neighbours.plus(coordinate.neighbours())
                    numberString = numberString.plus(char)
                    coordinate = coordinate.east()
                    char = tile(coordinate)
                    seen.add(coordinate)
                }

                val symbol = neighbours.sorted().toSet().firstOrNull { c -> tile(c) !in "0123456789$defaultValue" }
                if (symbol != null)
                    result.add(Triple(symbol, tile(symbol), numberString.toInt()))
            }
        }
    }

    return result.toList()
}

fun task1(numbers : List<Triple<Coordinate, Char, Int>>): Int = numbers.sumOf { it.third }

fun task2(numbers : List<Triple<Coordinate, Char, Int>>): Int = numbers
    .filter { it.second == '*' }
    .groupBy { it.first }
    .filter { entry -> entry.value.size == 2 }
    .mapValues { entry ->  entry.value.map { it.third }.fold(1){item, sum -> sum * item} }
    .map { it.value }
    .sum()

fun main() {
    val grid = "Day03Input.txt".filePathAsGrid('.')
    val numbers = grid.getNumbers()

    println("task1 ${task1(numbers)}")
    println("task2 ${task2(numbers)}")
}
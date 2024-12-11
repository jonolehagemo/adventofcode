package aoc2024.day08

import datastructures.Coordinate
import datastructures.Grid
import extensions.cartesianProduct
import extensions.filePathToGrid
import extensions.println

fun Pair<Coordinate, Coordinate>.pairSort(): Pair<Coordinate, Coordinate> = if (first < second) this else second to first

fun Grid.antennaPairs(): Set<Pair<Coordinate, Coordinate>> =
    coordinateCharMap
        .toList()
        .groupBy { it.second }
        .mapValues { entry ->
            entry.value
                .map { it.first }
                .let { list -> list.cartesianProduct(list) }
                .filter { it.first != it.second }
                .distinctBy { it.pairSort() }
        }.flatMap { it.value }
        .toSet()

fun Pair<Coordinate, Coordinate>.antiNodes1(grid: Grid): List<Coordinate> =
    listOf(first - (second - first), second + (second - first)).filter { grid.isInBounds(it) }

fun Pair<Coordinate, Coordinate>.antiNodes2(grid: Grid): List<Coordinate> =
    generateSequence(first) { it + (second - first) }.takeWhile { grid.isInBounds(it) }.toList() +
        generateSequence(second) { it - (second - first) }.takeWhile { grid.isInBounds(it) }.toList()

fun main() {
    val input = "aoc2024/Day08Input.txt".filePathToGrid('.')
    input
        .antennaPairs()
        .flatMap { it.antiNodes1(input) }
        .toSet()
        .count()
        .println()
    input
        .antennaPairs()
        .flatMap { it.antiNodes2(input) }
        .toSet()
        .count()
        .println()
}

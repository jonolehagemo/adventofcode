package aoc2022.day08

import datastructures.Coordinate
import datastructures.Grid
import extensions.filePathToStringList
import extensions.println
import extensions.toGrid

fun List<Pair<Coordinate, Char>>.findHighestTree(): Int = maxOfOrNull { it.second.toString().toInt() } ?: -1

fun Grid.countVisibleTrees(): Int =
    export()
        .map { (c, char) -> c to char.toString().toInt() }
        .map { (c, value) ->
            c.row == 0 ||
                c.row == rowMax() ||
                c.column == 0 ||
                c.column == columnMax() ||
                sliceNorth(c).findHighestTree() < value ||
                sliceEast(c).findHighestTree() < value ||
                sliceWest(c).findHighestTree() < value ||
                sliceSouth(c).findHighestTree() < value
        }.count { it }

fun List<Pair<Coordinate, Char>>.findScenicView(value: Int): Int =
    this
        .map { (_, v) -> v.toString().toInt() }
        .withIndex()
        .firstOrNull { value <= it.value }
        ?.index
        ?.plus(1) ?: this.size

fun Grid.maxScenicView(): Int =
    export()
        .map { (c, char) -> c to char.toString().toInt() }
        .maxOf { (c, value) ->
            listOf(sliceNorth(c), sliceEast(c), sliceSouth(c), sliceWest(c))
                .map { it.findScenicView(value) }
                .reduce { a, b -> a * b }
        }

fun main() {
    val input = "aoc2022/Day08Input.txt".filePathToStringList().toGrid('0')
    input.countVisibleTrees().println()
    input.maxScenicView().println()
}

package aoc2022.day15

import datastructures.Coordinate
import extensions.filePathToStringList
import extensions.println
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun List<String>.toSensorBeaconPairs(): List<Pair<Coordinate, Coordinate>> =
    map { line ->
        val regex =
            """Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""".toRegex()
        val matchResult = regex.find(line)!!
        val (sx, sy, bx, by) = matchResult.destructured
        Coordinate(sy.toInt(), sx.toInt()) to Coordinate(by.toInt(), bx.toInt())
    }

fun IntRange.overlap(other: IntRange): Boolean = max(this.first, other.first) < min(this.last, other.last)

fun List<IntRange>.merge(): List<IntRange> {
    val result =
        this
            .sortedBy { it.first }
            .drop(1)
            .fold(listOf(sortedBy { it.first }.first())) { sum: List<IntRange>, item: IntRange ->
                if (sum.none { it.overlap(item) }) {
                    println("overlap $item")
                    return@fold sum.plus(item) as List<IntRange>
                } else {
                    println("nope $item")
                    val last = sum.takeLast(1).first()
                    val merged =
                        IntRange(
                            min(last.first, item.first),
                            max(last.last, item.last),
                        )
                    return@fold sum.dropLast(1).plus(merged) as List<IntRange>
                }
            }

    result.println()
    return emptyList()
}

fun List<Pair<Coordinate, Coordinate>>.countPositions(y: Int): Int {
    this
        .filter { (sensor, beacon) ->
            val manhattanDistance = sensor.manhattanDistance(beacon)
            y in sensor.row - manhattanDistance..sensor.row + manhattanDistance
        }.map { (sensor, _) ->
            val yDistance = abs(sensor.row - y)
            IntRange(sensor.column - yDistance, sensor.column + yDistance)
        }.sortedBy { it.first }
        .also { it.println() }

    return 0
}

fun main() {
    val input =
        "aoc2022/Day15Input.txt".filePathToStringList().toSensorBeaconPairs()
    input.println()
}

package aoc2023.day06

import extensions.*
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt

fun List<String>.toBoatRaceWins(): Int {
    val time = this.first().removeBefore(':').toDoubleList()
    val distance = this.last().removeBefore(':').toDoubleList()
    return time.zip(distance) { a, b -> a to b }
// see https://en.wikipedia.org/wiki/Quadratic_formula
// from ax^2 + bx + c = d where a=1, b=time, c=0, d=distance -> x^2 + time * x - distance = 0
        .map { (time, distance) ->
            val d = sqrt(time.pow(2) - 4 * distance)
            return@map ceil((time + d) / 2 - 1) - floor((time - d) / 2 + 1) + 1
        }
        .reduce { a, b -> a * b }
        .toInt()
}

fun main() {
    val lines = "aoc2023/Day06Input.txt".filePathToStringList()
    lines.toBoatRaceWins().println()
    lines.map { it.removeSpaces() }.toBoatRaceWins().println()
}
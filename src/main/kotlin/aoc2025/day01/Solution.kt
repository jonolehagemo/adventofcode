package aoc2025.day01

import extensions.filePathToStringList
import extensions.println
import kotlin.math.abs

const val start = 50
const val rotationSize = 100

fun List<String>.toIntList() =
    map {
        it
            .replace("R", "")
            .replace("L", "-")
            .toInt()
    }

fun Int.rotate(move: Int): Pair<Int, Int> {
    val rotations = abs(move / rotationSize)
    val new = this + (move % rotationSize)

    val result = when {
        new >= rotationSize -> new - rotationSize
        new < 0 -> rotationSize + new
        else -> new
    }

    val passed0 = when {
        this > 0 && new < 0 -> 1
        new == 0 -> 1
        new >= rotationSize -> 1
        else -> 0
    }

    return result to rotations + passed0
}


fun main() {
    val rotations = "aoc2025/Day01Input.txt"
        .filePathToStringList()
        .toIntList()
            .fold(listOf(start to 0)) { acc, i ->
                acc + acc.last().first.rotate(i)
            }
    rotations.count { it.first == 0 }.println()
    rotations.sumOf { it.second }.println()
}
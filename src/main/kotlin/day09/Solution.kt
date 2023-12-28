package day09

import java.io.File

fun extrapolate(history: List<Int>): Pair<Int, Int> {
    if (history.all { it == 0 })
        return 0 to 0
    val extrapolated = extrapolate(history.zipWithNext{ a, b -> b - a })
    return history.first() - extrapolated.first to history.last() + extrapolated.second
}

fun process(filePath: String): Pair<Int, Int> = File(ClassLoader.getSystemResource(filePath).file)
    .readLines()
    .map { it.split(" ").map(String::toInt) }
    .map { extrapolate(it) }
    .fold(0 to 0) { sum, item -> sum.first + item.first to sum.second + item.second }

fun main() {
    process("Day09Input.txt").also { println(it.second) }.also { println(it.first) }
}
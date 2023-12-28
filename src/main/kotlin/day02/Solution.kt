package day02

import java.io.File

fun getInput(filePath: String) = File(ClassLoader.getSystemResource(filePath).file)
    .readLines()
    .map { line ->
        line.split(": ")[1]
            .replace("; ", ", ")
            .split(", ")
            .map { it.split(" ") }
            .map { it[1] to it[0].toInt() }
    }

fun process1(input: List<List<Pair<String, Int>>>): Int = input
    .withIndex()
    .filter { (_, line) ->
        line.none { mapOf("red" to 12, "green" to 13, "blue" to 14).getOrDefault(it.first, 0) < it.second }
    }
    .sumOf { (index, _) -> index + 1 }

fun process2(input: List<List<Pair<String, Int>>>): Int = input
    .sumOf { line ->
        line.groupBy { it.first }
            .mapValues { (_, group) -> group.maxOf { it.second } }
            .values
            .toList()
            .fold(1) { sum, item -> sum * item }
            .toInt()
    }

fun main() {
    val input = getInput("Day02Input.txt")
    println("""process ${process1(input)}""")
    println("""process ${process2(input)}""")
}
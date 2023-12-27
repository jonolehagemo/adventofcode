package day02

import java.io.File

fun process1(filePath: String): Int = File(ClassLoader.getSystemResource(filePath).file)
    .readLines()
    .filter { line ->
        line.split(": ")[1]
            .split("; ")
            .flatMap { it.split(", ") }
            .map { it.split(" ") }
            .map { it[1] to it[0].toInt() }
            .none { mapOf("red" to 12, "green" to 13, "blue" to 14).getOrDefault(it.first, 0) < it.second }
    }
    .sumOf { line -> line.drop(5).split(": ").first().toInt() }

fun process2(filePath: String): Int = File(ClassLoader.getSystemResource(filePath).file)
    .readLines().sumOf { line ->
        line.replace(";", ",")
            .split(": ")[1]
            .split(", ")
            .map { it.split(" ") }
            .map { it[1] to it[0].toInt() }
            .groupBy { it.first }
            .mapValues { (_, group) -> group.maxOf { it.second } }
            .values
            .toList()
            .fold(1) { sum, item -> sum * item }
            .toInt()
    }


fun main() {
    println("""process ${process1("Day02Input.txt")}""")
    println("""process ${process2("Day02Input.txt")}""")
}
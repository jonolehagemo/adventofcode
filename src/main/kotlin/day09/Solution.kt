package day09

import java.io.File
fun getInput(filePath: String): List<List<Int>> =
    File(ClassLoader.getSystemResource(filePath).file).readLines().map { it.split(" ").map(String::toInt) }

fun List<Int>.extrapolateNext(): Int =
    if (this.all { it == 0 }) 0 else this.last() + this.zipWithNext { a, b -> b - a }.extrapolateNext()

fun main() {
    println(getInput("Day09Input.txt").sumOf { it.extrapolateNext() })
    println(getInput("Day09Input.txt").sumOf { it.reversed().extrapolateNext() })
}
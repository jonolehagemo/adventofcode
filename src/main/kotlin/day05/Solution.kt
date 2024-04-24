package day05

import datastructures.*
import kotlin.math.*

fun String.toDoubleList(): List<Double> = this.trim().split("\\s+".toRegex()).map { it.toDouble() }

fun List<String>.toBoatRaces(): List<Pair<Double, Double>> {
    val time = this.first().removeBefore(':').toDoubleList()
    val distance = this.last().removeBefore(':').toDoubleList()
    return time.zip(distance) { a, b -> a to b }
}

fun process(boatRaces: List<Pair<Double, Double>>): Int = boatRaces
    .map { (time, distance) ->
        val d = sqrt(time.pow(2) - 4 * distance)
        return@map ceil((time + d) / 2 - 1) - floor((time - d) / 2 + 1) + 1
    }
    .product()
    .toInt()

fun main() {
    val lines = "Day05Input.txt".filePathAsStringList()
    println("task1 ${process(lines.toBoatRaces())}")
    println("task2 ${process(lines.map { it.removeSpaces() }.toBoatRaces())}")
}
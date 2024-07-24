package day14

import extensions.*

fun List<String>.sum(): Int =
    withIndex().sumOf { (index, line) -> line.filter { char -> char == 'O' }.sumOf { size - index } }

fun List<String>.rollWest(): List<String> =
    map { line ->
        line
            .split('#')
            .joinToString("#") { it.split("").sortedDescending().joinToString("") }
    }

fun List<String>.rollNorth(): List<String> = transpose().rollWest().transpose()
fun List<String>.rollSouth(): List<String> = rotateRight().rollWest().rotateLeft()
fun List<String>.rollEast(): List<String> = map { it.reversed() }.rollWest().map { it.reversed() }
fun List<String>.cycle(): List<String> = rollNorth().rollWest().rollSouth().rollEast()

fun List<String>.findCycle(cycles: Long): List<String> {
    val cache: MutableMap<List<String>, Int> = mutableMapOf()
    val states: MutableList<List<String>> = mutableListOf()
    var i = 0
    var current = this

    while (true) {
        if (cache.contains(current))
            break

        cache[current] = i
        states.add(current)
        current = current.cycle()
        i++
    }

    val first = cache.getOrDefault(current, 0)

    return states[((cycles - first) % (i - first) + first).toInt()]
}

fun main() {
    "Day14Input.txt".filePathToStringList().sum().println()
    "Day14Input.txt".filePathToStringList().findCycle(1_000_000_000).sum().println()
}
package day14

import extensions.filePathToStringList
import extensions.println
import extensions.rotateLeft

val cache: MutableMap<List<String>, Int> = mutableMapOf()
val states: MutableList<List<String>> = mutableListOf()

fun List<String>.sum(): Int = this
    .sumOf { line ->
        line
            .split('#')
            .joinToString("#") { it.split("").sortedDescending().joinToString("") }
            .withIndex()
            .filter { (_, char) -> char == 'O' }
            .sumOf { (index, _) -> line.length - index }
    }

fun List<String>.step(): List<String> = this
    .rotateLeft()
    .map { line ->
        line
            .split('#')
            .joinToString("#") { it.split("").sortedDescending().joinToString("") }
    }

fun List<String>.cycle(): List<String> = this.step().step().step().step()

fun List<String>.findCycle(cycles: Long): List<String> {
    var i = 0
    var current = this

    while (true){
        if (cache.contains(current))
            break

        cache[current] = i
        states.add(current)
        current = current.cycle()
        i++
    }

    val first = cache.getOrDefault(current, 0)
    val calculatedIndex = ((cycles - first) % (i - first) + first).toInt()
    println(cache.size)
    println(cache)
    println(states.size)
    println(states)
    println(first)
    println(calculatedIndex)
    states.map { it.sum() }.println()

    return states[calculatedIndex]
}
fun main() {
    val input = "Day14Input.txt"
        .filePathToStringList()

    // Part 1
    input.rotateLeft().sum().println()

    // Part 2
    input.findCycle(1_000_000_000).sum().println()
}
package aoc2023.day20

import extensions.filePathToStringList
import extensions.println
import math.lcm

enum class ModuleType { UNKNOWN, BROADCASTER, FLIPFLOP, CONJUNCTION }

data class Module(
    val type: ModuleType = ModuleType.UNKNOWN,
    var lastPulse: Boolean = false,
    var start: Int = 0,
    var end: Int = 0
)

data class Relation(val origin: String, val destination: String)

fun List<String>.toModules(): Map<String, Module> =
    associate { line ->
        val name = line.substringBefore(" -> ")
        when {
            name == "broadcaster" -> name to Module(ModuleType.BROADCASTER)
            name.startsWith('%') -> name.drop(1) to Module(ModuleType.FLIPFLOP)
            name.startsWith('&') -> name.drop(1) to Module(ModuleType.CONJUNCTION)
            else -> name to Module()
        }
    }

fun List<String>.toRelations(): List<Relation> =
    flatMap { line ->
        line
            .replace("%", "")
            .replace("&", "")
            .split(" -> ")
            .let { (from, destinations) ->
                destinations.split(", ").map { Relation(from, it) }
            }
    }

fun List<String>.process1(starts: Int): Int {
    val modules = this.toModules()
    val relations = this.toRelations()
    val deque: ArrayDeque<Pair<String, Boolean>> = ArrayDeque(0)
    var lo = 0
    var hi = 0

    for (i in (0..<starts)) {
        deque.add("broadcaster" to false)

        while (deque.isNotEmpty()) {
            val (current, pulse) = deque.removeFirst()
            val module = modules.getOrDefault(current, Module())
            val destinations = relations.filter { it.origin == current }

            if (pulse) hi++
            else lo++

            when {
                (module.type == ModuleType.BROADCASTER) ->
                    deque.addAll(destinations.map { it.destination to false })

                (module.type == ModuleType.FLIPFLOP && !pulse) -> {
                    module.lastPulse = !module.lastPulse
                    deque.addAll(destinations.map { it.destination to module.lastPulse })
                }

                (module.type == ModuleType.CONJUNCTION) -> {
                    module.lastPulse = !relations
                        .filter { current == it.destination }
                        .map { modules.getOrDefault(it.origin, Module(ModuleType.BROADCASTER)).lastPulse }
                        .all { it }
                    deque.addAll(destinations.map { relation -> relation.destination to module.lastPulse })
                }
            }
        }
    }

    return lo * hi
}

fun findOrigin(destination: String, steps: Int, relations: List<Relation>): List<String> = relations
    .filter { it.destination == destination }
    .map { it.origin }
    .let { result -> if (steps <= 1) result else result.flatMap { findOrigin(it, steps - 1, relations) } }

fun List<String>.process2(destination: String): Long {
    val modules = this.toModules()
    val relations = this.toRelations()
    val deque: ArrayDeque<Pair<String, Boolean>> = ArrayDeque(0)
    var presses = 0
    val outputOrigins = findOrigin(destination, 2, relations)

    while (
        modules.entries
            .filter { it.key in outputOrigins }
            .any { it.value.end == 0 }
    ) {
        presses++
        deque.add("broadcaster" to false)

        while (deque.isNotEmpty()) {
            val (current, pulse) = deque.removeFirst()
            val module = modules.getOrDefault(current, Module())
            val destinations = relations.filter { it.origin == current }

            when {
                (module.type == ModuleType.BROADCASTER) ->
                    deque.addAll(destinations.map { it.destination to false })

                (module.type == ModuleType.FLIPFLOP && !pulse) -> {
                    module.lastPulse = !module.lastPulse
                    deque.addAll(destinations.map { it.destination to module.lastPulse })
                }

                (module.type == ModuleType.CONJUNCTION) -> {
                    module.lastPulse = !relations
                        .filter { current == it.destination }
                        .map { modules.getOrDefault(it.origin, Module(ModuleType.BROADCASTER)).lastPulse }
                        .all { it }
                    deque.addAll(destinations.map { it.destination to module.lastPulse })
                    if (module.start == 0 && !module.lastPulse)
                        module.start = presses

                    if (module.end == 0 && module.lastPulse)
                        module.end = presses
                }
            }
        }
    }

    val result = modules
        .filter { it.key in outputOrigins }
        .map { (it.value.end - it.value.start + 1).toLong() }
        .fold(1L) { sum, acc -> lcm(sum, acc) }

    return result
}

fun main() {
    "aoc2023/Day20Input.txt"
        .filePathToStringList()
        .process1(1000)
        .println()

    "aoc2023/Day20Input.txt"
        .filePathToStringList()
        .process2("rx")
        .println()
}
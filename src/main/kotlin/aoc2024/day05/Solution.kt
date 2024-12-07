package aoc2024.day05

import extensions.filePathToListOfStringList
import extensions.println

fun List<String>.toRules(): List<Pair<Int, Int>> = map { it.split('|').let { (a, b) -> a.toInt() to b.toInt() } }

fun List<String>.toActions(): List<List<Int>> = this.map { it.split(',').map { it.toInt() } }

fun List<Int>.isInOrder(rules: List<Pair<Int, Int>>): Boolean =
    generateSequence(0) { it + 1 }
        .map { drop(it) }
        .take(size - 1)
        .all { list ->
            list
                .drop(1)
                .all { s ->
                    s in
                        rules
                            .filter { it.first == list.first() }
                            .map { it.second }
                }
        }

fun List<Int>.middle(): Int = this[size.floorDiv(2)]

fun List<Int>.reOrder(rules: List<Pair<Int, Int>>): List<Int> {
    val deque = ArrayDeque(this)
    val result = mutableListOf<Int>()

    while (deque.isNotEmpty()) {
        val toCheck = deque.removeFirst()
        val rest = filter { it != toCheck && it !in result }
        val following = rules.filter { it.first == toCheck && it.second in rest }.map { it.second }

        if (rest.all { it in following }) {
            result.add(toCheck)
            continue
        }

        deque.addLast(toCheck)
    }

    return result
}

fun main() {
    val input = "aoc2024/Day05Input.txt".filePathToListOfStringList()
    val rules = input.first().toRules()
    val actions = input.last().toActions()
    actions
        .filter { it.isInOrder(rules) }
        .sumOf { it.middle() }
        .println()
    actions
        .filterNot { it.isInOrder(rules) }
        .map { it.reOrder(rules) }
        .sumOf { it.middle() }
        .println()
}

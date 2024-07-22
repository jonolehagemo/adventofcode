package day08

import extensions.filePathToStringList
import extensions.println

fun getInstructions(filePath: String): List<Int> =
    filePath.filePathToStringList().first().map { if (it == 'L') 0 else 1 }

fun getNetwork(filePath: String): Map<String, List<String>> =
    filePath.filePathToStringList()
        .drop(2)
        .associate {
            val (key, left, right) = Regex("(\\w+) = \\((\\w+), (\\w+)\\)").find(it)!!.destructured
            return@associate key to listOf(left, right)
        }

fun countSteps(instructions: List<Int>, network: Map<String, List<String>>, start: String): Int {
    var steps = 0
    var node = start

    while (!node.endsWith('Z')) {
        node = network.getOrDefault(node, emptyList())[instructions[steps % instructions.size]]
        steps++
    }

    return steps
}

fun findGreatestCommonDivisor(a: Long, b: Long): Long = if (b == 0L) a else findGreatestCommonDivisor(b, a % b)

fun main() {
    val instructions = getInstructions("Day08Input.txt")
    val network = getNetwork("Day08Input.txt")
    // task1
    countSteps(instructions, network, "AAA")
        .println()

    //task 2
    network.keys
        .filter { it.endsWith('A') }
        .map { countSteps(instructions, network, it).toLong() }
        .fold(1L) { sum, acc -> sum * acc / findGreatestCommonDivisor(sum, acc) }
        .println()
}
package day08

import extensions.filePathToStringList

fun getInstructions(filePath: String): List<Int> =
    filePath.filePathToStringList().first().map { if (it == 'L') 0 else 1 }

fun getNetwork(filePath: String): Map<String, List<String>> {
    val regex = Regex("(\\w+) = \\((\\w+), (\\w+)\\)")
    return filePath.filePathToStringList()
        .drop(2)
        .associate {
            val (key, left, right) = regex.find(it)!!.destructured
            return@associate key to listOf(left, right)
        }
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

fun findGreatestCommonDivisor(number1: Long, number2: Long): Long {
    var num1 = number1
    var num2 = number2

    while (num2 != 0L) {
        val temp = num2
        num2 = num1 % num2
        num1 = temp
    }

    return num1
}

fun main() {
    val instructions = getInstructions("Day08Input.txt")
    val network = getNetwork("Day08Input.txt")
    // task1
    println(countSteps(instructions, network, "AAA"))

    //task 2
    network.keys
        .filter { it.endsWith('A') }
        .map { countSteps(instructions, network, it).toLong() }
        .fold(1L) { sum, acc -> sum * acc / findGreatestCommonDivisor(sum, acc) }
        .also { println(it) }
}
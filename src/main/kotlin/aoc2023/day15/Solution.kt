package aoc2023.day15

import extensions.filePathToString
import extensions.println

fun String.elfHash(): Int = toCharArray().map { it.code }.fold(0) { sum, code -> ((sum + code) * 17) % 256 }

fun List<String>.calculate(): Int = this
    .fold(mutableMapOf<Int, List<Pair<String, Int>>>()) { result, string ->
        val label = if (string.contains('=')) string.substringBefore('=') else string.substringBefore('-')
        val box = label.elfHash()
        val focal = if (string.contains('=')) string.substringAfter('=').toInt() else 0
        val contents = result.getOrDefault(box, emptyList())

        if (string.contains('='))
            if (contents.any { it.first == label })
                result[box] = contents.map { (l, v) -> if (l == label) l to focal else l to v }
            else
                result[box] = contents.plus(label to focal)
        else
            result[box] = contents.filter { it.first != label }

        return@fold result
    }
    .map { (box, contents) ->
        contents.withIndex().sumOf { (index, pair) -> (box + 1) * (index + 1) * pair.second }
    }
    .sum()

fun main() {
    val input = "aoc2023/Day15Input.txt".filePathToString().split(',')
    input.sumOf { it.elfHash() }.println()
    input.calculate().println()
}
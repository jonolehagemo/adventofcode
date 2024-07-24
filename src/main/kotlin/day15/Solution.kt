package day15

import extensions.filePathToString
import extensions.println

fun String.elfHash(): Int = toCharArray().map { it.code }.fold(0) { sum, code -> ((sum + code) * 17) % 256 }

fun List<String>.calculate(): Int {
    val hashMap: MutableMap<Int, List<Pair<String, Int>>> = mutableMapOf()

    forEach { string ->
        val label = if (string.contains('=')) string.substringBefore('=') else string.substringBefore('-')
        val box = label.elfHash()
        val value = if (string.contains('=')) string.substringAfter('=').toInt() else 0
        val contents = hashMap.getOrDefault(box, emptyList())

        if (string.contains('='))
            if (contents.any { it.first == label })
                hashMap[box] = contents.map { (l, v) -> if (l == label) l to value else l to v }
            else
                hashMap[box] = contents.plus(label to value)
        else
            hashMap[box] = hashMap.getOrDefault(box, emptyList()).filter { it.first != label }
    }

    return hashMap
        .map { (box, contents) ->
            contents.withIndex().sumOf { (index, pair) -> (box + 1) * (index + 1) * pair.second }
        }
        .sum()
}

fun main() {
    "Day15Input.txt".filePathToString().split(',').sumOf { it.elfHash() }.println()
    "Day15Input.txt".filePathToString().split(',').calculate().println()
}
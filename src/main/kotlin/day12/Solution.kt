package day12

import extensions.filePathToStringList
import extensions.println

val cache: MutableMap<String, Long> = mutableMapOf()

fun String.toCondition(): Pair<String, List<Int>> =
    substringBefore(' ') to
            listOf<Int>().plus(substringAfter(' ').split(',').map { it.toInt() })

fun dfs(condition: String, groups: List<Int>): Long {
    val key = condition.plus(" ").plus(groups)

    if (cache.containsKey(key)) return cache.getOrDefault(key, 0)

    if (groups.isEmpty()) return if ('#' in condition) 0 else 1

    var count = 0L

    for (end in 0 .. condition.length){
        val start = end - (groups[0] -1)

        if (fits(condition, start, end))
            count += dfs(condition.substring(end + 1), groups.drop(1))
    }

    cache[key] = count
    return count
}

fun fits(condition: String, start: Int, end: Int): Boolean {
    // check for out of bounds
    if (start -1 < 0 || condition.length <= end +1) return false

    // check if segment can b surrounded by non-hash characters
    if (condition[start -1] == '#' || condition[end +1] == '#') return false

    // check if we were skipping any "#"
    if ('#' in condition.substring(0, start)) return false

    // check if segment is possible
    if ('.' in condition.substring(start, end +1)) return false

    return true
}

fun main() {
    "Day12Input.txt".filePathToStringList()
        .map { it.toCondition() }
        .sumOf { dfs('.'+it.first+'.', it.second) }
        .println()

    "Day12Input.txt".filePathToStringList()
        .map { it.toCondition() }
        .map {
            generateSequence { it.first }.take(5).joinToString("?") { it } to
                    generateSequence { it.second }.take(5).flatten().toList()
        }
        .sumOf { dfs('.'+it.first+'.', it.second) }
        .println()
}
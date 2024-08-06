package day19

import extensions.filePathToStringList
import extensions.println
import kotlin.math.max
import kotlin.math.min

data class Rule(val category: Char, val comparator: Char, val partNumber: Int, val destination: String)

fun List<String>.toWorkFlows(): Map<String, List<Rule>> = this
    .filter { !it.startsWith('{') && !it.startsWith(' ') }
    .associate { line ->
        val key = line.substringBefore('{')
        val checks = line.substringAfter('{').substringBefore('}').split(',')
        val rules = checks
            .dropLast(1)
            .map { s ->
                val regex = """([a-z])([<>])(\d+?):([a-zAR]+)""".toRegex()
                val matchResult = regex.find(s)!!
                val (category, comparator, partNumber, destination) = matchResult.destructured
                Rule(category.first(), comparator.first(), partNumber.toInt(), destination)
            }
            .plus(Rule('x', '>', 0, checks.last()))

        key to rules
    }

fun List<String>.toParts(): List<Map<Char, Int>> = this
    .filter { it.startsWith('{') }
    .map { line ->
        line
            .substringAfter('{').substringBefore('}').split(',')
            .associate { it.substringBefore('=').first() to it.substringAfter('=').toInt() }
    }

fun isMatch(operator: Char, a: Int, b: Int): Boolean = (operator == '<' && a < b) || (operator == '>' && a > b)

fun isAccepted(part: Map<Char, Int>, workFlows: Map<String, List<Rule>>, key: String = "in"): Boolean {
    if (key == "A")
        return true

    workFlows[key]?.forEach { rule ->
        if (part[rule.category]?.let { isMatch(rule.comparator, it, rule.partNumber) } == true)
            return isAccepted(part, workFlows, rule.destination)
    }

    return false
}

fun count(ranges: Map<Char, Pair<Int, Int>>, workFlows: Map<String, List<Rule>>, key: String = "in"): Long {
    if (key == "R")
        return 0L

    if (key == "A")
        return ranges.values.map { (it.second - it.first + 1).toLong() }.fold(1L) { a, b -> a * b }

    val currentRange = ranges.toMutableMap()
    var total = 0L

    workFlows[key]?.forEach { rule ->
        val (low, high) = currentRange.getOrDefault(rule.category, (1 to 4000))
        val trueRange =
            if (rule.comparator == '<') low to min(rule.partNumber - 1, high)
            else max(rule.partNumber + 1, low) to high
        val falseRange =
            if (rule.comparator == '>') low to min(rule.partNumber, high)
            else max(rule.partNumber, low) to high

        if (trueRange.first <= trueRange.second)
            total += count(currentRange + (rule.category to trueRange), workFlows, rule.destination)

        if (falseRange.first <= falseRange.second)
            currentRange[rule.category] = falseRange
    }

    return total
}

fun main() {
    val input = "Day19Input.txt".filePathToStringList()
    val workFlows = input.toWorkFlows()
    val parts = input.toParts()

    parts
        .filter { isAccepted(it, workFlows) }
        .sumOf { part -> part.values.sumOf { it.toLong() } }
        .println()

    count(
        mapOf('x' to Pair(1, 4000), 'm' to Pair(1, 4000), 'a' to Pair(1, 4000), 's' to Pair(1, 4000)),
        workFlows
    ).println()
}
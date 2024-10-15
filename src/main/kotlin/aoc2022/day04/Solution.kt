package aoc2022.day04

import extensions.filePathToStringList
import extensions.println
import kotlin.math.max
import kotlin.math.min

fun String.toIntRange(): IntRange = IntRange(substringBefore('-').toInt(), substringAfter('-').toInt())

fun String.toIntRangePair(): Pair<IntRange, IntRange> = substringBefore(',').toIntRange() to substringAfter(',').toIntRange()

fun IntRange.fullOverlap(other: IntRange) = (first <= other.first && other.last <= last) || (other.first <= first && last <= other.last)

fun IntRange.partialOverlap(other: IntRange) =
    (max(first, other.first) <= min(other.last, last)) && (min(other.first, first) <= max(last, other.last))

fun main() {
    val input = "aoc2022/Day04Input.txt".filePathToStringList().map { it.toIntRangePair() }
    input.count { (a, b) -> a.fullOverlap(b) }.println()
    input.count { (a, b) -> a.partialOverlap(b) }.println()
}

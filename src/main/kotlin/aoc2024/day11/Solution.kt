package aoc2024.day11

import aoc2022.day03.splitAtIndex
import extensions.filePathToLongList
import extensions.println

val cache: MutableMap<Pair<Long, Int>, Long> = mutableMapOf()

fun Long.hasEvenDigits(): Boolean = toString().length % 2 == 0

fun Long.split(): List<Long> =
    toString()
        .splitAtIndex(toString().length / 2)
        .let { (left, right) -> listOf(left.toLong(), right.toLong()) }

fun blink(
    stone: Long,
    blinks: Int,
    cache: MutableMap<Pair<Long, Int>, Long>,
    key: Pair<Long, Int> = stone to blinks
): Long =
    when {
        blinks == 0 -> 1
        key in cache -> cache.getValue(key)
        else -> {
            val result = when {
                stone == 0L -> blink(1, blinks - 1, cache)
                stone.hasEvenDigits() -> stone.split()
                    .sumOf { blink(it, blinks - 1, cache) }

                else -> blink(stone * 2024, blinks - 1, cache)
            }
            cache[key] = result
            result
        }
    }


fun main() {
    val input: List<Long> =
        "aoc2024/Day11Input.txt".filePathToLongList()
    input.sumOf { blink(it, 25, cache) }.println()
    input.sumOf { blink(it, 75, cache) }.println()
}
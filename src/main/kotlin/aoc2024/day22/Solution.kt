package aoc2024.day22

import extensions.filePathToStringList
import extensions.println

fun Long.mix(other: Long): Long = this.xor(other)

fun Long.prune(): Long = this.mod(16777216L)

fun Long.generateSecret(): Long =
    this
        .let { it.mix(it * 64L).prune() }
        .let { it.mix(it.floorDiv(32L)).prune() }
        .let { it.mix(it * 2048L).prune() }

fun Long.generateSecrets(count: Int): List<Long> =
    generateSequence(this.generateSecret()) { it.generateSecret() }.take(count).toList()

fun Long.lastDigit(): Long = this.mod(10L)

fun Long.findPriceChanges(count: Int): List<Pair<List<Long>, Long>> =
    listOf(this)
        .plus(this.generateSecrets(count))
        .zipWithNext { a, b -> b.lastDigit() to b.lastDigit() - a.lastDigit() }
        .windowed(4) { w -> w.map { it.second } to w.last().first }
        .groupBy({ it.first }, { it.second })
        .mapValues { it.value.first() }
        .toList()

fun main() {
    val count = 2000
    val input = "aoc2024/Day22Input.txt".filePathToStringList().map { it.toLong() }
    input.sumOf { it.generateSecrets(count).last() }.println()
    input
        .flatMap { long -> long.findPriceChanges(count).sortedByDescending { it.second } }
        .groupBy({ it.first }, {it.second})
        .mapValues { it.value.sum() }
        .map { it.value to it.key }
        .sortedByDescending { it.first }
        .first()
        .first
        .println()
}
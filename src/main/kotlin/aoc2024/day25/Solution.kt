package aoc2024.day25

import extensions.cartesianProduct
import extensions.filePathToStringList
import extensions.println
import extensions.transpose

const val CHUNK_LENGTH = 8
const val KEY_LENGTH = 5

fun List<String>.toCount(): List<Int> =
    drop(1)
        .take(KEY_LENGTH)
        .transpose()
        .map { it.count { char ->  char == '#' } }

fun main(){
    "aoc2024/Day25Input.txt"
        .filePathToStringList()
        .chunked(CHUNK_LENGTH)
        .partition { it.first() == "#####" }
        .let { (locks, keys) -> locks.map { it.toCount() }.cartesianProduct(keys.map { it.toCount() }) }
        .count { (lock, key) -> lock.zip(key).all { (l, k) -> l + k <= KEY_LENGTH } }
        .println()
}
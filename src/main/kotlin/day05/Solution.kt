package day05

import extensions.println
import java.io.File

data class RangeOffset(
    val offset: Long,
    val range: LongRange,
)

fun getSeeds(filePath: String): List<Long> =
    File(ClassLoader.getSystemResource(filePath).file)
        .readLines()
        .first()
        .split(": ")
        .last()
        .split(" ")
        .map { it.toLong() }

fun getMappings(filePath: String): List<List<RangeOffset>> =
    File(ClassLoader.getSystemResource(filePath).file)
        .readText()
        .split("\n\n")
        .drop(1)
        .map { mappings ->
            mappings
                .split("\n")
                .drop(1)
                .map { mapping ->
                    val (destinationRangeStart, sourceRangeStart, rangeLength) = mapping.split(" ").map { it.toLong() }
                    RangeOffset(
                        destinationRangeStart - sourceRangeStart,
                        sourceRangeStart..sourceRangeStart + rangeLength
                    )
                }
        }

fun List<RangeOffset>.getMappedValue(x: Long): Long {
    this.map { if (x in it.range) return x + it.offset }
    return x
}

fun foldSeedWithMapping(mappers: List<List<RangeOffset>>, seed: Long): Long =
    mappers.fold(seed) { mappedSeed, mapper -> mapper.getMappedValue(mappedSeed) }

fun main() {
    val filePath = "Day05Input.txt"
    val seeds = getSeeds(filePath)
    val mappers = getMappings(filePath)
    seeds
        .minOf { seed ->
            mappers.fold(seed) { mappedSeed, mapper ->
                mapper.getMappedValue(mappedSeed)
            }
        }
        .println()
    // the following naive approach takes a long time...
    seeds
        .mapIndexedNotNull { i, seedsRange ->
            if (i % 2 != 0) {
                val startValue = seeds[i - 1]
                (startValue..<startValue + seedsRange).minOf {
                    foldSeedWithMapping(mappers, it)
                }
            } else null
        }
        .minOf { it }
        .println()
}

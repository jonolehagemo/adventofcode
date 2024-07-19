package day05

import extensions.println
import java.io.File
import kotlin.math.max
import kotlin.math.min

data class RangeMapping(val destinationStart: Long, val sourceStart: Long, val rangeLength: Long)

fun getSeeds(filePath: String): List<Long> =
    File(ClassLoader.getSystemResource(filePath).file)
        .readLines()
        .first()
        .split(": ")
        .last()
        .split(" ")
        .map { it.toLong() }

fun getMappings(filePath: String): List<List<RangeMapping>> =
    File(ClassLoader.getSystemResource(filePath).file)
        .readText()
        .split("\n\n")
        .drop(1)
        .map { mappings ->
            mappings
                .split("\n")
                .drop(1)
                .map { mapping ->
                    val (destinationRangeStart, sourceRangeStart, rangeLength) =
                        mapping.split(" ").map { it.toLong() }
                    RangeMapping(
                        destinationStart = destinationRangeStart,
                        sourceStart = sourceRangeStart,
                        rangeLength = rangeLength
                    )
                }
        }

fun getSeedsAsRanges(filePath: String): List<LongRange> =
    File(ClassLoader.getSystemResource(filePath).file)
        .readLines()
        .first()
        .split(": ")
        .last()
        .split(" ")
        .chunked(2) { it[0].toLong() until it[0].toLong() + it[1].toLong() }

fun rangeIntersect(r1: LongRange, r2: LongRange): LongRange {
    val left = max(r1.first, r2.first)
    val right = min(r1.last, r2.last)
    if (right - left + 1 > 0)
        return left..right
    return LongRange.EMPTY
}

fun LongRange.intersect(other: LongRange): LongRange {
    val left = max(this.first, other.first)
    val right = min(this.last, other.last)
    if (right - left + 1 > 0)
        return LongRange(start = left, endInclusive = right)
    return LongRange.EMPTY
}
fun flattenMappings(transitions: List<List<RangeMapping>>, seedRanges: List<LongRange>): List<LongRange> {
    var currentRanges = seedRanges
    for (transition in transitions) {
        val newRanges = mutableListOf<LongRange>()

        // Find all intersections
        for (r in currentRanges) {
            for (line in transition) {
                // The Kotlin built-in intersection takes forever because it converts to sets
                // using my own extension function
                val lineRange = (line.sourceStart until (line.sourceStart + line.rangeLength))
                val intersect = lineRange.intersect(r)
                if (!intersect.isEmpty()) {
                    val start = line.destinationStart + (intersect.first() - line.sourceStart)
                    newRanges.add(start until start + intersect.last - intersect.first + 1)
                }
            }
        }

        currentRanges = newRanges
    }
    return currentRanges
}

fun List<RangeMapping>.getMappedValue(x: Long): Long {
    this.map {
        if (x in it.sourceStart until it.sourceStart + it.rangeLength)
            return x - it.sourceStart + it.destinationStart
    }
    return x
}

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

    val seedRanges = getSeedsAsRanges(filePath)
    val endRanges = flattenMappings(mappers, seedRanges)
    endRanges.minOf { it.first }.println()
}

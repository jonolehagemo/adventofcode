package aoc2023.day22

import datastructures.LongCoordinate
import extensions.filePathToStringList
import extensions.println
import kotlin.math.max
import kotlin.math.min

data class Brick(
    val id: Int = 0,
    val startX: Int = 0,
    val startY: Int = 0,
    val startZ: Int = 0,
    val endX: Int = 0,
    val endY: Int = 0,
    val endZ: Int = 0,
) : Comparable<Brick> {
    override fun compareTo(other: Brick): Int = compareValuesBy(this, other, { it.startZ }, { it.endZ })

    fun overlaps(other: Brick): Boolean =
        max(this.startX, other.startX) <= min(this.endX, other.endX) &&
            max(this.startY, other.startY) <= min(this.endY, other.endY)

    fun base(): List<LongCoordinate> = LongCoordinate(startY.toLong(), startX.toLong()).toList(LongCoordinate(endY.toLong(), endX.toLong()))

    companion object {
        fun of(input: String): Brick =
            input
                .replace('~', ',')
                .split(",")
                .map { it.toInt() }
                .let {
                    Brick(it[0], it[1], it[2], it[3], it[4], it[5])
                }
    }
}

fun List<String>.toBricks(): List<Brick> = map { Brick.of(it) }

fun List<Brick>.toRelations(): List<Pair<Brick, Brick>> {
    val maxX = maxOf { it.endX }.also { it.println() }
    val maxY = maxOf { it.endY }.also { it.println() }
    val bricks = sorted().mapIndexed { index, brick -> brick.copy(id = index) }
    val floor: MutableMap<LongCoordinate, Pair<Int, Int>> =
        (0..maxX)
            .flatMap { x ->
                (0..maxY).map { y ->
                    Pair(LongCoordinate(y.toLong(), x.toLong()), 0 to 0)
                }
            }.toMap()
            .toMutableMap()
    bricks.first().println()
    val relations = mutableListOf<Pair<Brick, Brick>>()

    bricks.forEach { brick ->
        val supportedByIds = brick.base().map { floor.getOrDefault(it, Pair(0, 0)).first }
        val zIndices = brick.base().map { floor.getOrDefault(it, Pair(0, 0)).first }
        val maxZ = zIndices.max()
    }

    return listOf(Brick() to Brick())
}

fun main() {
    "aoc2023/Day22Input.txt"
        .filePathToStringList()
        .toBricks() // .also { it.println() }
        .toRelations()
        .also { it.println() }
}

package aoc2023.day24

import extensions.filePathToStringList
import extensions.println

fun <E> List<E>.cartesianPairs(): List<Pair<E, E>> =
    this.flatMapIndexed { index, left ->
        this.indices.drop(index).map { right -> left to this[right] }
    }

data class Hailstone(val px: Long, val py: Long, val pz: Long, val vx: Long, val vy: Long, val vz: Long) {
    private val slope = if (vx == 0L) Double.NaN else vy / vx.toDouble()

    fun intersectionWith(other: Hailstone): Intersection? {
        if (slope.isNaN() || other.slope.isNaN() || slope == other.slope) return null

        val c = py - slope * px
        val otherC = other.py - other.slope * other.px

        val x = (otherC - c) / (slope - other.slope)
        val t1 = (x - px) / vx
        val t2 = (x - other.px) / other.vx

        if (t1 < 0 || t2 < 0) return null

        val y = slope * (x - px) + py
        return Intersection(x, y, t1)
    }

}

data class Intersection(val x: Double, val y: Double, val time: Double)

fun List<String>.toHailstones(): List<Hailstone> = map { string ->
    string
        .replace('@', ',')
        .split(',')
        .map { it.trim().toLong() }
        .let { Hailstone(it[0], it[1], it[2], it[3], it[4], it[5]) }
}

fun List<Hailstone>.countCollisions(range: ClosedFloatingPointRange<Double>): Int = this
    .cartesianPairs()
    .filter { it.first != it.second }
    .mapNotNull { it.first.intersectionWith(it.second) }
    .count { (x, y, _) -> x in range && y in range }

fun main() {
    "aoc2023/Day24Input.txt".filePathToStringList()
        .toHailstones()
        .countCollisions(200_000_000_000_000.0..400_000_000_000_000.0)
        .println()
}
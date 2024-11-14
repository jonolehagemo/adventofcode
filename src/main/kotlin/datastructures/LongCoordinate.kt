package datastructures

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class LongCoordinate(
    val row: Long,
    val column: Long,
) : Comparable<LongCoordinate> {
    override fun toString(): String = "'$row|$column'"

    override fun compareTo(other: LongCoordinate) = compareValuesBy(this, other, { it.row }, { it.column })

    private fun northWest(): LongCoordinate = LongCoordinate(row - 1, column - 1)

    fun north(): LongCoordinate = LongCoordinate(row - 1, column)

    private fun northEast(): LongCoordinate = LongCoordinate(row - 1, column + 1)

    fun west(): LongCoordinate = LongCoordinate(row, column - 1)

    fun east(): LongCoordinate = LongCoordinate(row, column + 1)

    private fun southWest(): LongCoordinate = LongCoordinate(row + 1, column - 1)

    fun south(): LongCoordinate = LongCoordinate(row + 1, column)

    private fun southEast(): LongCoordinate = LongCoordinate(row + 1, column + 1)

    fun neighbours(): List<LongCoordinate> =
        listOf(
            northWest(),
            north(),
            northEast(),
            west(),
            east(),
            southWest(),
            south(),
            southEast(),
        )

    fun neighboursNEWS(): List<LongCoordinate> = listOf(north(), east(), west(), south())

    fun shortestPath(other: LongCoordinate): Long = abs(row - other.row) + abs(column - other.column)

    operator fun plus(other: LongCoordinate): LongCoordinate = LongCoordinate(this.row + other.row, this.column + other.column)

    operator fun minus(other: LongCoordinate): LongCoordinate = LongCoordinate(this.row - other.row, this.column - other.column)

    operator fun times(factor: Long): LongCoordinate = LongCoordinate(this.row * factor, this.column * factor)

    fun floorDiv(divisor: Long): LongCoordinate = LongCoordinate(this.row.floorDiv(divisor), this.column.floorDiv(divisor))

    fun oppositeDirection(): LongCoordinate = LongCoordinate(-row, -column)

    fun toList(other: LongCoordinate): List<LongCoordinate> =
        this
            .let {
                min(it.row, other.row)..max(it.row, other.row) to min(it.column, other.column)..max(it.column, other.column)
            }.let { (rowRange, columnRange) ->
                rowRange.flatMap { row -> columnRange.map { column -> LongCoordinate(row, column) } }
            }

    companion object {
        @JvmStatic
        val ORIGIN = LongCoordinate(0L, 0L)
    }
}

package datastructures

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Coordinate(
    val row: Int,
    val column: Int,
) : Comparable<Coordinate> {
    override fun toString(): String = "'$row|$column'"

    override fun compareTo(other: Coordinate) = compareValuesBy(this, other, { it.row }, { it.column })

    private fun northWest(): Coordinate = Coordinate(row - 1, column - 1)

    fun north(): Coordinate = Coordinate(row - 1, column)

    private fun northEast(): Coordinate = Coordinate(row - 1, column + 1)

    fun west(): Coordinate = Coordinate(row, column - 1)

    fun east(): Coordinate = Coordinate(row, column + 1)

    private fun southWest(): Coordinate = Coordinate(row + 1, column - 1)

    fun south(): Coordinate = Coordinate(row + 1, column)

    private fun southEast(): Coordinate = Coordinate(row + 1, column + 1)

    fun neighbours(): List<Coordinate> =
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

    fun neighboursNEWS(): List<Coordinate> = listOf(north(), east(), west(), south())

    fun shortestPath(other: Coordinate): Int = abs(row - other.row) + abs(column - other.column)

    operator fun plus(other: Coordinate): Coordinate = Coordinate(this.row + other.row, this.column + other.column)

    operator fun minus(other: Coordinate): Coordinate = Coordinate(this.row - other.row, this.column - other.column)

    operator fun times(factor: Int): Coordinate = Coordinate(this.row * factor, this.column * factor)

    fun floorDiv(divisor: Int): Coordinate = Coordinate(this.row.floorDiv(divisor), this.column.floorDiv(divisor))

    fun oppositeDirection(): Coordinate = Coordinate(-row, -column)

    fun toList(other: Coordinate): List<Coordinate> =
        this
            .let {
                min(it.row, other.row)..max(it.row, other.row) to min(it.column, other.column)..max(it.column, other.column)
            }.let { (rowRange, columnRange) ->
                rowRange.flatMap { row -> columnRange.map { column -> Coordinate(row, column) } }
            }

    companion object {
        @JvmStatic
        val ORIGIN = Coordinate(0, 0)
    }
}
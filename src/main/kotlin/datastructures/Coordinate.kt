package datastructures

import kotlin.math.abs

data class Coordinate(val row: Long, val column: Long) : Comparable<Coordinate> {
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

    fun neighbours(): List<Coordinate> = listOf(
        northWest(), north(), northEast(),
        west(), east(),
        southWest(), south(), southEast()
    )

    fun neighboursNEWS(): List<Coordinate> = listOf(north(), east(), west(), south())

    fun shortestPath(other: Coordinate): Long = abs(row - other.row) + abs(column - other.column)

    operator fun plus(other: Coordinate):Coordinate =
        Coordinate(this.row + other.row, this.column + other.column)

    fun oppositeDirection(): Coordinate = Coordinate(-row , -column)

    companion object {
        @JvmStatic
        val ORIGIN  = Coordinate(0L, 0L)
    }
}


package datastructures

data class Coordinate(val y: Int, val x: Int) : Comparable<Coordinate> {
    override fun toString(): String = "'$y-$x'"
    override fun compareTo(other: Coordinate) = compareValuesBy(this, other, { it.y }, { it.x })

    fun northWest(): Coordinate = Coordinate(y - 1, x - 1)
    fun north(): Coordinate = Coordinate(y - 1, x)
    fun northEast(): Coordinate = Coordinate(y - 1, x + 1)
    fun west(): Coordinate = Coordinate(y, x - 1)
    fun east(): Coordinate = Coordinate(y, x + 1)
    fun southWest(): Coordinate = Coordinate(y + 1, x - 1)
    fun south(): Coordinate = Coordinate(y + 1, x)
    fun southEast(): Coordinate = Coordinate(y + 1, x + 1)

    fun neighbours(): List<Coordinate> = listOf(
        northWest(), north(), northEast(),
        west(), east(),
        southWest(), south(), southEast()
    )

    fun neighboursNEWS(): List<Coordinate> = listOf(north(), east(), west(), south())
}
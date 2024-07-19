package datastructures

data class Grid(
    val coordinateCharMap: Map<Coordinate, Char>,
    val defaultValue: Char = ' ',
    val start: Coordinate = coordinateCharMap
        .entries
        .first { it.value != defaultValue }
        .key,
    val finish: Coordinate = coordinateCharMap
        .entries
        .last { it.value != defaultValue }
        .key,
) {
    fun rowRange(): IntRange = 0..coordinateCharMap.keys.maxOf { it.row }
    fun columnRange(): IntRange = 0..coordinateCharMap.keys.maxOf { it.column }

    fun findCoordinateByTile(tile: Char): List<Coordinate> = coordinateCharMap
        .toList()
        .filter { it.second == tile }
        .map { it.first }

    fun tile(c: Coordinate): Char = coordinateCharMap.getOrDefault(c, defaultValue)
    private fun tileCount(c: Coordinate): Int = if (tile(c) != defaultValue) 1 else 0

    fun neighboursCount(c: Coordinate): Int =
        if (tile(c) == defaultValue) 0 else c.neighboursNEWS().sumOf { tileCount(it) }

    fun neighbours(c: Coordinate): List<Coordinate> = when (tile(c)) {
        '^' -> listOf(c.north())
        'v' -> listOf(c.south())
        '>' -> listOf(c.east())
        '<' -> listOf(c.west())
        '.' -> {
            val mutableList: MutableList<Coordinate> = mutableListOf()
            if (tile(c.north()) !in setOf(defaultValue, 'v')) mutableList.add(c.north())
            if (tile(c.south()) !in setOf(defaultValue, '^')) mutableList.add(c.south())
            if (tile(c.east()) !in setOf(defaultValue, '<')) mutableList.add(c.east())
            if (tile(c.west()) !in setOf(defaultValue, '>')) mutableList.add(c.west())

            mutableList
        }

        else -> emptyList()
    }

    fun nodes(): List<Coordinate> = coordinateCharMap
        .keys
        .map { it to neighboursCount(it) }
        .filter { (_, count) -> count != 2 }
        .map { it.first }

    private fun edge(previous: Coordinate, current: Coordinate, steps: Int = 0): Pair<Coordinate, Int> = when {
        previous == finish ->
            previous to 0

        neighboursCount(current) == 2 ->
            neighbours(current)
                .firstOrNull { next -> next != previous }
                .let { next -> edge(current, next ?: previous, steps + 1) }

        else ->
            current to steps
    }

    fun toGraph() =
        Graph(nodes().associateWith { start ->
            neighbours(start).map { neighbour -> edge(start, neighbour, 1) }.toSet()
        })

    override fun toString(): String = this
        .rowRange().joinToString("\n") { rowIndex ->
            columnRange().joinToString("") { columnIndex ->
                tile(
                    Coordinate(
                        row = rowIndex,
                        column = columnIndex
                    )
                ).toString()
            }
        }
}

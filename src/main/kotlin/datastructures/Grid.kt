package datastructures

data class Grid(
    val coordinateCharMap: Map<Coordinate, Char>,
    val defaultValue: Char = ' ',
    val start: Coordinate =
        coordinateCharMap
            .entries
            .first { it.value != defaultValue }
            .key,
    val finish: Coordinate =
        coordinateCharMap
            .entries
            .last { it.value != defaultValue }
            .key,
    val rowLength: Int = coordinateCharMap.keys.maxOf { it.row },
    val columnLength: Int = coordinateCharMap.keys.maxOf { it.column },
) {
    fun rowMax(): Int = rowLength

    fun columnMax(): Int = columnLength

    fun rowRange(): IntRange = 0..<rowMax()

    fun columnRange(): IntRange = 0..<columnMax()

    fun isInBounds(c: Coordinate): Boolean = c.row in rowRange() && c.column in columnRange()

    fun isOutOfBounds(c: Coordinate): Boolean = !isInBounds(c)

    fun findCoordinateByTile(tile: Char): List<Coordinate> =
        coordinateCharMap
            .toList()
            .filter { it.second == tile }
            .map { it.first }

    fun tile(c: Coordinate): Char = coordinateCharMap.getOrDefault(c, defaultValue)

    private fun tileCount(c: Coordinate): Int = if (tile(c) != defaultValue) 1 else 0

    fun neighboursCount(c: Coordinate): Int = if (tile(c) == defaultValue) 0 else c.neighboursNEWS().sumOf { tileCount(it) }

    fun neighbours(c: Coordinate): List<Coordinate> =
        when (tile(c)) {
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

    fun neighboursNEWS(c: Coordinate): List<Coordinate> = c.neighboursNEWS().filter { isInBounds(it) }

    fun sliceNorth(c: Coordinate): List<Pair<Coordinate, Char>> =
        (c.north().row downTo 0)
            .map { row -> c.copy(row = row) to tile(c.copy(row = row)) }

    fun sliceEast(c: Coordinate): List<Pair<Coordinate, Char>> =
        (c.east().column..coordinateCharMap.keys.maxOf { it.column })
            .map { column -> c.copy(column = column) to tile(c.copy(column = column)) }

    fun sliceSouth(c: Coordinate): List<Pair<Coordinate, Char>> =
        (c.south().row..coordinateCharMap.keys.maxOf { it.row })
            .map { row -> c.copy(row = row) to tile(c.copy(row = row)) }

    fun sliceWest(c: Coordinate): List<Pair<Coordinate, Char>> =
        (c.west().column downTo 0)
            .map { column -> c.copy(column = column) to tile(c.copy(column = column)) }

    fun export(): List<Pair<Coordinate, Char>> =
        rowRange().flatMap { row ->
            columnRange().map { column ->
                Coordinate(row, column) to tile(Coordinate(row, column))
            }
        }

    fun coordinates(): List<Coordinate> = coordinateCharMap.keys.toList().sorted()

    fun nodes(): List<Coordinate> =
        coordinateCharMap
            .keys
            .map { it to neighboursCount(it) }
            .filter { (_, count) -> count != 2 }
            .map { it.first }

    override fun toString(): String =
        this
            .rowRange()
            .joinToString("\n") { rowIndex ->
                columnRange().joinToString("") { columnIndex ->
                    tile(
                        Coordinate(
                            row = rowIndex,
                            column = columnIndex,
                        ),
                    ).toString()
                }
            }

    operator fun plus(toAdd: Pair<Coordinate, Char>): Grid =
        Grid(
            coordinateCharMap =
                coordinateCharMap
                    .toMutableMap()
                    .apply { this[toAdd.first] = toAdd.second }
                    .toMap(),
            defaultValue = defaultValue,
        )

    fun transpose(): Grid =
        Grid(
            this.coordinateCharMap.entries.associate { Coordinate(it.key.column, it.key.row) to it.value },
            defaultValue,
        )

    fun countDistinctRowTiles(): List<IndexedValue<Int>> =
        this
            .rowRange()
            .map { rowIndex ->
                IndexedValue(
                    index = rowIndex.toInt(),
                    value =
                        this
                            .columnRange()
                            .map { columnIndex -> tile(Coordinate(rowIndex, columnIndex)) }
                            .distinct()
                            .count(),
                )
            }

    fun countDistinctColumnTiles(): List<IndexedValue<Int>> =
        this
            .columnRange()
            .map { columnIndex ->
                IndexedValue(
                    index = columnIndex.toInt(),
                    value =
                        this
                            .rowRange()
                            .map { rowIndex -> tile(Coordinate(rowIndex, columnIndex)) }
                            .distinct()
                            .count(),
                )
            }
}

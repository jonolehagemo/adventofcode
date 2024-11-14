package datastructures

data class LongGrid(
    val coordinateCharMap: Map<LongCoordinate, Char>,
    val defaultValue: Char = ' ',
    val start: LongCoordinate =
        coordinateCharMap
            .entries
            .first { it.value != defaultValue }
            .key,
    val finish: LongCoordinate =
        coordinateCharMap
            .entries
            .last { it.value != defaultValue }
            .key,
) {
    fun rowMax(): Long = coordinateCharMap.keys.maxOf { it.row }

    fun columnMax(): Long = coordinateCharMap.keys.maxOf { it.column }

    fun rowRange(): LongRange = 0..rowMax()

    fun columnRange(): LongRange = 0..columnMax()

    fun isInBounds(c: LongCoordinate): Boolean = c.row in rowRange() && c.column in columnRange()

    fun isOutOfBounds(c: LongCoordinate): Boolean = !isInBounds(c)

    fun findCoordinateByTile(tile: Char): List<LongCoordinate> =
        coordinateCharMap
            .toList()
            .filter { it.second == tile }
            .map { it.first }

    fun tile(c: LongCoordinate): Char = coordinateCharMap.getOrDefault(c, defaultValue)

    private fun tileCount(c: LongCoordinate): Int = if (tile(c) != defaultValue) 1 else 0

    fun neighboursCount(c: LongCoordinate): Int = if (tile(c) == defaultValue) 0 else c.neighboursNEWS().sumOf { tileCount(it) }

    fun neighbours(c: LongCoordinate): List<LongCoordinate> =
        when (tile(c)) {
            '^' -> listOf(c.north())
            'v' -> listOf(c.south())
            '>' -> listOf(c.east())
            '<' -> listOf(c.west())
            '.' -> {
                val mutableList: MutableList<LongCoordinate> = mutableListOf()
                if (tile(c.north()) !in setOf(defaultValue, 'v')) mutableList.add(c.north())
                if (tile(c.south()) !in setOf(defaultValue, '^')) mutableList.add(c.south())
                if (tile(c.east()) !in setOf(defaultValue, '<')) mutableList.add(c.east())
                if (tile(c.west()) !in setOf(defaultValue, '>')) mutableList.add(c.west())

                mutableList
            }

            else -> emptyList()
        }

    fun neighboursNEWS(c: LongCoordinate): List<LongCoordinate> =
        c.neighboursNEWS().filter { isInBounds(it) }

    fun sliceNorth(c: LongCoordinate): List<Pair<LongCoordinate, Char>> =
        (c.north().row downTo 0L)
            .map { row -> c.copy(row = row) to tile(c.copy(row = row)) }

    fun sliceEast(c: LongCoordinate): List<Pair<LongCoordinate, Char>> =
        (c.east().column..coordinateCharMap.keys.maxOf { it.column })
            .map { column -> c.copy(column = column) to tile(c.copy(column = column)) }

    fun sliceSouth(c: LongCoordinate): List<Pair<LongCoordinate, Char>> =
        (c.south().row..coordinateCharMap.keys.maxOf { it.row })
            .map { row -> c.copy(row = row) to tile(c.copy(row = row)) }

    fun sliceWest(c: LongCoordinate): List<Pair<LongCoordinate, Char>> =
        (c.west().column downTo 0)
            .map { column -> c.copy(column = column) to tile(c.copy(column = column)) }

    fun export(): List<Pair<LongCoordinate, Char>> =
        rowRange().flatMap { row ->
            columnRange().map { column ->
                LongCoordinate(row, column) to tile(LongCoordinate(row, column))
            }
        }

    fun nodes(): List<LongCoordinate> =
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
                        LongCoordinate(
                            row = rowIndex,
                            column = columnIndex,
                        ),
                    ).toString()
                }
            }

    operator fun plus(toAdd: Pair<LongCoordinate, Char>): LongGrid =
        LongGrid(coordinateCharMap = coordinateCharMap
            .toMutableMap()
            .apply { this[toAdd.first] = toAdd.second }
            .toMap(),
            defaultValue = defaultValue
        )

    fun transpose(): LongGrid =
        LongGrid(
            this.coordinateCharMap.entries.associate { LongCoordinate(it.key.column, it.key.row) to it.value },
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
                            .map { columnIndex -> tile(LongCoordinate(rowIndex, columnIndex)) }
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
                            .map { rowIndex -> tile(LongCoordinate(rowIndex, columnIndex)) }
                            .distinct()
                            .count(),
                )
            }
}

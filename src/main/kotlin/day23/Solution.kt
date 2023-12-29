package day23

import java.io.File

data class Coordinate(val y: Int, val x: Int)

fun Coordinate.key(): String = "${this.y}|${this.x}"
fun Coordinate.north(): Coordinate = Coordinate(this.y - 1, this.x)
fun Coordinate.east(): Coordinate = Coordinate(this.y, this.x + 1)
fun Coordinate.south(): Coordinate = Coordinate(this.y + 1, this.x)
fun Coordinate.west(): Coordinate = Coordinate(this.y, this.x - 1)

data class ElvenMap(
    val elvenMap: List<String> = emptyList(),
    val neighboursMap: MutableMap<String, List<Coordinate>> = mutableMapOf()
) {
    val start: Coordinate
        get() = Coordinate(0, this.elvenMap.first().indexOf('S'))
    val end: Coordinate
        get() = Coordinate(this.elvenMap.size - 1, this.elvenMap.last().lastIndexOf('E'))
}

fun ElvenMap.tile(coordinate: Coordinate): Char = this.elvenMap[coordinate.y][coordinate.x]

fun ElvenMap.neighbours(coordinate: Coordinate): List<Coordinate> {
    if (!neighboursMap.containsKey(coordinate.key())) {
        neighboursMap[coordinate.key()] = when (this.tile(coordinate)) {
            'S' -> listOf(coordinate.south())
            '^' -> listOf(coordinate.north())
            '>' -> listOf(coordinate.east())
            'v' -> listOf(coordinate.south())
            '<' -> listOf(coordinate.west())
            'E' -> emptyList()
            '.' -> {
                val mutableList: MutableList<Coordinate> = mutableListOf()
                if (this.tile(coordinate.north()) in listOf('.', '^', '>', '<'))
                    mutableList.add(coordinate.north())
                if (this.tile(coordinate.east()) in listOf('.', '^', '>', 'v'))
                    mutableList.add(coordinate.east())
                if (this.tile(coordinate.south()) in listOf('.', '>', 'v', '<', 'E'))
                    mutableList.add(coordinate.south())
                if (this.tile(coordinate.west()) in listOf('.', '^', 'v', '<'))
                    mutableList.add(coordinate.west())

                mutableList.toList()
            }

            else -> emptyList()
        }
//        println("cache miss ${neighboursMap.keys.size}")
    }
    return neighboursMap[coordinate.key()] ?: emptyList()

}
fun ElvenMap.removeSlopes(): ElvenMap = this.copy(
    elvenMap = this.elvenMap.map {
        it.replace('^', '.')
            .replace('>', '.')
            .replace('v', '.')
            .replace('<', '.')
    }, neighboursMap = mutableMapOf()
)


fun getInput(filePath: String): ElvenMap {
    val lines = File(ClassLoader.getSystemResource(filePath).file).readLines().toMutableList()
    lines[0] = lines.first().replace(".", "S")
    lines[lines.size - 1] = lines.last().replace(".", "E")

    return ElvenMap(elvenMap = lines)
}

fun move(coordinateList: Set<Coordinate>, current: Coordinate, elvenMap: ElvenMap): Int =
    if (elvenMap.tile(current) == 'E') {
        println(coordinateList.size)
        coordinateList.size
    }
//    else if (current in coordinateList) {
//        println("current in coordinateList")
//        0
//    }
    else {
//        println(coordinateList.size)
        elvenMap
            .neighbours(current)
            .filter { coordinate -> coordinate !in coordinateList }
            .maxOfOrNull { next ->
                move(setOf(coordinateList, setOf(current)).flatten().toSet(), next, elvenMap)
            } ?: 0
    }


fun process1(elvenMap: ElvenMap): Int = move(
    setOf(elvenMap.start), elvenMap.start.south(), elvenMap
)


fun main() {
    println("Task 1")
    val elvenMap = getInput("Day23Input.txt")
    println(process1(elvenMap))
    println("y.size = ${elvenMap.elvenMap.size}, x.size = ${elvenMap.elvenMap.first().length}")

//    println("Task 2")
//    val elvenMapWithoutSlopes = elvenMap.removeSlopes()
//    println(process1(elvenMapWithoutSlopes))

}
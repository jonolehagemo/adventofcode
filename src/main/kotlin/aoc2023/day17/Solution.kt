package aoc2023.day17

import datastructures.LongCoordinate
import datastructures.LongGrid
import extensions.filePathToGrid
import extensions.println
import java.util.*

data class Counter(
    val heatLoss: Int,
    val coordinate: LongCoordinate,
    val direction: LongCoordinate,
    val n: Int,
)

fun LongGrid.minimize(
    lowerBound: Int = 0,
    upperBound: Int = 0,
): Int {
    val visited: MutableSet<Counter> = mutableSetOf()
    val priorityQueue = PriorityQueue { a: Counter, b: Counter -> a.heatLoss - b.heatLoss }
    priorityQueue.add(Counter(0, start, LongCoordinate.ORIGIN, 0))

    while (priorityQueue.isNotEmpty()) {
        val counter = priorityQueue.remove()

        if (counter.coordinate == finish && counter.n >= lowerBound) {
            return counter.heatLoss
        }

        if (isOutOfBounds(counter.coordinate)) {
            continue
        }

        if (visited.contains(counter.copy(heatLoss = 0))) {
            continue
        }

        visited.add(counter.copy(heatLoss = 0))

        if (counter.direction != LongCoordinate.ORIGIN && counter.n < upperBound) {
            val next = counter.coordinate + counter.direction

            priorityQueue.add(
                counter.copy(
                    heatLoss = counter.heatLoss + tile(next).digitToInt(),
                    coordinate = next,
                    direction = counter.direction,
                    n = counter.n + 1,
                ),
            )
        }

        if (counter.coordinate == LongCoordinate.ORIGIN || lowerBound <= counter.n) {
            LongCoordinate.ORIGIN
                .neighboursNEWS()
                .filter { it != counter.direction && it != counter.direction.oppositeDirection() }
                .forEach {
                    val next = counter.coordinate + it

                    priorityQueue.add(
                        counter.copy(
                            heatLoss = counter.heatLoss + tile(next).digitToInt(),
                            coordinate = next,
                            direction = it,
                            n = 1,
                        ),
                    )
                }
        }
    }

    return 0
}

fun main() {
    "aoc2023/Day17Input.txt".filePathToGrid(defaultValue = '9').minimize(0, 3).println()
    "aoc2023/Day17Input.txt".filePathToGrid(defaultValue = '9').minimize(4, 10).println()
}

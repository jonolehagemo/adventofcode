package aoc2022.day11

import extensions.filePathToListOfStringList
import extensions.println
import extensions.toProduct
import java.lang.Math.floorDiv

data class Monkey(
    val items: MutableList<Long>,
    val operation: String,
    val divisibleBy: Long,
    val trueMonkey: Int,
    val falseMonkey: Int,
    var inspections: Int = 0,
) {
    private fun calculate(item: Long, operation: String): Long =
        operation
            .replace("old", item.toString())
            .split(' ')
            .let { (aS, operator, bS) ->
                val a = aS.toLong()
                val b = bS.toLong()
                when (operator) {
                    "*" -> a * b
                    "/" -> floorDiv(a, b)
                    "+" -> a + b
                    "-" -> a - b
                    else -> {
                        a
                    }
                }
            }

    fun doInspections(divisor: Long): List<Pair<Int, Long>> {
        val result = items.toList()
            .map { old ->
                val new = floorDiv(calculate(old, operation), divisor)
                val id =
                    if ((new % divisibleBy) == 0L) trueMonkey else falseMonkey
                id to new
            }
        inspections += result.size
        items.clear()
        return result
    }

}

fun List<List<String>>.toMonkeyMap(): Map<Int, Monkey> =
    withIndex().associate { (index, list) ->
        index to Monkey(
            items =
            list[1]
                .substringAfter("items: ")
                .split(',')
                .map { it.trim().toLong() }
                .toMutableList(),
            operation = list[2].substringAfter("new = "),
            divisibleBy = list[3].substringAfter("by ").toLong(),
            trueMonkey = list[4].substringAfter("monkey ").toInt(),
            falseMonkey = list[5].substringAfter("monkey ").toInt(),
        )
    }

fun Map<Int, Monkey>.round(divisor: Long): Map<Int, Monkey> {
    val monkeys: MutableMap<Int, Monkey> = toMutableMap()

    for ((_, monkey) in monkeys.entries.sortedBy { it.key }) {
        monkey.doInspections(divisor)
            .forEach { (id, item) ->
                monkeys[id]?.items?.add(item)
            }
    }

    return monkeys.toMap()
}

fun Map<Int, Monkey>.rounds(rounds: Int, divisor: Long): Long {
    var monkeyMap = this

    for (i in 0..<rounds) {
        monkeyMap = monkeyMap.round(divisor)
    }

    val result =
        monkeyMap.entries.map { it.value.inspections.toLong() }.sortedDescending()
            .take(2).also { it.println() }.reduce { a, b -> a * b }
    return result
}

fun main() {
    val monkeyMap =
        "aoc2022/Day11Input.txt"
            .filePathToListOfStringList()
            .toMonkeyMap()
    monkeyMap
        .rounds(20, 3)
        .println()
}

package aoc2022.day11

import extensions.filePathToListOfStringList
import extensions.println
import java.math.BigInteger

data class Monkey(
    val items: MutableList<BigInteger>,
    val operation: String,
    val divisibleBy: BigInteger,
    val trueMonkey: Int,
    val falseMonkey: Int,
    var inspections: Int = 0,
) {
    private fun calculate(
        item: BigInteger,
        operation: String,
    ): BigInteger =
        operation
            .replace("old", item.toString())
            .split(' ')
            .let { (aS, operator, bS) ->
                val a = aS.toBigInteger()
                val b = bS.toBigInteger()
                when (operator) {
                    "*" -> a.multiply(b)
                    "/" -> a.divide(b)
                    "+" -> a.add(b)
                    "-" -> a.subtract(b)
                    else -> {
                        a
                    }
                }
            }

    fun doInspections(divisor: BigInteger): List<Pair<Int, BigInteger>> {
        val result =
            items
                .toList()
                .map { old ->
                    val new = calculate(old, operation).divide(divisor)
                    val id =
                        if ((new % divisibleBy) == BigInteger.ZERO) trueMonkey else falseMonkey
                    id to new
                }
        inspections += result.size
        items.clear()
        return result
    }
}

fun List<List<String>>.toMonkeyMap(): Map<Int, Monkey> =
    withIndex().associate { (index, list) ->
        index to
            Monkey(
                items =
                    list[1]
                        .substringAfter("items: ")
                        .split(',')
                        .map { it.trim().toBigInteger() }
                        .toMutableList(),
                operation = list[2].substringAfter("new = "),
                divisibleBy = list[3].substringAfter("by ").toBigInteger(),
                trueMonkey = list[4].substringAfter("monkey ").toInt(),
                falseMonkey = list[5].substringAfter("monkey ").toInt(),
            )
    }

fun Map<Int, Monkey>.round(divisor: BigInteger): Map<Int, Monkey> {
    val monkeys: MutableMap<Int, Monkey> = toMutableMap()

    for ((_, monkey) in monkeys.entries.sortedBy { it.key }) {
        monkey
            .doInspections(divisor)
            .forEach { (id, item) ->
                monkeys[id]?.items?.add(item)
            }
    }

    return monkeys.toMap()
}

fun Map<Int, Monkey>.rounds(
    rounds: Int,
    divisor: BigInteger,
): BigInteger {
    var monkeyMap = this

    for (i in 0..<rounds) {
        monkeyMap = monkeyMap.round(divisor)
    }

    val result =
        monkeyMap.entries
            .map { it.value.inspections.toBigInteger() }
            .sortedDescending()
            .take(2)
            .also { it.println() }
            .reduce { a, b -> a * b }
    return result
}

fun main() {
    val monkeyMap =
        "aoc2022/Day11Input.txt"
            .filePathToListOfStringList()
            .toMonkeyMap()
    monkeyMap
        .rounds(20, BigInteger.valueOf(3L))
        .println()
}

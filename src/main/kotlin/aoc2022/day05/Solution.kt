package aoc2022.day05

import extensions.filePathToListOfStringList
import extensions.println
import extensions.rotateRight

fun List<String>.toStacks(): List<String> =
    this
        .map { it.padEnd(maxOf { s -> s.length }, ' ') }
        .rotateRight()
        .filter { it.first() != ' ' }
        .map { it.drop(1).trim() }

fun List<String>.toInstructions(): List<String> =
    this.map {
        val regex = """move (\d+) from (\d) to (\d)""".toRegex()
        val matchResult = regex.find(it)!!
        val (move, from, to) = matchResult.destructured
        "$move,${from.toInt() - 1},${to.toInt() - 1}"
    }

fun main() {
    val input = "aoc2022/Day05Input.txt".filePathToListOfStringList()
    val stacks = input.first().toStacks()
    val instructions = input.last().toInstructions()

    instructions
        .fold(stacks) { result, instruction ->
            val (move, from, to) = instruction.split(",")
            val toMove = result[from.toInt()].takeLast(move.toInt())
            val mutableResult = result.toMutableList()
            mutableResult[from.toInt()] = result[from.toInt()].dropLast(move.toInt())
            mutableResult[to.toInt()] = result[to.toInt()] + toMove.reversed()
            mutableResult.toList()
        }.joinToString("") { it.last().toString() }
        .println()

    instructions
        .fold(stacks) { result, instruction ->
            val (move, from, to) = instruction.split(",")
            val toMove = result[from.toInt()].takeLast(move.toInt())
            val mutableResult = result.toMutableList()
            mutableResult[from.toInt()] = result[from.toInt()].dropLast(move.toInt())
            mutableResult[to.toInt()] = result[to.toInt()] + toMove
            mutableResult.toList()
        }.joinToString("") { it.last().toString() }
        .println()
}

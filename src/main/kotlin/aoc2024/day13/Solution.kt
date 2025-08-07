package aoc2024.day13

import extensions.filePathToStringList
import extensions.println

// I had help from ThunderChaser, https://www.reddit.com/r/adventofcode/comments/1hd7irq/2024_day_13_an_explanation_of_the_mathematics/

data class ClawMachine(
    val aX: Long,
    val aY: Long,
    val bX: Long,
    val bY: Long,
    val prizeX: Long,
    val prizeY: Long
){
    fun solve(): Long {
        val det = aX * bY - aY * bX
        val a = (prizeX * bY - prizeY * bX) / det
        val b = (prizeY * aX - prizeX * aY) / det
        return if (aX * a + bX * b == prizeX && aY * a + bY * b == prizeY) a * 3 + b else 0
    }

    fun scale(factor: Long) = copy(prizeX = prizeX + factor, prizeY = prizeY + factor)

    companion object {
        fun of(input: List<String>): ClawMachine {
            val aX = input[0].substringAfter("X+").substringBefore(",").toLong()
            val aY = input[0].substringAfter("Y+").toLong()
            val bX = input[1].substringAfter("X+").substringBefore(",").toLong()
            val bY = input[1].substringAfter("Y+").toLong()
            val prizeX = input[2].substringAfter("X=").substringBefore(",").toLong()
            val prizeY = input[2].substringAfter("Y=").toLong()

            return ClawMachine(aX, aY, bX, bY, prizeX, prizeY)
        }
    }
}

fun main(){
    val input = "aoc2024/Day13Input.txt"
        .filePathToStringList()
        .chunked(4)
        .map { ClawMachine.of(it) }

    input.sumOf { it.solve() }.println()
    input.map { it.scale(10000000000000L) }.sumOf { it.solve() }.println()
}
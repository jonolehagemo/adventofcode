package aoc2022.day01

import extensions.filePathToListOfStringList
import extensions.println

fun main() {
    val elves = "aoc2022/Day01Input.txt".filePathToListOfStringList().map { elf -> elf.sumOf { it.toInt() } }
    elves.max().println()
    elves.sortedDescending().take(3).sum().println()
}

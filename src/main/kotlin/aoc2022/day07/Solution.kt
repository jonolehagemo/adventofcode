package aoc2022.day07

import extensions.filePathToStringList
import extensions.println
import java.util.Stack

data class Directory(
    val subDirectories: MutableMap<String, Directory> = mutableMapOf(),
    val files: MutableMap<String, Int> = mutableMapOf(),
) {
    val totalSize: Int
        get() = subDirectories.values.sumOf { it.totalSize } + files.values.sum()
}

fun List<String>.toFileSystem(): Directory {
    val path = Stack<Directory>()
    val root = Directory()
    var currentWorkingDirectory = root

    for (line in this) {
        when {
            line.startsWith("$ cd") -> {
                if (line.endsWith("..")) {
                    currentWorkingDirectory = path.pop()
                } else {
                    val subDirectory = Directory()
                    val name = line.drop(5)
                    currentWorkingDirectory.subDirectories[name] = subDirectory
                    path.push(currentWorkingDirectory)
                    currentWorkingDirectory = subDirectory
                }
            }

            line.first().isDigit() -> {
                val (size, name) = line.split(" ")
                currentWorkingDirectory.files[name] = size.toInt()
            }

            else -> continue
        }
    }

    return root
}

fun Directory.findDirectoriesBelowThreshold(threshold: Int): Int =
    subDirectories.values
        .sumOf { it.findDirectoriesBelowThreshold(threshold) }
        .plus(if (threshold < totalSize) 0 else totalSize)

fun Directory.findSmallestDirectoryOverThreshold(threshold: Int): Int =
    subDirectories.values
        .filter { threshold < it.totalSize }
        .minOfOrNull { it.findSmallestDirectoryOverThreshold(threshold) }
        ?: totalSize

fun main() {
    val fileSystem =
        "aoc2022/Day07Input.txt".filePathToStringList().toFileSystem()
    fileSystem.findDirectoriesBelowThreshold(100_000).println()
    val spaceNeeded = (70_000_000 - 30_000_000 - fileSystem.totalSize) * -1
    fileSystem.findSmallestDirectoryOverThreshold(spaceNeeded).println()
}

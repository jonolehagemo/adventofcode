package day13

import extensions.filePathToListOfStringList
import extensions.println
import extensions.transpose

fun List<String>.patternSum(): Int =
    this.findSplit() * 100 + this.transpose().findSplit()

fun List<String>.findSplit(): Int {
    for (i in 0 .. this.size - 2) {
        if (this.reflects(i))
            return i + 1
    }

    return 0
}

fun List<String>.reflects(start: Int): Boolean {
    var lower = start
    var upper = start + 1
    var fixedOne = false

    while (0 <= lower && upper < this.size) {
        if (this[lower] != this[upper])
            if (fixedOne)
                return false
            else if (isFixable(this[lower], this[upper]))
                fixedOne = true
            else
                return false

        lower--
        upper++
    }

    return true
}

fun isFixable(row1: String, row2: String): Boolean {
    var differences = 0

    for ((a, b)  in row1 zip row2)
        if (a != b)
            differences++

    return differences == 1
}


fun main() {
    "Day13Input.txt".filePathToListOfStringList()
        .sumOf { it.patternSum() }
        .println()
    // 26473 is too low
}
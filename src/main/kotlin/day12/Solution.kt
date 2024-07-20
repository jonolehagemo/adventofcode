package day12

import extensions.filePathToStringList
import extensions.println

data class Record(val condition: String, val matches: List<Int>)

fun List<String>.toRecords(): List<Record> = map { line ->
    Record(line.split(' ')[0], line.split(' ')[1].split(',').map { it.toInt() })
}

fun Record.isValid(): Boolean =
    (!this.condition.contains("?"))
            && this.matches == this.condition.split(".").map { it.length }.filter { it > 0 }

fun Record.individual(): Long {
    println("$this = ${this.isValid()}")
    if (!condition.contains("?"))
        return if (isValid()) 1L else 0L


//    if (matches.isEmpty() && condition.isEmpty())
//        return 1
//    if (matches.isEmpty() && condition.contains('#'))
//        return 0
//    if (matches.isEmpty())
//        return 1
//    if (condition.length < matches.first())
//        return 0
//
//    val start = "#".repeat(matches[0])
//    if (condition.startsWith(start))
//        return Record(
//            condition = condition.replaceFirst(start, ""),
//            matches = matches.drop(1)
//        ).individual()

    return copy(condition = condition.replaceFirst("?", "#")).individual() +
            copy(condition = condition.replaceFirst("?", ".")).individual()
}

fun List<Record>.times(n: Int): List<Record> = map {
    Record(
        condition = generateSequence { it.condition }.take(n).joinToString("?") { it },
        matches = generateSequence { it.matches }.take(n).flatten().toList()
    )
}


fun main() {
    "Day12Input.txt".filePathToStringList().toRecords()
        .sumOf { it.individual() }
        .println()

    "Day12Input.txt".filePathToStringList().toRecords().times(2).take(1).sumOf { it.individual() }
        .println()

}
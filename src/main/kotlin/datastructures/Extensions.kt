package datastructures

import java.io.File
import kotlin.math.sqrt

//fun String.toGrid(defaultValue: Char): Grid {
//    val side = sqrt(this.length.toDouble()).toInt()
//
//    return Grid(coordinateCharMap = this
//        .withIndex()
//        .filter { (_, char) -> char != defaultValue }
//        .associate { (index, char) -> Coordinate((index - (index % side)) / side, index % side) to char },
//        defaultValue = defaultValue
//    )
//}

fun List<String>.toGrid(defaultValue: Char): Grid = Grid(this, defaultValue = defaultValue)

fun String.removeSpaces(): String = this.replace(" ", "")


fun String.filePathAsString(): String =
    File(ClassLoader.getSystemResource(this).file).readLines().joinToString("")

fun String.filePathAsStringList(): List<String> =
    File(ClassLoader.getSystemResource(this).file).readLines()

fun String.filePathAsGrid(defaultValue: Char): Grid =
    File(ClassLoader.getSystemResource(this).file).readLines().toGrid(defaultValue)

fun List<Double>.product(): Double = this.reduce{ a, b -> a * b }
fun String.removeBefore(char: Char): String = this.substring(this.indexOf(char) + 1)
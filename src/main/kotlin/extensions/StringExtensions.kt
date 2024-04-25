package extensions

import datastructures.Grid
import java.io.File

fun String.removeSpaces(): String = this.replace(" ", "")

fun String.filePathToStringList(): List<String> =
    File(ClassLoader.getSystemResource(this).file).readLines()

fun String.filePathToGrid(defaultValue: Char): Grid =
    File(ClassLoader.getSystemResource(this).file).readLines().toGrid(defaultValue)

fun String.removeBefore(char: Char): String = this.substring(this.indexOf(char) + 1)

fun String.toDoubleList(): List<Double> = this.trim().split("\\s+".toRegex()).map { it.toDouble() }

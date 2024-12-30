package aoc2024.day09

import extensions.filePathToString
import extensions.println
import java.util.concurrent.atomic.AtomicLong

data class Block(val id: Long, val size: Int)

fun String.toBlocks(): List<Block?> {
    val idNumber = AtomicLong(0)

    return flatMapIndexed { index, c ->
        val size = c.toString().toInt()

        if (index % 2 == 0) {
            val id = idNumber.getAndIncrement()
            List<Block?>(size) { Block(id, size) }
        } else {
            List<Block?>(size) { null }
        }
    }
}

fun List<Block?>.deFragment1(): List<Block?>{
    val result = this.toMutableList()
    var lastIndex = result.lastIndex

    while (0 <= lastIndex){
        val lastItem = result[lastIndex]
        val firstIndex = result.indexOfFirst { it == null }

        if (lastItem != null && firstIndex < lastIndex){
            result[firstIndex] = result[lastIndex]
            result[lastIndex] = null
        }

        lastIndex--
    }

    return result
}

fun List<Block?>.deFragment2(): List<Block?>{
    val result = this.toMutableList()
    var lastIndex = result.lastIndex

    while (0 < lastIndex){
        val lastItem = result[lastIndex]
        val lastItemSize = lastItem?.size ?: 1

        if (lastItem != null && 0 < lastItemSize){
            try {
                val firstIndex = result.windowed(lastItemSize).indexOfFirst { w -> w.all { it == null }  }

                if (firstIndex < lastIndex) {
                    (firstIndex..<firstIndex + lastItemSize).forEach { index ->
                        result[index] = lastItem
                    }
                    (lastIndex - lastItemSize + 1..lastIndex).forEach { index ->
                        result[index] = null
                    }
                }

            } catch (_: IndexOutOfBoundsException){}
        }

        lastIndex-=lastItemSize
    }

    return result
}

fun List<Block?>.checksum(): Long =
    mapIndexedNotNull { index, block -> index * (block?.id ?: 0) }.sum()


fun main() {
    val input = "aoc2024/Day09Input.txt".filePathToString().toBlocks()
    input.deFragment1().checksum().println()
}

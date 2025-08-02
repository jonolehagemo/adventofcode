package aoc2024.day11

import extensions.filePathToLongList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("Task 1") {
        forAll(
            row("aoc2024/Day11TestInput1.txt", 0, 2),
            row("aoc2024/Day11TestInput1.txt", 1, 3),
            row("aoc2024/Day11TestInput1.txt", 2, 4),
            row("aoc2024/Day11TestInput1.txt", 3, 5),
            row("aoc2024/Day11TestInput1.txt", 4, 9),
            row("aoc2024/Day11TestInput1.txt", 5, 13),
            row("aoc2024/Day11TestInput1.txt", 6, 22),
            row("aoc2024/Day11TestInput1.txt", 25, 55312),
            row("aoc2024/Day11Input.txt", 25, 220722),
            row("aoc2024/Day11Input.txt", 75, 261952051690787L),
        ) { filepath, blinks, expected ->
            When("$filepath $blinks -> $expected") {
                val input = filepath.filePathToLongList()
                val result = input.sumOf { blink(it, blinks, cache) }

                Then("count of $result should be $expected") {
                    result shouldBe expected
                }
            }
        }
    }
})
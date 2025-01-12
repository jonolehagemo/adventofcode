package aoc2024.day10

import extensions.filePathToGrid
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("Task 1") {
        forAll(
            row("aoc2024/Day10TestInput1.txt", 2),
            row("aoc2024/Day10TestInput2.txt", 4),
            row("aoc2024/Day10TestInput3.txt", 3),
            row("aoc2024/Day10TestInput4.txt", 36),
            row("aoc2024/Day10Input.txt", 566),
        ) { filepath, expected ->
            When(filepath) {
                val input = filepath.filePathToGrid(' ')
                val trails = input.toTrails()
                val result = trails.distinct().size

                Then("count should be $expected") {
                    result shouldBe expected
                }
            }
        }
    }

    Given("task 2") {
        forAll(
            row("aoc2024/Day10TestInput4.txt", 81),
            row("aoc2024/Day10Input.txt", 1324),
        ) { filepath, expected ->
            When(filepath) {
                val input = filepath.filePathToGrid(' ')
                val trails = input.toTrails()
                val result = trails.size

                Then("count should be $expected") {
                    result shouldBe expected
                }
            }
        }
    }
})

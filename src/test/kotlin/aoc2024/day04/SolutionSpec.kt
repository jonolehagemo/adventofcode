package aoc2024.day04

import extensions.filePathToGrid
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("task 1") {
            forAll(
                row("aoc2024/Day04TestInput1.txt", 18),
//                row("aoc2024/Day04Input.txt", 2496),
            ) { filepath, expected ->
                When(filepath) {
                    val input = filepath.filePathToGrid(' ')
                    val result = input.findWord("XMAS").count()

                    Then("Task 1: $result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

        Given("task 2") {
            forAll(
                row("aoc2024/Day04TestInput1.txt", 9),
//                row("aoc2024/Day04Input.txt", 1967),
            ) { filepath, expected ->
                When(filepath) {
                    val input = filepath.filePathToGrid(' ')
                    val result =
                        input
                            .findWord("MAS")
                            .filter { it[0].row != it[1].row && it[0].column != it[1].column }
                            .groupingBy { it[1] }
                            .eachCount()
                            .count { it.value == 2 }

                    Then("Task 2: $result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }
    })

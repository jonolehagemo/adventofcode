package aoc2024.day08

import extensions.filePathToGrid
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("task 1") {
            forAll(
                row("aoc2024/Day08TestInput1.txt", 14),
                row("aoc2024/Day08Input.txt", 280),
            ) { filepath, expected ->
                When(filepath) {
                    val input = filepath.filePathToGrid('.')
                    val result =
                        input
                            .antennaPairs()
                            .flatMap { it.antiNodes1(input) }
                            .toSet()

                    Then("Task 1: ${result.size} should be $expected") {
                        result.size shouldBe expected
                    }
                }
            }
        }

        Given("task 2") {
            forAll(
                row("aoc2024/Day08TestInput1.txt", 34),
                row("aoc2024/Day08Input.txt", 958),
            ) { filepath, expected ->
                When(filepath) {
                    val input = filepath.filePathToGrid('.')
                    val result =
                        input
                            .antennaPairs()
                            .flatMap { it.antiNodes2(input) }
                            .toSet()

                    Then("Task 1: ${result.size} should be $expected") {
                        result.size shouldBe expected
                    }
                }
            }
        }
    })

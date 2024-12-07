package aoc2024.day06

import extensions.filePathToGrid
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("task 1") {
            forAll(
                row("aoc2024/Day06TestInput1.txt", 41),
                row("aoc2024/Day06Input.txt", 5030),
            ) { filepath, expected ->
                When(filepath) {
                    val input = filepath.filePathToGrid('.')
                    val result = input.path().distinctBy { it.first }.count()

                    Then("Task 1: $result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

        Given("task 2") {
            forAll(
                row("aoc2024/Day06TestInput1.txt", 6),
//                row("aoc2024/Day06Input.txt", 1909), // 1909 is too low
            ) { filepath, expected ->
                When(filepath) {
                    val input = filepath.filePathToGrid('.')
                    val result =
                        input
                            .export()
                            .filter { it.second == '.' }
                            .count { input.add(it.first, '#').path().isEmpty() }

                    Then("Task 1: $result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }
    })

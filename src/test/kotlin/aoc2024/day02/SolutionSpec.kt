package aoc2024.day02

import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("isSafe()") {
            forAll(
                row("7 6 4 2 1", true, true),
                row("1 2 7 8 9", false, false),
                row("9 7 6 2 1", false, false),
                row("1 3 2 4 5", false, true),
                row("8 6 4 4 1", false, true),
                row("1 3 6 7 9", true, true),
            ) { line, expected1, expected2 ->
                When(line) {
                    val input = line.split(' ').map { it.toInt() }

                    Then("Task 1: result should be $expected1") {
                        input.isSafe(0) shouldBe expected1
                    }

                    Then("Task 2: result should be $expected2") {
                        input.isSafe(1) shouldBe expected2
                    }
                }
            }
        }

        Given("processing of test input") {
            forAll(
                row("aoc2024/Day02TestInput1.txt", 2, 4),
                row("aoc2024/Day02Input.txt", 341, 404),
            ) { filepath, expected1, expected2 ->
                When(filepath) {
                    val input = filepath.filePathToStringList().map { it.split(' ').map { it.toInt() } }
                    val result1 = input.count { it.isSafe(0) }

                    Then("Task 1: $result1 should be $expected1") {
                        result1 shouldBe expected1
                    }

                    val result2 = input.count { it.isSafe(1) }

                    Then("Task 2: $result2 should be $expected2") {
                        result2 shouldBe expected2
                    }
                }
            }
        }
    })

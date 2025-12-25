package aoc2025.day01

import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("Int.rotate() extension function") {
            forAll(
                row(50, -68, 82,1),
                row(82, -30, 52, 0),
                row(52, 48, 0, 1),
                row(0, -5, 95, 0),
                row(95, 60, 55, 1),
                row(55, -55, 0, 1),
                row(0, -1, 99, 0),
                row(99, -99, 0, 1),
                row(0, 14, 14, 0),
                row(14, -82, 32, 1),
                row(50, 1000, 50, 10),
                row(50, -1000, 50, 10),
            ) { start, move, expected1, expected2 ->
                When("rotating $start by $move") {
                    val result = start.rotate(move)

                    Then("the result.first should be $expected1") {
                        result.first shouldBe expected1
                    }

                    Then("the result.second should be $expected2") {
                        result.second shouldBe expected2
                    }
                }
            }
        }

        Given("processing of test input") {
            forAll(
                row(
                    "aoc2025/Day01TestInput1.txt",
                    3,
                    6,
                ),
                row(
                    "aoc2025/Day01Input.txt",
                    995,
                    5847,
                ),
            ) { filepath, expected1, expected2 ->
                When(filepath) {
                    val rotations = filepath
                        .filePathToStringList()
                        .toIntList()
                        .fold(listOf(start to 0)) { acc, i ->
                            acc + acc.last().first.rotate(i)
                        }
                    val task1 = rotations
                        .count { it.first == 0 }

                    val task2 = rotations
                        .sumOf { it.second }

                    Then("Task 1: $task1 should be $expected1") {
                        task1 shouldBe expected1
                    }

                    Then("Task 2: $task2 should be $expected2") {
                        task2 shouldBe expected2
                    }
                }
            }
        }
    })
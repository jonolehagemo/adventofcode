package aoc2024.day05

import extensions.filePathToListOfStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("task 1") {
            forAll(
                row("aoc2024/Day05TestInput1.txt", 143),
                row("aoc2024/Day05Input.txt", 5713),
            ) { filepath, expected ->
                When(filepath) {
                    val input = filepath.filePathToListOfStringList()
                    val rules = input.first().toRules()
                    val actions = input.last().toActions()
                    val result =
                        actions
                            .filter { it.isInOrder(rules) }
                            .sumOf { it.middle() }

                    Then("Task 1: $result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

        Given("unordered lists") {
            forAll(
                row(listOf(75, 97, 47, 61, 53), listOf(97, 75, 47, 61, 53)),
            ) { unordered, expected ->
                When(unordered.toString()) {
                    val rules =
                        "aoc2024/Day05TestInput1.txt"
                            .filePathToListOfStringList()
                            .first()
                            .toRules()
                    val result = unordered.reOrder(rules)

                    Then("$result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

        Given("task 2") {
            forAll(
                row("aoc2024/Day05TestInput1.txt", 123),
                row("aoc2024/Day05Input.txt", 5180),
            ) { filepath, expected ->
                When(filepath) {
                    val input = filepath.filePathToListOfStringList()
                    val rules = input.first().toRules()
                    val actions = input.last().toActions()
                    val result =
                        actions
                            .filter { !it.isInOrder(rules) }
                            .map { it.reOrder(rules) }
                            .sumOf { it.middle() }

                    Then("Task 2: $result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }
    })

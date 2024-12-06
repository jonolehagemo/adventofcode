package aoc2024.day03

import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("calculateMultiplications()") {
            forAll(
                row("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))", 161),
            ) { line, expected1 ->
                When(line) {
                    val result1 = line.sumMultiplications()

                    Then("Task 1: result should be $expected1") {
                        result1 shouldBe expected1
                    }
                }
            }
        }

        Given("calculateMultiplicationsWithDo()") {
            forAll(
                row("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))", 48),
            ) { line, expected1 ->
                When(line) {
                    val result1 = line.sumFromDoToDont()

                    Then("Task 1: result should be $expected1") {
                        result1 shouldBe expected1
                    }
                }
            }
        }

        Given("task 1") {
            forAll(
                row("aoc2024/Day03TestInput1.txt", 161),
                row("aoc2024/Day03Input.txt", 187194524),
            ) { filepath, expected ->
                When(filepath) {
                    val input = filepath.filePathToStringList().joinToString("")
                    val result = input.sumMultiplications()

                    Then("Task 1: $result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

        Given("task 2") {
            forAll(
                row("aoc2024/Day03TestInput2.txt", 48),
                row("aoc2024/Day03Input.txt", 127092535),
            ) { filepath, expected ->
                When(filepath) {
                    val input = filepath.filePathToStringList().joinToString("")
                    val result = input.sumFromDoToDont()

                    Then("Task 2: $result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }
    })

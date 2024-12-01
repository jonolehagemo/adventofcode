package aoc2024.day01

import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("processing of test input") {
            forAll(
                row("aoc2024/Day01TestInput1.txt", 11, 31),
                row("aoc2024/Day01Input.txt", 1590491, 22588371),
            ) { filepath, expected1, expected2 ->
                When(filepath) {
                    val input = filepath.filePathToStringList()
                    val left = input.map { it.substringBefore(' ').toInt() }
                    val right = input.map { it.substringAfterLast(' ').toInt() }
                    val result1 = (left to right).sumDistances()
                    val result2 = (left to right).sumSimilarityScore()

                    Then("Task 1: $result1 should be $expected1") {
                        result1 shouldBe expected1
                    }

                    Then("Task2: $result2 should be $expected2") {
                        result2 shouldBe expected2
                    }
                }
            }
        }
    })

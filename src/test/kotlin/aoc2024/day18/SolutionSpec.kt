package aoc2024.day18

import datastructures.Coordinate
import datastructures.Grid
import extensions.cartesianProduct
import extensions.filePathToStringList
import extensions.println
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("task 1") {

            forAll(
                row("aoc2024/Day18TestInput1.txt", 6, 12, 22),
                row("aoc2024/Day18Input.txt", 70, 1024, 252),
            ) { filepath, length, takenBytes, expected ->
                When(filepath) {
                    val result = process(length, takenBytes, filepath)

                    Then("Task 1: $result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

        Given("task 2") {

            forAll(
                row("aoc2024/Day18TestInput1.txt", 6, 12, Coordinate(column=6, row=1)),
                row("aoc2024/Day18Input.txt", 70, 1024, Coordinate(column=5, row=60)),
            ) { filepath, length, takenBytes, expected ->
                When(filepath) {
                    val corrupted = filepath.filePathToStringList().map { it.split(',').let { (x, y) -> Coordinate(y.toInt(), x.toInt()) } }
                    val index = generateSequence(takenBytes) { it + 1 }
                        .map { it to process(length, it, filepath) }
                        .takeWhile { it.second != -1 }
                        .last().first

                    val result = corrupted[index]

                    Then("Task 1: ${result.toXYString()} should be ${expected.toXYString()}") {
                        result shouldBe expected
                    }
                }
            }
        }
    })

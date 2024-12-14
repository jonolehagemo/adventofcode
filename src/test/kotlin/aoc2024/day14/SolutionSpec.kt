package aoc2024.day14

import datastructures.Coordinate
import extensions.filePathToStringList
import extensions.println
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("Example") {
            val startPosition = Coordinate(4, 2)
            val velocity = Coordinate(-3, 2)
            forAll(
                row(0, Coordinate(4, 2)),
                row(1, Coordinate(1, 4)),
                row(2, Coordinate(5, 6)),
                row(3, Coordinate(2, 8)),
                row(4, Coordinate(10, 10)),
                row(5, Coordinate(3, 1)),
            ) { seconds, expected ->
                When("$seconds second(s)") {
                    val result = Pair(startPosition, velocity).calculatePosition(11, 7, seconds)

                    Then("$result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

        Given("task 1 and 100 seconds") {
            val seconds = 100

            forAll(
                row("aoc2024/Day14TestInput1.txt", 11, 7, 12),
//                row("aoc2024/Day14Input.txt", 101, 103, 280),
            ) { filepath, width, height, expected ->
                When(filepath) {
                    val input = filepath.filePathToStringList().map { it.toCoordinatePair() }
                    val result =
                        input
                            .map { it.calculatePosition(width, height, seconds) }
                            .sorted()
                    result.println()

                    Then("Task 1: ${result.size} should be $expected") {
                        result.size shouldBe expected
                    }
                }
            }
        }

//        Given("task 2") {
//            forAll(
//                row("aoc2024/Day14TestInput1.txt", 34),
//                row("aoc2024/Day14Input.txt", 958),
//            ) { filepath, expected ->
//                When(filepath) {
//                    val input = filepath.filePathToGrid('.')
//                    val result =
//                        input
//                            .antennaPairs()
//                            .flatMap { it.antiNodes2(input) }
//                            .toSet()
//
//                    Then("Task 1: ${result.size} should be $expected") {
//                        result.size shouldBe expected
//                    }
//                }
//            }
//        }
    })

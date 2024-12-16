package aoc2024.day14

import datastructures.Coordinate
import datastructures.Grid
import extensions.filePathToStringList
import extensions.println
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("Example 1") {
            val startPosition = Coordinate(4, 2)
            val velocity = Coordinate(-3, 2)
            forAll(
                row(0, Coordinate(4, 2)),
                row(1, Coordinate(1, 4)),
                row(2, Coordinate(5, 6)),
                row(3, Coordinate(2, 8)),
                row(4, Coordinate(6, 10)),
                row(5, Coordinate(3, 1)),
                row(6, Coordinate(0, 3)),
                row(7, Coordinate(4, 5)),
                row(8, Coordinate(1, 7)),
                row(9, Coordinate(5, 9)),
            ) { seconds, expected ->
                When("$seconds second(s)") {
                    val result = (startPosition to velocity).calculatePosition(
                        11,
                        7,
                        seconds
                    )

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
                row("aoc2024/Day14Input.txt", 101, 103, 216772608),
            ) { filepath, width, height, expected ->
                When(filepath) {
                    val input = filepath.filePathToStringList()

                    val result =
                        input
                            .map {
                                it.toCoordinatePair()
                                    .calculatePosition(width, height, seconds)
                            }
                            .filter {
                                it.row != (height.floorDiv(2))
                                        && it.column != (width.floorDiv(2))
                            }
                            .map {
                                Pair(
                                    it.row < (height.floorDiv(2)),
                                    it.column < (width.floorDiv(2))
                                ) to it
                            }
                            .groupingBy { it.first }
                            .eachCount()
                            .map { it.value }
                            .reduce { a, b -> a * b }

                    Then("Task 1: $result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

        Given("task 2 and ? seconds") {
            forAll(
                row("aoc2024/Day14Input.txt", 101, 103, "*********", 6888),
            ) { filepath, width, height, pattern, expected ->
                When(filepath) {
                    val input = filepath.filePathToStringList()
                        .map { it.toCoordinatePair() }
                    val result = (1..1_000_000).firstOrNull { seconds ->
                        input
                            .map {
                                it.calculatePosition(
                                    width,
                                    height,
                                    seconds
                                )
                            }
                            .let { list ->
                                Grid(
                                    coordinateCharMap = list.associateWith { '*' },
                                    defaultValue = ' ',
                                    rowLength = height,
                                    columnLength = width
                                ).toString().contains(pattern)
                            }
                    } ?: 1_000_000

                    Then("Task 2: $result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

    })

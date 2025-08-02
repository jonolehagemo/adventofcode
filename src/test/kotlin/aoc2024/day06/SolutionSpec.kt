package aoc2024.day06

import datastructures.Grid
import extensions.filePathToGrid
import extensions.println
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
                    val start = input.findCoordinateByValue('^').first()
                    val lab = Grid(
                        coordinateCharMap = input.coordinateCharMap,
                        start = start
                    )
                    val guardPath = lab.path()
                    lab.columnLength.println()
                    lab.columnRange().println()
                    lab.rowLength.println()
                    lab.rowRange().println()
                    val result = guardPath
                        .first
                        .count()

                    Then("Task 1: $result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

        Given("task 2") {
            forAll(
                row("aoc2024/Day06TestInput1.txt", 6),
                row("aoc2024/Day06Input.txt", 1928),
            ) { filepath, expected ->
                When(filepath) {
                    val input = filepath.filePathToGrid('.')
                    val start = input.findCoordinateByValue('^').first()
                    val lab = Grid(
                        coordinateCharMap = input.coordinateCharMap,
                        start = start
                    )
                    val guardPath = lab.path()

                    val result =
                        guardPath
                            .first
                            .toList()
                            .withIndex()
                            .filter { (_, obstacle) -> obstacle != start }
                            .count { (index, obstacle) ->
                                lab
                                    .plus(obstacle to '#')
                                    .path()
                                    .second
                            }

                    Then("Task 1: $result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }
    })

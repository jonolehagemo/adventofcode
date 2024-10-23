package aoc2022.day09

import datastructures.Coordinate
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
                row("aoc2022/Day09TestInput1.txt", 13),
//                row("aoc2022/Day09Input.txt", 1854),
            ) { filePath, expectedSize ->
                When("$filePath -[size]-> $expectedSize") {
                    val input = filePath.filePathToStringList()
                    val result = input.positions()
                    result.println()

                    Then("$result -> $expectedSize") {
                        result
                            .map { it.second }
                            .toSet()
                            .count() shouldBe expectedSize
                    }

                    Then("result should be expected list of positions") {
                        result shouldBe
                            listOf(
                                // Start
                                Coordinate(0, 0) to Coordinate(0, 0),
                                // R 4
                                Coordinate(0, 1) to Coordinate(0, 0),
                                Coordinate(0, 2) to Coordinate(0, 1),
                                Coordinate(0, 3) to Coordinate(0, 2),
                                Coordinate(0, 4) to Coordinate(0, 3),
                                // U 4
                                Coordinate(-1, 4) to Coordinate(0, 3),
                                Coordinate(-2, 4) to Coordinate(-1, 4),
                                Coordinate(-3, 4) to Coordinate(-2, 4),
                                Coordinate(-4, 4) to Coordinate(-3, 4),
                                // L 3
                                Coordinate(-4, 3) to Coordinate(-3, 4),
                                Coordinate(-4, 2) to Coordinate(-4, 3),
                                Coordinate(-4, 1) to Coordinate(-4, 2),
                                // D 1
                                Coordinate(-3, 1) to Coordinate(-4, 2),
                                // R 4
                                Coordinate(-3, 2) to Coordinate(-4, 2),
                                Coordinate(-3, 3) to Coordinate(-4, 2),
                                Coordinate(-3, 4) to Coordinate(-3, 3),
                                Coordinate(-3, 5) to Coordinate(-3, 4),
                                // D 1
                                Coordinate(-2, 5) to Coordinate(-3, 4),
                                // L 5
                                Coordinate(-2, 4) to Coordinate(-3, 4),
                                Coordinate(-2, 3) to Coordinate(-3, 4),
                                Coordinate(-2, 2) to Coordinate(-2, 3),
                                Coordinate(-2, 1) to Coordinate(-2, 2),
                                Coordinate(-2, 0) to Coordinate(-2, 1),
                                // R 2
                                Coordinate(-2, 1) to Coordinate(-2, 1),
                                Coordinate(-2, 2) to Coordinate(-2, 1),
                            )
                    }
                }
            }
        }
    })

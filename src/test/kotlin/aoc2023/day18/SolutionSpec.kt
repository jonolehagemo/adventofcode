package aoc2023.day18

import datastructures.LongCoordinate
import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("Part 1, toCoordinates") {
            val filePath = "aoc2023/Day18TestInput1.txt"

            When("converting file to list of Coordinates") {
                val coordinates =
                    filePath
                        .filePathToStringList()
                        .toCoordinates1()

                Then("length should be 14") {
                    coordinates.size shouldBe 14
                }

                Then("coordinates should be as expected") {
                    coordinates shouldBe
                        listOf(
                            LongCoordinate(0, 6),
                            LongCoordinate(5, 0),
                            LongCoordinate(0, -2),
                            LongCoordinate(2, 0),
                            LongCoordinate(0, 2),
                            LongCoordinate(2, 0),
                            LongCoordinate(0, -5),
                            LongCoordinate(-2, 0),
                            LongCoordinate(0, -1),
                            LongCoordinate(-2, 0),
                            LongCoordinate(0, 2),
                            LongCoordinate(-3, 0),
                            LongCoordinate(0, -2),
                            LongCoordinate(-2, 0),
                        )
                }
            }
        }

        Given("Part 1, calculateAreaByCircumference") {
            val filePath = "aoc2023/Day18TestInput1.txt"
            val circumference =
                filePath
                    .filePathToStringList()
                    .toCoordinates1()

            When("calculating result for circumference") {
                val result =
                    circumference
                        .calculateAreaByCircumference()

                Then("the result ($result) should be as expected (62)") {
                    result shouldBe 62
                }
            }
        }

        Given("Part 1, results") {
            forAll(
                row("aoc2023/Day18TestInput1.txt", 62L),
//            row("Day18Input.txt", 95356L),
            ) { filePath, expectedResult ->
                When("calculating result for $filePath") {
                    val result =
                        filePath
                            .filePathToStringList()
                            .toCoordinates1()
                            .calculateAreaByCircumference()

                    Then("the result ($result) should be as expected ($expectedResult)") {
                        result shouldBe expectedResult
                    }
                }
            }
        }

        Given("Part 2, toCoordinates") {
            val filePath = "aoc2023/Day18TestInput1.txt"

            When("converting file to list of Coordinates") {
                val coordinates =
                    filePath
                        .filePathToStringList()
                        .toCoordinates2()

                Then("length should be 14") {
                    coordinates.size shouldBe 14
                }

                Then("coordinates should be as expected") {
                    coordinates shouldBe
                        listOf(
                            LongCoordinate(0, 461937),
                            LongCoordinate(56407, 0),
                            LongCoordinate(0, 356671),
                            LongCoordinate(863240, 0),
                            LongCoordinate(0, 367720),
                            LongCoordinate(266681, 0),
                            LongCoordinate(0, -577262),
                            LongCoordinate(-829975, 0),
                            LongCoordinate(0, -112010),
                            LongCoordinate(829975, 0),
                            LongCoordinate(0, -491645),
                            LongCoordinate(-686074, 0),
                            LongCoordinate(0, -5411),
                            LongCoordinate(-500254, 0),
                        )
                }
            }
        }

        Given("Part 2, results") {
            forAll(
                row("aoc2023/Day18TestInput1.txt", 952408144115L),
//            row("Day18Input.txt", 92291468914147L),
            ) { filePath, expectedResult ->
                When("calculating result for $filePath") {
                    val result =
                        filePath
                            .filePathToStringList()
                            .toCoordinates2()
                            .calculateAreaByCircumference()

                    Then("the result ($result) should be as expected ($expectedResult)") {
                        result shouldBe expectedResult
                    }
                }
            }
        }
    })

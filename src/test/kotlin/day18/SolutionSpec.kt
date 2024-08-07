package day18

import datastructures.Coordinate
import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("Part 1, toCoordinates") {
        val filePath = "Day18TestInput1.txt"

        When("converting file to list of Coordinates") {
            val coordinates = filePath
                .filePathToStringList()
                .toCoordinates1()
                .toRelativeCoordinates()

            Then("length should be 14") {
                coordinates.size shouldBe 14
            }

            Then("coordinates should be as expected") {
                coordinates shouldBe listOf(
                    Coordinate(0, 6),
                    Coordinate(5, 6),
                    Coordinate(5, 4),
                    Coordinate(7, 4),
                    Coordinate(7, 6),
                    Coordinate(9, 6),
                    Coordinate(9, 1),
                    Coordinate(7, 1),
                    Coordinate(7, 0),
                    Coordinate(5, 0),
                    Coordinate(5, 2),
                    Coordinate(2, 2),
                    Coordinate(2, 0),
                    Coordinate(0, 0),
                )
            }
        }
    }

    Given("Part 1, calculateAreaByCircumference") {
        val filePath = "Day18TestInput1.txt"
        val circumference = filePath
            .filePathToStringList()
            .toCoordinates1()
            .toRelativeCoordinates()

        When("calculating result for circumference") {
            val result = circumference
                .calculateAreaByCircumference()

            Then("the result ($result) should be as expected (62)") {
                result shouldBe 62
            }
        }
    }


    Given("Part 1, results") {
        forAll(
            row("Day18TestInput1.txt", 62L),
//            row("Day18Input.txt", 95356L),
        ) { filePath, expectedResult ->
            When("calculating result for $filePath") {
                val result = filePath
                    .filePathToStringList()
                    .toCoordinates1()
                    .toRelativeCoordinates()
                    .calculateAreaByCircumference()

                Then("the result ($result) should be as expected ($expectedResult)") {
                    result shouldBe expectedResult
                }
            }
        }
    }

    Given("Part 2, toCoordinates") {
        val filePath = "Day18TestInput1.txt"

        When("converting file to list of Coordinates") {
            val coordinates = filePath
                .filePathToStringList()
                .toCoordinates2()

            Then("length should be 14") {
                coordinates.size shouldBe 14
            }

            Then("coordinates should be as expected") {
                coordinates shouldBe listOf(
                    Coordinate(0, 461937),
                    Coordinate(56407, 0),
                    Coordinate(0, 356671),
                    Coordinate(863240, 0),
                    Coordinate(0, 367720),
                    Coordinate(266681, 0),
                    Coordinate(0, -577262),
                    Coordinate(-829975, 0),
                    Coordinate(0, -112010),
                    Coordinate(829975, 0),
                    Coordinate(0, -491645),
                    Coordinate(-686074, 0),
                    Coordinate(0, -5411),
                    Coordinate(-500254, 0),
                )
            }
        }
    }


    Given("Part 2, results") {
        forAll(
            row("Day18TestInput1.txt", 952408144115L),
//            row("Day18Input.txt", 92291468914147L),
        ) { filePath, expectedResult ->
            When("calculating result for $filePath") {
                val result = filePath
                    .filePathToStringList()
                    .toCoordinates2()
                    .toRelativeCoordinates()
                    .calculateAreaByCircumference()

                Then("the result ($result) should be as expected ($expectedResult)") {
                    result shouldBe expectedResult
                }
            }
        }
    }
})
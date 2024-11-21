package aoc2022.day14

import datastructures.Coordinate
import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("filepath to input") {
            forAll(
                row("aoc2022/Day14TestInput1.txt", 20, 24, 93),
                row("aoc2022/Day14Input.txt", 560, 805, 25161),
            ) { filepath, expectedCoordinates, task1Expected, task2Expected ->
                When("filepath is $filepath") {
                    val from = Coordinate(0, 500)
                    val input = filepath.filePathToStringList().toCoordinateSet()

                    Then("the number of read coordinates should be the $expectedCoordinates expected coordinates") {
                        input.size shouldBe expectedCoordinates
                    }

                    Then("the result for task 1 should be the $task1Expected expected sand grains") {
                        val result = input.getSandUnits(from)
                        result shouldBe task1Expected
                    }

                    Then("the result for task 2 should be the $task2Expected expected sand grains") {
                        val floor: Set<Coordinate> =
                            (input.maxOf { it.row } + 2).let { floorLevel ->
                                (
                                    Coordinate(floorLevel, from.column - floorLevel)
                                        .toList(Coordinate(floorLevel, from.column + floorLevel))
                                ).toSet()
                            }
                        val result = input.plus(floor).getSandUnits(from)
                        result shouldBe task2Expected
                    }
                }
            }
        }
    })

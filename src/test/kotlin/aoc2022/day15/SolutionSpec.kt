package aoc2022.day15

import datastructures.Coordinate
import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("a list with one input line") {
            val result =
                listOf("Sensor at x=2, y=18: closest beacon is at x=-2, y=15").toSensorBeaconPairs()

            Then("result should be a pair of Coordinate") {
                result shouldBe listOf(Coordinate(18, 2) to Coordinate(15, -2))
            }
        }

        Given("a list of IntRange ") {
            forAll(
                row(
                    listOf(IntRange(0, 2), IntRange(1, 3)),
                    listOf(IntRange(0, 3)),
                ),
            ) { range, expected ->
                Then("result should be a merged list of ranges") {
                    val result = range.merge()
                    result shouldBe expected
                }
            }
        }

        Given("filepath to input") {
            forAll(
                row("aoc2022/Day15TestInput1.txt", 10, 26),
                //                row("aoc2022/Day15Input.txt", 560, 805, 25161),
            ) { filepath, y, task1Expected ->
                When("filepath is $filepath") {
                    Then("the result for task 1 should be the $task1Expected expected sand grains") {
                        val result =
                            filepath
                                .filePathToStringList()
                                .toSensorBeaconPairs()
                                .countPositions(y)
                        result shouldBe task1Expected
                    }

//                    Then("the result for task 2 should be the $task2Expected expected sand grains") {
//                        val result = filepath.toCoordinateSet().task2(from)
//                        result shouldBe task2Expected
//                    }
                }
            }
        }
// ..####B######################..
    })

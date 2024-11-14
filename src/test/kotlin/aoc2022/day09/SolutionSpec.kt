package aoc2022.day09

import datastructures.LongCoordinate
import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("task 1, move()") {
            forAll(
                row(
                    listOf(LongCoordinate(0, 0), LongCoordinate(0, 0), LongCoordinate(0, 0)),
                    LongCoordinate.ORIGIN.east(),
                    listOf(LongCoordinate(0, 1), LongCoordinate(0, 0), LongCoordinate(0, 0)),
                ),
                row(
                    listOf(LongCoordinate(0, 2), LongCoordinate(0, 1), LongCoordinate(0, 0)),
                    LongCoordinate.ORIGIN.east(),
                    listOf(LongCoordinate(0, 3), LongCoordinate(0, 2), LongCoordinate(0, 1)),
                ),
                row(
                    listOf(LongCoordinate(0, 2), LongCoordinate(0, 1), LongCoordinate(0, 0)),
                    LongCoordinate.ORIGIN.south(),
                    listOf(LongCoordinate(1, 2), LongCoordinate(0, 1), LongCoordinate(0, 0)),
                ),
                row(
                    listOf(LongCoordinate(1, 2), LongCoordinate(0, 1), LongCoordinate(0, 0)),
                    LongCoordinate.ORIGIN.south(),
                    listOf(LongCoordinate(2, 2), LongCoordinate(1, 2), LongCoordinate(1, 1)),
                ),
            ) { rope, direction, expected ->
                When("$rope -[moves]-> $direction") {
                    val result = rope.move(direction)

                    Then("$result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

        Given("task 1") {
            forAll(
                row("aoc2022/Day09TestInput1.txt", 13),
//                row("aoc2022/Day09Input.txt", 6269),
            ) { filePath, expectedSize ->
                When("$filePath -[size]-> $expectedSize") {
                    val input = filePath.filePathToStringList()
                    val result = input.positions(2)

                    Then("$result -> $expectedSize") {
                        result.count() shouldBe expectedSize
                    }

                    Then("result should be expected list of positions") {
                        result shouldBe
                            listOf(
                                // Start
                                LongCoordinate(0, 0) to LongCoordinate(0, 0),
                                // R 4
                                LongCoordinate(0, 1) to LongCoordinate(0, 0),
                                LongCoordinate(0, 2) to LongCoordinate(0, 1),
                                LongCoordinate(0, 3) to LongCoordinate(0, 2),
                                LongCoordinate(0, 4) to LongCoordinate(0, 3),
                                // U 4
                                LongCoordinate(-1, 4) to LongCoordinate(0, 3),
                                LongCoordinate(-2, 4) to LongCoordinate(-1, 4),
                                LongCoordinate(-3, 4) to LongCoordinate(-2, 4),
                                LongCoordinate(-4, 4) to LongCoordinate(-3, 4),
                                // L 3
                                LongCoordinate(-4, 3) to LongCoordinate(-3, 4),
                                LongCoordinate(-4, 2) to LongCoordinate(-4, 3),
                                LongCoordinate(-4, 1) to LongCoordinate(-4, 2),
                                // D 1
                                LongCoordinate(-3, 1) to LongCoordinate(-4, 2),
                                // R 4
                                LongCoordinate(-3, 2) to LongCoordinate(-4, 2),
                                LongCoordinate(-3, 3) to LongCoordinate(-4, 2),
                                LongCoordinate(-3, 4) to LongCoordinate(-3, 3),
                                LongCoordinate(-3, 5) to LongCoordinate(-3, 4),
                                // D 1
                                LongCoordinate(-2, 5) to LongCoordinate(-3, 4),
                                // L 5
                                LongCoordinate(-2, 4) to LongCoordinate(-3, 4),
                                LongCoordinate(-2, 3) to LongCoordinate(-3, 4),
                                LongCoordinate(-2, 2) to LongCoordinate(-2, 3),
                                LongCoordinate(-2, 1) to LongCoordinate(-2, 2),
                                LongCoordinate(-2, 0) to LongCoordinate(-2, 1),
                                // R 2
                                LongCoordinate(-2, 1) to LongCoordinate(-2, 1),
                                LongCoordinate(-2, 2) to LongCoordinate(-2, 1),
                            )
                    }
                }
            }
        }

        Given("task 2") {
            forAll(
                row("aoc2022/Day09TestInput1.txt", 1),
                row("aoc2022/Day09TestInput2.txt", 36),
                row("aoc2022/Day09Input.txt", 2557),
            ) { filePath, expectedSize ->
                When("$filePath -[size]-> $expectedSize") {
                    val input = filePath.filePathToStringList()
                    val result = input.positions(10)

                    Then("result -> expectedSize") {
                        result.count() shouldBe expectedSize
                    }
                }
            }
        }
    })

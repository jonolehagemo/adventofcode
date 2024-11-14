package aoc2023.day16

import datastructures.LongCoordinate
import extensions.filePathToGrid
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("Part 1, path") {
            val filePath = "aoc2023/Day16TestInput1.txt"
            forAll(
                row(LongCoordinate(0, 0), LongCoordinate(0, 1)),
                row(LongCoordinate(0, 1), LongCoordinate(0, 1)),
                row(LongCoordinate(1, 1), LongCoordinate(1, 0)),
                row(LongCoordinate(2, 1), LongCoordinate(1, 0)),
                row(LongCoordinate(3, 1), LongCoordinate(1, 0)),
                row(LongCoordinate(4, 1), LongCoordinate(1, 0)),
                row(LongCoordinate(5, 1), LongCoordinate(1, 0)),
                row(LongCoordinate(6, 1), LongCoordinate(1, 0)),
                row(LongCoordinate(7, 1), LongCoordinate(1, 0)),
                row(LongCoordinate(7, 0), LongCoordinate(0, -1)),
                row(LongCoordinate(7, 2), LongCoordinate(0, 1)),
                row(LongCoordinate(7, 3), LongCoordinate(0, 1)),
                row(LongCoordinate(7, 4), LongCoordinate(0, 1)),
                row(LongCoordinate(6, 4), LongCoordinate(-1, 0)),
                row(LongCoordinate(6, 5), LongCoordinate(0, 1)),
                row(LongCoordinate(6, 6), LongCoordinate(0, 1)),
                row(LongCoordinate(7, 6), LongCoordinate(1, 0)),
                row(LongCoordinate(8, 6), LongCoordinate(1, 0)),
                row(LongCoordinate(8, 5), LongCoordinate(0, -1)),
                row(LongCoordinate(8, 7), LongCoordinate(0, 1)),
                row(LongCoordinate(8, 4), LongCoordinate(0, -1)),
                row(LongCoordinate(7, 7), LongCoordinate(-1, 0)),
                row(LongCoordinate(9, 7), LongCoordinate(1, 0)),
                row(LongCoordinate(8, 3), LongCoordinate(0, -1)),
                row(LongCoordinate(6, 7), LongCoordinate(-1, 0)),
                row(LongCoordinate(8, 2), LongCoordinate(0, -1)),
                row(LongCoordinate(6, 6), LongCoordinate(0, -1)),
                row(LongCoordinate(8, 1), LongCoordinate(0, -1)),
                row(LongCoordinate(5, 6), LongCoordinate(-1, 0)),
                row(LongCoordinate(7, 1), LongCoordinate(-1, 0)),
                row(LongCoordinate(9, 1), LongCoordinate(1, 0)),
                row(LongCoordinate(4, 6), LongCoordinate(-1, 0)),
                row(LongCoordinate(3, 6), LongCoordinate(-1, 0)),
                row(LongCoordinate(2, 6), LongCoordinate(-1, 0)),
                row(LongCoordinate(2, 5), LongCoordinate(0, -1)),
                row(LongCoordinate(2, 7), LongCoordinate(0, 1)),
                row(LongCoordinate(1, 5), LongCoordinate(-1, 0)),
                row(LongCoordinate(3, 5), LongCoordinate(1, 0)),
                row(LongCoordinate(2, 8), LongCoordinate(0, 1)),
                row(LongCoordinate(0, 5), LongCoordinate(-1, 0)),
                row(LongCoordinate(4, 5), LongCoordinate(1, 0)),
                row(LongCoordinate(2, 9), LongCoordinate(0, 1)),
                row(LongCoordinate(0, 4), LongCoordinate(0, -1)),
                row(LongCoordinate(5, 5), LongCoordinate(1, 0)),
                row(LongCoordinate(0, 3), LongCoordinate(0, -1)),
                row(LongCoordinate(6, 5), LongCoordinate(1, 0)),
                row(LongCoordinate(0, 2), LongCoordinate(0, -1)),
                row(LongCoordinate(7, 5), LongCoordinate(1, 0)),
                row(LongCoordinate(0, 1), LongCoordinate(0, -1)),
                row(LongCoordinate(8, 5), LongCoordinate(1, 0)),
                row(LongCoordinate(9, 5), LongCoordinate(1, 0)),
            ) { coordinate, direction ->
                When("$coordinate -> $direction") {
                    val result =
                        filePath
                            .filePathToGrid('.')
                            .energized(LongCoordinate(0L, -1L), LongCoordinate(0L, 1L))

                    Then("the coordinate ($coordinate) with direction ($direction) should be in the result") {
                        coordinate to direction shouldBeIn result
                    }
                }
            }
        }

        Given("Part 1, energized") {
            val energized =
                "aoc2023/Day16TestInput1.txt"
                    .filePathToGrid('.')
                    .energized(LongCoordinate(0L, -1L), LongCoordinate(0L, 1L))
                    .map { it.first }
                    .toSet()

            val result =
                "aoc2023/Day16TestInput2.txt"
                    .filePathToGrid('.')
                    .nodes()
                    .toSet()

            Then("intersect should be 0") {
                println("energized - result\n -> $energized - $result\n = ${energized - result}")
                println("result - energized\n -> $result - $energized\n = ${result - energized}")
                println(energized intersect result)

                (energized intersect result).size shouldBe 31
            }
        }

        Given("Part 1, results") {
            forAll(
                row("aoc2023/Day16TestInput1.txt", 46),
//            row("Day16Input.txt", 7608),
            ) { filePath, expectedResult ->
                When("calculating result for $filePath") {
                    val result =
                        filePath
                            .filePathToGrid('.')
                            .energized(LongCoordinate(0L, -1L), LongCoordinate(0L, 1L))
                            .groupBy { it.first }
                            .count()

                    Then("the result ($result) should be as expected ($expectedResult)") {
                        result shouldBe expectedResult
                    }
                }
            }
        }

        Given("Part 2, results") {
            forAll(
                row("aoc2023/Day16TestInput1.txt", 51),
//            row("Day16Input.txt", 7608),
            ) { filePath, expectedResult ->
                When("calculating result for $filePath") {
                    val grid = filePath.filePathToGrid('.')
                    val startRows =
                        grid
                            .rowRange()
                            .toList()
                            .map { it.toInt() }
                            .flatMap {
                                listOf(
                                    LongCoordinate(it.toLong(), -1L) to LongCoordinate(0L, 1L),
                                    LongCoordinate(it.toLong(), grid.columnRange().last + 1L) to LongCoordinate(0L, -1L),
                                )
                            }

                    val startColumns =
                        grid
                            .columnRange()
                            .toList()
                            .map { it.toInt() }
                            .flatMap {
                                listOf(
                                    LongCoordinate(-1L, it.toLong()) to LongCoordinate(1L, 0L),
                                    LongCoordinate(grid.rowRange().last + 1L, it.toLong()) to LongCoordinate(-1L, 0L),
                                )
                            }

                    val result =
                        startRows
                            .plus(startColumns)
                            .maxOf { (start, direction) ->
                                grid
                                    .energized(start, direction)
                                    .groupBy { it.first }
                                    .count()
                            }

                    Then("the result ($result) should be as expected ($expectedResult)") {
                        result shouldBe expectedResult
                    }
                }
            }
        }
    })
/*  r,  c
N (-1,  0) \ V ( 0, -1)
S ( 1,  0) \ Ø ( 0,  1)
V ( 0, -1) \ N (-1,  0)
Ø ( 0,  1) \ S ( 1,  0)
* */

/*  r,  c
N (-1,  0) / Ø ( 0,  1)
S ( 1,  0) / V ( 0, -1)
V ( 0, -1) / S ( 1,  0)
Ø ( 0,  1) / N (-1,  0)
* */

/*  r,  c
N (-1,  0) | N (-1,  0)
S ( 1,  0) | S ( 1,  0)
V ( 0, -1) | N (-1,  0) S ( 1,  0)
Ø ( 0,  1) | N (-1,  0) S ( 1,  0)
* */

/*  r,  c
N (-1,  0) - V ( 0, -1) Ø ( 0,  1)
S ( 1,  0) - V ( 0, -1) Ø ( 0,  1)
V ( 0, -1) - V ( 0, -1)
Ø ( 0,  1) - Ø ( 0,  1)
* */

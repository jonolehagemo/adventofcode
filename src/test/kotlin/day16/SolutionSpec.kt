package day16

import datastructures.Coordinate
import extensions.filePathToGrid
import extensions.println
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("Part 1, path") {
        val filePath = "Day16TestInput1.txt"
        forAll(
            row(Coordinate(0, 0), Coordinate(0, 1)),
            row(Coordinate(0, 1), Coordinate(0, 1)),
            row(Coordinate(1, 1), Coordinate(1, 0)),
            row(Coordinate(2, 1), Coordinate(1, 0)),
            row(Coordinate(3, 1), Coordinate(1, 0)),
            row(Coordinate(4, 1), Coordinate(1, 0)),
            row(Coordinate(5, 1), Coordinate(1, 0)),
            row(Coordinate(6, 1), Coordinate(1, 0)),
            row(Coordinate(7, 1), Coordinate(1, 0)),
            row(Coordinate(7, 0), Coordinate(0, -1)),
            row(Coordinate(7, 2), Coordinate(0, 1)),
            row(Coordinate(7, 3), Coordinate(0, 1)),
            row(Coordinate(7, 4), Coordinate(0, 1)),
            row(Coordinate(6, 4), Coordinate(-1, 0)),
            row(Coordinate(6, 5), Coordinate(0, 1)),
            row(Coordinate(6, 6), Coordinate(0, 1)),
            row(Coordinate(7, 6), Coordinate(1, 0)),
            row(Coordinate(8, 6), Coordinate(1, 0)),
            row(Coordinate(8, 5), Coordinate(0, -1)),
            row(Coordinate(8, 7), Coordinate(0, 1)),
            row(Coordinate(8, 4), Coordinate(0, -1)),
            row(Coordinate(7, 7), Coordinate(-1, 0)),
            row(Coordinate(9, 7), Coordinate(1, 0)),
            row(Coordinate(8, 3), Coordinate(0, -1)),
            row(Coordinate(6, 7), Coordinate(-1, 0)),
            row(Coordinate(8, 2), Coordinate(0, -1)),
            row(Coordinate(6, 6), Coordinate(0, -1)),
            row(Coordinate(8, 1), Coordinate(0, -1)),
            row(Coordinate(5, 6), Coordinate(-1, 0)),
            row(Coordinate(7, 1), Coordinate(-1, 0)),
            row(Coordinate(9, 1), Coordinate(1, 0)),
            row(Coordinate(4, 6), Coordinate(-1, 0)),
            row(Coordinate(3, 6), Coordinate(-1, 0)),
            row(Coordinate(2, 6), Coordinate(-1, 0)),
            row(Coordinate(2, 5), Coordinate(0, -1)),
            row(Coordinate(2, 7), Coordinate(0, 1)),
            row(Coordinate(1, 5), Coordinate(-1, 0)),
            row(Coordinate(3, 5), Coordinate(1, 0)),
            row(Coordinate(2, 8), Coordinate(0, 1)),
            row(Coordinate(0, 5), Coordinate(-1, 0)),
            row(Coordinate(4, 5), Coordinate(1, 0)),
            row(Coordinate(2, 9), Coordinate(0, 1)),
            row(Coordinate(0, 4), Coordinate(0, -1)),
            row(Coordinate(5, 5), Coordinate(1, 0)),
            row(Coordinate(0, 3), Coordinate(0, -1)),
            row(Coordinate(6, 5), Coordinate(1, 0)),
            row(Coordinate(0, 2), Coordinate(0, -1)),
            row(Coordinate(7, 5), Coordinate(1, 0)),
            row(Coordinate(0, 1), Coordinate(0, -1)),
            row(Coordinate(8, 5), Coordinate(1, 0)),
            row(Coordinate(9, 5), Coordinate(1, 0)),

            ) { coordinate, direction ->
            When("$coordinate -> $direction") {
                val result = filePath
                    .filePathToGrid('.')
                    .energized(Coordinate(0L, -1L), Coordinate(0L, 1L))

                Then("the coordinate ($coordinate) with direction ($direction) should be in the result") {
                    coordinate to direction shouldBeIn result
                }

            }
        }
    }

    Given("Part 1, energized") {
        val energized = "Day16TestInput1.txt"
            .filePathToGrid('.')
            .energized(Coordinate(0L, -1L), Coordinate(0L, 1L))
            .map { it.first }
            .toSet()

        val result = "Day16TestInput2.txt"
            .filePathToGrid('.')
            .nodes()
            .toSet()

        Then("intersect should be 0"){
            (energized - result).println()
            (result - energized).println()

            (energized intersect result).shouldBeNull()
        }
    }


    Given("Part 1, results") {
        forAll(
            row("Day16TestInput1.txt", 46),
//            row("Day16Input.txt", 7608),
        ) { filePath, expectedResult ->
            When("calculating result for $filePath") {
                val result = filePath
                    .filePathToGrid('.')
                    .energized(Coordinate(0L,-1L), Coordinate(0L, 1L))
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
            row("Day16TestInput1.txt", 51),
//            row("Day16Input.txt", 7608),
        ) { filePath, expectedResult ->
            When("calculating result for $filePath") {
                val grid = filePath.filePathToGrid('.')
                val startRows = grid.rowRange().toList().map { it.toInt() }
                    .flatMap {
                        listOf(
                            Coordinate(it.toLong(), -1L) to Coordinate(0L, 1L),
                            Coordinate(it.toLong(), grid.columnRange().last + 1L) to Coordinate(0L, -1L),
                        )
                    }

                val startColumns = grid.columnRange().toList().map { it.toInt() }
                    .flatMap {
                        listOf(
                            Coordinate(-1L, it.toLong()) to Coordinate(1L, 0L),
                            Coordinate(grid.rowRange().last + 1L, it.toLong()) to Coordinate(-1L, 0L),
                        )
                    }

                val result = startRows.plus(startColumns)
                    .maxOf { (start, direction) ->
                        grid.energized(start, direction)
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
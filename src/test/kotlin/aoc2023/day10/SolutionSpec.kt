package aoc2023.day10

import datastructures.Coordinate
import extensions.filePathToGrid
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("filenames of test input files") {
            forAll(
                row("aoc2023/Day10TestInput1.txt", 1, 1, 'S', 4),
                row("aoc2023/Day10TestInput2.txt", 2, 0, 'S', 8),
            ) { fileName, expectedY, expectedX, expectedTile, expectedLength ->
                When("input file name is $fileName") {
                    val inputGrid = fileName.filePathToGrid('.')
                    val start = inputGrid.findCoordinateByTile('S').first()
                    val grid = inputGrid.copy(start = start)
                    val cycle = dfs(Coordinate(-1, -1), start, grid, emptyList())

                    Then("y should be as expected y") {
                        start.row shouldBe expectedY
                    }

                    Then("x should be as expected x") {
                        start.column shouldBe expectedX
                    }

                    Then("pipe should be as expected pipe") {
                        grid.coordinateCharMap[start] shouldBe expectedTile
                    }

                    Then("cycle should be as expected cycle") {
                        cycle.size shouldBe expectedLength * 2
                    }
                }
            }
        }
    })

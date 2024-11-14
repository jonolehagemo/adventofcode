package aoc2023.day23

import datastructures.LongCoordinate
import extensions.filePathToStringList
import extensions.toLongGrid
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({

        Given("test input") {
            forAll(
                row("Task 1", "aoc2023/Day23TestInput1.txt".filePathToStringList(), 94),
                row("Task 2", "aoc2023/Day23TestInput1.txt".filePathToStringList().removeSlopes(), 154),
            ) { description, input, expected ->
                When(description) {
                    Then("steps should be $expected") {
                        // true shouldBe true
                        process(input) shouldBe expected
                    }
                }
            }
        }

        Given("a Grid with expected nodes") {
            val grid = "aoc2023/Day23TestInput1.txt".filePathToStringList().toLongGrid('#')
            val expectedNodes =
                setOf(
                    LongCoordinate(row = 0, column = 1),
                    LongCoordinate(row = 3, column = 11),
                    LongCoordinate(row = 5, column = 3),
                    LongCoordinate(row = 11, column = 21),
                    LongCoordinate(row = 13, column = 5),
                    LongCoordinate(row = 13, column = 13),
                    LongCoordinate(row = 19, column = 13),
                    LongCoordinate(row = 19, column = 19),
                    LongCoordinate(row = 22, column = 21),
                ).sortedBy { it.toString() }.toSet()
            val nodes = grid.nodes().sortedBy { it.toString() }.toSet()

            Then("nodes should be $expectedNodes") {
                nodes shouldBe expectedNodes
            }
        }

        Given("tile(), neighboursCount(), neighbours()") {
            val grid = "aoc2023/Day23TestInput1.txt".filePathToStringList().toLongGrid('#')
            forAll(
                row(LongCoordinate(1, 1), '.', 2, listOf(LongCoordinate(0, 1), LongCoordinate(1, 2))),
                row(LongCoordinate(1, 2), '.', 2, listOf(LongCoordinate(1, 3), LongCoordinate(1, 1))),
                row(LongCoordinate(4, 3), 'v', 2, listOf(LongCoordinate(5, 3))),
                row(LongCoordinate(5, 3), '.', 3, listOf(LongCoordinate(5, 4), LongCoordinate(6, 3))),
                row(LongCoordinate(13, 13), '.', 4, listOf(LongCoordinate(13, 14), LongCoordinate(14, 13))),
                row(LongCoordinate(0, 0), '#', 0, emptyList()),
                row(LongCoordinate(-1, -1), '#', 0, emptyList()),
            ) { coordinate, expectedTile, expectedCount, expectedList ->
                When(coordinate.toString()) {
                    val tile = grid.tile(coordinate)
                    val count = grid.neighboursCount(coordinate)
                    val neighbours = grid.neighbours(coordinate)

                    Then("tile should be '$expectedTile'") {
                        tile shouldBe expectedTile
                    }

                    Then("count should be '$expectedCount'") {
                        count shouldBe expectedCount
                    }

                    Then("neighbours should be ${expectedList.map { it.toString() }}") {
                        neighbours.map { it.toString() }.sorted() shouldBe expectedList.map { it.toString() }.sorted()
                    }
                }
            }
        }

        Given("a graph") {
            val grid = "aoc2023/Day23TestInput1.txt".filePathToStringList().toLongGrid('#')
            val graph = grid.toGraph()

            forAll(
                row(LongCoordinate(0, 1), LongCoordinate(5, 3), 15),
                row(LongCoordinate(5, 3), LongCoordinate(13, 5), 22),
                row(LongCoordinate(5, 3), LongCoordinate(3, 11), 22),
                row(LongCoordinate(19, 19), LongCoordinate(22, 21), 5),
                row(LongCoordinate(22, 21), LongCoordinate(22, 21), 0),
            ) { start, finish, expectedStepCount ->
                When("$start -> $finish : $expectedStepCount") {
                    val steps = graph.longestPath(start, finish, 0, emptySet())

                    Then("expectedStepCount should be as expected") {
                        steps shouldBe expectedStepCount
                    }
                }
            }
        }
    })

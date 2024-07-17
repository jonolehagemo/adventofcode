package day23

import datastructures.Coordinate
import extensions.filePathToStringList
import extensions.toGrid
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({

    Given("test input") {
        forAll(
            row("Task 1", "Day23TestInput1.txt".filePathToStringList(), 94),
            row("Task 2", "Day23TestInput1.txt".filePathToStringList().removeSlopes(), 154),
        ) { description, input, expected ->
            When(description) {
                Then("steps should be $expected") {
                    //true shouldBe true
                    process(input) shouldBe expected
                }
            }
        }
    }

    Given("a Grid with expected nodes") {
        val grid = "Day23TestInput1.txt".filePathToStringList().toGrid('#')
        val expectedNodes = setOf(
            Coordinate(row = 0, column = 1),
            Coordinate(row = 3, column = 11),
            Coordinate(row = 5, column = 3),
            Coordinate(row = 11, column = 21),
            Coordinate(row = 13, column = 5),
            Coordinate(row = 13, column = 13),
            Coordinate(row = 19, column = 13),
            Coordinate(row = 19, column = 19),
            Coordinate(row = 22, column = 21)
        ).sortedBy { it.toString() }.toSet()
        val nodes = grid.nodes().sortedBy { it.toString() }.toSet()

        Then("nodes should be $expectedNodes") {
            nodes shouldBe expectedNodes
        }
    }

    Given("tile(), neighboursCount(), neighbours()") {
        val grid = "Day23TestInput1.txt".filePathToStringList().toGrid('#')
        forAll(
            row(Coordinate(1, 1), '.', 2, listOf(Coordinate(0, 1), Coordinate(1, 2))),
            row(Coordinate(1, 2), '.', 2, listOf(Coordinate(1, 3), Coordinate(1, 1))),
            row(Coordinate(4, 3), 'v', 2, listOf(Coordinate(5, 3))),
            row(Coordinate(5, 3), '.', 3, listOf(Coordinate(5, 4), Coordinate(6, 3))),
            row(Coordinate(13, 13), '.', 4, listOf(Coordinate(13, 14), Coordinate(14, 13))),
            row(Coordinate(0, 0), '#', 0, emptyList()),
            row(Coordinate(-1, -1), '#', 0, emptyList()),
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
        val grid = "Day23TestInput1.txt".filePathToStringList().toGrid('#')
        val graph = grid.toGraph()

        forAll(
            row(Coordinate(0, 1), Coordinate(5, 3), 15),
            row(Coordinate(5, 3), Coordinate(13, 5), 22),
            row(Coordinate(5, 3), Coordinate(3, 11), 22),
            row(Coordinate(19, 19), Coordinate(22, 21), 5),
            row(Coordinate(22, 21), Coordinate(22, 21), 0),
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

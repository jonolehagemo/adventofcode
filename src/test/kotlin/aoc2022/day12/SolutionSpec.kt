package aoc2022.day12

import datastructures.Graph
import extensions.filePathToStringList
import extensions.toGrid
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("task 1, Shortest path from S to E") {
            forAll(
                row("aoc2022/Day12TestInput1.txt", 31),
//                row("aoc2022/Day12Input.txt", 425),
            ) { filepath, expected ->
                When("$filepath $expected") {
                    val inputGrid = filepath.filePathToStringList().toGrid(' ')
                    val start = inputGrid.findCoordinateByTile('S').first()
                    val finish = inputGrid.findCoordinateByTile('E').first()
                    val grid = inputGrid.plus(start to 'a').plus(finish to 'z')
                    val result =
                        grid
                            .toGraph()
                            .shortestPathDijkstra(start)
                            .getOrDefault(finish, Graph.DijkstraLookup(distance = 0))
                            .distance

                    Then("$result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

        Given("task 2, Shortest path from E to any a") {
            forAll(
                row("aoc2022/Day12TestInput1.txt", 29),
//                row("aoc2022/Day12Input.txt", 418),
            ) { filepath, expected ->
                When("$filepath $expected") {
                    val inputGrid = filepath.filePathToStringList().toGrid(' ')
                    val start = inputGrid.findCoordinateByTile('S').first()
                    val finish = inputGrid.findCoordinateByTile('E').first()
                    val grid = inputGrid.plus(start to 'a').plus(finish to 'z')
                    val result =
                        grid
                            .toGraph()
                            .reverseEdges()
                            .shortestPathDijkstra(finish)
                            .filter { lookup -> grid.tile(lookup.key) == 'a' }
                            .minBy { it.value.distance }
                            .value.distance

                    Then("$result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }
    })

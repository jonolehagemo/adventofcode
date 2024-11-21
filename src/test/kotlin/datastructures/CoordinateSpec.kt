package datastructures

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class CoordinateSpec :
    BehaviorSpec({
        Given("a coordinate") {
            val c1 = Coordinate(row = 0, column = 0)

            When("finding shortest path to another coordinate") {
                val shortestPath = c1.manhattanDistance(Coordinate(row = 3, column = 3))
                val expectedResult = 6

                Then("expanded galaxy should be equal content of test input file #2") {
                    shortestPath shouldBe expectedResult
                }
            }
        }

        Given("two coordinates") {
            forAll(
                row(
                    Coordinate.ORIGIN,
                    Coordinate(0, 2),
                    listOf(
                        Coordinate(0, 0),
                        Coordinate(0, 1),
                        Coordinate(0, 2),
                    ),
                ),
                row(
                    Coordinate(0, -1),
                    Coordinate(0, 1),
                    listOf(
                        Coordinate(0, -1),
                        Coordinate(0, 0),
                        Coordinate(0, 1),
                    ),
                ),
                row(
                    Coordinate.ORIGIN,
                    Coordinate(2, 0),
                    listOf(
                        Coordinate(0, 0),
                        Coordinate(1, 0),
                        Coordinate(2, 0),
                    ),
                ),
            ) { a, b, expected ->
                When("$a -> $b => $expected") {
                    val result = a.toList(b)

                    Then("$result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }
    })

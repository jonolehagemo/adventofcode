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

        Given("a direction") {
            forAll(
                row("north", Coordinate.ORIGIN.north(), "west", Coordinate.ORIGIN.west()),
                row("south", Coordinate.ORIGIN.south(), "east", Coordinate.ORIGIN.east()),
                row("east", Coordinate.ORIGIN.east(), "north", Coordinate.ORIGIN.north()),
                row("west", Coordinate.ORIGIN.west(), "south", Coordinate.ORIGIN.south()),
            ){ directionDescription, direction, expectedDescription, expected ->
                Then("going $directionDescription and turning left should result in $expectedDescription") {
                    direction.turnLeft() shouldBe expected
                }
            }

            forAll(
                row("north", Coordinate.ORIGIN.north(), "east", Coordinate.ORIGIN.east()),
                row("south", Coordinate.ORIGIN.south(), "west", Coordinate.ORIGIN.west()),
                row("east", Coordinate.ORIGIN.east(), "south", Coordinate.ORIGIN.south()),
                row("west", Coordinate.ORIGIN.west(), "north", Coordinate.ORIGIN.north()),
            ){ directionDescription, direction, expectedDescription, expected ->
                Then("going $directionDescription and turning right should result in $expectedDescription") {
                    direction.turnRight() shouldBe expected
                }
            }

            forAll(
                row("north", Coordinate.ORIGIN.north(), "south", Coordinate.ORIGIN.south()),
                row("south", Coordinate.ORIGIN.south(), "north", Coordinate.ORIGIN.north()),
                row("east", Coordinate.ORIGIN.east(), "west", Coordinate.ORIGIN.west()),
                row("west", Coordinate.ORIGIN.west(), "east", Coordinate.ORIGIN.east()),
            ){ directionDescription, direction, expectedDescription, expected ->
                Then("going $directionDescription and turning to the opposite direction should result in $expectedDescription") {
                    direction.oppositeDirection() shouldBe expected
                }
            }
        }


    })

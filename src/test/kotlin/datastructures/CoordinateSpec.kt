package datastructures

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class CoordinateSpec :
    BehaviorSpec({
        Given("a coordinate") {
            val c1 = LongCoordinate(row = 0, column = 0)

            When("finding shortest path to another coordinate") {
                val shortestPath = c1.shortestPath(LongCoordinate(row = 3, column = 3))
                val expectedResult = 6

                Then("expanded galaxy should be equal content of test input file #2") {
                    shortestPath shouldBe expectedResult
                }
            }
        }
    })

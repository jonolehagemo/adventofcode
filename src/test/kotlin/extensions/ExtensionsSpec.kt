package extensions

import datastructures.Coordinate
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class ExtensionsSpec : BehaviorSpec({

    Given("a list of strings as test input") {
        val testInput = listOf(
            "#.###",
            "#...#",
            "###.#",
        )
        When("using List<String>.toGrid()") {
            val grid = testInput.toGrid('#')
            println(grid)
            Then("result should be expected") {
                //true shouldBe true
                grid.start shouldBe Coordinate(row = 0, column = 1)
                grid.finish shouldBe Coordinate(row = 2, column = 3)
            }
        }
    }
})

package day11

import extensions.filePathToGrid
import extensions.filePathToStringList
import extensions.toGrid
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("filename of test input file #1 converted to Grid") {
        val testInput1 = "Day11TestInput1.txt".filePathToStringList()
        val rowsToExpand = testInput1.rowsToExpand()
        val columnsToExpand = testInput1.columnsToExpand()
        val grid = testInput1.toGrid('.')

        When("expanding galaxy") {
            val expandedGalaxy = grid.expand(2, rowsToExpand, columnsToExpand)
            val expectedResult = "Day11TestInput2.txt".filePathToGrid('.')

            Then("expanded galaxy should be equal content of test input file #2") {
                expandedGalaxy.nodes().zip(expectedResult.nodes()) { a, b -> a shouldBe b }
            }
        }
    }

    Given("a list of strings") {
        val testInput = listOf("123", "456", "789")

        When("rotating left") {
            val rotated = testInput.rotateLeft()
            val expectedResult = listOf("369", "258", "147")

            Then("the rotated result should be as expected") {
                rotated shouldBe expectedResult
            }
        }
    }
})
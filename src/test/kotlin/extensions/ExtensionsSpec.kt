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

            Then("result should be expected") {
                grid.start shouldBe Coordinate(row = 0, column = 1)
                grid.finish shouldBe Coordinate(row = 2, column = 3)
            }
        }
    }

    Given("a file path to a test input file") {
        val filePath = "extensions/ListOfStringList.txt"

        When("using String.filePathToListOfStringList()") {
            val listOfList = filePath.filePathToListOfStringList()

            Then("result should be expected") {
                //true shouldBe true
                listOfList.size shouldBe 3
                listOfList.first().first() shouldBe "list1"
                listOfList.first().last() shouldBe "list2"
                listOfList.last().first() shouldBe "list5"
                listOfList.last().last() shouldBe "list6"
            }
        }
    }

    Given("a list of strings") {

        When("using List<String>.rotateLeft()") {
            val result = listOf("123", "456", "789").rotateLeft()

            Then("result should be expected") {
                result shouldBe listOf("369", "258", "147")
            }
        }

        When("using List<String>.rotateRight()") {
            val result = listOf("123", "456", "789").rotateRight()

            Then("result should be expected") {
                result shouldBe listOf("741", "852", "963")
            }
        }
    }

})

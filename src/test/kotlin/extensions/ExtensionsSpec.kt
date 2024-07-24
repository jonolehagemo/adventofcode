package extensions

import datastructures.Coordinate
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
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
                listOfList.size shouldBe 3
                listOfList.first().first() shouldBe "list1"
                listOfList.first().last() shouldBe "list2"
                listOfList.last().first() shouldBe "list5"
                listOfList.last().last() shouldBe "list6"
            }
        }
    }

    Given("a list of strings") {
        forAll(
            row("123456789", "369258147", "741852963"),
            row("123456789ABCDEFG", "48CG37BF26AE159D", "D951EA62FB73GC84"),
        ) { input, expectedRotatedLeft, expectedRotatedRight ->
            When("input is $input") {
                Then("using List<String>.rotateLeft() with should be $expectedRotatedLeft") {
                    input.toChunkedStringList().rotateLeft().toTextString("") shouldBe expectedRotatedLeft
                }

                Then("using List<String>.rotateRight() with should be $expectedRotatedRight") {
                    input.toChunkedStringList().rotateRight().toTextString("") shouldBe expectedRotatedRight
                }
            }

        }
    }
})

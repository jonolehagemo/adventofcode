package aoc2023.day20


import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("Part 1, results") {
        forAll(
            row("aoc2023/Day20TestInput1.txt", 0, 0),
            row("aoc2023/Day20TestInput1.txt", 1, 32),
            row("aoc2023/Day20TestInput1.txt", 1_000, 32_000_000),
            row("aoc2023/Day20TestInput2.txt", 1_000, 11687500),
            row("aoc2023/Day20Input.txt", 1_000, 898731036),
        ) { filePath: String, starts: Int, expectedResult: Int ->
            When("pushing button on $filePath $starts times") {
                val result = filePath.filePathToStringList()
                    .process1(starts)


                Then("the result ($result) should be as expected ($expectedResult)") {
                    result shouldBe expectedResult
                }
            }
        }
    }

    Given("Part 2, find origin") {
        forAll(
            row("aoc2023/Day20TestInput2.txt", "output", 1, listOf("con")),
            row("aoc2023/Day20TestInput2.txt", "output", 2, listOf("a", "b")),
        ) { filePath, destination, steps, expectedResult ->
            When("finding origin for $destination $steps steps out") {
                val relations = filePath.filePathToStringList().toRelations()
                val result = findOrigin(destination, steps, relations)

                Then("the result ($result) should be as expected ($expectedResult)") {
                    result shouldBe expectedResult
                }
            }
        }
    }

    Given("Part 2, results") {
        forAll(
            row("aoc2023/Day20Input.txt", "rx", 229414480926893),
        ) { filePath, destination, expectedResult ->
            When(filePath) {
                val result = filePath.filePathToStringList()
                    .process2(destination)


                Then("the result ($result) should be as expected ($expectedResult)") {
                    result shouldBe expectedResult
                }
            }
        }
    }
})
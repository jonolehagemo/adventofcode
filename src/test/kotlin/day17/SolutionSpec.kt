package day17

import extensions.filePathToGrid
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("Part 1, results") {
        forAll(
            row("Day17TestInput1.txt", 102),
//            row("Day17Input.txt", 970),
        ) { filePath, expectedResult ->
            When("calculating result for $filePath") {
                val result = filePath
                    .filePathToGrid('9')
                    .minimize(0, 3)

                Then("the result ($result) should be as expected ($expectedResult)") {
                    result shouldBe expectedResult
                }
            }
        }
    }

    Given("Part 2, results") {
        forAll(
            row("Day17TestInput1.txt", 94),
            row("Day17Input.txt", 1149),
        ) { filePath, expectedResult ->
            When("calculating result for $filePath") {
                val result = filePath
                    .filePathToGrid('9')
                    .minimize(4, 10)

                Then("the result ($result) should be as expected ($expectedResult)") {
                    result shouldBe expectedResult
                }
            }
        }
    }
})
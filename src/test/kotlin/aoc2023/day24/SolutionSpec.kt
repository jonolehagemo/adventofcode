package aoc2023.day24

import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("Part 1, results") {
        forAll(
            row("aoc2023/Day24TestInput1.txt", 7.0, 27.0, 2),
//            row("aoc2023/Day24Input.txt", 200_000_000_000_000.0, 400_000_000_000_000.0, 11246),
        ) { filePath, lowerBoundary, upperBoundary, expectedResult ->
            When("calculating result for $filePath") {
                val result = filePath
                    .filePathToStringList()
                    .toHailstones()
                    .countCollisions(lowerBoundary..upperBoundary)

                Then("the result ($result) should be as expected ($expectedResult)") {
                    result shouldBe expectedResult
                }
            }
        }
    }

})
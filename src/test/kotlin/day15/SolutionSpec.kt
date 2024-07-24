package day15

import extensions.filePathToString
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("Part 1, elfHash") {
        forAll(
            row("rn=1", 30),
            row("cm-", 253),
            row("qp=3", 97),
            row("cm=2", 47),
            row("qp-", 14),
            row("pc=4", 180),
            row("ot=9", 9),
            row("ab=5", 197),
            row("pc-", 48),
            row("pc=6", 214),
            row("ot=7", 231),
        ) { string, expectedResult ->
            When("calculating result for $string") {
                val result = string.elfHash()

                Then("the result ($result) should be as expected ($expectedResult)") {
                    result shouldBe expectedResult
                }

            }
        }
    }

    Given("Part 1, results") {
        forAll(
            row("Day15TestInput1.txt", 1320),
            row("Day15Input.txt", 510801),
        ) { filePath, expectedResult ->
            When("calculating result for $filePath") {
                val result = filePath.filePathToString().split(',').sumOf { it.elfHash() }

                Then("the result ($result) should be as expected ($expectedResult)") {
                    result shouldBe expectedResult
                }

            }
        }
    }

    Given("Part 2, calculate") {
        forAll(
            row("Day15TestInput1.txt", 145),
            row("Day15Input.txt", 212763),
        ) { filePath, expectedResult ->
            When("calculating result for $filePath") {
                val result = filePath.filePathToString().split(',').calculate()

                Then("the result ($result) should be as expected ($expectedResult)") {
                    result shouldBe expectedResult
                }
            }
        }
    }
})


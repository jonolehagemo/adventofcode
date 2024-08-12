package aoc2023.day14

import extensions.filePathToStringList
import extensions.toTextString
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("Part 1, rolls") {
        val input = "aoc2023/Day14TestInput1.txt".filePathToStringList()

        Then("the rolled should be as expected") {
            input.rollNorth() shouldBe "aoc2023/Day14TestInput1Rolled.txt".filePathToStringList()
        }
    }

    Given("Part 1, results") {
        forAll(
            row("aoc2023/Day14TestInput1.txt", 136),
            row("aoc2023/Day14Input.txt", 107053),
        ) { filePath, expectedResult ->
            When("calculating result for $filePath") {
                val result = filePath.filePathToStringList().rollNorth().sum()

                Then("the result ($result) should be as expected ($expectedResult)") {
                    result shouldBe expectedResult
                }

            }
        }
    }

    Given("Part 2, cycles") {
        val input = "aoc2023/Day14TestInput1.txt".filePathToStringList()

        When("doing cycles") {
            val result1 = input.cycle()
            val result2 = result1.cycle()
            val result3 = result2.cycle()

            Then("the result should be as expected 1") {
                result1.toTextString() shouldBe "aoc2023/Day14TestInput2.txt".filePathToStringList().toTextString()
            }

            Then("the result should be as expected 2") {
                result2.toTextString() shouldBe "aoc2023/Day14TestInput3.txt".filePathToStringList().toTextString()
            }

            Then("the result should be as expected 3") {
                result3.toTextString() shouldBe "aoc2023/Day14TestInput4.txt".filePathToStringList().toTextString()
            }
        }
    }

    Given("Part 2, results") {
        val cycles = 1_000_000_000L

        forAll(
            row("aoc2023/Day14TestInput1.txt", 64),
            row("aoc2023/Day14Input.txt", 88371),
        ) { filePath, expectedResult ->

            When("calculating the $cycles'th cycle for $filePath") {
                val cycleN = filePath.filePathToStringList().findCycle(cycles)
                val result = cycleN.sum()

                Then("the result ($result) should be as expected ($expectedResult)") {
                    result shouldBe expectedResult
                }

            }
        }
    }
})
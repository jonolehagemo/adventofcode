package day19

import extensions.filePathToStringList
import extensions.println
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import kotlin.math.pow

class SolutionSpec  : BehaviorSpec({
    Given("Part 1, results") {
        forAll(
            row("Day19TestInput1.txt", 19114),
            row("Day19Input.txt", 432434),
        ) { filePath, expectedResult ->
            When("calculating result for $filePath") {
                val input = filePath.filePathToStringList()
                val workFlows = input.toWorkFlows()
                val parts = input.toParts()

                val result = parts
                    .filter { isAccepted(it, workFlows) }
                    .sumOf { part -> part.values.sumOf { it.toLong() } }

                Then("the result ($result) should be as expected ($expectedResult)") {
                    result shouldBe expectedResult
                }
            }
        }
    }

    Given("Part 2, results") {
        forAll(                 //           256000000000000
            row("Day19TestInput1.txt", 167409079868000L),
            row("Day19Input.txt", 132557544578569L),
        ) { filePath, expectedResult ->
            When("calculating result for $filePath") {
                val input = filePath.filePathToStringList()
                val workFlows = input.toWorkFlows()
                val partRange = mapOf(
                    'x' to Pair(1, 4000), 'm' to Pair(1, 4000), 'a' to Pair(1, 4000), 's' to Pair(1, 4000)
                )
                val result = count(partRange, workFlows)

                Then("the result ($result) should be as expected ($expectedResult)") {
                    result shouldBe expectedResult
                }
            }
        }
    }
})
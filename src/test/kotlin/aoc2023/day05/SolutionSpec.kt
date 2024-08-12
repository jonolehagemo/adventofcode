package aoc2023.day05

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("a file path to test input") {
        val filePath = "aoc2023/Day05TestInput1.txt"

        When("getting seeds") {
            val seeds = getSeeds(filePath)

            Then("result should be as expected") {
                seeds shouldBe listOf(79L, 14L, 55L, 13L)
            }
        }

        When("getting mappings") {
            val mappings = getMappings(filePath)

            Then("first mapping should be as expected") {
                mappings.first() shouldBe listOf(
                    RangeMapping(destinationStart = 50, sourceStart = 98L, rangeLength = 2L),
                    RangeMapping(destinationStart = 52, sourceStart = 50L, rangeLength = 48L)
                )
            }

            Then("last mapping should be as expected") {
                mappings.last() shouldBe listOf(
                    RangeMapping(destinationStart = 60, sourceStart = 56L, rangeLength = 37L),
                    RangeMapping(destinationStart = 56, sourceStart = 93L, rangeLength = 4L)
                )
            }

        }
    }
})

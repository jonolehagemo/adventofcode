package day05

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("a file path to test input") {
        val filePath = "Day05TestInput1.txt"

        When("getting seeds") {
            val seeds = getSeeds(filePath)
            println(seeds)

            Then("result should be as expected") {
                seeds shouldBe listOf(79L, 14L, 55L, 13L)
            }
        }

        When("getting mappings") {
            val mappings = getMappings(filePath)
            println(mappings)

            Then("first mapping should be as expected") {
                mappings.first() shouldBe listOf(RangeOffset(offset=-48, range=98L..100L), RangeOffset(offset=2, range=50L..98L))
            }

            Then("last mapping should be as expected") {
                mappings.last() shouldBe listOf(RangeOffset(offset=4, range=56L..93L), RangeOffset(offset=-37, range=93L..97L))
            }

        }


    }
})

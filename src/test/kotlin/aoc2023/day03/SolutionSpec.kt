package aoc2023.day03

import extensions.filePathToGrid
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("test input") {
        val parts = "aoc2023/Day03TestInput1.txt".filePathToGrid('.').getParts()

        Then("task1 should be 4361") {
            parts.sumOf { it.partNumber } shouldBe 4361
        }

        Then("task2 should be 467835") {
            parts.sumGearRatios() shouldBe 467835
        }
    }
})
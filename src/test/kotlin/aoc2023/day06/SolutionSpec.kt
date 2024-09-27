package aoc2023.day06

import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("process1 of test input") {
        val input = "aoc2023/Day06TestInput1.txt".filePathToStringList().toBoatRaceWins()
        forAll(
            row(0, 4),
            row(1, 8),
            row(2, 9),
        ) { expectedIndex, expected ->
            When("expectedIndex = $expectedIndex") {
                val filteredInput = input.filterIndexed { index, _ -> expectedIndex == index }
                Then("result should be $expected") {
                    process(filteredInput) shouldBe expected
                }
            }
        }
    }
})
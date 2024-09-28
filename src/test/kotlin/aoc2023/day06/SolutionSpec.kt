package aoc2023.day06

import extensions.filePathToStringList
import extensions.removeSpaces
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("process1 of test input") {
        val sut = "aoc2023/Day06TestInput1.txt".filePathToStringList()
        forAll(
            row("Solving task 1", sut, 288),
            row("Solving task 2", sut.map { it.removeSpaces() }, 71503),
        ) { description, input, expected ->
            When("$description => $expected") {
                val result = input.toBoatRaceWins()
                Then("result should be $expected") {
                    result shouldBe expected
                }
            }
        }
    }
})
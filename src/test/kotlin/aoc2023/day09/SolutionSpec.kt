package aoc2023.day09

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("processing of test input") {
        val input = getInput("aoc2023/Day09TestInput1.txt")
        val next = input.sumOf { it.extrapolateNext() }
        val previous = input.sumOf { it.reversed().extrapolateNext() }
        Then("result should be as expected") {
            next shouldBe 114
            previous shouldBe 2
        }
    }

    Given("extrapolating of test input") {
        forAll(
            row(listOf(0, 3, 6, 9, 12, 15), -3 to 18),
            row(listOf(1, 3, 6, 10, 15, 21), 0 to 28),
            row(listOf(10, 13, 16, 21, 30, 45), 5 to 68),
        ) { history, expected ->
            Then(history.toString()) {
                history.extrapolateNext() shouldBe expected.second
                history.reversed().extrapolateNext() shouldBe expected.first
            }
        }
    }
})

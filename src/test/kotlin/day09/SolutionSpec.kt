package day09

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("processing of test input") {
        val result = process("Day09TestInput1.txt")
        Then("result should be as expected") {
             result shouldBe (2 to 114)
        }
    }

    Given("extrapolating of test input") {
        forAll(
            row(listOf(0, 3, 6, 9, 12, 15), -3 to 18),
            row(listOf(1, 3, 6, 10, 15, 21), 0 to 28),
            row(listOf(10, 13, 16, 21, 30, 45), 5 to 68),
        ) { history, expected ->
            Then(history.toString()){
                extrapolate(history) shouldBe expected
            }
        }
    }
})

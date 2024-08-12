package math

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class FormulasSpec : BehaviorSpec({
    Given("input for finding greatest common divisor, gcd(a, b)") {
        forAll(
            row(-1L, 5L, 0L),
            row(0L, 5L, 0L),
            row(6L, 0L, 6L),
            row(2L * 3L, 3L * 5L, 3L),
            row(2L * 3L * 5L, 2L * 2L * 3L * 7L, 2L * 3L),
        ) { a, b, expectedResult ->
            When("a = $a and b = $b") {
                val result = gcd(a, b)

                Then("the result ($result) should be as expected ($expectedResult)") {
                    result shouldBe expectedResult
                }
            }
        }
    }

    Given("input for finding least common multiple, lcm(a, b)") {
        forAll(
            row(-1L, 6L, 0L),
            row(0L, 6L, 0L),
            row(6L, -1L, 0L),
            row(6L, 0L, 0L),
            row(6L, 18L, 18L),
            row(2L * 3L, 3L * 5L, 2L * 3L * 5L),
            row(2L * 3L * 5L, 2L * 2L * 3L * 7L, 2L * 2L * 3L * 5L * 7L),

            ) { a, b, expectedResult ->
            When("a = $a and b = $b") {
                val result = lcm(a, b)

                Then("the result ($result) should be as expected ($expectedResult)") {
                    result shouldBe expectedResult
                }
            }
        }
    }

})
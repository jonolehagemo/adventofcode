package aoc2023.day02

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("process1 of test input") {
        forAll(
            row("aoc2023/Day02TestInput1.txt", 8),
        ) { filepath, expected ->
            Then(filepath) {
                process1(getInput(filepath)) shouldBe expected
            }
        }
    }

    Given("process2 of test input") {
        forAll(
            row("aoc2023/Day02TestInput1.txt", 2286),
        ) { filepath, expected ->
            Then(filepath) {
                process2(getInput(filepath)) shouldBe expected
            }
        }
    }

})

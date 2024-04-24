package day03

import datastructures.filePathAsGrid
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("test input") {
        val grid = "Day03TestInput1.txt".filePathAsGrid('.')
        val numbers = grid.getNumbers()

        Then("task1() should be 4361") {
            task1(numbers) shouldBe 4361
        }

        Then("task2() should be 467835") {
            task2(numbers) shouldBe 467835
        }
    }
})
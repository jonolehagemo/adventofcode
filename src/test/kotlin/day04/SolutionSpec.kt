package day04

import datastructures.filePathAsStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("test input") {
        val cards = "Day04TestInput1.txt".filePathAsStringList().toWinningNumbersCount()

        Then("task1() should be 13") {
            task1(cards) shouldBe 13
        }

        Then("task2() should be 30") {
            task2(cards) shouldBe 30
        }
    }
})
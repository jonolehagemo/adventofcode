package aoc2023.day04

import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlin.math.pow

class SolutionSpec :
    BehaviorSpec({
        Given("test input") {
            val wins = "aoc2023/Day04TestInput1.txt".filePathToStringList().toWinningNumbersCount()

            Then("task1() should be 13") {
                wins.sumOf { 2.0.pow(it - 1).toInt() } shouldBe 13
            }

            Then("task2() should be 30") {
                wins.sumCopies() shouldBe 30
            }
        }
    })

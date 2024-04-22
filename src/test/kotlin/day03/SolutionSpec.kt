package day03

import datastructures.filePathAsGrid
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("test input") {
        forAll(
            row("Day03TestInput1.txt", 4361, 467835),
        ) { filepath, expected1, expected2 ->
            When(filepath) {
                val grid = filepath.filePathAsGrid('.')
                val numbers = grid.getNumbers()

                Then("sum should be $expected1") {
                    numbers.sumOf { it.third } shouldBe expected1
                }

                Then("sum should be $expected2") {
                    val temp = numbers
                        .filter { it.second == '*' }
                        .groupBy { it.first }
                        .filter { entry -> entry.value.size == 2 }
                        .mapValues { entry ->  entry.value.map { it.third }.fold(1){item, sum -> sum * item} }
                        .onEach { println(it) }
                        .map { it.value }
                        .sum()
                    temp shouldBe expected2
                }
            }
        }
    }


})
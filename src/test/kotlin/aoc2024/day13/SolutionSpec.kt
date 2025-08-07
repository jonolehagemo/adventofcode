package aoc2024.day13

import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("Tasks") {
            forAll(
                row("aoc2024/Day13TestInput1.txt", 480L, 875318608908L),
                row("aoc2024/Day13Input.txt", 35997L, 82510994362072L),
            ) { filepath, expected1, expected2 ->
                When(filepath) {
                    val input = filepath
                        .filePathToStringList()
                        .chunked(4)
                        .map { ClawMachine.of(it) }

                    val result1 = input.sumOf { it.solve() }
                    val result2 = input.map { it.scale(10000000000000L) }
                        .sumOf { it.solve() }

                    Then("Task 1, $result1 should be $expected1") {
                        result1 shouldBe expected1
                    }

                    Then("Task 2, $result2 should be $expected2") {
                        result2 shouldBe expected2
                    }
                }
            }
        }
    })

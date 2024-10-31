package aoc2022.day11

import extensions.filePathToListOfStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("task 1, cycles()") {
            forAll(
                row("aoc2022/Day11TestInput1.txt", 4),
            ) { filepath, expected ->
                When("$filepath $expected") {
                    val result =
                        filepath.filePathToListOfStringList() // .toMonkeys()
                    Then("$result should be $expected") {
                        result.size shouldBe expected
                    }
                }
            }
        }
    })

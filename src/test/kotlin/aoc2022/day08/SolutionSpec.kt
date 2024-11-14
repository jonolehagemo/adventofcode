package aoc2022.day08

import extensions.filePathToStringList
import extensions.toLongGrid
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("counting visible trees, task 1") {
            forAll(
                row("aoc2022/Day08TestInput1.txt", 21),
//                row("aoc2022/Day08Input.txt", 1854),
            ) { filePath, expectedSize ->
                When("$filePath -[size]-> $expectedSize") {
                    val input =
                        filePath.filePathToStringList().toLongGrid('0')
                    val result =
                        input.countVisibleTrees()

                    Then("$result -> $expectedSize") {
                        result shouldBe expectedSize
                    }
                }
            }
        }

        Given("counting visible trees, task 2") {
            forAll(
                row("aoc2022/Day08TestInput1.txt", 8),
//                row("aoc2022/Day08Input.txt", 527340),
            ) { filePath, expectedSize ->
                When("$filePath -[size]-> $expectedSize") {
                    val input =
                        filePath.filePathToStringList().toLongGrid('0')
                    val result =
                        input.maxScenicView()

                    Then("$result -> $expectedSize") {
                        result shouldBe expectedSize
                    }
                }
            }
        }
    })

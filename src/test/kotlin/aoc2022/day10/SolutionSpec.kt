package aoc2022.day10

import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("task 1, cycles()") {
            forAll(
                row("aoc2022/Day10TestInput1.txt", listOf(1, 1, 1, 4, 4, -1)),
            ) { filepath, expected ->
                When("$filepath $expected") {
                    val input = filepath.filePathToStringList()
                    val result = input.signalStrengths()
                    Then("$result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

        Given("task 1, signal strengths") {
            forAll(
                row("aoc2022/Day10TestInput2.txt", 13140),
            ) { filepath, expected ->
                When("$filepath $expected") {
                    val input = filepath.filePathToStringList()
                    val result =
                        input
                            .signalStrengths()
                            .withIndex()
                            .filter { (index, _) -> ((index + 21) % 40) == 0 }
                            .sumOf { (index, value) -> (index + 1) * value }

                    Then("$result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

        Given("task 2, to image") {
            forAll(
                row(
                    "aoc2022/Day10TestInput2.txt",
                    "##..##..##..##..##..##..##..##..##..##..\n###...###...###...###...###...###...###.\n####....####....####....####....####....\n#####.....#####.....#####.....#####.....\n######......######......######......####\n#######.......#######.......#######.....",
                ),
            ) { filepath, expected ->
                When("$filepath $expected") {
                    val input = filepath.filePathToStringList()
                    val result =
                        input
                            .signalStrengths()
                            .toImage()

                    Then("$result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }
    })

package aoc2022.day11

import extensions.filePathToListOfStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import java.math.BigInteger

class SolutionSpec :
    BehaviorSpec({
        Given("task 1, to Monkeys") {
            forAll(
                row("aoc2022/Day11TestInput1.txt", 4),
            ) { filepath, expected ->
                When("$filepath $expected") {
                    val result =
                        filepath.filePathToListOfStringList().toMonkeyMap()
                    Then("$result should be $expected") {
                        result.size shouldBe expected
                    }
                }
            }
        }

        Given("task 1, 20 rounds") {
            forAll(
                row("aoc2022/Day11TestInput1.txt", 10605),
                row("aoc2022/Day11Input.txt", 72884),
            ) { filepath, expected ->
                When("$filepath $expected") {
                    val result =
                        filepath.filePathToListOfStringList().toMonkeyMap().rounds(20, BigInteger.valueOf(3L))
                    Then("$result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

        Given("task 2, 10000 rounds") {
            forAll(
                row("aoc2022/Day11TestInput1.txt", 2713310158),
//                row("aoc2022/Day11Input.txt", 72884),
            ) { filepath, expected ->
                When("$filepath $expected") {
                    val result =
                        filepath.filePathToListOfStringList().toMonkeyMap().rounds(10000, BigInteger.ONE)
                    Then("$result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }
    })

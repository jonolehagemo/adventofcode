package aoc2024.day07

import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("a Calculator") {
            forAll(
                row(listOf(10L, 19L), listOf(29L, 190L), 190L, true),
                row(listOf(81L, 40L, 27L), listOf(148L, 3267L, 3267L, 87480L), 3267L, true),
                row(listOf(17L, 8L, 14L), listOf(39L, 350L, 150L, 1904L), 192, false),
            ) { list, expectedCalculations, target, isInTarget ->
                When("input is $list and operators are +, *") {
                    val calculations =
                        calculate(list.first(), list.drop(1), listOf(add, multiply))

                    Then("$calculations should be $expectedCalculations") {
                        calculations shouldBe expectedCalculations
                    }

                    val isTargetInCalculations = (target in calculations)

                    Then("target should be expected") {
                        isTargetInCalculations shouldBe isInTarget
                    }
                }
            }

            forAll(
                row(listOf(10L, 19L), listOf(29L, 190L, 1019L), 190L, true),
                row(listOf(81L, 40L, 27L), listOf(148L, 3267L, 12127L, 3267L, 87480L, 324027L, 8167L, 219780L, 814027L), 3267L, true),
                row(listOf(17L, 8L, 14L), listOf(39L, 350L, 2514L, 150L, 1904L, 13614L, 192L, 2492L, 17814L), 192, false),
            ) { list, expectedCalculations, target, isInTarget ->
                When("input is $list and operators are +, *, ||") {
                    val calculations = calculate(list.first(), list.drop(1), listOf(add, multiply, concat))

                    Then("$calculations should be $expectedCalculations") {
                        calculations shouldBe expectedCalculations
                    }

                    val isTargetInCalculations = (target in calculations)

                    Then("$target should be in ${calculations.sorted()}") {
                        isTargetInCalculations shouldBe true
                    }
                }
            }
        }

        Given("task 1") {
            forAll(
                row("aoc2024/Day07TestInput1.txt", 3749L),
                row("aoc2024/Day07Input.txt", 1620690235709L),
            ) { filepath, expected ->
                When(filepath) {
                    val input =
                        filepath
                            .filePathToStringList()
                            .map {
                                it.substringBefore(':').toLong() to
                                    it.substringAfter(": ").split(' ').map { it.toLong() }
                            }
                    val result =
                        input
                            .map { it.first to calculate(it.second.first(), it.second.drop(1), listOf(add, multiply)) }
                            .filter { it.first in it.second }
                            .sumOf { it.first }

                    Then("Task 1: $result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

        Given("task 2") {
            forAll(
                row("aoc2024/Day07TestInput1.txt", 11387L),
                row("aoc2024/Day07Input.txt", 145397611075341L),
            ) { filepath, expected ->
                When(filepath) {
                    val input =
                        filepath
                            .filePathToStringList()
                            .map {
                                it.substringBefore(':').toLong() to
                                    it.substringAfter(": ").split(' ').map { it.toLong() }
                            }
                    val result =
                        input
                            .map { it.first to calculate(it.second.first(), it.second.drop(1), listOf(add, multiply, concat)) }
                            .filter { it.first in it.second }
                            .sumOf { it.first }

                    Then("Task 2: $result should be $expected") {
                        result shouldBe expected
                    }
                }
            }
        }
    })

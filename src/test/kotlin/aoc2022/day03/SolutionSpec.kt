package aoc2022.day03

import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("a list of strings") {
            forAll(
                row("vJrwpWtwJgWrhcsFMMfFFhFp", 'p'),
                row("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL", 'L'),
                row("PmmdzqPrVvPwwTWBwg", 'P'),
                row("wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn", 'v'),
                row("ttgJtRGJQctTZtZT", 't'),
                row("CrZsJsPPZsGzwwsLwLmpwMDw", 's'),
            ) { string, expected ->
                When("finding error for $string") {
                    val result = string.splitAtIndex(string.length / 2).findCommonChar()

                    Then("$result -> $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

        Given("a list of chars") {
            forAll(
                row('p', 16),
                row('L', 38),
                row('P', 42),
                row('v', 22),
                row('t', 20),
                row('s', 19),
            ) { char, expected ->
                When("finding error for $char") {
                    val result = char.toValue()

                    Then("$result -> $expected") {
                        result shouldBe expected
                    }
                }
            }
        }

        Given("test input for task 1") {
            forAll(
                row("aoc2022/Day03TestInput1.txt", 157),
            ) { filename, expected ->
                When(filename) {
                    val result =
                        filename
                            .filePathToStringList()
                            .sumOf { it.splitAtIndex(it.length / 2).findCommonChar().toValue() }

                    Then("$result -> $expected") {
                        result shouldBe expected
                    }
                }
            }
        }
    })

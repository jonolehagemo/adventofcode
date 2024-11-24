package aoc2022.day25

import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("a list of decimal and SNAFU") {
            forAll(
                row(1, "1"),
                row(2, "2"),
                row(3, "1="),
                row(4, "1-"),
                row(5, "10"),
                row(6, "11"),
                row(7, "12"),
                row(8, "2="),
                row(9, "2-"),
                row(10, "20"),
                row(15, "1=0"),
                row(20, "1-0"),
                row(2022, "1=11-2"),
                row(12345, "1-0---0"),
                row(314159265, "1121-1110-1=0"),
            ) { decimal, snafu ->
                When("converting from decimal ($decimal) to SNAFU") {
                    val calculatedSnafu = decimal.toLong().toSnafu()

                    Then("calculated SNAFU ($calculatedSnafu) should be the expected SNAFU ($snafu)") {
                        calculatedSnafu shouldBe snafu
                    }
                }

                When("converting from SNAFU ($snafu) to expected decimal ($decimal)") {
                    val calculatedDecimal = snafu.toDecimal()

                    Then("calculated decimal ($calculatedDecimal) should be the expected decimal ($decimal)") {
                        calculatedDecimal shouldBe decimal
                    }
                }
            }
        }

        Given("a list of SNAFU and decimal") {
            forAll(
                row("2=-01", 976),
                row("1=-0-2", 1747),
                row("12111", 906),
                row("2=0=", 198),
                row("21", 11),
                row("2=01", 201),
                row("111", 31),
                row("20012", 1257),
                row("112", 32),
                row("1=-1=", 353),
                row("1-12", 107),
                row("12", 7),
                row("1=", 3),
                row("122", 37),
            ) { snafu, expectedDecimal ->
                When("converting from SNAFU ($snafu) to decimal") {
                    val decimal = snafu.toDecimal()

                    Then("decimal ($decimal) should be the expected decimal ($expectedDecimal)") {
                        decimal shouldBe expectedDecimal
                    }
                }
            }
        }

        Given("filepath to input") {
            forAll(
                row("aoc2022/Day25TestInput1.txt", 4890L, "2=-1=0"),
                row("aoc2022/Day25Input.txt", 29694520452605L, "2=-0=01----22-0-1-10"),
            ) { filepath, task1ExpectedDecimal, task1ExpectedSnafu ->
                When("filepath is $filepath") {
                    val input = filepath.filePathToStringList()
                    val resultDecimal = input.sumOf { it.toDecimal() }
                    val resultSnafu = resultDecimal.toSnafu()

                    Then("the decimal sum should be $task1ExpectedDecimal") {
                        resultDecimal shouldBe task1ExpectedDecimal
                    }

                    Then("the SNAFU sum should be $task1ExpectedSnafu") {
                        resultSnafu shouldBe task1ExpectedSnafu
                    }
                }
            }
        }
    })

package aoc2022.day02

import extensions.filePathToStringList
import extensions.println
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

@OptIn(ExperimentalKotest::class)
class SolutionSpec :
    BehaviorSpec(
        {
            Given("A char -> toRps") {
                forAll(
                    row('X', 'A'),
                    row('Y', 'B'),
                    row('Z', 'C'),
                ) { char, expected ->
                    When("$char -> $expected") {
                        val result = char.toRps()
                        Then("$result should be $expected") {
                            result shouldBe expected
                        }
                    }
                }
            }

            Given("A char -> toValue") {
                forAll(
                    row('A', 1),
                    row('B', 2),
                    row('C', 3),
                ) { char, expected ->
                    When("$char -> $expected") {
                        val result = char.toValue()
                        Then("$result should be $expected") {
                            result shouldBe expected
                        }
                    }
                }
            }

            Given("A char -> toResult") {
                forAll(
                    row('X', 0),
                    row('Y', 3),
                    row('Z', 6),
                ) { char, expected ->
                    When("$char -> $expected") {
                        val result = char.toResult()
                        Then("$result should be $expected") {
                            result shouldBe expected
                        }
                    }
                }
            }

            Given("rounds in task 1") {
                forAll(
                    row("A X", 4), // 1 to 3
                    row("A Y", 8), // 2 to 6
                    row("A Z", 3), // 3 to 0
                    row("B X", 1), // 1 to 0
                    row("B Y", 5), // 2 to 3
                    row("B Z", 9), // 3 to 6
                    row("C X", 7), // 1 to 6
                    row("C Y", 2), // 2 to 0
                    row("C Z", 6), // 3 to 3
                ) { round, expected ->
                    Then(round) {
                        round
                            .also { it.println() }
                            .also { it.println() }
                            .let { round ->
                                combinations()
                                    .first { round == it.round }
                                    .also { it.println() }
                                    .let { it.me.toValue() + it.result.toResult() }
                                    .also { it.println() } shouldBe expected
                            }
                    }
                }
            }

            Given("processing of test input for task 1") {
                forAll(
                    row("aoc2022/Day02TestInput1.txt", 15),
                    row("aoc2022/Day02TestInput2.txt", 45),
                    row("aoc2022/Day02Input.txt", 9177),
                ) { filepath, expected ->
                    Then(filepath) {
                        filepath
                            .filePathToStringList()
                            .map { s ->
                                combinations().first {
                                    s.first() == it.opponent && s.last().toRps() == it.me
                                }
                            }.sumOf { it.me.toValue() + it.result.toResult() } shouldBe expected
                    }
                }
            }

            Given("processing of test input for task 1 OLD") {
                forAll(
                    row("aoc2022/Day02TestInput1.txt", 15),
                    row("aoc2022/Day02TestInput2.txt", 45),
                    row("aoc2022/Day02Input.txt", 9177),
                ) { filepath, expected ->
                    Then(filepath) {
                        filepath
                            .filePathToStringList()
                            .map { round ->
                                combinations().first {
                                    round.first() == it.opponent && round.last().toRps() == it.me
                                }
                            }.sumOf { it.me.toValue() + it.result.toResult() } shouldBe expected
                    }
                }
            }

            Given("processing of test input for task 2") {
                forAll(
                    row("aoc2022/Day02TestInput1.txt", 12),
                    //            row("aoc2022/Day02TestInput2.txt", 45),
                    //            row("aoc2022/Day02Input.txt", 9177),
                ) { filepath, expected ->
                    Then(filepath) {
                        filepath
                            .filePathToStringList()
                            .map { round ->
                                combinations().first {
                                    round.first() == it.opponent && round.last() == it.result
                                }
                            }.sumOf { it.me.toValue() + it.result.toResult() }
                            .also { it.println() } shouldBe expected
                    }
                }
            }
        },
    )

/*
package aoc2022.day02

import extensions.filePathToStringList
import extensions.println

data class Combination(
    val round: String,
    val opponent: Char,
    val me: Char,
    val result: Char,
)

fun combinations(): List<Combination> =
    listOf(
        // Losses
        Combination("A Y", 'A', 'B', 'X'),
        Combination("B Z", 'B', 'C', 'X'),
        Combination("C X", 'C', 'A', 'X'),
        // Draws
        Combination("A X", 'A', 'A', 'Y'),
        Combination("B Y", 'B', 'B', 'Y'),
        Combination("C Z", 'C', 'C', 'Y'),
        // Wins
        Combination("A Z", 'A', 'C', 'Z'),
        Combination("B X", 'B', 'A', 'Z'),
        Combination("C Y", 'C', 'B', 'Z'),
    )

fun Char.toRps(): Char = if (this.code > 'C'.code) (this.code - 'X'.code + 'A'.code).toChar() else this

fun Char.toValue(): Int = this.code - 'A'.code + 1

fun Char.toResult(): Int = (this.code - 'X'.code) * 3

fun String.toPointsPairs1x(): Int =
    when (this) {
        "A X" -> 4 // 0 to 3 // 1 to 3
        "A Y" -> 4 // 3 to 1 // 1 to 6
        "A Z" -> 8 // 6 to 2 // 1 to 0
        "B X" -> 1 // 0 to 1 // 2 to 0
        "B Y" -> 5 // 3 to 2 // 2 to 3
        "B Z" -> 9 // 6 to 3 // 2 to 6
        "C X" -> 3 // 0 to 3 // 3 to 6
        "C Y" -> 4 // 3 to 1 // 3 to 0
        "C Z" -> 8 // 6 to 2 // 3 to 3

        else -> 0
    }

fun main() {
    "aoc2022/Day02Input.txt"
        .filePathToStringList()
        .sumOf { it.toPointsPairs1x() }
        .println()

//    "aoc2022/Day02Input.txt"
//        .filePathToStringList()
//        .sumOf { it.toPointsPairs2() }
//        .println()
}

* */

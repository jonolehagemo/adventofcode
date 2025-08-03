package aoc2024.day22

import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("a Long") {
        When("mixing 42L with 15L") {
            val long = 42L
            val other = 15L
            val result = long.mix(other)

            Then("the result should be 37L") {
                result shouldBe 37
            }
        }

        When("pruning a Long of 100_000_000") {
            val long = 100_000_000L
            val result = long.prune()

            Then("the result should be 16113920") {
                result shouldBe 16113920
            }
        }
    }

    Given("secret number 123") {
        val secretNumber = 123L

        When("calculating the next 10 secret numbers") {
            val expected = listOf(
                15887950L,
                16495136L,
                527345L,
                704524L,
                1553684L,
                12683156L,
                11100544L,
                12249484L,
                7753432L,
                5908254L
            )
            val result = secretNumber.generateSecrets(10)

            Then("they should be as expected") {
                result shouldBe expected
            }
        }

        When("getting the last digit") {
            val expected = 3L
            val result = secretNumber.lastDigit()

            Then("result ($result) should be as expected ($expected)") {
                result shouldBe expected
            }
        }

        When("find price changes") {
            val expected = listOf(-1L, -1L, 0L, 2L)
            val priceChanges = secretNumber.findPriceChanges(10)
            val result = priceChanges
                .sortedByDescending { it.second }
                .first().first

            Then("result should be as expected") {
                result shouldBe expected
            }

        }
    }

    Given("task 1") {
        forAll(
            row("aoc2024/Day22TestInput1.txt", 37327623),
            row("aoc2024/Day22Input.txt", 13461553007L),
        ) { filepath, expected ->
            When(filepath) {
                val input =
                    filepath.filePathToStringList().map { it.toLong() }
                val result =
                    input.sumOf { it.generateSecrets(2000).last() }

                Then("Task 1: $result should be $expected") {
                    result shouldBe expected
                }
            }
        }
    }

    Given("task 2") {
        forAll(
            row("aoc2024/Day22TestInput2.txt", 23),
            row("aoc2024/Day22Input.txt", 1499),
        ) { filepath, expected ->
            When(filepath) {
                val input =
                    filepath.filePathToStringList().map { it.toLong() }
                val result =
                    input
                        .flatMap { long ->
                            long.findPriceChanges(2000)
                                .sortedByDescending { it.second }
                        }
                        .groupBy({ it.first }, { it.second })
                        .mapValues { it.value.sum() }
                        .map { it.value to it.key }
                        .sortedByDescending { it.first }
                        .first()
                        .first

                Then("Task 2: $result should be $expected") {
                    result shouldBe expected
                }
            }
        }
    }
})

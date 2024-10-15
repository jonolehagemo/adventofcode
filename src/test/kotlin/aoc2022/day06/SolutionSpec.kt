package aoc2022.day06

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("test input") {
            forAll(
                row("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 7),
                row("bvwbjplbgvbhsrlpgdmjqwftvncz", 5),
                row("nppdvjthqldpwncqszvftbrmjlhg", 6),
                row("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 10),
                row("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 11),
            ) { input, expected ->
                When("$input -> $expected") {
                    val result = input.marker(4)

                    Then("$result -> $expected") {
                        result shouldBe expected
                    }
                }
            }
        }
    })

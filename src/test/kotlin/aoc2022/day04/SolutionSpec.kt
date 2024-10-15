package aoc2022.day04

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec :
    BehaviorSpec({
        Given("ranges") {
            forAll(
                row(IntRange(2, 4), IntRange(6, 8), false, false),
                row(IntRange(2, 3), IntRange(4, 5), false, false),
                row(IntRange(5, 7), IntRange(7, 9), false, true),
                row(IntRange(2, 8), IntRange(3, 7), true, true),
                row(IntRange(6, 6), IntRange(4, 6), true, true),
                row(IntRange(2, 6), IntRange(4, 8), false, true),
            ) { range1, range2, expected1, expected2 ->
                When("full overlap with range1 = $range1 and range2 = $range2 -> $expected1") {
                    val result = range1.fullOverlap(range2)

                    Then("$result -> $expected1") {
                        result shouldBe expected1
                    }
                }

                When("partial overlap with range1 = $range1 and range2 = $range2 -> $expected2") {
                    val result = range1.partialOverlap(range2)

                    Then("$result -> $expected2") {
                        result shouldBe expected2
                    }
                }
            }
        }
    })

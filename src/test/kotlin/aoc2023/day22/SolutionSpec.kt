package aoc2023.day22

import aoc2023.day20.process1
import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.collections.shouldBeSameSizeAs
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class SolutionSpec : BehaviorSpec({
    Given("Part 1, a list of Bricks") {
        val bricks = "aoc2023/Day22TestInput1.txt".filePathToStringList().toBricks()

        When("shuffling and sorting") {
            val shuffled = bricks.shuffled()
            val sorted = shuffled.sorted()

            Then("bricks should be same size as shuffled") {
                bricks shouldBeSameSizeAs shuffled
            }

            Then("bricks should not be equal to shuffled") {
                bricks shouldNotBe shuffled
            }

            Then("shuffled should not be equal to sorted") {
                shuffled shouldNotBe sorted
            }

            Then("bricks should be equal to sorted") {
                bricks shouldBe sorted
            }
        }
    }

//    Given("Part 1, results") {
//        forAll(
//            row("aoc2023/Day22TestInput1.txt", 5),
//            row("aoc2023/Day22Input.txt", 1_000),
//        ) { filePath: String, expectedResult: Int ->
//            When("calculating the number bricks that can be disintegrated safely for $filePath") {
//                val result = filePath.filePathToStringList().toBricks().countSafe()
//
//
//                Then("the result ($result) should be as expected ($expectedResult)") {
//                    result shouldBe expectedResult
//                }
//            }
//        }    }
//
})
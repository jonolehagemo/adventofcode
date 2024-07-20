package day12

import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("a list of input strings") {
        val sut = listOf(".# 1").toRecords()

        When("times(5)") {
            val result = sut.times(5)
            val expectedResult = listOf(Record(".#?.#?.#?.#?.#", listOf(1, 1, 1, 1, 1)))

            Then("the result should be as expected") {
                result shouldBe expectedResult
            }
        }
    }

    Given("file paths to test input files") {
        forAll(
            row("Day12TestInput1.txt", 1L),
            row("Day12TestInput2.txt", 10L),
            row("Day12ProdInput.txt", 4L),
        ){ filePath, expectedArrangements ->
            When(filePath) {
                val result = filePath.filePathToStringList().toRecords().takeLast(1).sumOf { it.individual() }

                Then("the result should be as expected") {
                    result shouldBe expectedArrangements
                }
            }
        }
    }

    Given("a list of records") {
        forAll(
            row(Record("???.###", listOf(1,1,3)), false),
            row(Record("#.#.###", listOf(1,1,3)), true),
            row(Record("#.#.#.#", listOf(1,1,3)), false),
        ){ record, expectedResult ->
            When(record.toString()) {
                val result = record.isValid()

                Then("the result should be as expected") {
                    result shouldBe expectedResult
                }
            }
        }
    }

})
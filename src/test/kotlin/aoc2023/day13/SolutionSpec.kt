package aoc2023.day13

import extensions.filePathToListOfStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("file paths to test input files") {
        forAll(
            row("aoc2023/Day13TestInput1.txt", 405),
        ) { filePath, expectedSum
            ->
            When(filePath) {
                val input = filePath.filePathToListOfStringList()

                val result = input
                    .sumOf {
                        it.patternSum()
                    }

                Then("the resultSum result should be as expected") {
                    result shouldBe expectedSum
                }
            }
        }
    }

})

/*
#.##..##.
..##..##.
#.#.##.#.
 */
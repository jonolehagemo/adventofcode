package aoc2023.day25

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({

    Given("valid input") {
        forAll(
            row("Test input", "aoc2023/Day25TestInput1.txt", 15),
        ) { description, filePath, expected ->
            When(description) {
                val graph = getInput(filePath)

                Then("graph should contain $expected nodes") {
                    graph.adjacencyList.keys.size shouldBe expected
                }
            }
        }
    }

    Given("process") {
        forAll(
            row("Test input", "aoc2023/Day25TestInput1.txt", 54),
        ) { description, filePath, expected ->
            When(description) {
                val graph = getInput(filePath)
                val result = process(graph)

                Then("result should be $expected") {
                    result shouldBe expected
                }
            }
        }
    }

})

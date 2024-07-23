package day14

import extensions.filePathToStringList
import extensions.rotateLeft
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("file path to test input file") {
        val input = "Day14TestInput1.txt"
            .filePathToStringList()
            .rotateLeft()

        When("getting result for part 1") {
            val result = input
                .sumOf { line ->
                    line
                        .split('#')
                        .joinToString("#") { it.split("").sortedDescending().joinToString("") }
                        .withIndex()
                        .filter { (_, char) -> char == 'O' }
                        .sumOf { (index, _) -> line.length - index }
                }

            Then("the result should be as expected") {
                result shouldBe 136
            }
        }

        When("getting result for part 2") {
            val cycles = 1_000_000_000L
            val result = input.findCycle(cycles).sum()

            Then("the result should be as expected") {
                result shouldBe 64
            }
        }
    }
})
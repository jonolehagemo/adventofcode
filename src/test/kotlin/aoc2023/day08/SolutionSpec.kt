package aoc2023.day08

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({

    Given("process1 of test input") {
        forAll(
            row("aoc2023/Day08TestInput1.txt", 2),
            row("aoc2023/Day08TestInput2.txt", 6),
        ) { filePath, expected ->
            When("filePath = $filePath") {
                val instructions = getInstructions(filePath)
                val network = getNetwork(filePath)

                Then("result should be $expected") {
                    countSteps(instructions, network, "AAA") shouldBe expected
                }
            }
        }
    }

    Given("processing instructions") {
        val instructions = "LLR"

        When("steps = 0") {
            val steps = 0
            val instruction = instructions[steps % instructions.length]
            Then("instruction should be L") {
                instruction shouldBe 'L'
            }
        }

        When("steps = 5") {
            val steps = 5
            val instruction = instructions[steps % instructions.length]

            Then("instruction should be R") {
                instruction shouldBe 'R'
            }
        }


    }

})
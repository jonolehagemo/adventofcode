package aoc2023.day13

import extensions.filePathToListOfStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("test patterns") {
        val pattern1 = """#.##..##.
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#.""".trimIndent()

        val pattern2 = """#...##..#
#....#..#
..##..###
#####.##.
#####.##.
..##..###
#....#..#""".trimIndent()

        forAll(
            row("pattern1", pattern1, 0, 5),
            row("pattern2", pattern2, 0, 400),
            row("pattern1", pattern1, 1, 300),
            row("pattern2", pattern2, 1, 100),
        ) { description, pattern, difference, expectedSum
            ->
            When("$description: diff = $difference -> $expectedSum") {
                val result = pattern.split("\n").patternSum(difference)

                Then("the resultSum result should be as expected") {
                    result shouldBe expectedSum
                }
            }
        }
    }

    Given("file paths to test input files") {
        forAll(
            row("aoc2023/Day13TestInput1.txt", 0, 405),
            row("aoc2023/Day13Input.txt", 0, 39939),
        ) { filePath, difference, expectedSum
            ->
            When(filePath) {
                val input = filePath.filePathToListOfStringList()

                val result = input
                    .sumOf {
                        it.patternSum(difference)
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
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#.
*/

/*
#.##..##.
..##..##.
#.#.##.#.
 */
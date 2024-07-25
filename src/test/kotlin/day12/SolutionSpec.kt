package day12

import extensions.filePathToStringList
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class SolutionSpec : BehaviorSpec({
    Given("file paths to test input files") {
        forAll(
            row("Day12TestInput1.txt", 1L),
            row("Day12TestInput2.txt", 10L),
//            row("Day12Input.txt", 4L),
        ) { filePath, expectedArrangements ->
            When(filePath) {
                val result = filePath
                    .filePathToStringList()
                    .map { it.toCondition() }
                    .takeLast(1)
                    .sumOf { dfs('.' + it.first + '.', it.second) }

                Then("the result should be as expected") {
                    result shouldBe expectedArrangements
                }
            }
        }
    }

    Given("a list of records") {
        forAll(
            row("#.#.###", listOf(1, 1, 3), 1L),
            row("", listOf(), 1L),
            row("#", listOf(), 0L),
            row("#", listOf(1), 1L),
            row("", listOf(1), 0L),
            row("?", listOf(1), 1L),
            row("?.", listOf(1), 1L),
            row(".?", listOf(1), 1L),
            row(".?.", listOf(1), 1L),
            row("#?#", listOf(1, 1), 1L),
            row("???", listOf(1, 1), 1L),

            row("???.###", listOf(1, 1, 3), 1L),
            row("??.??", listOf(1, 1), 4L),
            row(".??..??...?##.", listOf(1, 1, 3), 4L),
            row("?#?#?#?#?#?#?#?", listOf(1, 3, 1, 6), 1L),
            row("????.#...#...", listOf(4, 1, 1), 1L),
            row("????.######..#####.", listOf(1, 6, 5), 4L),
            row("?###????????", listOf(3, 2, 1), 10L),
        ) { condition, numbers, expectedResult ->
            When("'$condition' -> $numbers") {
                val result = dfs(".$condition.", numbers)

                Then("the result should be $expectedResult") {
                    result shouldBe expectedResult
                }
            }
        }
    }
})